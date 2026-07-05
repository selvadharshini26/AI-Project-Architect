import { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';

export default function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [form, setForm] = useState({ usernameOrEmail: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await login(form.usernameOrEmail, form.password);
      navigate(location.state?.from || '/dashboard', { replace: true });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mx-auto flex min-h-[calc(100vh-73px)] max-w-md flex-col justify-center px-6 py-16">
      <span className="eyebrow mb-3">Access — 01</span>
      <h1 className="mb-8 font-display text-2xl font-semibold text-paper">Sign in to your drafting table</h1>

      <form onSubmit={handleSubmit} className="sheet space-y-5 p-7">
        <div>
          <label className="field-label" htmlFor="usernameOrEmail">
            Username or email
          </label>
          <input
            id="usernameOrEmail"
            required
            className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass"
            value={form.usernameOrEmail}
            onChange={(e) => setForm({ ...form, usernameOrEmail: e.target.value })}
            placeholder="ada.lovelace"
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
            className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass"
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })}
            placeholder="••••••••"
          />
        </div>

        {error && <p className="font-mono text-xs text-danger">{error}</p>}

        <button type="submit" disabled={loading} className="btn-primary w-full">
          {loading ? 'Signing in…' : 'Sign in'}
        </button>
      </form>

      <p className="mt-6 text-center font-mono text-xs text-slate">
        No account yet?{' '}
        <Link to="/register" className="text-brass hover:text-brass-bright">
          Register here
        </Link>
      </p>
    </div>
  );
}
