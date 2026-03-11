package org.example;

public class CategoryPlugin {

    public void register(Router router){

        router.register("/user/category", () -> {
            System.out.println("List categories");
        });

    }

}