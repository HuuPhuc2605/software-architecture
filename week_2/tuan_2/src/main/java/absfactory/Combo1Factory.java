package absfactory;

public class Combo1Factory implements ComboFactory {

    @Override
    public MainEat mainEat() {
        return new Rice();
    }

    @Override
    public MainDrink mainDrink() {
        return new Coffee();
    }
}
