package com.example.form_workflow_engine.service;

import com.example.form_workflow_engine.model.WorkflowDefinition;
import com.example.form_workflow_engine.model.WorkflowInstance;
import com.example.form_workflow_engine.model.dto.WorkflowDTO;
import com.example.form_workflow_engine.repository.WorkflowDefinitionRepository;
import com.example.form_workflow_engine.repository.WorkflowInstanceRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkflowService {

    @Autowired
    private WorkflowDefinitionRepository workflowDefinitionRepository;

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<WorkflowDefinition> getAllWorkflowDefinitions() {
        return workflowDefinitionRepository.findAll();
    }

    public Optional<WorkflowDefinition> getWorkflowDefinitionById(Long id) {
        return workflowDefinitionRepository.findById(id);
    }

    public WorkflowDefinition createWorkflowDefinition(WorkflowDTO dto, String createdBy) {
        WorkflowDefinition workflow = new WorkflowDefinition(
                dto.getName(),
                dto.getFormTemplateId(),
                dto.getDefinition(),
                createdBy
        );
        return workflowDefinitionRepository.save(workflow);
    }

    public WorkflowInstance createWorkflowInstance(Long workflowDefinitionId, Long formSubmissionId) {
        Optional<WorkflowDefinition> workflowDef = workflowDefinitionRepository.findById(workflowDefinitionId);
        if (workflowDef.isPresent()) {
            try {
                JsonNode definition = objectMapper.readTree(workflowDef.get().getDefinition());
                String initialState = definition.get("states").get(0).asText();

                WorkflowInstance instance = new WorkflowInstance(workflowDefinitionId, formSubmissionId, initialState);
                return workflowInstanceRepository.save(instance);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing workflow definition", e);
            }
        }
        throw new RuntimeException("Workflow definition not found with ID: " + workflowDefinitionId);
    }

    public WorkflowInstance transitionWorkflow(Long instanceId, String toState, String username, List<String> userRoles) {
        Optional<WorkflowInstance> instanceOpt = workflowInstanceRepository.findById(instanceId);
        if (instanceOpt.isEmpty()) {
            throw new RuntimeException("Workflow instance not found with ID: " + instanceId);
        }

        WorkflowInstance instance = instanceOpt.get();
        String fromState = instance.getCurrentState();

        Optional<WorkflowDefinition> workflowDefOpt = workflowDefinitionRepository.findById(instance.getWorkflowDefinitionId());
        if (workflowDefOpt.isEmpty()) {
            throw new RuntimeException("Workflow definition not found for instance ID: " + instanceId);
        }

        String definition = workflowDefOpt.get().getDefinition();

        // Debug logs
        System.out.println("Attempting transition:");
        System.out.println(" - From: " + fromState);
        System.out.println(" - To: " + toState);
        System.out.println(" - User: " + username);
        System.out.println(" - Roles: " + userRoles);

        if (isTransitionAllowed(definition, fromState, toState, userRoles)) {
            instance.setCurrentState(toState);
            instance.setUpdatedBy(username);
            instance.setUpdatedAt(LocalDateTime.now());

            return workflowInstanceRepository.save(instance);
        } else {
            throw new RuntimeException("❌ Transition not allowed from '" + fromState + "' to '" + toState +
                    "' for roles: " + userRoles);
        }
    }

    private boolean isTransitionAllowed(String workflowDefinition, String fromState, String toState, List<String> userRoles) {
        try {
            JsonNode definition = objectMapper.readTree(workflowDefinition);
            JsonNode transitions = definition.get("transitions");

            if (transitions == null || !transitions.isArray()) {
                System.err.println("⚠️ No 'transitions' node found or not an array in workflow definition.");
                return false;
            }

            for (JsonNode transition : transitions) {
                String from = transition.get("from").asText();
                String to = transition.get("to").asText();
                System.out.println("Checking transition: " + from + " -> " + to);

                if (from.equalsIgnoreCase(fromState) && to.equalsIgnoreCase(toState)) {
                    JsonNode allowedRoles = transition.get("allowed_roles");
                    if (allowedRoles != null && allowedRoles.isArray()) {
                        for (JsonNode roleNode : allowedRoles) {
                            String allowedRole = roleNode.asText();
                            for (String userRole : userRoles) {
                                if (userRole.equalsIgnoreCase(allowedRole)) {
                                    System.out.println("✅ Role matched: " + userRole);
                                    return true;
                                }
                            }
                        }
                        System.out.println("❌ No matching role found for transition.");
                    } else {
                        System.err.println("⚠️ 'allowed_roles' is missing or not an array.");
                    }
                }
            }

            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error validating transition from '" + fromState + "' to '" + toState + "'", e);
        }
    }

    public List<WorkflowInstance> getAllWorkflowInstances() {
        return workflowInstanceRepository.findAll();
    }

    public Optional<WorkflowInstance> getWorkflowInstanceById(Long id) {
        return workflowInstanceRepository.findById(id);
    }
}
