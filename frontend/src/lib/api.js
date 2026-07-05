import axios from 'axios';

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export const api = axios.create({
  baseURL: BASE_URL,
  headers: { 'Content-Type': 'application/json' },
});

// Attach the JWT (if present) to every outgoing request.
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('apa_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Normalize errors so callers can read `err.message` regardless of
// whether the failure came from the backend's ApiResponse envelope,
// a network failure, or something unexpected.
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const backendMessage = error?.response?.data?.message;
    const status = error?.response?.status;

    if (status === 401 && !error.config?.url?.includes('/api/auth/')) {
      localStorage.removeItem('apa_token');
      localStorage.removeItem('apa_user');
    }

    return Promise.reject({
      status,
      message: backendMessage || error.message || 'Something went wrong. Please try again.',
    });
  }
);

// ---- Auth ----
export const authApi = {
  register: (payload) => api.post('/api/auth/register', payload).then((r) => r.data.data),
  login: (payload) => api.post('/api/auth/login', payload).then((r) => r.data.data),
};

// ---- Projects ----
export const projectsApi = {
  generate: (payload) => api.post('/api/projects/generate', payload).then((r) => r.data.data),
  list: (page = 0, size = 9) =>
    api.get('/api/projects', { params: { page, size } }).then((r) => r.data.data),
  search: (title, page = 0, size = 9) =>
    api.get('/api/projects/search', { params: { title, page, size } }).then((r) => r.data.data),
  getById: (id) => api.get(`/api/projects/${id}`).then((r) => r.data.data),
  update: (id, payload) => api.put(`/api/projects/${id}`, payload).then((r) => r.data.data),
  remove: (id) => api.delete(`/api/projects/${id}`).then((r) => r.data),
};
