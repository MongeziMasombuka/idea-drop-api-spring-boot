Idea Drop API

Version: 1.0
Description: API secured with Bearer token (JWT authentication)

Base URL: http://localhost:8080

Authentication

This API uses Bearer token authentication. Include the JWT token in the Authorization header for all protected endpoints:

Authorization: Bearer <your_token_here>

Endpoints
Auth
Register a new user

POST /api/v1/auth/register

Request Body:

{
  "username": "johndoe",
  "email": "johndoe@example.com",
  "password": "password123"
}


Response:

User registered successfully

Login a user

POST /api/v1/auth/login

Request Body:

{
  "email": "johndoe@example.com",
  "password": "password123"
}


Response:

{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "johndoe@example.com"
}

Logout

POST /api/v1/auth/logout

Request Body: None

Response:

Logged out successfully

Refresh token

POST /api/v1/auth/refresh

Request Body: None

Response:

{
  "accessToken": "new_jwt_token_here",
  "email": "johndoe@example.com"
}

Ideas
Get all ideas

GET /api/v1/ideas

Response:

[
  {
    "title": "New App Idea",
    "summary": "An app to track ideas",
    "description": "This app helps users manage their ideas effectively.",
    "tags": ["productivity", "ideas"],
    "userId": "b7f6c9d1-2c3e-4f91-8f36-12b3c4d2a9e1"
  }
]

Create a new idea

POST /api/v1/ideas

Request Body:

{
  "title": "New App Idea",
  "summary": "An app to track ideas",
  "description": "This app helps users manage their ideas effectively.",
  "tags": ["productivity", "ideas"]
}


Response:

{
  "title": "New App Idea",
  "summary": "An app to track ideas",
  "description": "This app helps users manage their ideas effectively.",
  "tags": ["productivity", "ideas"],
  "userId": "b7f6c9d1-2c3e-4f91-8f36-12b3c4d2a9e1"
}

Get idea by ID

GET /api/v1/ideas/{id}

Response:

{
  "title": "New App Idea",
  "summary": "An app to track ideas",
  "description": "This app helps users manage their ideas effectively.",
  "tags": ["productivity", "ideas"],
  "userId": "b7f6c9d1-2c3e-4f91-8f36-12b3c4d2a9e1"
}

Update an idea

PUT /api/v1/ideas/{id}

Request Body:

{
  "title": "Updated App Idea",
  "summary": "An improved idea tracker",
  "description": "The app now allows collaboration and tagging.",
  "tags": ["productivity", "collaboration"]
}


Response:

{
  "title": "Updated App Idea",
  "summary": "An improved idea tracker",
  "description": "The app now allows collaboration and tagging.",
  "tags": ["productivity", "collaboration"],
  "userId": "b7f6c9d1-2c3e-4f91-8f36-12b3c4d2a9e1"
}

Delete an idea

DELETE /api/v1/ideas/{id}

Response:

Idea deleted successfully

Schemas
IdeaRequest
{
  "title": "string",
  "summary": "string",
  "description": "string",
  "tags": ["string"]
}

IdeaResponse
{
  "title": "string",
  "summary": "string",
  "description": "string",
  "tags": ["string"],
  "userId": "uuid"
}

RegisterRequest
{
  "username": "string",
  "email": "string",
  "password": "string"
}

AuthRequest
{
  "email": "string",
  "password": "string"
}

AuthResponse
{
  "accessToken": "string",
  "email": "string"
}

Security

All /api/v1/ideas endpoints require a Bearer JWT token.

Auth endpoints (register, login) do not require a token.
