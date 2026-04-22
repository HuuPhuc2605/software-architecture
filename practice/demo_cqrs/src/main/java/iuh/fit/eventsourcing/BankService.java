package iuh.fit.eventsourcing;

class BankService {
    private EventStore store;

    public BankService(EventStore store) {
        this.store = store;
    }

    public void deposit(int amount) {
        System.out.println("Command: Deposit " + amount);
        store.save(new MoneyDeposited(amount));
    }

    public void withdraw(int amount) {
        System.out.println("Command: Withdraw " + amount);
        store.save(new MoneyWithdrawn(amount));
    }
}
