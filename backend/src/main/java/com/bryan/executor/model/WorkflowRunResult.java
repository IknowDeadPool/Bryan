package com.bryan.executor.model;

import java.util.List;
import java.util.Map;

public class WorkflowRunResult {
    private final List<StepLog> logs;
    private final Map<String, Object> state;
    private final Object finalOutput;

    public WorkflowRunResult(List<StepLog> logs, Map<String, Object> state, Object finalOutput) {
        this.logs = logs;
        this.state = state;
        this.finalOutput = finalOutput;
    }

    public List<StepLog> getLogs() { return logs; }
    public Map<String, Object> getState() { return state; }
    public Object getFinalOutput() { return finalOutput; }
}
