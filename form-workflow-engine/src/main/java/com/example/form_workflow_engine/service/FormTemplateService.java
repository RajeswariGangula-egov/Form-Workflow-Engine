package com.example.form_workflow_engine.service;

import com.example.form_workflow_engine.model.FormTemplate;
import com.example.form_workflow_engine.model.dto.FormTemplateDTO;
import com.example.form_workflow_engine.repository.FormTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FormTemplateService {

    @Autowired
    private FormTemplateRepository formTemplateRepository;

    public List<FormTemplate> getAllFormTemplates() {
        return formTemplateRepository.findAll();
    }

    public Optional<FormTemplate> getFormTemplateById(Long id) {
        return formTemplateRepository.findById(id);
    }

    public FormTemplate createFormTemplate(FormTemplateDTO dto, String createdBy) {
        FormTemplate formTemplate = new FormTemplate(dto.getTitle(), dto.getSchema(), createdBy);
        return formTemplateRepository.save(formTemplate);
    }

    public FormTemplate updateFormTemplate(Long id, FormTemplateDTO dto, String updatedBy) {
        Optional<FormTemplate> existingTemplate = formTemplateRepository.findById(id);
        if (existingTemplate.isPresent()) {
            FormTemplate template = existingTemplate.get();
            template.setTitle(dto.getTitle());
            template.setSchema(dto.getSchema());
            return formTemplateRepository.save(template);
        }
        throw new RuntimeException("Form template not found with id: " + id);
    }

    public void deleteFormTemplate(Long id) {
        formTemplateRepository.deleteById(id);
    }
}