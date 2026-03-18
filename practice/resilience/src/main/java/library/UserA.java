package library;

public class UserA implements Observer{

    @Override
    public void update(String message) {
        System.out.println("Thông báo cho khách hàng: " + message);

    }
}
