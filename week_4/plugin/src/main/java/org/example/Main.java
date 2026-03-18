package org.example;


public class Main {
    public static void main(String[] args) {
        PluginManager manager = new PluginManager();

        manager.register(new UserPlugin());
        manager.register(new PostPlugin());

        Router router = new Router(manager);
        router.route("/user");
        router.route("/post");
        router.route("/category");
    }
}