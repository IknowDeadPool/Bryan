package com.bryan.tools.impl;

import com.bryan.tools.*;

import java.util.HashMap;
import java.util.Map;

public class TextStatsTool implements AutomationTool {

    @Override
    public String getName() {
        return "TextStats";
    }

    @Override
    public ToolResult execute(ToolContext context) {
        // Prefer explicit input, fallback to lastOutput from state
        Object textObj = context.get("text");
        if (textObj == null) {
            textObj = context.state().get("lastOutput");
        }

        if (textObj == null) {
            return ToolResult.failure("Missing input: provide 'text' or run after a tool that produces text.");
        }

        String text = textObj.toString();

        int charCount = text.length();
        int lineCount = text.isEmpty() ? 0 : text.split("\\R", -1).length;
        int wordCount = text.isBlank() ? 0 : text.trim().split("\\s+").length;

        Map<String, Object> stats = new HashMap<>();
        stats.put("chars", charCount);
        stats.put("words", wordCount);
        stats.put("lines", lineCount);

        return ToolResult.success(stats);
    }
}
