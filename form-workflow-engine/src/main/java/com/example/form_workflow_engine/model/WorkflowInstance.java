package com.example.form_workflow_engine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_instances")
public class WorkflowInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workflow_definition_id", nullable = false)
    private Long workflowDefinitionId;

    @Column(name = "form_submission_id", nullable = false)
    private Long formSubmissionId;

    @Column(name = "current_state", nullable = false)
    private String currentState;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    // Constructors
    public WorkflowInstance() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public WorkflowInstance(Long workflowDefinitionId, Long formSubmissionId, String currentState) {
        this();
        this.workflowDefinitionId = workflowDefinitionId;
        this.formSubmissionId = formSubmissionId;
        this.currentState = currentState;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getWorkflowDefinitionId() { return workflowDefinitionId; }
    public void setWorkflowDefinitionId(Long workflowDefinitionId) { this.workflowDefinitionId = workflowDefinitionId; }

    public Long getFormSubmissionId() { return formSubmissionId; }
    public void setFormSubmissionId(Long formSubmissionId) { this.formSubmissionId = formSubmissionId; }

    public String getCurrentState() { return currentState; }
    public void setCurrentState(String currentState) { this.currentState = currentState; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
}