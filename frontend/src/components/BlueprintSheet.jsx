export default function BlueprintSheet({ tag, title, children, className = '' }) {
  return (
    <section className={`sheet px-6 py-6 sm:px-8 sm:py-7 ${className}`}>
      {(tag || title) && (
        <div className="mb-4 flex items-baseline justify-between gap-4 border-b border-ink/10 pb-3">
          <h3 className="font-display text-base font-semibold text-ink sm:text-lg">{title}</h3>
          {tag && <span className="eyebrow whitespace-nowrap !text-ink/40">{tag}</span>}
        </div>
      )}
      {children}
    </section>
  );
}
