package factory;

public class Test {
    public static void main(String[] args) {
    Drink drink = DrinkFactory.order("coffee");
    drink.make();
    }
}
