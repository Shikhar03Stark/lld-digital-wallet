package com.shikhar03stark.service.command.impl;

import com.shikhar03stark.observer.CommandNotifier;
import com.shikhar03stark.service.WalletManagerService;
import com.shikhar03stark.service.command.WalletCommand;
import com.shikhar03stark.service.command.WalletCommandType;

public class CreateWalletCommand implements WalletCommand {

    private String holder;
    private double balance;
    private final WalletManagerService walletManagerService;
    private final CommandNotifier commandNotifier;

    public CreateWalletCommand(String holder, double balance, WalletManagerService walletManagerService, CommandNotifier commandNotifier) {
        this.holder = holder;
        this.balance = balance;
        this.walletManagerService = walletManagerService;
        this.commandNotifier = commandNotifier;
    }

    @Override
    public void execute() {
        walletManagerService.createWallet(holder, balance);
        commandNotifier.publishCommand(this);
    }

    @Override
    public WalletCommandType getCommandType() {
        return WalletCommandType.CreateWalletCommand;
    }

}
