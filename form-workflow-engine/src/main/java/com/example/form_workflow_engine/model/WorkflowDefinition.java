package com.example.form_workflow_engine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_definitions")
public class WorkflowDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "form_template_id")
    private Long formTemplateId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String definition; // JSON workflow definition

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    // Constructors
    public WorkflowDefinition() {
        this.createdAt = LocalDateTime.now();
    }

    public WorkflowDefinition(String name, Long formTemplateId, String definition, String createdBy) {
        this();
        this.name = name;
        this.formTemplateId = formTemplateId;
        this.definition = definition;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getFormTemplateId() { return formTemplateId; }
    public void setFormTemplateId(Long formTemplateId) { this.formTemplateId = formTemplateId; }

    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}