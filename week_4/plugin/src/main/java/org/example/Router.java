package org.example;

import java.util.HashMap;
import java.util.Map;

public class Router {

    private Map<String, Runnable> routes = new HashMap<>();

    public void register(String path, Runnable action){
        routes.put(path, action);
    }

    public void handle(String path){
        if(routes.containsKey(path)){
            routes.get(path).run();
        }else{
            System.out.println("Route not found");
        }
    }
}