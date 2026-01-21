package factory;

public class DrinkFactory {
    public static Drink order(String type){
        if (type.equalsIgnoreCase("coffee")){
            return new Coffee();
        }  if (type.equalsIgnoreCase("milk")){
            return new Coffee();
        }
        throw new IllegalArgumentException("Quán không có món này");
    }
}
