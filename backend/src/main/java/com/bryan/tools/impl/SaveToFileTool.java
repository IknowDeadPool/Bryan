package com.bryan.tools.impl;

import com.bryan.tools.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class SaveToFileTool implements AutomationTool {

    @Override
    public String getName() {
        return "SaveToFile";
    }

    @Override
    public String getDescription() {
        return "Saves text (or previous step output) to a file on disk.";
    }

    @Override
    public List<ToolInputSpec> getInputSpec() {
        return List.of(
                new ToolInputSpec("path", true, "string", "Output file path (relative to working dir or absolute)."),
                new ToolInputSpec("text", false, "string", "Optional text to save. If omitted, saves lastOutput.")
        );
    }

    @Override
    public ToolResult execute(ToolContext context) {
        try {
            String outPath = context.getString("path");
            if (outPath == null || outPath.isBlank()) {
                return ToolResult.failure("Missing required input: path");
            }

            Object textObj = context.get("text");
            if (textObj == null) {
                textObj = context.state().get("lastOutput");
            }
            if (textObj == null) {
                return ToolResult.failure("Nothing to save: provide 'text' or run after a tool that produces output.");
            }

            String content;
            if (textObj instanceof String) {
                content = (String) textObj;
            } else {
                // If it's a Map/List/etc, write pretty JSON
                var mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                content = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(textObj);
            }

            Path p = Path.of(outPath);
            if (!p.isAbsolute()) {
                p = Path.of(System.getProperty("user.dir")).resolve(outPath);
            }
            p = p.normalize();

            // Ensure parent directories exist
            Path parent = p.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            Files.writeString(p, content, StandardCharsets.UTF_8);
            return ToolResult.success("Saved to: " + p.toAbsolutePath());

        } catch (Exception e) {
            return ToolResult.failure("Failed to save file: " + e.getMessage());
        }
    }
}
