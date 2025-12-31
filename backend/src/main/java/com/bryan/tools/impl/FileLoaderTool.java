package com.bryan.tools.impl;

import com.bryan.tools.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileLoaderTool implements AutomationTool {

    @Override
    public String getName() {
        return "FileLoader";
    }

    @Override
    public ToolResult execute(ToolContext context) {
        String pathStr = context.getString("path");

        try {
            if (pathStr == null || pathStr.isBlank()) {
                return ToolResult.failure("Missing required input: path");
            }

            Path p = Path.of(pathStr);
            if (!p.isAbsolute()) {
                p = Path.of(System.getProperty("user.dir")).resolve(pathStr);
            }
            p = p.normalize();

            if (!Files.exists(p)) {
                return ToolResult.failure("File not found at: " + p.toAbsolutePath());
            }

            // Try common charsets (UTF-8 first, then Windows-friendly fallbacks)
            List<Charset> charsets = List.of(
                    StandardCharsets.UTF_8,
                    Charset.forName("windows-1252"),
                    StandardCharsets.ISO_8859_1
            );

            Exception last = null;
            for (Charset cs : charsets) {
                try {
                    String content = Files.readString(p, cs);
                    return ToolResult.success(content);
                } catch (Exception e) {
                    last = e;
                }
            }

            return ToolResult.failure("Failed to read file with supported encodings. Last error: " +
                    (last == null ? "unknown" : last.getClass().getName() + " " + last.getMessage()));

        } catch (Exception e) {
            return ToolResult.failure(
                    "Failed to load file. receivedPath='" + pathStr + "'" +
                            " userDir='" + System.getProperty("user.dir") + "'" +
                            " exception=" + e.getClass().getName() +
                            " error=" + e.getMessage()
            );
        }
    }
}
