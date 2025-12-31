package com.bryan.tools;

import java.util.HashMap;
import java.util.Map;

public class ToolRegistry {
    private final Map<String, AutomationTool> tools = new HashMap<>();

    public void register(AutomationTool tool) {
        tools.put(tool.getName(), tool);
    }

    public boolean contains(String name) {
        return tools.containsKey(name);
    }

    public AutomationTool get(String name) {
        return tools.get(name);
    }
}