CREATE INDEX idx_workflow_def_form_template ON workflow_definitions(form_template_id);
CREATE INDEX idx_form_sub_template ON form_submissions(form_template_id);
CREATE INDEX idx_form_sub_user ON form_submissions(submitted_by);
CREATE INDEX idx_workflow_inst_state ON workflow_instances(current_state);
CREATE INDEX idx_workflow_inst_def ON workflow_instances(workflow_definition_id);