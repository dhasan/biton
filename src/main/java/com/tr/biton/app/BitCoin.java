package com.tr.biton.app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.listeners.DownloadProgressTracker;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;


public class BitCoin {
	private NetworkParameters params;
	private BlockChain chain;
	private SPVBlockStore blockStore;
	private PeerGroup peers;
	
	private Context context;
		
	public static final Integer MULTISIG_ADDRESS = 	0;
	public static final Integer REDEEM_SCRIPT = 	1;
	public static final Integer MULTISIG_HASH160 = 	2;
	public static final Integer SCRIPT 	=			3;
	public static final Integer OUTPUT_SCRIPT 	=	4;
	public static final Integer FEEDING_TRANSACTION=5;
	
	final Logger logger = LoggerFactory.getLogger(BitCoin.class);
	
	public BlockChain getChain() {
		return chain;
	}

	public void setChain(BlockChain chain) {
		this.chain = chain;
	}

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

	
	public void removeWallet(Wallet w){
		Context.propagate(context);
		
		chain.removeWallet(w);
		peers.removeWallet(w);
	}
	
	public void addWallet(Wallet w){
		Context.propagate(context);
		
		chain.addWallet(w);
		peers.addWallet(w);
	}
	
	public Wallet restoreWallet(String seedcode, long creationtime){
		Context.propagate(context);
		
		DeterministicSeed seed;
		Wallet w = null;
		try {
			seed = new DeterministicSeed(seedcode, null, "", creationtime);
			w = Wallet.fromSeed(params, seed);
		} catch (UnreadableWalletException e) {
			
			logger.error("unable to restore a wallet");
			e.printStackTrace();
		}
		
		return w;
	}
	
	public String getWalletSeed(Wallet w){
		Context.propagate(context);
		
		DeterministicSeed seed = w.getKeyChainSeed();
		return Joiner.on(" ").join(seed.getMnemonicCode());
		
	}
	
	public void setContext(){
		Context.propagate(context);
	}
	
	public long getWalletDate(Wallet w){
		Context.propagate(context);
		
		DeterministicSeed seed = w.getKeyChainSeed();
		return seed.getCreationTimeSeconds();
	}
	
	public Wallet createWallet(){
		Context.propagate(context);
		
		Wallet w = new Wallet(params);
		return w;
	}
	public Wallet createSinglekeyWallet(){
		Context.propagate(context);
		
		List<ECKey> list = new ArrayList<ECKey>();
		ECKey key = new ECKey();
		list.add(key);
		Wallet w = Wallet.fromKeys(params, list);
		w.removeKey(key);
		return w;
	}
	
	public Wallet restoreSinglekeyWallet(byte[] prvkey){
		Context.propagate(context);
		
		ECKey key = ECKey.fromPrivate(prvkey);
		List<ECKey> list = new ArrayList<ECKey>();
		list.add(key);
		Wallet w = Wallet.fromKeys(params, list);
		return w;
	}
	
	public Wallet restoreSinglekeyWatchWallet(byte[] pub){
		Context.propagate(context);
		
		ECKey key = ECKey.fromPublicOnly(pub);
		List<ECKey> list = new ArrayList<ECKey>();
		list.add(key);
		Wallet w = Wallet.fromKeys(params, list);
		return w;
	}
	
	public HashMap<Integer,String>  createContract(byte[] escrow, byte[] buyer, byte[] seller){
		Context.propagate(context);
	
		HashMap<Integer, String> contractmap = new HashMap<Integer, String>();
		ECKey escrowpubkey = ECKey.fromPublicOnly(escrow);
		ECKey buyerpubkey = ECKey.fromPublicOnly(buyer);
		ECKey sellerpubkey = ECKey.fromPublicOnly(seller);
		
		List<ECKey> keys = ImmutableList.of(escrowpubkey, buyerpubkey, sellerpubkey);
		
		Script redeemScript = ScriptBuilder.createRedeemScript(2, keys);
		Script script = ScriptBuilder.createP2SHOutputScript(redeemScript);
		logger.info("Redeem script: " + byteArrayToHex(redeemScript.getProgram()));
		
		Address multisig = Address.fromP2SHScript(params, script);
			
		logger.debug("Multisig address: " + multisig.toString());
		contractmap.put(MULTISIG_ADDRESS, multisig.toString());
		contractmap.put(REDEEM_SCRIPT, byteArrayToHex(redeemScript.getProgram()));
		contractmap.put(MULTISIG_HASH160, byteArrayToHex(multisig.getHash160()));
		contractmap.put(SCRIPT, byteArrayToHex(script.getProgram()));
	
		return contractmap;	
		
	}
	
	static public byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	
	static public String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x", b & 0xff));
		   return sb.toString();
	}
	
	public void addScriptToWallet(String script, Wallet w){
		Context.propagate(context);
		//Wallet dummyw = new Wallet(params);
		
		List<Script> scripts = new ArrayList<Script>();
		Script sc = new Script(hexStringToByteArray(script));//, dummyw.getKeyChainSeed().getCreationTimeSeconds());
		scripts.add(sc);
		
		Context.propagate(context);
		w.addWatchedScripts(scripts);
		
	}
	
	public void addAddressToWallet(String hash160, Wallet w){
		Context.propagate(context);
		
		w.addWatchedAddress(new Address(params, hexStringToByteArray(hash160)));
	}

	public BitCoin(String chainfile, boolean testnet){
		String chainfileapp;
		
		if (testnet){
			params = TestNet3Params.get();
			chainfileapp = chainfile + "-test.spv";
		}else{
			params = MainNetParams.get();
			chainfileapp = chainfile + "-main.spv";
		}
		try{
			blockStore = new SPVBlockStore(params, new File(chainfileapp));
		} catch (BlockStoreException e) {
			logger.error("Can't istantiate SPV!");
			e.printStackTrace();
		}
		
		try {
			chain = new BlockChain(params, blockStore);
			peers = new PeerGroup(params,chain);
		} catch (BlockStoreException e) {
			logger.error("can't get chain from spv");
			e.printStackTrace();
		}

		peers.addPeerDiscovery(new DnsDiscovery(params));
		DownloadProgressTracker bListener = new DownloadProgressTracker() {
			
            @Override
            public void doneDownload() {
                logger.info("Blockchain downloaded");
            }
		};

		peers.start();
        peers.startBlockChainDownload(bListener);

        try {
			bListener.await();
		} catch (InterruptedException e) {
			logger.error("download was interrupted");
			e.printStackTrace();
		}
        
        logger.info("Blockchain is synced");
        context = Context.get();
	}
	
	public NetworkParameters getNetworkParams(){
		return params;
	}
	
}
