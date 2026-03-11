package org.example;


public class Main {
    public static void main(String[] args) {

        Router router = new Router();

        new UserPlugin().register(router);
        new PostPlugin().register(router);
        new CategoryPlugin().register(router);

        router.handle("/user");
        router.handle("/user/post");
        router.handle("/user/category");

    }
}