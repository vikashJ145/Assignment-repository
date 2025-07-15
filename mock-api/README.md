# Mock API Setup

This mock API uses JSON Server to provide a local REST API with full CRUD support for users.

## Setup Instructions

1. Install JSON Server globally if not already installed:

```
npm install -g json-server
```

2. Navigate to the `mock-api` directory:

```
cd mock-api
```

3. Start the JSON Server:

```
json-server --watch db.json --port 3000
```

The API will be available at `http://localhost:3000`.

## Endpoints

- GET /users
- GET /users/{id}
- POST /users
- PUT /users/{id}
- DELETE /users/{id}

Use this local API for running the automated tests for full CRUD support.
