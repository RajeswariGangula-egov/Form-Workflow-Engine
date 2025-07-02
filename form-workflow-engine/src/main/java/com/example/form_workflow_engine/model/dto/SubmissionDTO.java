package com.example.form_workflow_engine.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubmissionDTO {
    @NotNull(message = "Form template ID is required")
    private Long formTemplateId;

    @NotBlank(message = "Data is required")
    private String data;

    // Constructors
    public SubmissionDTO() {}

    public SubmissionDTO(Long formTemplateId, String data) {
        this.formTemplateId = formTemplateId;
        this.data = data;
    }

    // Getters and Setters
    public Long getFormTemplateId() { return formTemplateId; }
    public void setFormTemplateId(Long formTemplateId) { this.formTemplateId = formTemplateId; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}