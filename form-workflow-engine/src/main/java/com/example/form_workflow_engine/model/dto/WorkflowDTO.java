package com.example.form_workflow_engine.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WorkflowDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Form template ID is required")
    private Long formTemplateId;

    @NotBlank(message = "Definition is required")
    private String definition;

    // Constructors
    public WorkflowDTO() {}

    public WorkflowDTO(String name, Long formTemplateId, String definition) {
        this.name = name;
        this.formTemplateId = formTemplateId;
        this.definition = definition;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getFormTemplateId() { return formTemplateId; }
    public void setFormTemplateId(Long formTemplateId) { this.formTemplateId = formTemplateId; }

    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }
}