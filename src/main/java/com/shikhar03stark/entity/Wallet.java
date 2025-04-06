package com.shikhar03stark.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.shikhar03stark.util.RandomIdGenerator;

public class Wallet {
    private String walletId;
    private String holder;
    private double balance;
    private Semaphore walletSem;
    private List<Transaction> transactions = new ArrayList<>();


    public Wallet(String holder, double balance) {
        this.holder = holder;
        this.balance = balance;
        this.walletId = RandomIdGenerator.generateRandomId(12);
        this.walletSem = new Semaphore(1);
    }


    public String getWalletId() {
        return walletId;
    }


    public String getHolder() {
        return holder;
    }


    public double getBalance() {
        return balance;
    }


    public Semaphore getWalletSem() {
        return walletSem;
    }

    public void updateBalance(double amount, Transaction transaction) {
        this.balance += amount;
        transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }


}
