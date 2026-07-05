import { Link } from 'react-router-dom';

export default function NotFoundPage() {
  return (
    <div className="mx-auto flex max-w-md flex-col items-center px-6 py-24 text-center">
      <span className="eyebrow mb-3">Error — off the grid</span>
      <h1 className="mb-3 font-display text-xl font-semibold text-paper">Sheet not found</h1>
      <p className="mb-6 text-sm text-slate">
        This drawing doesn't exist, or it's been moved to a different sheet number.
      </p>
      <Link to="/dashboard" className="btn-primary">
        Back to archive
      </Link>
    </div>
  );
}
