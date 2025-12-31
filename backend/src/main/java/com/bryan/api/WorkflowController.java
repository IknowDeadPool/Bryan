package com.bryan.api;

import com.bryan.executor.WorkflowExecutor;
import com.bryan.executor.model.StepLog;
import com.bryan.executor.model.WorkflowStep;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final WorkflowExecutor workflowExecutor;

    public WorkflowController(WorkflowExecutor workflowExecutor) {
        this.workflowExecutor = workflowExecutor;
    }

    @PostMapping("/execute")
    public List<StepLog> execute(@RequestBody List<WorkflowStep> steps) {
        return workflowExecutor.execute(steps);
    }
}
