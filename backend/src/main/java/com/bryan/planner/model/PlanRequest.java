package com.bryan.planner.model;

public class PlanRequest {
    private String goal;

    public PlanRequest() {}

    public PlanRequest(String goal) {
        this.goal = goal;
    }

    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
}
