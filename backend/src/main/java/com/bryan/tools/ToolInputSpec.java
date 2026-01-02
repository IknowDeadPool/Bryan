package com.bryan.tools;

public class ToolInputSpec {
    private String name;
    private boolean required;
    private String type;
    private String description;

    public ToolInputSpec() {}

    public ToolInputSpec(String name, boolean required, String type, String description) {
        this.name = name;
        this.required = required;
        this.type = type;
        this.description = description;
    }

    public String getName() { return name; }
    public boolean isRequired() { return required; }
    public String getType() { return type; }
    public String getDescription() { return description; }
}
