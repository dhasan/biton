package com.tr.biton.interfaces;

import org.bitcoinj.core.Coin;

public interface EscrowContractObserver {
	void Funded(String adddress, Coin by, Coin to);
	void Spend(String fromaddress, Coin amount, Coin left);
}
