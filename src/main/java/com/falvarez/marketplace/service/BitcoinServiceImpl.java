package com.falvarez.marketplace.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bitcoin.protocols.payments.Protos.PaymentRequest;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.StoredBlock;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.listeners.DownloadProgressTracker;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.protocols.payments.PaymentProtocol;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.KeyChainGroup;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.Wallet.BalanceType;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;

@Service
public class BitcoinServiceImpl implements IBitcoinService {

	final String ADDRESS = "n2cYxqvxCnjBkvRoMmWYFxnietzP4Wasf7";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<Boolean, String> SendCoin(String privKey, String value) {
		Map<Boolean, String> response = new HashMap<Boolean, String>();
		/*
		 * NetworkParameters params = TestNet3Params.get(); Transaction transaction =
		 * new Transaction(params);
		 * 
		 * String pk = "cPwSy3V8EmLehfczmhRaHTRBWRTCMhjDoxyg8WfivWc7YsiM65xh";
		 * 
		 * DumpedPrivateKey dk = DumpedPrivateKey.fromBase58(params, pk); ECKey ecKey =
		 * dk.getKey();
		 * 
		 * Coin coin = Coin.parseCoin(value); LegacyAddress to =
		 * LegacyAddress.fromBase58(params, ADDRESS); transaction.addOutput(coin, to);
		 * 
		 * Wallet wallet = Wallet.createBasic(params); wallet.importKey(ecKey);
		 * System.out.println(ecKey.getPubKey()); try { BlockStore blockStore = new
		 * MemoryBlockStore(params); BlockChain chain = new BlockChain(params, wallet,
		 * blockStore); PeerGroup peerGroup = new PeerGroup(params, chain);
		 * peerGroup.addWallet(wallet); peerGroup.startAsync(); SendRequest request =
		 * SendRequest.forTx(transaction); Wallet.SendResult result =
		 * wallet.sendCoins(request); response.put(true,
		 * "coins sent. transaction hash: " + result.tx.getTxId()); } catch
		 * (InsufficientMoneyException e) { // response.put(false,
		 * "Not enough coins in your wallet. " +
		 * wallet.currentReceiveAddress().toString() // + " Missing " +
		 * e.missing.getValue()); System.out.println(e.getMessage()); } catch
		 * (BlockStoreException e) { System.out.println(e.getMessage()); }
		 */
		File file = new File("test.wallet");
		String pk = "cPwSy3V8EmLehfczmhRaHTRBWRTCMhjDoxyg8WfivWc7YsiM65xh";
		pk = "91q72aMm4FGEpMDBs4Q6ayLGpxTg8z2hiJEqsLHbJFuiwSHZobK";
		NetworkParameters params = TestNet3Params.get();

		DumpedPrivateKey dk = DumpedPrivateKey.fromBase58(params, pk);
		ECKey ecKey = dk.getKey();
		Wallet wallet = Wallet.createBasic(params);
		wallet.importKey(ecKey);
		System.out.println(wallet.toString());
		try {
			// Set up the components and link them together.
			BlockStore blockStore = new MemoryBlockStore(params);
			BlockChain chain = new BlockChain(params, wallet, blockStore);

			final PeerGroup peerGroup = new PeerGroup(params, chain);
			peerGroup.startAsync();

			wallet.addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
				@Override
				public synchronized void onCoinsReceived(Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) {
					System.out.println("\nReceived tx " + tx.getTxId());
					System.out.println(tx.toString());
				}
			});

			// Now download and process the block chain.
			peerGroup.setDownloadTxDependencies(0);
			peerGroup.downloadBlockChain();
			peerGroup.stopAsync();
			wallet.saveToFile(file);
			System.out.println("\nDone!\n");
			System.out.println(wallet.toString());
		} catch (BlockStoreException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		return response;
	}

}
