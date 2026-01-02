package com.bryan.api;

import com.bryan.tools.AutomationTool;
import com.bryan.tools.ToolRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ToolsController {

    private final ToolRegistry toolRegistry;

    public ToolsController(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    @GetMapping("/api/tools")
    public List<Map<String, Object>> listTools() {
        return toolRegistry.all().values().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private Map<String, Object> toDto(AutomationTool tool) {
        return Map.of(
                "name", tool.getName(),
                "description", tool.getDescription(),
                "inputs", tool.getInputSpec()
        );
    }
}
