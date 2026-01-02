//package com.bryan.api;
//
//import com.bryan.api.model.WorkflowExecutionResponse;
//import com.bryan.executor.WorkflowExecutor;
//import com.bryan.executor.model.WorkflowRunResult;
//import com.bryan.executor.model.WorkflowStep;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/workflows")
//public class WorkflowController {
//
//    private final WorkflowExecutor workflowExecutor;
//
//    public WorkflowController(WorkflowExecutor workflowExecutor) {
//        this.workflowExecutor = workflowExecutor;
//    }
//
//    @PostMapping("/execute")
//    public WorkflowExecutionResponse execute(@RequestBody List<WorkflowStep> steps) {
//        WorkflowRunResult result = workflowExecutor.execute(steps);
//        return new WorkflowExecutionResponse(result.getLogs(), result.getState(), result.getFinalOutput());
//    }
//}


package com.bryan.api;

import com.bryan.api.model.WorkflowExecutionResponse;
import com.bryan.api.model.WorkflowRunRequest;
import com.bryan.api.model.WorkflowRunResponse;
import com.bryan.executor.WorkflowExecutor;
import com.bryan.executor.model.WorkflowRunResult;
import com.bryan.executor.model.WorkflowStep;
import com.bryan.planner.RuleBasedPlanner;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final WorkflowExecutor workflowExecutor;
    private final RuleBasedPlanner planner;

    public WorkflowController(WorkflowExecutor workflowExecutor, RuleBasedPlanner planner) {
        this.workflowExecutor = workflowExecutor;
        this.planner = planner;
    }

    @PostMapping("/execute")
    public WorkflowExecutionResponse execute(@RequestBody List<WorkflowStep> steps) {
        WorkflowRunResult result = workflowExecutor.execute(steps);
        return new WorkflowExecutionResponse(result.getLogs(), result.getState(), result.getFinalOutput());
    }

    @PostMapping("/run")
    public WorkflowRunResponse run(@RequestBody WorkflowRunRequest request) {
        String goal = request.getGoal();
        List<WorkflowStep> steps = planner.plan(goal);

        WorkflowRunResult result = workflowExecutor.execute(steps);

        return new WorkflowRunResponse(
                goal,
                steps,
                result.getLogs(),
                result.getState(),
                result.getFinalOutput()
        );
    }
}
