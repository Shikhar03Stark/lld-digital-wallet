# Digital Wallet

## Overview

(Readme by AI)

The **Digital Wallet** project is a low-level design (LLD) implementation of a wallet system that supports wallet creation, transactions, and offers. It is built using Java and follows object-oriented design principles. The system is designed to handle concurrent transactions, ensure thread safety, and provide extensibility for future enhancements.

This project implements the **Command Pattern** and **Observer Pattern** to decouple components and ensure scalability. It also includes custom exception handling and utility classes to manage core functionalities.

---

## Features

1. **Wallet Management**:
   - Create wallets for users with an initial balance.
   - Retrieve wallet details and transaction history.

2. **Transaction Management**:
   - Perform transactions between wallets.
   - Support for different transaction types: `ON_DEMAND`, `OFFER`, and `REFUND`.

3. **Offer Management**:
   - Automatically apply offers based on transaction conditions.

4. **Concurrency Handling**:
   - Thread-safe operations using semaphores to prevent race conditions during transactions.

5. **Extensibility**:
   - Easily extendable to add new features like additional transaction types or notification systems.

6. **Design Patterns**:
   - **Command Pattern**: Encapsulates requests as objects, allowing for parameterization and queuing of requests.
   - **Observer Pattern**: Enables decoupled communication between components (e.g., notifying subscribers of commands).

---

## Design and Low-Level Design (LLD)

### 1. **Core Components**

#### a. **Wallet**
- Represents a user's wallet with attributes like `walletId`, `holder`, `balance`, and a list of transactions.
- Thread-safe operations using a semaphore to ensure atomic updates to the wallet balance.

#### b. **Transaction**
- Represents a transaction between two wallets.
- Includes attributes like `transactionId`, `sourceWallet`, `destinationWallet`, `amount`, and `transactionType`.

#### c. **TransactionType**
- Enum defining the types of transactions:
  - `ON_DEMAND`: User-initiated transactions.
  - `OFFER`: System-initiated offers.
  - `REFUND`: Refunds initiated by the system or merchants.

---

### 2. **Service Layer**

#### a. **WalletManagerService**
- Manages wallet creation and transactions.
- Ensures thread safety during transactions.
- Provides methods to retrieve wallet overviews and statements.

#### b. **OfferManagerService**
- Implements the **Observer Pattern** to listen for specific commands.
- Automatically applies offers when certain conditions are met (e.g., balance matching).

---

### 3. **Command Pattern**

#### a. **WalletCommand**
- Interface defining the structure of commands with methods like `execute()` and `getCommandType()`.

#### b. **WalletCommandInvoker**
- Executes commands by invoking their `execute()` method.
- Stores the latest command for potential undo/redo functionality.

#### c. **Command Implementations**
- **CreateWalletCommand**: Creates a new wallet.
- **CreateTransactionCommand**: Executes a transaction between wallets.

---

### 4. **Observer Pattern**

#### a. **CommandNotifier**
- Maintains a mapping of command types to subscribers.
- Notifies subscribers when a command is executed.

#### b. **CommandSubscriber**
- Interface for components that want to listen to specific commands.

---

### 5. **Utility Classes**

#### a. **RandomIdGenerator**
- Generates unique IDs for wallets and transactions.

#### b. **Custom Exceptions**
- **WalletAlreadyExistsException**: Thrown when attempting to create a wallet for an existing user.
- **InsufficientFundsException**: Thrown when a wallet has insufficient funds for a transaction.

---

## Pros

1. **Scalability**:
   - The use of design patterns ensures that the system can be easily extended to add new features.

2. **Thread Safety**:
   - Semaphores ensure that wallet operations are atomic, preventing race conditions.

3. **Decoupled Design**:
   - The Command and Observer patterns decouple components, making the system modular and easier to maintain.

4. **Extensibility**:
   - New transaction types, commands, or subscribers can be added without modifying existing code.

5. **Custom Error Handling**:
   - Provides meaningful exceptions for better debugging and error management.

---

## Drawbacks

1. **Complexity**:
   - The use of multiple design patterns increases the complexity of the codebase, which may be challenging for new developers.

2. **Performance Overhead**:
   - The use of semaphores and the observer pattern may introduce slight performance overhead in high-concurrency scenarios.

3. **Limited Features**:
   - The current implementation lacks advanced features like multi-currency support, transaction rollbacks, or analytics.

4. **Testing Coverage**:
   - The project currently has minimal unit tests, which may lead to undetected bugs in edge cases.

---

## Future Enhancements

1. **Add Unit Tests**:
   - Increase test coverage to ensure reliability and robustness.

2. **Support for Multi-Currency**:
   - Extend the wallet and transaction system to handle multiple currencies.

3. **Transaction Rollbacks**:
   - Implement rollback functionality for failed transactions.

4. **Analytics and Reporting**:
   - Add features to generate reports and analytics for wallets and transactions.

5. **Notification System**:
   - Notify users of transactions and offers via email or SMS.