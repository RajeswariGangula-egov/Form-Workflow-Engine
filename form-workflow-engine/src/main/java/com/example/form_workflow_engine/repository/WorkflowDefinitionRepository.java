package com.example.form_workflow_engine.repository;

import com.example.form_workflow_engine.model.WorkflowDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinition, Long> {
    Optional<WorkflowDefinition> findByFormTemplateId(Long formTemplateId);
}