# Realworld API Documentation

This directory contains OpenAPI 3.0 specifications for the Realworld API.

## Available Specifications

- [Profiles API](./profiles-api.yaml) - Specification for the `/api/profiles` endpoints

## How to Use

These YAML files can be imported into tools like:

- [Swagger UI](https://swagger.io/tools/swagger-ui/)
- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)

## Authentication

Most endpoints require authentication using JWT tokens. Include the token in the Authorization header:

```
Authorization: Bearer <your_token>
```

## API Overview

The Realworld API follows RESTful principles and returns responses in JSON format.

### Profiles API

The Profiles API allows you to:

- Get a user's profile
- Follow a user
- Unfollow a user

For detailed information, see the [Profiles API specification](./profiles-api.yaml).