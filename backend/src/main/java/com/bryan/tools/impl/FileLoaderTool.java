package com.bryan.tools.impl;

import com.bryan.tools.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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

            byte[] bytes = Files.readAllBytes(p);
            if (bytes.length == 0) return ToolResult.success("");

            // Detect BOM (Byte Order Mark)
            // UTF-16 LE BOM: FF FE
            // UTF-16 BE BOM: FE FF
            // UTF-8 BOM: EF BB BF
            Charset cs = StandardCharsets.UTF_8;
            int offset = 0;

            if (bytes.length >= 2) {
                int b0 = bytes[0] & 0xFF;
                int b1 = bytes[1] & 0xFF;

                if (b0 == 0xFF && b1 == 0xFE) {          // UTF-16 LE
                    cs = StandardCharsets.UTF_16LE;
                    offset = 2;
                } else if (b0 == 0xFE && b1 == 0xFF) {   // UTF-16 BE
                    cs = StandardCharsets.UTF_16BE;
                    offset = 2;
                }
            }
            if (bytes.length >= 3) {
                int b0 = bytes[0] & 0xFF;
                int b1 = bytes[1] & 0xFF;
                int b2 = bytes[2] & 0xFF;
                if (b0 == 0xEF && b1 == 0xBB && b2 == 0xBF) { // UTF-8 BOM
                    cs = StandardCharsets.UTF_8;
                    offset = 3;
                }
            }

            String content = new String(bytes, offset, bytes.length - offset, cs);
            return ToolResult.success(content);

        } catch (Exception e) {
            return ToolResult.failure(
                    "Failed to load file. receivedPath='" + pathStr + "'" +
                            " userDir='" + System.getProperty("user.dir") + "'" +
                            " exception=" + e.getClass().getName() +
                            " error=" + e.getMessage()
            );
        }
    }

    @Override
    public String getDescription() {
        return "Loads a text file from disk and outputs its contents as a string. BOM/encoding-aware.";
    }

    @Override
    public java.util.List<ToolInputSpec> getInputSpec() {
        return java.util.List.of(
                new ToolInputSpec("path", true, "string", "Path to the file (relative to working dir or absolute).")
        );
    }

}
