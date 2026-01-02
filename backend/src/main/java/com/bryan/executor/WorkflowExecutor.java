package com.bryan.executor;

import com.bryan.executor.model.*;
import com.bryan.tools.AutomationTool;
import com.bryan.tools.ToolContext;
import com.bryan.tools.ToolRegistry;
import com.bryan.tools.ToolResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkflowExecutor {

    private final ToolRegistry toolRegistry;

    public WorkflowExecutor(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    public WorkflowRunResult execute(List<WorkflowStep> steps) {
        List<StepLog> logs = new ArrayList<>();
        Map<String, Object> state = new HashMap<>();
        Object finalOutput = null;

        for (int i = 0; i < steps.size(); i++) {
            WorkflowStep step = steps.get(i);
            String stepId = "step-" + (i + 1);

            String toolName = step.getToolName();
            if (toolName == null || toolName.isBlank()) {
                logs.add(new StepLog(stepId, null, ExecutionStatus.FAILED, "Missing toolName"));
                break;
            }

            if (!toolRegistry.contains(toolName)) {
                logs.add(new StepLog(stepId, toolName, ExecutionStatus.FAILED, "Tool not registered: " + toolName));
                break;
            }

            try {
                AutomationTool tool = toolRegistry.get(toolName);
                Map<String, Object> inputs = step.getInputs() == null ? Map.of() : step.getInputs();

                ToolContext ctx = new ToolContext(inputs, state);
                ToolResult result = tool.execute(ctx);

                if (result.isSuccess()) {
                    finalOutput = result.getData();
                    state.put("lastOutput", result.getData());
                    state.put("lastTool", toolName);
                    state.put(toolName + ".output", result.getData());

                    logs.add(new StepLog(stepId, toolName, ExecutionStatus.SUCCESS, "OK"));
                } else {
                    logs.add(new StepLog(stepId, toolName, ExecutionStatus.FAILED,
                            result.getMessage() == null ? "Tool failed" : result.getMessage()));
                    break;
                }

            } catch (Exception e) {
                logs.add(new StepLog(stepId, toolName, ExecutionStatus.FAILED, "Exception: " + e.getMessage()));
                break;
            }
        }

        return new WorkflowRunResult(logs, state, finalOutput);
    }


}
