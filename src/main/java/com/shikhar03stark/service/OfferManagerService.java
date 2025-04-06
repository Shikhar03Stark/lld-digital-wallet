package com.shikhar03stark.service;

import com.shikhar03stark.entity.Transaction;
import com.shikhar03stark.entity.TransactionType;
import com.shikhar03stark.entity.Wallet;
import com.shikhar03stark.observer.subscriber.CommandSubscriber;
import com.shikhar03stark.service.command.WalletCommand;
import com.shikhar03stark.service.command.WalletCommandInvoker;
import com.shikhar03stark.service.command.WalletCommandType;
import com.shikhar03stark.service.command.impl.CreateTransactionCommand;

public class OfferManagerService implements CommandSubscriber {

    private static OfferManagerService instance = null;
    private final WalletCommandInvoker invoker;
    private final WalletManagerService walletManagerService = WalletManagerService.getInstance();
    private final Wallet dummyWallet = new Wallet("FLIPKART", Double.MAX_VALUE);

    private OfferManagerService(WalletCommandInvoker invoker) {
        // Private constructor to prevent instantiation 
        this.invoker = invoker;
    }

    public static OfferManagerService getInstance(WalletCommandInvoker invoker) {
        if (instance == null) {
            instance = new OfferManagerService(invoker);
        }
        return instance;
    }

    private boolean isAmountEqual(double amount1, double amount2) {
        return Math.abs(amount1 - amount2) < 0.001; // Adjust precision as needed
    }

    private void handleTransactionCommand(CreateTransactionCommand command) {
        final Transaction t = command.getTransaction();
        if (t.getTransactionType() != TransactionType.ON_DEMAND) return;

        final Wallet source = t.getSourceWallet();
        final Wallet dest = t.getDestinationWallet();

        if (isAmountEqual(source.getBalance(), dest.getBalance())) {
            final WalletCommand offerSourceCommand = new CreateTransactionCommand(dummyWallet, source, 10f, TransactionType.OFFER, walletManagerService, null);
            final WalletCommand offerDestCommand = new CreateTransactionCommand(dummyWallet, dest, 10f, TransactionType.OFFER, walletManagerService, null);

            invoker.setCommand(offerSourceCommand);
            invoker.executeCommand();

            invoker.setCommand(offerDestCommand);
            invoker.executeCommand();
        }
    }

    @Override
    public void onCommand(WalletCommand command) {
        if (!command.getCommandType().equals(WalletCommandType.CreateTransactionCommand)) return;
        // Handle transaction which are only ONDEMAND
        if (command instanceof CreateTransactionCommand) {
            handleTransactionCommand((CreateTransactionCommand) command);
        }
    }


}
