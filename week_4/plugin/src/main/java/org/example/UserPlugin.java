package org.example;

public class UserPlugin implements Plugin {
    @Override
    public String handle(String path) {
        if (path.equals("/user")) {
            return "List User";
        }
        return null;
    }
}