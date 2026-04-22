package iuh.fit.eventsourcing;

class MoneyWithdrawn implements Event {
    public int amount;

    public MoneyWithdrawn(int amount) {
        this.amount = amount;
    }
}