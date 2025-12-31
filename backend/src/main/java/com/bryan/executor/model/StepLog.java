package com.bryan.executor.model;

import java.time.Instant;

public class StepLog {
    private String stepId;
    private String toolName;
    private ExecutionStatus status;
    private String message;
    private Instant timestamp;

    public StepLog() {}

    public StepLog(String stepId, String toolName, ExecutionStatus status, String message) {
        this.stepId = stepId;
        this.toolName = toolName;
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public String getStepId() { return stepId; }
    public String getToolName() { return toolName; }
    public ExecutionStatus getStatus() { return status; }
    public String getMessage() { return message; }
    public Instant getTimestamp() { return timestamp; }
}
