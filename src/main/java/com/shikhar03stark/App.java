package com.shikhar03stark;

import com.shikhar03stark.entity.TransactionType;
import com.shikhar03stark.entity.Wallet;
import com.shikhar03stark.observer.CommandNotifier;
import com.shikhar03stark.service.OfferManagerService;
import com.shikhar03stark.service.WalletManagerService;
import com.shikhar03stark.service.command.WalletCommand;
import com.shikhar03stark.service.command.WalletCommandInvoker;
import com.shikhar03stark.service.command.WalletCommandType;
import com.shikhar03stark.service.command.impl.CreateTransactionCommand;
import com.shikhar03stark.service.command.impl.CreateWalletCommand;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        final String holder1 = "Shikhar";
        final String holder2 = "Harshit";
        final String holder3 = "Shruty";

        final WalletManagerService walletManager = WalletManagerService.getInstance();
        final WalletCommandInvoker invoker = new WalletCommandInvoker();
        final CommandNotifier commandNotifier = CommandNotifier.getInstance();
        final OfferManagerService offerManager = OfferManagerService.getInstance(invoker);
        
        commandNotifier.registerSubscriber(WalletCommandType.CreateTransactionCommand, offerManager);

        // Create wallets
        final WalletCommand walletCmd1 = new CreateWalletCommand(holder1, 1000f, walletManager, commandNotifier);
        final WalletCommand walletCmd2 = new CreateWalletCommand(holder2, 2000f, walletManager, commandNotifier);
        final WalletCommand walletCmd3 = new CreateWalletCommand(holder3, 2500f, walletManager, commandNotifier);

        invoker.setCommand(walletCmd1);
        invoker.executeCommand();

        invoker.setCommand(walletCmd2);
        invoker.executeCommand();

        invoker.setCommand(walletCmd3);
        invoker.executeCommand();

        final Wallet wallet1 = walletManager.getWalletByHolder(holder1);
        final Wallet wallet2 = walletManager.getWalletByHolder(holder2);
        final Wallet wallet3 = walletManager.getWalletByHolder(holder3);

        final WalletCommand transactionCommand = new CreateTransactionCommand(wallet2, wallet1, 500f, TransactionType.ON_DEMAND, walletManager, commandNotifier);
        invoker.setCommand(transactionCommand);
        invoker.executeCommand();

        final WalletCommand transactionCommand2 = new CreateTransactionCommand(wallet3, wallet1, 1000f, TransactionType.ON_DEMAND, walletManager, commandNotifier);
        invoker.setCommand(transactionCommand2);
        invoker.executeCommand();

        final WalletCommand transactionCommand3 = new CreateTransactionCommand(wallet1, wallet2, 200f, TransactionType.ON_DEMAND, walletManager, commandNotifier);
        invoker.setCommand(transactionCommand3);
        invoker.executeCommand();

        System.out.println(walletManager.getOverviewOfWallets());

        System.out.println(walletManager.getWalletStatement(wallet1));


    }
}
