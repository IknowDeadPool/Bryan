package com.bryan.api;

import com.bryan.planner.RuleBasedPlanner;
import com.bryan.planner.model.PlanRequest;
import com.bryan.planner.model.PlanResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
public class PlannerController {

    private final RuleBasedPlanner planner;

    public PlannerController(RuleBasedPlanner planner) {
        this.planner = planner;
    }

    @PostMapping("/plan")
    public PlanResponse plan(@RequestBody PlanRequest request) {
        return new PlanResponse(planner.plan(request.getGoal()));
    }
}
