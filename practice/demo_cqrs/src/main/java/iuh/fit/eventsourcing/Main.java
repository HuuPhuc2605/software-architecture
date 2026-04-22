package iuh.fit.eventsourcing;

public class Main {
    public static void main(String[] args) {
        EventStore store = new EventStore();
        BankService service = new BankService(store);

        // ===== COMMAND =====
        service.deposit(1000);
        service.withdraw(200);
        service.deposit(500);

        // ===== REBUILD STATE =====
        BankAccount account = new BankAccount();
        account.replay(store.getAllEvents());

        // ===== RESULT =====
        System.out.println("Final Balance = " + account.getBalance());
    }
}
