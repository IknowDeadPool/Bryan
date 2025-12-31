package com.bryan.tools;

public class ToolResult {
    private final boolean success;
    private final Object data;
    private final String message;

    private ToolResult(boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static ToolResult success(Object data) {
        return new ToolResult(true, data, null);
    }

    public static ToolResult failure(String message) {
        return new ToolResult(false, null, message);
    }

    public boolean isSuccess() { return success; }
    public Object getData() { return data; }
    public String getMessage() { return message; }
}
