package factory;

public class Coffee implements Drink{
    @Override
    public void make() {
        System.out.printf("Pha cà phê");
    }
}
