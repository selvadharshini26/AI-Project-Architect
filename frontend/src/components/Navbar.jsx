import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';

export default function Navbar() {
  const { isAuthenticated, user, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <header className="border-b border-line bg-panel/80 backdrop-blur">
      <div className="mx-auto flex max-w-6xl items-center justify-between px-6 py-4">
        <Link to="/" className="flex items-center gap-2.5">
          <svg width="22" height="22" viewBox="0 0 24 24" fill="none" className="text-brass">
            <rect x="3" y="3" width="7" height="7" stroke="currentColor" strokeWidth="1.4" />
            <rect x="14" y="3" width="7" height="7" stroke="currentColor" strokeWidth="1.4" />
            <rect x="3" y="14" width="7" height="7" stroke="currentColor" strokeWidth="1.4" />
            <path d="M14 17.5H21M17.5 14V21" stroke="currentColor" strokeWidth="1.4" />
          </svg>
          <span className="font-display text-[15px] font-semibold tracking-tight text-paper">
            AI Project Architect
          </span>
        </Link>

        <nav className="flex items-center gap-6">
          {isAuthenticated ? (
            <>
              <Link to="/dashboard" className="eyebrow-dim hover:text-brass">
                Projects
              </Link>
              <Link to="/generate" className="eyebrow-dim hover:text-brass">
                New
              </Link>
              <div className="flex items-center gap-3 border-l border-line pl-6">
                <span className="font-mono text-xs text-slate">{user?.username}</span>
                <button
                  onClick={() => {
                    logout();
                    navigate('/login');
                  }}
                  className="btn-secondary !px-3 !py-1.5 !text-[11px]"
                >
                  Sign out
                </button>
              </div>
            </>
          ) : (
            <>
              <Link to="/login" className="eyebrow-dim hover:text-brass">
                Sign in
              </Link>
              <Link to="/register" className="btn-primary !px-4 !py-2 !text-[12px]">
                Get started
              </Link>
            </>
          )}
        </nav>
      </div>
    </header>
  );
}
