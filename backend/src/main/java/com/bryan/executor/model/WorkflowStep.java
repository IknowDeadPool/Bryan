package com.bryan.executor.model;

import java.util.Map;

public class WorkflowStep {
    private String toolName;
    private Map<String, Object> inputs;

    public WorkflowStep() {}

    public WorkflowStep(String toolName, Map<String, Object> inputs) {
        this.toolName = toolName;
        this.inputs = inputs;
    }

    public String getToolName() { return toolName; }
    public void setToolName(String toolName) { this.toolName = toolName; }

    public Map<String, Object> getInputs() { return inputs; }
    public void setInputs(Map<String, Object> inputs) { this.inputs = inputs; }
}
