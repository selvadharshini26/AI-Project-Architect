import { Link } from 'react-router-dom';

function formatDate(value) {
  if (!value) return '—';
  return new Date(value).toLocaleDateString(undefined, {
    year: 'numeric',
    month: 'short',
    day: '2-digit',
  });
}

export default function ProjectCard({ project, index }) {
  const stack = project.architectureDetails?.recommendedTechStack || [];

  return (
    <Link to={`/projects/${project.id}`} className="sheet group flex flex-col p-5 transition-transform hover:-translate-y-0.5">
      <div className="mb-3 flex items-start justify-between gap-3">
        <span className="eyebrow !text-ink/40">SHEET {String(index + 1).padStart(2, '0')}</span>
        <span className="font-mono text-[10px] uppercase tracking-widest text-ink/40">
          {formatDate(project.createdAt)}
        </span>
      </div>

      <h3 className="mb-2 font-display text-lg font-semibold leading-snug text-ink group-hover:text-ink/80">
        {project.title}
      </h3>

      <p className="mb-4 line-clamp-3 text-sm leading-relaxed text-ink/60">
        {project.description}
      </p>

      {stack.length > 0 && (
        <div className="mt-auto flex flex-wrap gap-1.5 border-t border-ink/10 pt-3">
          {stack.slice(0, 4).map((item) => (
            <span
              key={item}
              className="rounded-sm border border-ink/15 px-2 py-0.5 font-mono text-[10px] uppercase tracking-wide text-ink/60"
            >
              {item}
            </span>
          ))}
          {stack.length > 4 && (
            <span className="px-2 py-0.5 font-mono text-[10px] text-ink/40">
              +{stack.length - 4}
            </span>
          )}
        </div>
      )}
    </Link>
  );
}
