package com.bryan.config;

import com.bryan.tools.ToolRegistry;
import com.bryan.tools.impl.FileLoaderTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bryan.tools.impl.TextStatsTool;
import com.bryan.tools.impl.SaveToFileTool;
@Configuration
public class ToolConfig {

    @Bean
    public ToolRegistry toolRegistry() {
        ToolRegistry registry = new ToolRegistry();
        registry.register(new FileLoaderTool());
        registry.register(new TextStatsTool());
        registry.register(new SaveToFileTool());
        return registry;
    }
}
