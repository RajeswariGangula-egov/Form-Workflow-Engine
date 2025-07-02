# Form-Workflow-Engine
#  Configurable Form and Workflow Engine

A Spring Boot-based backend system to manage dynamic forms and workflows with role-based transitions using Keycloak.

## Features

- Define form templates via JSON schema
- Configure workflows as state machines
- Trigger workflows on form submission
- Secure role-based transitions with Keycloak

##  Tech Stack

- Java 17, Spring Boot, Spring Security
- PostgreSQL, JPA (Hibernate)
- Keycloak for authentication & role management



**Form Template:**
```json
{
  "title": "Leave Application",
  "fields": [
    { "name": "reason", "type": "text", "required": true },
    { "name": "from_date", "type": "date" },
    { "name": "to_date", "type": "date" }
  ]
}
