package com.example.form_workflow_engine.controller;

import com.example.form_workflow_engine.model.WorkflowDefinition;
import com.example.form_workflow_engine.model.WorkflowInstance;
import com.example.form_workflow_engine.model.dto.WorkflowDTO;
import com.example.form_workflow_engine.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    @Autowired
    private WorkflowService workflowService;

    @GetMapping("/definitions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<WorkflowDefinition>> getAllWorkflowDefinitions() {
        List<WorkflowDefinition> workflows = workflowService.getAllWorkflowDefinitions();
        return ResponseEntity.ok(workflows);
    }

    @GetMapping("/definitions/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WorkflowDefinition> getWorkflowDefinitionById(@PathVariable Long id) {
        return workflowService.getWorkflowDefinitionById(id)
                .map(workflow -> ResponseEntity.ok(workflow))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/definitions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WorkflowDefinition> createWorkflowDefinition(
            @Valid @RequestBody WorkflowDTO dto,
            Authentication authentication) {

        String username = authentication.getName();
        WorkflowDefinition workflow = workflowService.createWorkflowDefinition(dto, username);
        return ResponseEntity.ok(workflow);
    }

    @GetMapping("/instances")
    public ResponseEntity<List<WorkflowInstance>> getAllWorkflowInstances() {
        List<WorkflowInstance> instances = workflowService.getAllWorkflowInstances();
        return ResponseEntity.ok(instances);
    }

    @GetMapping("/instances/{id}")
    public ResponseEntity<WorkflowInstance> getWorkflowInstanceById(@PathVariable Long id) {
        return workflowService.getWorkflowInstanceById(id)
                .map(instance -> ResponseEntity.ok(instance))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/instances/{id}/transition")
    public ResponseEntity<WorkflowInstance> transitionWorkflow(
            @PathVariable Long id,
            @RequestBody Map<String, String> request,
            Authentication authentication) {

        try {
            String toState = request.get("toState");
            String username = authentication.getName();
            List<String> userRoles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(role -> role.replace("ROLE_", ""))
                    .collect(Collectors.toList());

            WorkflowInstance instance = workflowService.transitionWorkflow(id, toState, username, userRoles);
            return ResponseEntity.ok(instance);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}