CREATE TABLE form_submissions (
    id BIGSERIAL PRIMARY KEY,
    form_template_id BIGINT NOT NULL,
    data TEXT NOT NULL,
    submitted_by VARCHAR(255) NOT NULL,
    submitted_at TIMESTAMP,
    FOREIGN KEY (form_template_id) REFERENCES form_templates(id)
);
