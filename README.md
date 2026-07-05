# AI Project Architect

Full-stack app that turns a plain-English product idea into a complete software
architecture blueprint using Google's Gemini AI.

```
ai-project-architect/
├── backend/    Spring Boot 3 + MongoDB + JWT auth + Gemini integration
└── frontend/   React + Vite + Tailwind (drafting-table themed UI)
```

## Run the backend

```bash
cd backend
export GEMINI_API_KEY=your_key_here
export JWT_SECRET=$(openssl rand -base64 32)
mvn spring-boot:run
```

Needs MongoDB running locally (`mongod` or `docker run -d -p 27017:27017 mongo`).
Starts on `http://localhost:8080`. Swagger UI: `http://localhost:8080/swagger-ui.html`.

## Run the frontend

```bash
cd frontend
npm install
cp .env.example .env
npm run dev
```

Starts on `http://localhost:5173` and talks to the backend at `http://localhost:8080`
(configurable via `frontend/.env`).

## Typical flow

1. Start MongoDB, then the backend, then the frontend.
2. Register an account at `http://localhost:5173/register`.
3. Go to **New**, describe a project idea, and generate a blueprint.
4. View, search, edit, or delete your generated blueprints from the dashboard.

See `backend/README.md`-equivalent notes in the original setup message, and
`frontend/README.md` for frontend-specific details.
