package org.example;

public class Router {
    private PluginManager pluginManager;

    public Router(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    public void route(String path) {
        String response = pluginManager.handleRequest(path);
        System.out.println(response);
    }
}