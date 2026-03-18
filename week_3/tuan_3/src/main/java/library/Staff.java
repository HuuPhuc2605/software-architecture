package library;

public class Staff implements Observer{

    @Override
    public void update(String message) {
        System.out.println("Thông báo cho nhân viên: " + message);

    }
}
