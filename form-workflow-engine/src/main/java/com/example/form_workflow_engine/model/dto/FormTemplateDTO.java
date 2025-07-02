package com.example.form_workflow_engine.model.dto;

import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FormTemplateDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Schema is required")
    private String schema;

    // Constructors
    public FormTemplateDTO() {}

    public FormTemplateDTO(String title, String schema) {
        this.title = title;
        this.schema = schema;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSchema() { return schema; }
    public void setSchema(String schema) { this.schema = schema; }
}