package com.example.form_workflow_engine.service;

import com.example.form_workflow_engine.model.FormSubmission;
import com.example.form_workflow_engine.model.WorkflowDefinition;
import com.example.form_workflow_engine.model.WorkflowInstance;
import com.example.form_workflow_engine.model.dto.SubmissionDTO;
import com.example.form_workflow_engine.repository.FormSubmissionRepository;
import com.example.form_workflow_engine.repository.WorkflowDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FormSubmissionService {

    @Autowired
    private FormSubmissionRepository formSubmissionRepository;

    @Autowired
    private WorkflowDefinitionRepository workflowDefinitionRepository;

    @Autowired
    private WorkflowService workflowService;

    public List<FormSubmission> getAllSubmissions() {
        return formSubmissionRepository.findAll();
    }

    public Optional<FormSubmission> getSubmissionById(Long id) {
        return formSubmissionRepository.findById(id);
    }

    public FormSubmission submitForm(SubmissionDTO dto, String submittedBy) {
        // Save form submission
        FormSubmission submission = new FormSubmission(dto.getFormTemplateId(), dto.getData(), submittedBy);
        submission = formSubmissionRepository.save(submission);

        // Find associated workflow and create instance
        Optional<WorkflowDefinition> workflowDef = workflowDefinitionRepository.findByFormTemplateId(dto.getFormTemplateId());
        if (workflowDef.isPresent()) {
            workflowService.createWorkflowInstance(workflowDef.get().getId(), submission.getId());
        }

        return submission;
    }

    public List<FormSubmission> getSubmissionsByUser(String username) {
        return formSubmissionRepository.findBySubmittedBy(username);
    }
}