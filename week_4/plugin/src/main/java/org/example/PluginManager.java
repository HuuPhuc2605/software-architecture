package org.example;

import java.util.ArrayList;
import java.util.List;

public class PluginManager {
    private List<Plugin> plugins = new ArrayList<>();

    public void register(Plugin plugin) {
        plugins.add(plugin);
    }

    public String handleRequest(String path) {
        for (Plugin p : plugins) {
            String result = p.handle(path);
            if (result != null) {
                return result;
            }
        }
        return "404 Not Found";
    }
}