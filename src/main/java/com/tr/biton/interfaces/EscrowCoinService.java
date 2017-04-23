package com.tr.biton.interfaces;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.wallet.Wallet;

public interface EscrowCoinService {
	public Wallet createEscrowWallet(int conformations);
	public Address createEscrowContract(Wallet w, ECKey escrow, ECKey buyer, ECKey seller);
	public void escrowObserve(Address address, Wallet w);
	public String toString(Wallet w);
	public String toString(Wallet w, Address address);
}
