
import { useEffect, useMemo, useState } from "react";
import { useAuth } from "../bkendintigration/AuthContext";
import api from "../bkendintigration/api";
import EventFormModal from "../components/EventFormModal";
import UpdateFormModal from "../components/UpdateFormModal";
import EventRow from "../components/EventRow";

export default function AdminPanel() {
  const { adminId } = useAuth();
  const [search, setSearch] = useState("");
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState("");
  const [showEventForm, setShowEventForm] = useState(false);
  const [editingEvent, setEditingEvent] = useState(null);
  const [updateForEventId, setUpdateForEventId] = useState(null); 
  const [editingUpdate, setEditingUpdate] = useState(null);

  async function fetchEvents(q = "") {
    try {
      setLoading(true);
      setErr("");
      const res = await api.get(`/api/events${q ? `?search=${encodeURIComponent(q)}` : ""}`);
      setEvents(res.data || []);
    } catch (e) {
      setErr("Failed to load events");
      console.error(e);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchEvents();
  }, []);

  const onOpenCreate = () => {
    setEditingEvent(null);
    setShowEventForm(true);
  };

  const onOpenEdit = (ev) => {
    setEditingEvent(ev);
    setShowEventForm(true);
  };

  const onSaveEvent = async (form) => {
    // create or update based on presence of id
    if (editingEvent?.eventId) {
      await api.put(`/api/events/${editingEvent.eventId}`, form);
    } else {
      await api.post(`/api/events`, { ...form, createdById: Number(adminId) });
    }
    await fetchEvents(search);
    setShowEventForm(false);
  };

  const onDeleteEvent = async (ev) => {
    if (!confirm(`Delete event "${ev.title}"?`)) return;
    await api.delete(`/api/events/${ev.eventId}`);
    await fetchEvents(search);
  };

  const onAddUpdate = (eventId) => {
    setEditingUpdate(null);
    setUpdateForEventId(eventId);
  };

  const onEditUpdate = (eventId, upd) => {
    setEditingUpdate(upd);
    setUpdateForEventId(eventId);
  };

  const onSaveUpdate = async (eventId, data) => {
    if (editingUpdate?.updateId) {
      await api.put(`/api/event-updates/${editingUpdate.updateId}`, data);
    } else {
      await api.post(`/api/event-updates/event/${eventId}`, data);
    }
   
    setUpdateForEventId(null);
    setEditingUpdate(null);
  };

  const onDeleteUpdate = async (upd) => {
    if (!confirm(`Delete update "${upd.title}"?`)) return;
    await api.delete(`/api/event-updates/${upd.updateId}`);
    setUpdateForEventId(null);
    setEditingUpdate(null);
  };

  const header = useMemo(
    () => (
      <div className="flex items-center justify-between flex-wrap gap-3">
        <h1 className="text-2xl sm:text-3xl font-extrabold">Admin Dashboard</h1>
        <div className="flex items-center gap-2">
          <input
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            placeholder="Search by name…"
            className="bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold w-56"
          />
          <button
            onClick={() => fetchEvents(search)}
            className="bg-gold text-ink px-4 py-2 rounded-lg font-semibold hover:opacity-90 hover:text-maroon"
          >
            Search
          </button>
          <button
            onClick={onOpenCreate}
            className="bg-maroon text-paper px-4 py-2 rounded-lg font-semibold hover:opacity-90 hover:text-gold"
          >
            Create Event
          </button>
        </div>
      </div>
    ),
    [search]
  );

  return (
    <main className="max-w-6xl mx-auto px-6 py-8 space-y-6">
      {header}

      {loading && <div className="text-white/70">Loading events…</div>}
      {err && <div className="text-red-400">{err}</div>}

      {!loading && !events.length && (
        <div className="text-white/70">No events yet. Create the first one!</div>
      )}

      <div className="space-y-3">
        {events.map((ev) => (
          <EventRow
            key={ev.eventId}
            event={ev}
            canManage={String(ev.createdById) === String(adminId)}
            onEdit={() => onOpenEdit(ev)}
            onDelete={() => onDeleteEvent(ev)}
            onAddUpdate={() => onAddUpdate(ev.eventId)}
            onEditUpdate={(upd) => onEditUpdate(ev.eventId, upd)}
            onDeleteUpdate={onDeleteUpdate}
          />
        ))}
      </div>

      {showEventForm && (
        <EventFormModal
          initial={editingEvent}
          onClose={() => setShowEventForm(false)}
          onSave={onSaveEvent}
        />
      )}

      {updateForEventId && (
        <UpdateFormModal
          eventId={updateForEventId}
          initial={editingUpdate}
          onClose={() => {
            setUpdateForEventId(null);
            setEditingUpdate(null);
          }}
          onSave={onSaveUpdate}
        />
      )}
    </main>
  );
}
