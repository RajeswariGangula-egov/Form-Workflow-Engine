package com.example.form_workflow_engine.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "form_submissions")
public class FormSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form_template_id", nullable = false)
    private Long formTemplateId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String data; // JSON form data

    @Column(name = "submitted_by", nullable = false)
    private String submittedBy;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    // Constructors
    public FormSubmission() {
        this.submittedAt = LocalDateTime.now();
    }

    public FormSubmission(Long formTemplateId, String data, String submittedBy) {
        this();
        this.formTemplateId = formTemplateId;
        this.data = data;
        this.submittedBy = submittedBy;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFormTemplateId() { return formTemplateId; }
    public void setFormTemplateId(Long formTemplateId) { this.formTemplateId = formTemplateId; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }
}