package com.shikhar03stark.service.command;

public interface WalletCommand {
    WalletCommandType getCommandType();
    void execute();
}

