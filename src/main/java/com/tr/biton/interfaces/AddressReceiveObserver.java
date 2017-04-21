package com.tr.biton.interfaces;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.Wallet;

public interface AddressReceiveObserver{
	void onCoinsReceived(Wallet wallet, Transaction tx, Coin prevBalance, Coin newBalance, byte[] sc);
}