package com.example.form_workflow_engine.repository;

import com.example.form_workflow_engine.model.FormSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FormSubmissionRepository extends JpaRepository<FormSubmission, Long> {
    List<FormSubmission> findBySubmittedBy(String submittedBy);
}