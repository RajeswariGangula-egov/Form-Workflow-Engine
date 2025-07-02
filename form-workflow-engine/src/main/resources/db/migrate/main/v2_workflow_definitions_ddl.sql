CREATE TABLE workflow_definitions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    form_template_id BIGINT,
    definition TEXT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    FOREIGN KEY (form_template_id) REFERENCES form_templates(id)
);
