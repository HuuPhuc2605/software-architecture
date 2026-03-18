package org.example;

public class PostPlugin implements Plugin {
    @Override
    public String handle(String path) {
        if (path.equals("/post")) {
            return "List Post";
        }
        return null;
    }
}