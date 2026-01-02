package com.bryan.tools;

import java.util.List;

public interface AutomationTool {
    String getName();
    ToolResult execute(ToolContext context) throws ToolExecutionException;

    // Metadata for discovery + planner
    default String getDescription() {
        return "No description provided.";
    }

    // Expected inputs for humans + future planner
    default List<ToolInputSpec> getInputSpec() {
        return List.of();
    }
}
