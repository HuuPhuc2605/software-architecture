package iuh.fit.eventsourcing;

class MoneyDeposited implements Event {
    public int amount;

    public MoneyDeposited(int amount) {
        this.amount = amount;
    }
}
