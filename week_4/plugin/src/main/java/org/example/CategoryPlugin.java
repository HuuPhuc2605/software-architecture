package org.example;

public class CategoryPlugin implements Plugin {
    @Override
    public String handle(String path) {
        if (path.equals("/category")) {
            return "List Category";
        }
        return null;
    }
}