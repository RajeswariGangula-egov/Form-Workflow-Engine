-- Clear existing data (optional)
TRUNCATE TABLE workflow_instances, form_submissions, workflow_definitions, form_templates RESTART IDENTITY CASCADE;

-- Sample Form Templates
INSERT INTO form_templates (title, schema, created_at, created_by) VALUES 
('Leave Application', 
 '{"title": "Leave Application", "fields": [{"name": "reason", "type": "text", "required": true, "label": "Reason for Leave"}, {"name": "from_date", "type": "date", "required": true, "label": "From Date"}, {"name": "to_date", "type": "date", "required": true, "label": "To Date"}, {"name": "emergency_contact", "type": "text", "required": false, "label": "Emergency Contact"}]}', 
 NOW(), 
 'admin'),

('Expense Report', 
 '{"title": "Expense Report", "fields": [{"name": "description", "type": "text", "required": true, "label": "Expense Description"}, {"name": "amount", "type": "number", "required": true, "label": "Amount"}, {"name": "date", "type": "date", "required": true, "label": "Expense Date"}, {"name": "category", "type": "select", "required": true, "label": "Category", "options": ["Travel", "Meals", "Office Supplies", "Software", "Other"]}, {"name": "receipt_url", "type": "text", "required": false, "label": "Receipt URL"}]}', 
 NOW(), 
 'admin'),

('IT Support Request', 
 '{"title": "IT Support Request", "fields": [{"name": "issue_type", "type": "select", "required": true, "label": "Issue Type", "options": ["Hardware", "Software", "Network", "Access Request", "Other"]}, {"name": "priority", "type": "select", "required": true, "label": "Priority", "options": ["Low", "Medium", "High", "Critical"]}, {"name": "description", "type": "textarea", "required": true, "label": "Description"}, {"name": "affected_systems", "type": "text", "required": false, "label": "Affected Systems"}]}', 
 NOW(), 
 'admin');

-- Sample Workflow Definitions
INSERT INTO workflow_definitions (name, form_template_id, definition, created_at, created_by) VALUES 

('Leave Approval Workflow', 
 1, 
 '{"states": ["Draft", "Manager Review", "HR Review", "Approved", "Rejected"], "transitions": [{"from": "Draft", "to": "Manager Review", "allowed_roles": ["Employee", "Manager"]}, {"from": "Manager Review", "to": "HR Review", "allowed_roles": ["Manager"]}, {"from": "Manager Review", "to": "Rejected", "allowed_roles": ["Manager"]}, {"from": "HR Review", "to": "Approved", "allowed_roles": ["HR"]}, {"from": "HR Review", "to": "Rejected", "allowed_roles": ["HR"]}]}', 
 NOW(), 
 'admin'),

('Expense Approval Workflow', 
 2, 
 '{"states": ["Submitted", "Manager Review", "Finance Review", "Approved", "Rejected"], "transitions": [{"from": "Submitted", "to": "Manager Review", "allowed_roles": ["Employee"]}, {"from": "Manager Review", "to": "Finance Review", "allowed_roles": ["Manager"]}, {"from": "Manager Review", "to": "Rejected", "allowed_roles": ["Manager"]}, {"from": "Finance Review", "to": "Approved", "allowed_roles": ["Finance"]}, {"from": "Finance Review", "to": "Rejected", "allowed_roles": ["Finance"]}]}', 
 NOW(), 
 'admin'),

('IT Support Workflow', 
 3, 
 '{"states": ["Open", "Assigned", "In Progress", "Resolved", "Closed"], "transitions": [{"from": "Open", "to": "Assigned", "allowed_roles": ["IT_Admin"]}, {"from": "Assigned", "to": "In Progress", "allowed_roles": ["IT_Support"]}, {"from": "In Progress", "to": "Resolved", "allowed_roles": ["IT_Support"]}, {"from": "Resolved", "to": "Closed", "allowed_roles": ["Employee", "IT_Support"]}]}', 
 NOW(), 
 'admin');