import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { projectsApi } from '../lib/api';

export default function GeneratePage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ title: '', description: '', additionalConstraints: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const project = await projectsApi.generate(form);
      navigate(`/projects/${project.id}`);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="mx-auto max-w-3xl px-6 py-12">
      <span className="eyebrow mb-3">Commission — new drawing</span>
      <h1 className="mb-2 font-display text-2xl font-semibold text-paper">
        Describe the system you need drawn up
      </h1>
      <p className="mb-8 max-w-xl text-sm leading-relaxed text-slate">
        Give the AI a working title, a plain-English description of what the product does, and
        any constraints. It returns a full technical blueprint: requirements, stack, architecture,
        database design, and a delivery roadmap.
      </p>

      <form onSubmit={handleSubmit} className="sheet space-y-6 p-7 sm:p-8">
        <div>
          <label className="field-label" htmlFor="title">
            Project title
          </label>
          <input
            id="title"
            required
            maxLength={200}
            className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass"
            value={form.title}
            onChange={(e) => setForm({ ...form, title: e.target.value })}
            placeholder="e.g. Peer-to-peer equipment rental marketplace"
          />
        </div>

        <div>
          <label className="field-label" htmlFor="description">
            Description
          </label>
          <textarea
            id="description"
            required
            maxLength={3000}
            rows={6}
            className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass resize-y"
            value={form.description}
            onChange={(e) => setForm({ ...form, description: e.target.value })}
            placeholder="What does it do, who is it for, and what does a user actually accomplish with it?"
          />
          <p className="mt-1 text-right font-mono text-[10px] text-ink/30">
            {form.description.length}/3000
          </p>
        </div>

        <div>
          <label className="field-label" htmlFor="constraints">
            Constraints <span className="normal-case text-ink/30">(optional)</span>
          </label>
          <input
            id="constraints"
            className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass"
            value={form.additionalConstraints}
            onChange={(e) => setForm({ ...form, additionalConstraints: e.target.value })}
            placeholder="e.g. prefer microservices, must use Kafka, budget-friendly deployment"
          />
        </div>

        {error && <p className="font-mono text-xs text-danger">{error}</p>}

        <button type="submit" disabled={loading} className="btn-primary w-full sm:w-auto">
          {loading ? 'Drafting blueprint…' : 'Generate blueprint'}
        </button>
      </form>
    </div>
  );
}
