import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';

export default function HomePage() {
  const { isAuthenticated } = useAuth();

  return (
    <div className="mx-auto flex min-h-[calc(100vh-73px)] max-w-5xl flex-col justify-center px-6 py-16">
      <span className="eyebrow mb-4">Automated drafting service</span>
      <h1 className="mb-6 max-w-2xl font-display text-4xl font-semibold leading-[1.1] text-paper sm:text-5xl">
        Turn a one-paragraph idea into a full technical blueprint.
      </h1>
      <p className="mb-10 max-w-xl text-base leading-relaxed text-slate-light">
        Describe what you want to build. The AI drafts requirements, a tech stack, system
        architecture, database design, and a delivery roadmap — laid out like a proper set of
        engineering drawings, ready to hand to a team.
      </p>
      <div className="flex flex-wrap gap-4">
        <Link to={isAuthenticated ? '/generate' : '/register'} className="btn-primary">
          {isAuthenticated ? 'Draft a new blueprint' : 'Get started'}
        </Link>
        <Link to={isAuthenticated ? '/dashboard' : '/login'} className="btn-secondary">
          {isAuthenticated ? 'View your archive' : 'Sign in'}
        </Link>
      </div>

      <div className="mt-16 grid grid-cols-1 gap-5 sm:grid-cols-3">
        {[
          { tag: '01', label: 'Describe', copy: 'Write the idea in plain English, plus any constraints.' },
          { tag: '02', label: 'Generate', copy: 'Gemini drafts a full 17-section architecture spec.' },
          { tag: '03', label: 'Refine', copy: 'Edit, search, and revisit every blueprint you commission.' },
        ].map((step) => (
          <div key={step.tag} className="sheet p-5">
            <span className="eyebrow !text-ink/40">Step {step.tag}</span>
            <h3 className="mt-2 font-display text-base font-semibold text-ink">{step.label}</h3>
            <p className="mt-1.5 text-sm text-ink/60">{step.copy}</p>
          </div>
        ))}
      </div>
    </div>
  );
}
