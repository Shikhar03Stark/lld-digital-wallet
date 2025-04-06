package com.shikhar03stark.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shikhar03stark.observer.subscriber.CommandSubscriber;
import com.shikhar03stark.service.command.WalletCommand;
import com.shikhar03stark.service.command.WalletCommandType;

public class CommandNotifier {

    private Map<WalletCommandType, List<CommandSubscriber>> subMap;
    private static CommandNotifier instance = null;

    private CommandNotifier() {
        subMap = new HashMap<>();
    }

    public static CommandNotifier getInstance() {
        if (instance == null) {
            instance = new CommandNotifier();
        }
        return instance;
    }

    public void registerSubscriber(WalletCommandType commandType, CommandSubscriber subscriber) {
        if (!subMap.containsKey(commandType)) {
            subMap.put(commandType, new ArrayList<>());
        }
        subMap.get(commandType).add(subscriber);
    }

    public void publishCommand(WalletCommand command) {
        if (!subMap.containsKey(command.getCommandType())) {
            return;
        }
        for(CommandSubscriber subscriber : subMap.get(command.getCommandType())) {
            subscriber.onCommand(command);
        }
    }

}
