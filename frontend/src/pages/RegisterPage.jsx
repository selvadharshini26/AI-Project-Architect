import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';

export default function RegisterPage() {
  const { register } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: '', email: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await register(form.username, form.email, form.password);
      navigate('/dashboard', { replace: true });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mx-auto flex min-h-[calc(100vh-73px)] max-w-md flex-col justify-center px-6 py-16">
      <span className="eyebrow mb-3">Access — 02</span>
      <h1 className="mb-8 font-display text-2xl font-semibold text-paper">Set up your drafting table</h1>

      <form onSubmit={handleSubmit} className="sheet space-y-5 p-7">
        <div>
          <label className="field-label" htmlFor="username">
            Username
          </label>
          <input
            id="username"
            required
            minLength={3}
            maxLength={50}
            className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass"
            value={form.username}
            onChange={(e) => setForm({ ...form, username: e.target.value })}
            placeholder="ada.lovelace"
          />
        </div>
        <div>
          <label className="field-label" htmlFor="email">
            Email
          </label>
          <input
            id="email"
            type="email"
            required
            className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
            placeholder="ada@example.com"
          />
        </div>
        <div>
          <label className="field-label" htmlFor="password">
            Password
          </label>
          <input
            id="password"
            type="password"
            required
            minLength={6}
            className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass"
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })}
            placeholder="At least 6 characters"
          />
        </div>

        {error && <p className="font-mono text-xs text-danger">{error}</p>}

        <button type="submit" disabled={loading} className="btn-primary w-full">
          {loading ? 'Creating account…' : 'Create account'}
        </button>
      </form>

      <p className="mt-6 text-center font-mono text-xs text-slate">
        Already registered?{' '}
        <Link to="/login" className="text-brass hover:text-brass-bright">
          Sign in
        </Link>
      </p>
    </div>
  );
}
