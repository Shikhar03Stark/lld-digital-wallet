package com.shikhar03stark.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String walletId, double balance, double amount) {
        super("Insufficient funds in wallet " + walletId + " for transaction of amount " + amount + ". Current balance: " + balance + ".");
    }

}
