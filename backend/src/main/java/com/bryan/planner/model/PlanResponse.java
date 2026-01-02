package com.bryan.planner.model;

import com.bryan.executor.model.WorkflowStep;
import java.util.List;

public class PlanResponse {
    private List<WorkflowStep> steps;

    public PlanResponse() {}

    public PlanResponse(List<WorkflowStep> steps) {
        this.steps = steps;
    }

    public List<WorkflowStep> getSteps() { return steps; }
    public void setSteps(List<WorkflowStep> steps) { this.steps = steps; }
}
