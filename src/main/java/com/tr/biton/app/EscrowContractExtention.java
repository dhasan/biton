package com.tr.biton.app;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionInput;
import org.bitcoinj.core.TransactionOutput;
import org.bitcoinj.core.TransactionConfidence.ConfidenceType;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.WalletExtension;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.tr.biton.interfaces.EscrowContractObserver;

public class EscrowContractExtention implements WalletExtension{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final Logger logger = LoggerFactory.getLogger(EscrowContractExtention.class);
	
	private transient Wallet containingWallet;
	private transient PeerGroup peers;
	private transient Context context;
	private transient NetworkParameters params;
	private transient BlockChain chain;
	public BlockChain getChain() {
		return chain;
	}

	public void setChain(BlockChain chain) {
		this.chain = chain;
	}

	private int conformations;
	
	public PeerGroup getPeers() {
		return peers;
	}

	public void setPeers(PeerGroup peers) {
		this.peers = peers;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public NetworkParameters getParams() {
		return params;
	}

	public void setParams(NetworkParameters params) {
		this.params = params;
	}

	public int getConformations() {
		return conformations;
	}

	public void setConformations(int conformations) {
		this.conformations = conformations;
	}

	HashMap<String, EscrowContractEntry> entries;
	
	public HashMap<String, EscrowContractEntry> getEntries() {
		return entries;
	}

	public void setEntries(HashMap<String, EscrowContractEntry> entries) {
		this.entries = entries;
	}

	public void addListeners(Wallet containingWallet, 
			final Context context, 
			NetworkParameters params, 
			final BlockChain chain,
			PeerGroup peers,
			int confor){
		this.containingWallet = containingWallet;
		this.peers = peers;
		this.context = context;
		this.params = params;
		this.conformations = confor;
		this.chain = chain;
		Context.propagate(context);
		
		entries = new HashMap<String, EscrowContractEntry>();
		
		containingWallet.addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener(){
			private HashMap<String, EscrowContractEntry> entries;
			private NetworkParameters params;
			private int conformations;
			
			
			private void broadcast(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance){
				EscrowContractEntry entry;
				Coin amount;
				EscrowContractObserver obs;
				amount = newBalance.subtract(prevBalance);
				Address adr=null;
				for (TransactionOutput txo : tx.getOutputs()) {
					//logger.info("loop...........");
				    //System.out.println(item);
					if (txo.getAddressFromP2PKHScript(params)!=null){
						adr = txo.getAddressFromP2PKHScript(params);
					}else if (txo.getAddressFromP2SH(params)!=null){
						adr = txo.getAddressFromP2SH(params);
					}
					entry = entries.get(adr.toString());
					
					if (entry!=null){
						if ((tx.getConfidence().getConfidenceType()==ConfidenceType.BUILDING) ||
								(tx.getConfidence().getConfidenceType()==ConfidenceType.PENDING)){
							entry.addTransaction(tx);
							entry.addBalance(amount);
							
							logger.info(BitCoin.byteArrayToHex(tx.bitcoinSerialize()));
							obs = entry.getObserverbs();
							if (obs!=null)
								obs.Funded(adr.toString(), amount, newBalance);
						}else{ //corrupted
							//todo check if tx exist
							entry.removeTransaction(tx);
							entry.subtractBalance(amount);
							obs = entry.getObserverbs();
//							if (obs!=null)
//								obs.Corrupt(tx);
						}
					}
				}
			}
			
			public void onCoinsReceived(final Wallet wallet, final Transaction tx, final Coin prevBalance, final Coin newBalance) {
				// TODO Auto-generated method stub
				Context.propagate(context);
						
				if (conformations<0){
					broadcast(wallet,tx,prevBalance, newBalance);
				}else{
				//	logger.debug("Receive conformatitons: "+conformations);
					Futures.addCallback(tx.getConfidence().getDepthFuture(conformations), new FutureCallback<TransactionConfidence>() {

		                public void onSuccess(TransactionConfidence result) {
		                	//logger.debug("broadcasting..............: ");
		                	broadcast(wallet,tx,prevBalance, newBalance);
		                }
	
		                public void onFailure(Throwable t) {
		                    throw new RuntimeException(t);
		                    //TODO: send Error
		                }
		            });
				}	
				
			}
			
			public WalletCoinsReceivedEventListener init(HashMap<String, EscrowContractEntry> entries, NetworkParameters params, int conf){
				this.entries = entries;
				this.params = params;
				this.conformations = conf;
				
				return this;
			}
			
		}.init(entries,params,confor));
		
		peers.addWallet(containingWallet);
		chain.addWallet(containingWallet);
	}
	
	public void createSpend(String contract, String toaddress, Coin amount){
		Transaction spendTx = new Transaction(params);
		EscrowContractEntry entry = entries.get(contract);
		//HashSet<byte[]> txset = entry.getTransactions();
		//Sha256Hash sha256tx;
		ScriptBuilder scriptBuilder = new ScriptBuilder();
		boolean found = false;
		TransactionInput input;
		Transaction selectedtx = null;
		int outid=0;
		Context.propagate(context);
		for (Transaction tx : entry.getTransactions()){
			//sha256tx = Sha256Hash.wrap(txraw);
			
			for (TransactionOutput txo : tx.getOutputs()){
				if (   	!(txo.getAddressFromP2PKHScript(params).toString().equals(contract)) &&
						!(txo.getAddressFromP2SH(params).toString().equals(contract))){
					continue;
				}
				
				if (txo.getAddressFromP2PKHScript(params).toString().equals(contract)){
					if (txo.isAvailableForSpending()){
						if (txo.getValue().isGreaterThan(amount)){
							scriptBuilder.data(txo.getScriptBytes()); //
							selectedtx = tx;
							outid = txo.getIndex();
							found = true;
							break;
						}
					}
				}
				if (txo.getAddressFromP2SH(params).toString().equals(contract)){
					if (txo.isAvailableForSpending()){
						if (txo.getValue().isGreaterThan(amount)){ //TTODO: Check for fee
							scriptBuilder.data(txo.getScriptBytes()); //
							selectedtx = tx;
							outid = txo.getIndex();
							found = true;
							break;
						}
					}
				}
			}
			if (found == true)
				break;
		}
		
		if (found==true){
			input = spendTx.addInput(selectedtx.getHash(), outid, scriptBuilder.build());
			
			// Add outputs to the person receiving bitcoins

			Address receiverAddress = Address.fromBase58(params, toaddress);
			Coin charge = Coin.valueOf(10000); // 0.1 mBTC
			Script outputScript = ScriptBuilder.createOutputScript(receiverAddress);

	        spendTx.addOutput(charge, outputScript);
	        
		}
		
	}
	
	public void createSpends(HashMap<String, Coin> spends){
		
	}
	
	public void addEscrowContractObserver(Address address, EscrowContractObserver obs){
		Context.propagate(context);
		EscrowContractEntry entry = entries.get(address.toString());
		entry.setObserver(obs);

	}
	
	public Address createEscrowContract(ECKey escrow, ECKey buyer, ECKey seller){
		Context.propagate(context);

		List<ECKey> keys = ImmutableList.of(escrow, buyer, seller);
	
		Script redeemScript = ScriptBuilder.createRedeemScript(2, keys);
		Script script = ScriptBuilder.createP2SHOutputScript(redeemScript);
		//logger.info("Redeem script: " + byteArrayToHex(redeemScript.getProgram()));
		
		Address multisig = Address.fromP2SHScript(params, script);
		EscrowContractEntry entry = new EscrowContractEntry();
		entry.addKey(escrow, EscrowContractEntry.ESCROW);
		entry.addKey(buyer, EscrowContractEntry.BUYER);
		entry.addKey(buyer, EscrowContractEntry.SELLER);
		entry.setScript(script.getProgram());
		entry.setRedeemScript(redeemScript.getProgram());
		entry.setMultisigAddress(multisig);
		//entry.setHash160(multisig.getHash160());
		entries.put(multisig.toString(), entry);
		
		List<Script> scripts = new ArrayList<Script>();
		scripts.add(script);
		containingWallet.addWatchedScripts(scripts);
		
		//containingWallet.addWatchedAddress(new Address(params,multisig.getHash160()));
		
		return  multisig;
				
	}

	public String getWalletExtensionID() {
		// TODO Auto-generated method stub
		return "com.tr.biton.app.EscrowContractExtention";
	}

	public boolean isWalletExtensionMandatory() {
		// TODO Auto-generated method stub
		return false;
	}

	public byte[] serializeWalletExtension() {
		// TODO Auto-generated method stub
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(fos);
			Transaction tt = new Transaction(params);
			oos.writeObject(tt);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
		return fos.toByteArray();
	}

	public void deserializeWalletExtension(Wallet containingWallet, byte[] data) throws Exception {
		// TODO Auto-generated method stub
			
	}
	
	public String toString(){
		List<EscrowContractEntry> entrieslist =
			    new ArrayList<EscrowContractEntry>(entries.values());
		String str=new String();
		Coin totalamount = Coin.ZERO;
		str +="\n";
		str +=StringUtils.repeat("-",58) +"\n";
		str +="| "+StringUtils.leftPad("Escrow Address", 40)+" | "+StringUtils.leftPad("Balance", 11)+" |"+"\n";
		str +=StringUtils.repeat("-",58) + "\n";
		for (EscrowContractEntry ent: entrieslist){
			str +="| "+StringUtils.leftPad(ent.getMultisigAddress().toString(), 40) +" | "+StringUtils.leftPad(ent.getBalance().toFriendlyString(), 11)+" |"+"\n";
			totalamount.add(ent.getBalance());
		}
		str+=StringUtils.repeat("-",58) + "\n";
		str +="| "+StringUtils.leftPad("Total: "+Integer.toString(entries.size()), 40) +" | "+StringUtils.leftPad(totalamount.toFriendlyString(), 11)+" |"+"\n";
		str+=StringUtils.repeat("-",58) + "\n";
		return str;
	}
	
	public String toString(Address address){
		EscrowContractEntry ent = entries.get(address.toString());
		String str=new String();
		Coin send,receive;
		//Transaction tx;
		
		str +="\n";
		str +=StringUtils.repeat("-",122) +"\n";
		str +="| "+StringUtils.leftPad(new String("Address: "+ent.getMultisigAddress().toString()), 118)+" |\n";
		str +=StringUtils.repeat("-",122) +"\n";
		str +="| "+StringUtils.leftPad("Transactions", 64)+" | "+StringUtils.leftPad("Receive", 16)+" | "+StringUtils.leftPad("Send", 16)+" | "+StringUtils.leftPad("Conf", 13) + " |\n";
		str +=StringUtils.repeat("-",122) +"\n";
		int sendcnt=0;
		int reccnt =0;
		Coin sendsum= Coin.valueOf(0), receivesum =Coin.valueOf(0);
		for(Transaction tx: ent.getTransactions()){
			send = Coin.valueOf(0);//.ZERO;
			receive = Coin.valueOf(0);
			//tx = containingWallet.getTransaction(Sha256Hash.wrap(txbytes));
			sendcnt = 0;reccnt=0;
			for(TransactionOutput txo: tx.getOutputs()){
//				if 	( 
//						(!(txo.getAddressFromP2PKHScript(params).toString().equals(address.toString()))) && 
//						(!(txo.getAddressFromP2SH(params).toString().equals(address.toString()))) 
//					){ continue;}
				
				if (txo.getAddressFromP2PKHScript(params)!=null){
					if ((txo.getAddressFromP2PKHScript(params).toString().equals(address.toString()))){
						
						receive = receive.add(txo.getValue());
						receivesum = receivesum.add(txo.getValue());
						reccnt++;
					}
				
				}else if (txo.getAddressFromP2SH(params)!=null){
					if ((txo.getAddressFromP2SH(params).toString().equals(address.toString()))){
						
						receive = receive.add(txo.getValue());
						receivesum = receivesum.add(txo.getValue());
						reccnt++;
						
					}
				}
				
				
			}
			for(TransactionInput txi: tx.getInputs()){
//				if 	( 
//						(!txi.getOutpoint().getConnectedOutput().getAddressFromP2PKHScript(params).toString().equals(address.toString())) &&
//						(!txi.getOutpoint().getConnectedOutput().getAddressFromP2SH(params).toString().equals(address.toString()))
//					){ continue;}
				if (txi.getOutpoint().getConnectedOutput()!=null){
					if (txi.getOutpoint().getConnectedOutput().getAddressFromP2PKHScript(params)!=null){
						if (txi.getOutpoint().getConnectedOutput().getAddressFromP2PKHScript(params).toString().equals(address.toString())){
							send = send.add(txi.getValue());
							sendsum = sendsum.add(txi.getValue());
							sendcnt++;
						}
					}else if (txi.getOutpoint().getConnectedOutput().getAddressFromP2SH(params)!=null){
						if (txi.getOutpoint().getConnectedOutput().getAddressFromP2SH(params).toString().equals(address.toString())){
							send = send.add(txi.getValue());
							sendsum = sendsum.add(txi.getValue());
							sendcnt++;
						}
					}
				}
				
				
				
				
			}
			String co;
			if (tx.getConfidence().getConfidenceType()!=ConfidenceType.BUILDING)
				co = tx.getConfidence().getConfidenceType().toString();
			else
				co = Integer.toString(tx.getConfidence().getDepthInBlocks());
		
			
			String rst="",sst="";
			if (send.isGreaterThan(Coin.ZERO))
				sst = send.toFriendlyString() + " "+Integer.toString(sendcnt);
			if (receive.isGreaterThan(Coin.ZERO))
				rst = receive.toFriendlyString() +" "+Integer.toString(reccnt);
			
			str += "| "+StringUtils.leftPad(tx.getHashAsString(), 64)+" | "+StringUtils.leftPad(rst, 16)+" | "+StringUtils.leftPad(sst, 16) + " | "+StringUtils.leftPad(co, 13)+" |\n";
		}
		str +=StringUtils.repeat("-",122) +"\n";	
		str +="| "+StringUtils.leftPad("Address Balance: "+ent.getBalance().toFriendlyString(), 64)+" | "+StringUtils.leftPad(receivesum.toFriendlyString(), 16)+" | "+StringUtils.leftPad(sendsum.toFriendlyString(), 16)+" | "+StringUtils.leftPad(receivesum.subtract(sendsum).toFriendlyString(), 13) + " |\n";
		str +=StringUtils.repeat("-",122) +"\n";	
		return str;
	}
}
