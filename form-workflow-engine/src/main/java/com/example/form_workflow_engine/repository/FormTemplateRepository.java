package com.example.form_workflow_engine.repository;

import com.example.form_workflow_engine.model.FormTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormTemplateRepository extends JpaRepository<FormTemplate, Long> {
}