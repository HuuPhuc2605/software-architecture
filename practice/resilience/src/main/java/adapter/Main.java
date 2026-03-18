package adapter;

public class Main {
    public static void main(String[] args) {
        USB usb = new TypeCToUSBAdapter(new TypeCCharger());
        usb.connectUSB();
    }
}
