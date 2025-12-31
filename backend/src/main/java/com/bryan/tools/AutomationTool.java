package com.bryan.tools;

public interface AutomationTool {
    String getName();
    ToolResult execute(ToolContext context) throws ToolExecutionException;
}
