import { useEffect, useState, useCallback } from 'react';
import { Link } from 'react-router-dom';
import { projectsApi } from '../lib/api';
import ProjectCard from '../components/ProjectCard.jsx';

const PAGE_SIZE = 9;

export default function DashboardPage() {
  const [projects, setProjects] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [page, setPage] = useState(0);
  const [query, setQuery] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const load = useCallback(async (pageIndex, searchTerm) => {
    setLoading(true);
    setError('');
    try {
      const result = searchTerm
        ? await projectsApi.search(searchTerm, pageIndex, PAGE_SIZE)
        : await projectsApi.list(pageIndex, PAGE_SIZE);
      setProjects(result.content || []);
      setTotalPages(result.totalPages ?? 0);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    load(page, query.trim());
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [page]);

  const handleSearch = (e) => {
    e.preventDefault();
    setPage(0);
    load(0, query.trim());
  };

  return (
    <div className="mx-auto max-w-6xl px-6 py-12">
      <div className="mb-8 flex flex-col gap-4 sm:flex-row sm:items-end sm:justify-between">
        <div>
          <span className="eyebrow mb-3">Archive</span>
          <h1 className="font-display text-2xl font-semibold text-paper">Your blueprints</h1>
        </div>
        <Link to="/generate" className="btn-primary shrink-0">
          + New blueprint
        </Link>
      </div>

      <form onSubmit={handleSearch} className="mb-8 flex gap-3">
        <input
          className="field-input max-w-sm"
          placeholder="Search by title…"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
        />
        <button type="submit" className="btn-secondary">
          Search
        </button>
        {query && (
          <button
            type="button"
            className="btn-secondary"
            onClick={() => {
              setQuery('');
              setPage(0);
              load(0, '');
            }}
          >
            Clear
          </button>
        )}
      </form>

      {error && <p className="mb-6 font-mono text-xs text-danger">{error}</p>}

      {loading ? (
        <p className="font-mono text-sm text-slate">Unrolling blueprints…</p>
      ) : projects.length === 0 ? (
        <div className="sheet flex flex-col items-center gap-3 px-8 py-16 text-center">
          <span className="eyebrow !text-ink/40">Empty archive</span>
          <p className="max-w-sm text-sm text-ink/60">
            Nothing here yet. Describe an idea and let the AI draft your first architecture.
          </p>
          <Link to="/generate" className="btn-primary mt-2">
            Draft your first blueprint
          </Link>
        </div>
      ) : (
        <>
          <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3">
            {projects.map((project, i) => (
              <ProjectCard key={project.id} project={project} index={page * PAGE_SIZE + i} />
            ))}
          </div>

          {totalPages > 1 && (
            <div className="mt-10 flex items-center justify-center gap-4">
              <button
                className="btn-secondary !px-3 !py-1.5"
                disabled={page === 0}
                onClick={() => setPage((p) => Math.max(0, p - 1))}
              >
                Prev
              </button>
              <span className="font-mono text-xs text-slate">
                Page {page + 1} of {totalPages}
              </span>
              <button
                className="btn-secondary !px-3 !py-1.5"
                disabled={page >= totalPages - 1}
                onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
              >
                Next
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
}
