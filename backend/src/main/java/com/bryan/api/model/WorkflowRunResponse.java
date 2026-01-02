package com.bryan.api.model;

import com.bryan.executor.model.StepLog;
import com.bryan.executor.model.WorkflowStep;

import java.util.List;
import java.util.Map;

public class WorkflowRunResponse {
    private String goal;
    private List<WorkflowStep> plannedSteps;
    private List<StepLog> logs;
    private Map<String, Object> state;
    private Object finalOutput;

    public WorkflowRunResponse(String goal,
                               List<WorkflowStep> plannedSteps,
                               List<StepLog> logs,
                               Map<String, Object> state,
                               Object finalOutput) {
        this.goal = goal;
        this.plannedSteps = plannedSteps;
        this.logs = logs;
        this.state = state;
        this.finalOutput = finalOutput;
    }

    public String getGoal() { return goal; }
    public List<WorkflowStep> getPlannedSteps() { return plannedSteps; }
    public List<StepLog> getLogs() { return logs; }
    public Map<String, Object> getState() { return state; }
    public Object getFinalOutput() { return finalOutput; }
}
