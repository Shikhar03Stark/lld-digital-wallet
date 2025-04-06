package com.shikhar03stark.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shikhar03stark.entity.Transaction;
import com.shikhar03stark.entity.TransactionType;
import com.shikhar03stark.entity.Wallet;
import com.shikhar03stark.exception.InsufficientFundsException;
import com.shikhar03stark.exception.WalletAlreadyExistsException;

public class WalletManagerService {

    private final List<Wallet> wallets;
    private final Map<String, Wallet> walletHolderToWalletMap;

    private static WalletManagerService instance = null;

    public static WalletManagerService getInstance() {
        if (instance == null) {
            instance = new WalletManagerService();
        }
        return instance;
    }

    private WalletManagerService() {
        this.wallets = new ArrayList<>();
        this.walletHolderToWalletMap = new HashMap<>();
    }

    public Wallet createWallet(String holder, double initalAmount) {
        if (walletHolderToWalletMap.containsKey(holder)) {
            throw new WalletAlreadyExistsException(holder, walletHolderToWalletMap.get(holder).getWalletId());
        }
        Wallet wallet = new Wallet(holder, initalAmount);
        wallets.add(wallet);
        walletHolderToWalletMap.put(holder, wallet);
        return wallet;
    }

    public Transaction createTransaction(Wallet source, Wallet destination, double amount, TransactionType type) {
        try {
            source.getWalletSem().acquire();
            destination.getWalletSem().acquire();

            final Transaction transaction = new Transaction(source, destination, amount, type);

            if (type == TransactionType.REFUND) {
                Wallet temp = source;
                source = destination;
                destination = temp;
            }

            if (source.getBalance() < amount) {
                throw new InsufficientFundsException(source.getWalletId(), amount, source.getBalance());
            }
            
            source.updateBalance(-amount, transaction);
            destination.updateBalance(amount, transaction);

            return transaction;

        } catch (InterruptedException e) {
            throw new RuntimeException("Thread interrupted while acquiring wallet semaphore", e);
        } finally {
            source.getWalletSem().release();
            destination.getWalletSem().release();
        }
    }

    public String getOverviewOfWallets() {
        StringBuilder overview = new StringBuilder("Wallets Overview:\n");
        for (Wallet wallet : wallets) {
            overview.append("Holder: ").append(wallet.getHolder())
                    .append(", Wallet ID: ").append(wallet.getWalletId())
                    .append(", Balance: ").append(wallet.getBalance()).append("\n");
        }
        return overview.toString();
    }

    public String getWalletStatement(Wallet wallet) {
        StringBuilder statement = new StringBuilder("Wallet Statement for " + wallet.getHolder() + ":\n");
        for (Transaction transaction : wallet.getTransactions()) {
            statement.append(transaction.toString()).append("\n");
        }
        return statement.toString();
    }

    public Wallet getWalletByHolder(String holder) {
        return walletHolderToWalletMap.get(holder);
    }


}
