package org.example;

public class UserPlugin {

    public void register(Router router){

        router.register("/user", () -> {
            System.out.println("List users");
        });

    }

}