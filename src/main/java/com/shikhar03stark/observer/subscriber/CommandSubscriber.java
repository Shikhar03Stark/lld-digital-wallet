package com.shikhar03stark.observer.subscriber;

import com.shikhar03stark.service.command.WalletCommand;

public interface CommandSubscriber {

    void onCommand(WalletCommand command);
}
