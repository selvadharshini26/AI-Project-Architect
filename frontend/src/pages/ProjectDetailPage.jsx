import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { projectsApi } from '../lib/api';
import BlueprintSheet from '../components/BlueprintSheet.jsx';

function formatDate(value) {
  if (!value) return '—';
  return new Date(value).toLocaleDateString(undefined, {
    year: 'numeric',
    month: 'short',
    day: '2-digit',
  });
}

function ListSpec({ items }) {
  if (!items || items.length === 0) return <p className="text-sm text-ink/40">Not specified.</p>;
  return (
    <ul className="space-y-2">
      {items.map((item, i) => (
        <li key={i} className="flex gap-3 text-sm leading-relaxed text-ink/75">
          <span className="mt-0.5 font-mono text-[11px] text-brass/70">
            {String(i + 1).padStart(2, '0')}
          </span>
          <span>{item}</span>
        </li>
      ))}
    </ul>
  );
}

function ChipSpec({ items }) {
  if (!items || items.length === 0) return <p className="text-sm text-ink/40">Not specified.</p>;
  return (
    <div className="flex flex-wrap gap-2">
      {items.map((item) => (
        <span
          key={item}
          className="rounded-sm border border-ink/15 bg-ink/[0.03] px-2.5 py-1 font-mono text-xs text-ink/70"
        >
          {item}
        </span>
      ))}
    </div>
  );
}

function ProseSpec({ text }) {
  if (!text) return <p className="text-sm text-ink/40">Not specified.</p>;
  return <p className="whitespace-pre-line text-sm leading-relaxed text-ink/75">{text}</p>;
}

export default function ProjectDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [project, setProject] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [editing, setEditing] = useState(false);
  const [editForm, setEditForm] = useState({ title: '', description: '' });
  const [saving, setSaving] = useState(false);
  const [deleting, setDeleting] = useState(false);

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    projectsApi
      .getById(id)
      .then((data) => {
        if (cancelled) return;
        setProject(data);
        setEditForm({ title: data.title, description: data.description });
      })
      .catch((err) => !cancelled && setError(err.message))
      .finally(() => !cancelled && setLoading(false));
    return () => {
      cancelled = true;
    };
  }, [id]);

  const handleSave = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      const updated = await projectsApi.update(id, editForm);
      setProject(updated);
      setEditing(false);
    } catch (err) {
      setError(err.message);
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm('Delete this blueprint permanently?')) return;
    setDeleting(true);
    try {
      await projectsApi.remove(id);
      navigate('/dashboard');
    } catch (err) {
      setError(err.message);
      setDeleting(false);
    }
  };

  if (loading) {
    return <p className="mx-auto max-w-4xl px-6 py-16 font-mono text-sm text-slate">Unrolling blueprint…</p>;
  }

  if (error && !project) {
    return <p className="mx-auto max-w-4xl px-6 py-16 font-mono text-sm text-danger">{error}</p>;
  }

  if (!project) return null;

  const d = project.architectureDetails || {};

  const sections = [
    { tag: 'FIG. 01 — PROBLEM STATEMENT', title: 'Problem statement', body: <ProseSpec text={d.problemStatement} /> },
    { tag: 'FIG. 02 — FUNCTIONAL REQS', title: 'Functional requirements', body: <ListSpec items={d.functionalRequirements} /> },
    { tag: 'FIG. 03 — NON-FUNCTIONAL REQS', title: 'Non-functional requirements', body: <ListSpec items={d.nonFunctionalRequirements} /> },
    { tag: 'FIG. 04 — STACK', title: 'Recommended tech stack', body: <ChipSpec items={d.recommendedTechStack} /> },
    { tag: 'FIG. 05 — HIGH-LEVEL', title: 'High-level architecture', body: <ProseSpec text={d.highLevelArchitecture} /> },
    { tag: 'FIG. 06 — LOW-LEVEL', title: 'Low-level architecture', body: <ProseSpec text={d.lowLevelArchitecture} /> },
    { tag: 'FIG. 07 — DATABASE', title: 'Database design', body: <ProseSpec text={d.databaseDesign} /> },
    { tag: 'FIG. 08 — COLLECTIONS', title: 'MongoDB collections', body: <ChipSpec items={d.mongoCollections} /> },
    { tag: 'FIG. 09 — API SPEC', title: 'REST API specification', body: <ProseSpec text={d.restApiSpecification} /> },
    { tag: 'FIG. 10 — SERVICES', title: 'Microservices suggestion', body: <ProseSpec text={d.microservicesSuggestion} /> },
    { tag: 'FIG. 11 — FOLDER LAYOUT', title: 'Folder structure', body: <ProseSpec text={d.folderStructure} /> },
    { tag: 'FIG. 12 — DEPLOYMENT', title: 'Deployment strategy', body: <ProseSpec text={d.deploymentStrategy} /> },
    { tag: 'FIG. 13 — SECURITY', title: 'Security recommendations', body: <ListSpec items={d.securityRecommendations} /> },
    { tag: 'FIG. 14 — CI/CD', title: 'CI/CD recommendation', body: <ProseSpec text={d.ciCdRecommendation} /> },
    { tag: 'FIG. 15 — ROADMAP', title: 'Development roadmap', body: <ProseSpec text={d.developmentRoadmap} /> },
    { tag: 'FIG. 16 — TESTING', title: 'Testing strategy', body: <ProseSpec text={d.testingStrategy} /> },
    { tag: 'FIG. 17 — FUTURE WORK', title: 'Future enhancements', body: <ListSpec items={d.futureEnhancements} /> },
  ];

  return (
    <div className="mx-auto max-w-4xl px-6 py-12">
      <button onClick={() => navigate('/dashboard')} className="eyebrow-dim mb-6 hover:text-brass">
        ← Back to archive
      </button>

      {/* Cover / title block sheet */}
      <div className="sheet mb-8 overflow-hidden">
        <div className="p-7 sm:p-8">
          {editing ? (
            <form onSubmit={handleSave} className="space-y-4">
              <div>
                <label className="field-label">Title</label>
                <input
                  className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass"
                  value={editForm.title}
                  maxLength={200}
                  onChange={(e) => setEditForm({ ...editForm, title: e.target.value })}
                />
              </div>
              <div>
                <label className="field-label">Description</label>
                <textarea
                  rows={4}
                  className="field-input !bg-paper-dim !text-ink !border-ink/15 focus:!border-brass resize-y"
                  value={editForm.description}
                  maxLength={3000}
                  onChange={(e) => setEditForm({ ...editForm, description: e.target.value })}
                />
              </div>
              <div className="flex gap-3">
                <button type="submit" disabled={saving} className="btn-primary">
                  {saving ? 'Saving…' : 'Save changes'}
                </button>
                <button type="button" className="btn-secondary" onClick={() => setEditing(false)}>
                  Cancel
                </button>
              </div>
            </form>
          ) : (
            <>
              <div className="mb-3 flex items-start justify-between gap-4">
                <span className="eyebrow !text-ink/40">Blueprint</span>
                <div className="flex gap-2">
                  <button className="btn-secondary !border-ink/20 !text-ink/60 hover:!text-ink !px-3 !py-1.5 !text-[11px]" onClick={() => setEditing(true)}>
                    Edit
                  </button>
                  <button
                    disabled={deleting}
                    onClick={handleDelete}
                    className="btn-secondary !border-danger/40 !text-danger hover:!border-danger !px-3 !py-1.5 !text-[11px]"
                  >
                    {deleting ? 'Deleting…' : 'Delete'}
                  </button>
                </div>
              </div>
              <h1 className="mb-3 font-display text-2xl font-semibold text-ink sm:text-3xl">
                {project.title}
              </h1>
              <p className="text-sm leading-relaxed text-ink/65">{project.description}</p>
            </>
          )}
        </div>

        <dl className="title-block">
          <div>
            <dt>Drawn by</dt>
            <dd>{project.ownerUsername || '—'}</dd>
          </div>
          <div>
            <dt>Sheet no.</dt>
            <dd>{project.id?.slice(-8).toUpperCase()}</dd>
          </div>
          <div>
            <dt>Issued</dt>
            <dd>{formatDate(project.createdAt)}</dd>
          </div>
          <div>
            <dt>Revised</dt>
            <dd>{formatDate(project.updatedAt)}</dd>
          </div>
        </dl>
      </div>

      {error && <p className="mb-6 font-mono text-xs text-danger">{error}</p>}

      {/* Architecture spec sheets */}
      <div className="space-y-5">
        {sections.map((s) => (
          <BlueprintSheet key={s.tag} tag={s.tag} title={s.title}>
            {s.body}
          </BlueprintSheet>
        ))}
      </div>
    </div>
  );
}
