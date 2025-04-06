package com.shikhar03stark.exception;

public class WalletAlreadyExistsException extends RuntimeException {

    public WalletAlreadyExistsException(String holder, String walletId) {
        super("Wallet with ID " + walletId + " already exists for holder " + holder + ".");
    }

}
