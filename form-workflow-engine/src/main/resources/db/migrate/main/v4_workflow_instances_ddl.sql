CREATE TABLE workflow_instances (
    id BIGSERIAL PRIMARY KEY,
    workflow_definition_id BIGINT NOT NULL,
    form_submission_id BIGINT NOT NULL,
    current_state VARCHAR(255) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    FOREIGN KEY (workflow_definition_id) REFERENCES workflow_definitions(id),
    FOREIGN KEY (form_submission_id) REFERENCES form_submissions(id)
);
