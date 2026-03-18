package observer;

public class Main {
    public static void main(String[] args) {
        TemperatureSensor subject = new TemperatureSensor();

        Observer screen1 = new PhoneDisplay();
        Observer screen2 = new PhoneDisplay();

        subject.addObserver(screen1);
        subject.addObserver(screen2);

        subject.setTemperature(10);
        subject.setTemperature(20);
    }
}