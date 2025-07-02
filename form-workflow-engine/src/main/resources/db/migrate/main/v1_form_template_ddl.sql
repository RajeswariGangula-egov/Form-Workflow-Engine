CREATE TABLE form_templates (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    schema TEXT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255)
);