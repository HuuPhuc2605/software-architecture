package absfactory;

public class Combo2Factory implements ComboFactory{


    @Override
    public MainEat mainEat() {
        return new Noodle();
    }

    @Override
    public MainDrink mainDrink() {
        return new MilkTea();
    }
}
