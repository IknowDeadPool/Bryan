//package com.bryan.planner;
//
//import com.bryan.executor.model.WorkflowStep;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//public class RuleBasedPlanner {
//
//    public List<WorkflowStep> plan(String goal) {
//        if (goal == null) goal = "";
//        String g = goal.toLowerCase(Locale.ROOT);
//
//        List<WorkflowStep> steps = new ArrayList<>();
//
//        // Rule: if user mentions a file, plan to load it
//        String path = extractFilePath(goal);
//        if (path != null) {
//            steps.add(new WorkflowStep("FileLoader", Map.of("path", path)));
//        }
//
//        // Rule: if goal contains "stats" or "count", add TextStats
//        if (g.contains("stats") || g.contains("count") || g.contains("words") || g.contains("lines")) {
//            steps.add(new WorkflowStep("TextStats", Map.of()));
//        }
//
//        // Fallback: if no steps detected, return empty list (later we’ll add AI)
//        return steps;
//    }
//
//    // Very basic path extraction: finds something like sample.txt or abc.csv
//    private String extractFilePath(String goal) {
//        if (goal == null) return null;
//        // split by spaces and punctuation
//        String[] tokens = goal.split("[\\s,]+");
//        for (String t : tokens) {
//            String token = t.trim().replace("\"", "").replace("'", "");
//            if (token.matches(".*\\.(txt|csv|json|md)$")) {
//                return token;
//            }
//        }
//        return null;
//    }
//}




package com.bryan.planner;

import com.bryan.executor.model.WorkflowStep;
import com.bryan.tools.AutomationTool;
import com.bryan.tools.ToolInputSpec;
import com.bryan.tools.ToolRegistry;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RuleBasedPlanner {

    private final ToolRegistry toolRegistry;

    public RuleBasedPlanner(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    public List<WorkflowStep> plan(String goal) {
        if (goal == null) goal = "";
        String g = goal.toLowerCase(Locale.ROOT);

        // Pull available tools dynamically
        Collection<AutomationTool> tools = toolRegistry.all().values();

        List<WorkflowStep> steps = new ArrayList<>();

        // 1) If the goal mentions a file, choose a tool that expects "path"
        String path = extractFilePath(goal);
        if (path != null) {
            AutomationTool fileTool = findToolRequiringInput(tools, "path");
            if (fileTool != null) {
                steps.add(new WorkflowStep(fileTool.getName(), Map.of("path", path)));
            }
        }

        // 2) If the goal mentions stats/count/words/lines, choose a "stats-like" tool
        if (containsAny(g, "stats", "count", "words", "lines", "characters", "chars")) {
            AutomationTool statsTool = findToolByKeyword(tools, List.of("stats", "words", "lines", "chars", "character"));
            if (statsTool != null) {
                steps.add(new WorkflowStep(statsTool.getName(), Map.of()));
            }
        }

        return steps;
    }

    private AutomationTool findToolRequiringInput(Collection<AutomationTool> tools, String inputName) {
        for (AutomationTool t : tools) {
            for (ToolInputSpec spec : t.getInputSpec()) {
                if (spec.getName().equalsIgnoreCase(inputName) && spec.isRequired()) {
                    return t;
                }
            }
        }
        return null;
    }

    private AutomationTool findToolByKeyword(Collection<AutomationTool> tools, List<String> keywords) {
        for (AutomationTool t : tools) {
            String hay = (t.getName() + " " + t.getDescription()).toLowerCase(Locale.ROOT);
            for (String kw : keywords) {
                if (hay.contains(kw)) return t;
            }
        }
        return null;
    }

    private boolean containsAny(String text, String... kws) {
        for (String kw : kws) if (text.contains(kw)) return true;
        return false;
    }

    // Basic file token finder: sample.txt, data.csv, notes.md, config.json
    private String extractFilePath(String goal) {
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

