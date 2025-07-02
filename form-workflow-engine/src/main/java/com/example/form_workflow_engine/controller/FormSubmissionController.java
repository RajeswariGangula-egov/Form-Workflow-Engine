package com.example.form_workflow_engine.controller;

import com.example.form_workflow_engine.model.FormSubmission;
import com.example.form_workflow_engine.model.dto.SubmissionDTO;
import com.example.form_workflow_engine.service.FormSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class FormSubmissionController {

    @Autowired
    private FormSubmissionService formSubmissionService;

    @GetMapping
    public ResponseEntity<List<FormSubmission>> getAllSubmissions() {
        List<FormSubmission> submissions = formSubmissionService.getAllSubmissions();
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormSubmission> getSubmissionById(@PathVariable Long id) {
        return formSubmissionService.getSubmissionById(id)
                .map(submission -> ResponseEntity.ok(submission))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FormSubmission> submitForm(
            @Valid @RequestBody SubmissionDTO dto,
            Authentication authentication) {

        String username = authentication.getName();
        FormSubmission submission = formSubmissionService.submitForm(dto, username);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/my-submissions")
    public ResponseEntity<List<FormSubmission>> getMySubmissions(Authentication authentication) {
        String username = authentication.getName();
        List<FormSubmission> submissions = formSubmissionService.getSubmissionsByUser(username);
        return ResponseEntity.ok(submissions);
    }
}