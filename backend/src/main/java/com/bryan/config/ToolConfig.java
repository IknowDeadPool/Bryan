package com.bryan.config;

import com.bryan.tools.ToolRegistry;
import com.bryan.tools.impl.FileLoaderTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.bryan.tools.impl.TextStatsTool;
@Configuration
public class ToolConfig {

    @Bean
    public ToolRegistry toolRegistry() {
        ToolRegistry registry = new ToolRegistry();
        registry.register(new FileLoaderTool());
        registry.register(new TextStatsTool());
        return registry;
    }
}
