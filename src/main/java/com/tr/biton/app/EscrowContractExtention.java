package com.tr.biton.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
	final Logger logger = LoggerFactory.getLogger(EscrowContractExtention.class);
	
	private Wallet containingWallet;
	private PeerGroup peers;
	private Context context;
	private NetworkParameters params;
	
	HashMap<String, EscrowContractEntry> entries;
	
	public EscrowContractExtention(Wallet containingWallet, 
			Context context, 
			NetworkParameters params, 
			BlockChain chain,
			PeerGroup peers){
		this.containingWallet = containingWallet;
		this.peers = peers;
		this.context = context;
		this.params = params;
		
		entries = new HashMap<String, EscrowContractEntry>();
		
		containingWallet.addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener(){
			private HashMap<String, EscrowContractEntry> entries;
			private NetworkParameters params;
			private int conformations;
			
			
			private void broadcast(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance){
				EscrowContractEntry entry;
				Coin amount;
				amount = newBalance.subtract(prevBalance);
				for (TransactionOutput txo : tx.getOutputs()) {
					
				    //System.out.println(item);
					if (txo.getAddressFromP2PKHScript(params)!=null){
						logger.info("Output address: "+txo.getAddressFromP2PKHScript(params).toString());
						entry = entries.get(txo.getAddressFromP2PKHScript(params).toString());
						if (entry!=null){
							entry.addTransaction(tx.getHash().getBytes());
							entry.addBalance(amount);
							entry.getObserverbs().Funded(txo.getAddressFromP2PKHScript(params).toString(), amount, newBalance);
						}
					}//TODO: use else if
					if (txo.getAddressFromP2SH(params)!=null){
						logger.debug("Output script address: "+txo.getAddressFromP2SH(params).toString());
						entry = entries.get(txo.getAddressFromP2SH(params).toString());
						if (entry!=null){
							entry.addTransaction(tx.getHash().getBytes());
							entry.addBalance(amount);
							entry.getObserverbs().Funded(txo.getAddressFromP2SH(params).toString(), amount, newBalance);
						
						}
					}
				}
			}
			
			public void onCoinsReceived(final Wallet wallet, final Transaction tx, final Coin prevBalance, final Coin newBalance) {
				// TODO Auto-generated method stub
				
				if (conformations==0){
					broadcast(wallet,tx,prevBalance, newBalance);
				}else{
					Futures.addCallback(tx.getConfidence().getDepthFuture(conformations), new FutureCallback<TransactionConfidence>() {

		                public void onSuccess(TransactionConfidence result) {
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
			
		}.init(entries,params,1));
		
		peers.addWallet(containingWallet);
		//chain.addWallet(containingWallet);
	}
	
	public void createSpend(String contract, String toaddress, Coin amount){
		Transaction spendTx = new Transaction(params);
		EscrowContractEntry entry = entries.get(contract);
		HashSet<byte[]> txset = entry.getTransactions();
		Sha256Hash sha256tx;
		ScriptBuilder scriptBuilder = new ScriptBuilder();
		boolean found = false;
		TransactionInput input;
		byte[] selectedtx = null;
		int outid=0;
		for (byte[] txraw : txset){
			sha256tx = Sha256Hash.wrap(txraw);
			
			for (TransactionOutput txo : containingWallet.getTransaction(sha256tx).getOutputs()){
				if (   	!(txo.getAddressFromP2PKHScript(params).toString().equals(contract)) &&
						!(txo.getAddressFromP2SH(params).toString().equals(contract))){
					continue;
				}
				
				if (txo.getAddressFromP2PKHScript(params).toString().equals(contract)){
					if (txo.isAvailableForSpending()){
						if (txo.getValue().isGreaterThan(amount)){
							scriptBuilder.data(txo.getScriptBytes()); //
							selectedtx = txraw;
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
							selectedtx = txraw;
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
			input = spendTx.addInput(Sha256Hash.wrap(selectedtx), outid, scriptBuilder.build());
			
			// Add outputs to the person receiving bitcoins

			Address receiverAddress = Address.fromBase58(params, toaddress);
			Coin charge = Coin.valueOf(10000); // 0.1 mBTC
			Script outputScript = ScriptBuilder.createOutputScript(receiverAddress);

	        spendTx.addOutput(charge, outputScript);
	        
		}
		
	}
	
	public void createSpends(HashMap<String, Coin> spends){
		
	}
	
	public void addEscrowContractObserver(String address, EscrowContractObserver obs){
		EscrowContractEntry entry = entries.get(address);
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
		entry.setMultisigAddress(multisig.toString());
		entry.setHash160(multisig.getHash160());
		entries.put(multisig.toString(), entry);
		
		List<Script> scripts = new ArrayList<Script>();
		scripts.add(script);
		containingWallet.addWatchedScripts(scripts);
		
		//containingWallet.addWatchedAddress(new Address(params,multisig.getHash160()));
		
		return  multisig;
			
		//logger.debug("Multisig address: " + multisig.toString());
		//contractmap.put(MULTISIG_ADDRESS, multisig.toString());
		//contractmap.put(REDEEM_SCRIPT, byteArrayToHex(redeemScript.getProgram()));
		//contractmap.put(MULTISIG_HASH160, byteArrayToHex(multisig.getHash160()));
		//contractmap.put(SCRIPT, byteArrayToHex(script.getProgram()));
		
	}

	public String getWalletExtensionID() {
		// TODO Auto-generated method stub
		return "com.tr.biton.app.EscrowContractExtention";
	}

	public boolean isWalletExtensionMandatory() {
		// TODO Auto-generated method stub
		return true;
	}

	public byte[] serializeWalletExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deserializeWalletExtension(Wallet containingWallet, byte[] data) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
