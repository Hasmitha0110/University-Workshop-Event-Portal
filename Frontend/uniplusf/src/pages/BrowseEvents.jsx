// src/pages/BrowseEvents.jsx
import { useEffect, useState } from "react";
import api from "../bkendintigration/api";
import EventRow from "../components/EventRow";

export default function BrowseEvents() {
  const [events, setEvents] = useState([]);
  const [search, setSearch] = useState("");
  const [status, setStatus] = useState("");
  const [date, setDate] = useState("");
  const [loading, setLoading] = useState(false);

  async function fetchEvents() {
    setLoading(true);
    try {
      let url = "/api/events";
      if (search) {
        url += `?search=${encodeURIComponent(search)}`;
      }
      const res = await api.get(url);
      let data = res.data || [];

      // filter by status
      if (status) {
        data = data.filter(
          (e) => e.status.toLowerCase() === status.toLowerCase()
        );
      }

      // filter by date (exact match)
      if (date) {
        data = data.filter((e) => e.eventDate === date);
      }

      setEvents(data);
    } catch (err) {
      console.error("Failed to fetch events", err);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchEvents();
  }, []);

  return (
    <main className="max-w-7xl mx-auto px-6 py-8 space-y-6">
      <h1 className="text-3xl font-bold">Browse Events</h1>

      {/* Filters */}
      <div className="flex flex-wrap gap-3 mt-4">
        <input
          type="text"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          placeholder="Search by name…"
          className="bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
        />
        <select
          value={status}
          onChange={(e) => setStatus(e.target.value)}
          className="bg-black/40 border border-white/10 rounded-lg px-3 py-2"
        >
          <option value="">All Status</option>
          <option value="Ongoing">Ongoing</option>
          <option value="Upcoming">Upcoming</option>
          <option value="Concluded">Concluded</option>
        </select>
        <input
          type="date"
          value={date}
          onChange={(e) => setDate(e.target.value)}
          className="bg-black/40 border border-white/10 rounded-lg px-3 py-2"
        />
        <button
          onClick={fetchEvents}
          className="bg-gold text-ink px-4 py-2 rounded-lg font-semibold hover:opacity-90 hover:text-maroon"
        >
          Apply Filters
        </button>
      </div>

      {/* Results */}
      {loading && <div className="text-white/70">Loading…</div>}
      {!loading && !events.length && (
        <div className="text-white/70">No events found.</div>
      )}
      <div className="space-y-3">
        {events.map((ev) => (
          <EventRow
            key={ev.eventId}
            event={ev}
            canManage={false} // visitors cannot edit/delete
            onEdit={() => {}}
            onDelete={() => {}}
            onAddUpdate={() => {}}
            onEditUpdate={() => {}}
            onDeleteUpdate={() => {}}
          />
        ))}
      </div>
    </main>
  );
}
