# AI Project Architect — Frontend

A React + Vite + Tailwind frontend for the `ai-project-architect-backend` Spring Boot API.
Themed as a drafting table: generated architectures render as numbered engineering-drawing
sheets with a title block (drawn by / sheet no. / issued / revised).

## Setup

```bash
npm install
cp .env.example .env    # point VITE_API_BASE_URL at your backend if not localhost:8080
npm run dev
```

Opens on `http://localhost:5173`. Make sure the backend is running (default `http://localhost:8080`)
and that `app.cors.allowed-origins` on the backend includes `http://localhost:5173`.

## Pages

- `/` — landing page
- `/login`, `/register` — auth
- `/dashboard` — list + search your generated projects
- `/generate` — form to request a new AI-generated architecture
- `/projects/:id` — full blueprint view (all 17 architecture sections), with edit/delete

## Build

```bash
npm run build
npm run preview
```

Outputs static files to `dist/`, deployable to any static host (Netlify, Vercel, S3, nginx, etc.).
