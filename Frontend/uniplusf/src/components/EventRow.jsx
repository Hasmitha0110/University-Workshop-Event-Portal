// src/ui/EventRow.jsx
import { useEffect, useState } from "react";
import api from "../bkendintigration/api";

function statusBadge(status) {
  const base = "px-2 py-0.5 rounded text-xs font-semibold";
  if (!status) return null;
  if (status.toLowerCase() === "ongoing") return <span className={`${base} bg-green-600/20 text-green-400`}>ONGOING</span>;
  if (status.toLowerCase() === "upcoming") return <span className={`${base} bg-red-600/20 text-red-400`}>UPCOMING</span>;
  return <span className={`${base} bg-blue-600/20 text-blue-400`}>CONCLUDED</span>;
}

export default function EventRow({
  event,
  canManage,
  onEdit,
  onDelete,
  onAddUpdate,
  onEditUpdate,
  onDeleteUpdate,
}) {
  const [open, setOpen] = useState(false);
  const [updates, setUpdates] = useState([]);
  const [loading, setLoading] = useState(false);

  async function fetchUpdates() {
    try {
      setLoading(true);
      const res = await api.get(`/api/event-updates/event/${event.eventId}`);
      setUpdates(res.data || []);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if (open) fetchUpdates();
  }, [open]);

  return (
    <div className="border border-white/10 rounded-xl overflow-hidden bg-gradient-to-r from-maroon/60 to-ink">
      {/* Collapsed row */}
      <div className="flex items-center gap-3 p-4">
        {/* thumb */}
        <div className="h-12 w-12 bg-gradient-to-r from-ink/60 to-paper/20 rounded-md overflow-hidden shrink-0">
          {event.imageUrl ? (
            <img src={event.imageUrl} alt="" className="h-full w-full object-cover" />
          ) : null}
        </div>

        {/* main info */}
        <div className="flex-1">
          <div className="flex items-center gap-2 flex-wrap">
            <h3 className="font-semibold">{event.title}</h3>
            {statusBadge(event.status)}
          </div>
          <div className="text-xs text-white/70">
            Admin: <span className="text-white/90">{event.createdByName || "-"}</span>
           
          </div>
          <div className="text-xs text-white/70">
            Date: <span className="text-white/90">{event.eventDate}</span> . 
            Venue: <span className="text-white/90">{event.venue}</span>
          </div>
        </div>

        {/* actions */}
        <div className="flex items-center gap-2">
          <button
            onClick={() => setOpen((v) => !v)}
            className="text-sm px-3 py-1 rounded-lg border border-white/15 hover:bg-white/10"
          >
            {open ? "Hide Details" : "Details"}
          </button>

          {canManage && (
            <>
              <button
                onClick={onEdit}
                className="text-sm px-3 py-1 rounded-lg bg-gold text-ink hover:opacity-90 hover:text-maroon"
              >
                Edit
              </button>
              <button
                onClick={onDelete}
                className="text-sm px-3 py-1 rounded-lg bg-red-600 text-white hover:opacity-90"
              >
                Delete
              </button>
              <button
                onClick={onAddUpdate}
                className="text-sm px-3 py-1 rounded-lg bg-maroon text-paper hover:opacity-90 hover:text-gold"
              >
                Add Update
              </button>
            </>
          )}
        </div>
      </div>

      {/* Expanded details */}
      {open && (
        <div className="px-4 pb-4">
          <div className="text-white/80 text-sm mb-2">{event.description}</div>

          <div className="mt-3">
            <h4 className="font-semibold mb-2">Event Updates</h4>
            {loading && <div className="text-white/60 text-sm">Loading…</div>}
            {!loading && !updates.length && (
              <div className="text-white/60 text-sm">No updates yet.</div>
            )}
            <ul className="space-y-2">
              {updates.map((u) => (
                <li key={u.updateId} className="border border-white/10 rounded-lg p-3">
                  <div className="flex items-center justify-between">
                    <div className="font-medium">{u.title}</div>
                    <div className="text-xs text-white/60">
                      {new Date(u.createdAt).toLocaleString()}
                    </div>
                  </div>
                  {/* {u.imageUrl && (
                    <img src={u.imageUrl} alt="" className="mt-2 max-h-40 rounded" />
                  )} */}
                  <div className="text-sm text-white/80 mt-1">{u.content}</div>
                  {u.link && (
                    <a className="text-gold text-sm hover:underline" href={u.link} target="_blank" rel="noreferrer">
                      {u.link}
                    </a>
                  )}

                  {/* Only the update owner can manage their updates */}
                  {u.createdBy?.adminId && canManage && (
                    <div className="mt-2 flex gap-2">
                      <button
                        onClick={() => onEditUpdate(u)}
                        className="text-xs px-3 py-1 rounded bg-gold text-ink hover:opacity-90 hover:text-maroon"
                      >
                        Edit Update
                      </button>
                      <button
                        onClick={() => onDeleteUpdate(u)}
                        className="text-xs px-3 py-1 rounded bg-red-600 text-white hover:opacity-90"
                      >
                        Delete Update
                      </button>
                    </div>
                  )}
                </li>
              ))}
            </ul>
          </div>
        </div>
      )}
    </div>
  );
}
