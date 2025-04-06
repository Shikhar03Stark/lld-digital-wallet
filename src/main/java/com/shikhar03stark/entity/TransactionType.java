package com.shikhar03stark.entity;

public enum TransactionType {
    ON_DEMAND, // invoked by a user
    OFFER, // invoked by the system
    REFUND, // invoked by the system/merchant
}
