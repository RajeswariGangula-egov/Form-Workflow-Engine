package com.example.form_workflow_engine.repository;

import com.example.form_workflow_engine.model.WorkflowInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {
    List<WorkflowInstance> findByCurrentState(String currentState);
}