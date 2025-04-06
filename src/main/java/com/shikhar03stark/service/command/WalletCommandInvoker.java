package com.shikhar03stark.service.command;

public class WalletCommandInvoker {

    private WalletCommand latestCommand;

    public void setCommand(WalletCommand command) {
        this.latestCommand = command;
    }

    public void executeCommand() {
        if (latestCommand != null) {
            latestCommand.execute();
        } else {
            System.out.println("No command set to execute.");
        }
    }
}
