package com.shikhar03stark.entity;

import com.shikhar03stark.util.RandomIdGenerator;

public class Transaction {
    private String transactionId;
    private Wallet sourceWallet;
    private Wallet destinationWallet;
    private double amount;
    private TransactionType transactionType;

    public Transaction(Wallet sourceWallet, Wallet destinationWallet, double amount, TransactionType transactionType) {
        this.sourceWallet = sourceWallet;
        this.destinationWallet = destinationWallet;
        this.amount = amount;
        this.transactionType = transactionType;

        this.transactionId = RandomIdGenerator.generateRandomId(12);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Wallet getSourceWallet() {
        return sourceWallet;
    }

    public Wallet getDestinationWallet() {
        return destinationWallet;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        if (transactionType == TransactionType.REFUND) {
            return "Refund from " + sourceWallet.getHolder() + " to " + destinationWallet.getHolder() + " of amount " + amount;
        }
        else if (transactionType == TransactionType.OFFER) {
            return "Offer from " + sourceWallet.getHolder() + " to " + destinationWallet.getHolder() + " of amount " + amount;
        }
        return "Transaction from " + sourceWallet.getHolder() + " to " + destinationWallet.getHolder() + " of amount " + amount;
    }

    
    
}
