package com.bryan.api.model;

public class WorkflowRunRequest {
    private String goal;

    public WorkflowRunRequest() {}

    public WorkflowRunRequest(String goal) {
        this.goal = goal;
    }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
}
