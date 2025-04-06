package com.shikhar03stark.service.command.impl;

import com.shikhar03stark.entity.Transaction;
import com.shikhar03stark.entity.TransactionType;
import com.shikhar03stark.entity.Wallet;
import com.shikhar03stark.observer.CommandNotifier;
import com.shikhar03stark.service.WalletManagerService;
import com.shikhar03stark.service.command.WalletCommand;
import com.shikhar03stark.service.command.WalletCommandType;

public class CreateTransactionCommand implements WalletCommand {

    private Wallet source;
    private Wallet destination;
    private double amount;
    private TransactionType type;

    private Transaction transaction;

    private final WalletManagerService walletManagerService;
    private final CommandNotifier commandNotifier;
    

    public CreateTransactionCommand(Wallet source, Wallet destination, double amount, TransactionType type,
            WalletManagerService walletManagerService, CommandNotifier commandNotifier) {
        this.source = source;
        this.destination = destination;
        this.amount = amount;
        this.type = type;
        this.walletManagerService = walletManagerService;
        this.commandNotifier = commandNotifier;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public void execute() {
        if (transaction != null) {
            throw new IllegalStateException("Transaction already executed");
        }
        transaction = walletManagerService.createTransaction(source, destination, amount, type);
        if (commandNotifier != null) {
            commandNotifier.publishCommand(this);
        }
    }

    @Override
    public WalletCommandType getCommandType() {
        return WalletCommandType.CreateTransactionCommand;
    }

}
