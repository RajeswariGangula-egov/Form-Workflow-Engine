package com.example.form_workflow_engine.controller;

import com.example.form_workflow_engine.model.FormTemplate;
import com.example.form_workflow_engine.model.dto.FormTemplateDTO;
import com.example.form_workflow_engine.service.FormTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/forms")
public class FormTemplateController {

    @Autowired
    private FormTemplateService formTemplateService;

    @GetMapping
    public ResponseEntity<List<FormTemplate>> getAllFormTemplates() {
        List<FormTemplate> templates = formTemplateService.getAllFormTemplates();
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormTemplate> getFormTemplateById(@PathVariable Long id) {
        return formTemplateService.getFormTemplateById(id)
                .map(template -> ResponseEntity.ok(template))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FormTemplate> createFormTemplate(
            @Valid @RequestBody FormTemplateDTO dto,
            Authentication authentication) {

        String username = authentication.getName();
        FormTemplate template = formTemplateService.createFormTemplate(dto, username);
        return ResponseEntity.ok(template);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FormTemplate> updateFormTemplate(
            @PathVariable Long id,
            @Valid @RequestBody FormTemplateDTO dto,
            Authentication authentication) {

        try {
            String username = authentication.getName();
            FormTemplate template = formTemplateService.updateFormTemplate(id, dto, username);
            return ResponseEntity.ok(template);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFormTemplate(@PathVariable Long id) {
        formTemplateService.deleteFormTemplate(id);
        return ResponseEntity.noContent().build();
    }
}