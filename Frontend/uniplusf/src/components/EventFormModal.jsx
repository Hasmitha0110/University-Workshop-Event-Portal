import { useEffect, useState } from "react";

export default function EventFormModal({ initial, onClose, onSave }) {
  const [form, setForm] = useState({
    title: "",
    description: "",
    venue: "",
    imageUrl: "",
    eventDate: "",
  });

  useEffect(() => {
    if (initial) {
      setForm({
        title: initial.title || "",
        description: initial.description || "",
        venue: initial.venue || "",
        imageUrl: initial.imageUrl || "",
        eventDate: initial.eventDate || "",
      });
    }
  }, [initial]);

  const submit = async (e) => {
    e.preventDefault();
    await onSave(form);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 p-4">
      <form
        onSubmit={submit}
        className="bg-ink border border-white/10 rounded-2xl p-6 w-full max-w-lg space-y-3"
      >
        <h3 className="text-xl font-bold">{initial ? "Edit Event" : "Create Event"}</h3>

        <div>
          <label className="text-sm text-white/80">Title</label>
          <input
            className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
            value={form.title}
            onChange={(e) => setForm({ ...form, title: e.target.value })}
            required
          />
        </div>

        <div>
          <label className="text-sm text-white/80">Description</label>
          <textarea
            className="w-full min-h-24 bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
            value={form.description}
            onChange={(e) => setForm({ ...form, description: e.target.value })}
          />
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
          <div>
            <label className="text-sm text-white/80">Venue</label>
            <input
              className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
              value={form.venue}
              onChange={(e) => setForm({ ...form, venue: e.target.value })}
            />
          </div>
          <div>
            <label className="text-sm text-white/80">Date</label>
            <input
              type="date"
              className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
              value={form.eventDate}
              onChange={(e) => setForm({ ...form, eventDate: e.target.value })}
              required
            />
          </div>
        </div>

        <div className="flex justify-end gap-2 pt-2">
          <button
            type="button"
            onClick={onClose}
            className="px-4 py-2 rounded-lg border border-white/15 hover:bg-white/10"
          >
            Cancel
          </button>
          <button
            type="submit"
            className="px-4 py-2 rounded-lg bg-gold text-ink font-semibold hover:opacity-90 hover:text-maroon"
          >
            {initial ? "Save Changes" : "Create"}
          </button>
        </div>
      </form>
    </div>
  );
}
