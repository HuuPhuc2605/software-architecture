package org.example;

public class PostPlugin {

    public void register(Router router){

        router.register("/user/post", () -> {
            System.out.println("List posts");
        });

    }

}