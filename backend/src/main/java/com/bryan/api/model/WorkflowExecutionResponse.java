package com.bryan.api.model;

import com.bryan.executor.model.StepLog;

import java.util.List;
import java.util.Map;

public class WorkflowExecutionResponse {
    private List<StepLog> logs;
    private Map<String, Object> state;
    private Object finalOutput;

    public WorkflowExecutionResponse(List<StepLog> logs, Map<String, Object> state, Object finalOutput) {
        this.logs = logs;
        this.state = state;
        this.finalOutput = finalOutput;
    }

    public List<StepLog> getLogs() { return logs; }
    public Map<String, Object> getState() { return state; }
    public Object getFinalOutput() { return finalOutput; }
}
