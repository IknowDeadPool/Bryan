package com.bryan.tools;

import java.util.Map;

public class ToolContext {
    private final Map<String, Object> inputs;
    private final Map<String, Object> state;
    public ToolContext(Map<String, Object> inputs, Map<String, Object> state) {
        this.inputs = inputs;
        this.state = state;
    }

    public String getString(String key) {
        Object v = inputs.get(key);
        return v == null ? null : v.toString();
    }

    public Object get(String key) {
        return inputs.get(key);
    }
    public Map<String, Object> state(){
        return state;
    }
}