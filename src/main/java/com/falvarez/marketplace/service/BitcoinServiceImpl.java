package com.falvarez.marketplace.service;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.springframework.stereotype.Service;

@Service
public class BitcoinServiceImpl implements IBitcoinService {

	final String ADDRESS = "n2cYxqvxCnjBkvRoMmWYFxnietzP4Wasf7";

	@Override
	public void SendCoin(String privKey, String value) {
		NetworkParameters params = TestNet3Params.get();
		Transaction transaction = new Transaction(params);
		DumpedPrivateKey dk = DumpedPrivateKey.fromBase58(params, privKey);
		ECKey ecKey = dk.getKey();
		Coin coin = Coin.parseCoin(value);
		LegacyAddress to = LegacyAddress.fromBase58(params, ADDRESS);
		transaction.addOutput(coin, to);
		Wallet wallet = Wallet.createBasic(params);
		wallet.importKey(ecKey);
		System.out.println(ecKey.getPubKey());
		try {
			BlockStore blockStore = new MemoryBlockStore(params);
			BlockChain chain = new BlockChain(params, wallet, blockStore);
			PeerGroup peerGroup = new PeerGroup(params, chain);
			peerGroup.addWallet(wallet);
			peerGroup.startAsync();
			SendRequest request = SendRequest.forTx(transaction);
			Wallet.SendResult result = wallet.sendCoins(request);
			System.out.println("coins sent. transaction hash: " + result.tx.getTxId());
		} catch (InsufficientMoneyException e) {
			System.out.println("Not enough coins in your wallet. " + wallet.currentReceiveAddress().toString()
					+ " Missing " + e.missing.getValue());
			System.out.println(e.getMessage());
		} catch (BlockStoreException e) {
			System.out.println(e.getMessage());
		}
	}

}
