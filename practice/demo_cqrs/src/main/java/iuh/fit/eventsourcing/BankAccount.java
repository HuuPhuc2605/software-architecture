package iuh.fit.eventsourcing;

import java.util.List;

class BankAccount {
    private int balance = 0;

    // áp dụng event
    public void apply(Event event) {
        if (event instanceof MoneyDeposited) {
            balance += ((MoneyDeposited) event).amount;
        }
        else if (event instanceof MoneyWithdrawn) {
            balance -= ((MoneyWithdrawn) event).amount;
        }
    }

    // replay tất cả event
    public void replay(List<Event> events) {
        for (Event e : events) {
            apply(e);
        }
    }

    public int getBalance() {
        return balance;
    }
}