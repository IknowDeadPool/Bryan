package com.bryan.planner;

import com.bryan.executor.model.WorkflowStep;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RuleBasedPlanner {

    public List<WorkflowStep> plan(String goal) {
        if (goal == null) goal = "";
        String g = goal.toLowerCase(Locale.ROOT);

        List<WorkflowStep> steps = new ArrayList<>();

        // Rule: if user mentions a file, plan to load it
        String path = extractFilePath(goal);
        if (path != null) {
            steps.add(new WorkflowStep("FileLoader", Map.of("path", path)));
        }

        // Rule: if goal contains "stats" or "count", add TextStats
        if (g.contains("stats") || g.contains("count") || g.contains("words") || g.contains("lines")) {
            steps.add(new WorkflowStep("TextStats", Map.of()));
        }

        // Fallback: if no steps detected, return empty list (later we’ll add AI)
        return steps;
    }

    // Very basic path extraction: finds something like sample.txt or abc.csv
    private String extractFilePath(String goal) {
        if (goal == null) return null;
        // split by spaces and punctuation
        String[] tokens = goal.split("[\\s,]+");
        for (String t : tokens) {
            String token = t.trim().replace("\"", "").replace("'", "");
            if (token.matches(".*\\.(txt|csv|json|md)$")) {
                return token;
            }
        }
        return null;
    }
}
