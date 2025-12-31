package com.bryan.tools;

import java.util.Map;

public class ToolContext {
    private final Map<String, Object> inputs;

    public ToolContext(Map<String, Object> inputs) {
        this.inputs = inputs;
    }

    public String getString(String key) {
        Object v = inputs.get(key);
        return v == null ? null : v.toString();
    }

    public Object get(String key) {
        return inputs.get(key);
    }
}