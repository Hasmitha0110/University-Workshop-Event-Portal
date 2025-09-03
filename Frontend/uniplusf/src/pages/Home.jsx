import { useEffect, useState } from "react";
import api from "../bkendintigration/api";
import EventRow from "../components/EventRow";
import { Link } from "react-router-dom";

export default function HomePage() {
  const [weeklyEvents, setWeeklyEvents] = useState({});
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    async function fetchEvents() {
      setLoading(true);
      try {
        const res = await api.get("/api/events");
        const data = res.data || [];

        // Get current week range
        const now = new Date();
        const day = now.getDay(); // 0 = Sun, 1 = Mon ...
        const monday = new Date(now);
        monday.setDate(now.getDate() - (day === 0 ? 6 : day - 1));
        monday.setHours(0, 0, 0, 0);

        const sunday = new Date(monday);
        sunday.setDate(monday.getDate() + 6);
        sunday.setHours(23, 59, 59, 999);

        // Initialize weekdays
        const grouped = {
          Monday: [],
          Tuesday: [],
          Wednesday: [],
          Thursday: [],
          Friday: [],
          Saturday: [],
          Sunday: [],
        };

        // Group events by weekday
        data.forEach((ev) => {
          const eventDate = new Date(ev.eventDate);
          if (eventDate >= monday && eventDate <= sunday) {
            const weekday = eventDate.toLocaleDateString("en-US", {
              weekday: "long",
            });
            grouped[weekday]?.push(ev);
          }
        });

        setWeeklyEvents(grouped);
      } catch (err) {
        console.error("Failed to load events", err);
      } finally {
        setLoading(false);
      }
    }

    fetchEvents();
  }, []);

  return (
     <main className="max-w-9xl mx-auto px-8 py-10 space-y-16">
      {/* Hero Section */}
      <section className="text-center py-16 bg-gradient-to-r from-maroon to-gold rounded-2xl shadow-lg">
        <h1 className="text-5xl font-bold text-white mb-6">
          Welcome to UniPlus 🎓
        </h1>
        <p className="text-lg text-white/90 mb-7">
          Plan, coordinate, and explore university events with ease.
        </p>
        <Link
                to="/browse"
                className="bg-white text-ink px-6 py-3 rounded-lg font-semibold hover:bg-black hover:text-white transition">
                Browse Events
              </Link>
      </section>

      {/* Weekly Events */}
      <h2 className="text-3xl pl-9 font-bold mb-6">This Week's Events</h2>
      <section className="max-w-7xl flex flex-col mx-auto">
        
        {loading && <p className="text-white/70">Loading events…</p>}
        {!loading &&
          Object.keys(weeklyEvents).map((day) => (
            <div key={day} className="mb-2">
              <h3 className="text-xl font-semibold text-white/80 mb-2">{day}</h3>
              {weeklyEvents[day].length === 0 ? (
                <p className="text-maroon/100">No events</p>
              ) : (
                <div className="space-y-4">
                  {weeklyEvents[day].map((ev) => (
                    <EventRow
                      key={ev.eventId}
                      event={ev}
                      canManage={false}
                      onEdit={() => {}}
                      onDelete={() => {}}
                      onAddUpdate={() => {}}
                      onEditUpdate={() => {}}
                      onDeleteUpdate={() => {}}
                    />
                  ))}
                </div>
              )}
            </div>
          ))}
      </section>

      {/* Info Cards */}
      <section >
        <div className="mt-12 bg-ink border border-white/10 rounded-2xl p-8">
          <h2 className="text-2xl font-bold text-gold mb-6">How It Works</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="text-center p-6 bg-black/20 rounded-lg">
              <div className="w-12 h-12 bg-gold text-ink rounded-full flex items-center justify-center mx-auto mb-4 font-bold text-xl">1</div>
              <h3 className="font-semibold text-white mb-2">Register</h3>
              <p className="text-white/70">Submit your admin application with university credentials</p>
            </div>
            
            <div className="text-center p-6 bg-black/20 rounded-lg">
              <div className="w-12 h-12 bg-gold text-ink rounded-full flex items-center justify-center mx-auto mb-4 font-bold text-xl">2</div>
              <h3 className="font-semibold text-white mb-2">Start Creating</h3>
              <p className="text-white/70">Plan and manage events for the university community</p>
            </div>
          </div>
        </div>
      </section>
    </main>
  );
}
