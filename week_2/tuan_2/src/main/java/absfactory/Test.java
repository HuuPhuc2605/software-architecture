package absfactory;

public class Test {
    public static void main(String[] args) {
        ComboFactory comboFactory = new Combo1Factory();
        comboFactory.mainDrink().make();
        System.out.println("");
        comboFactory.mainEat().cook();
    }
}
