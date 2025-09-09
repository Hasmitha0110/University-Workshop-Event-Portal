import { useEffect, useState } from "react";

export default function UpdateFormModal({ eventId, initial, onSave, onClose }) {
  const [form, setForm] = useState({
    title: "",
    content: "",
    imageUrl: "",
    link: "",
  });

  useEffect(() => {
    if (initial) {
      setForm({
        title: initial.title || "",
        content: initial.content || "",
        imageUrl: initial.imageUrl || "",
        link: initial.link || "",
      });
    }
  }, [initial]);

  const submit = async (e) => {
    e.preventDefault();
    await onSave(eventId, form);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 p-4">
      <form
        onSubmit={submit}
        className="bg-ink border border-white/10 rounded-2xl p-6 w-full max-w-lg space-y-3"
      >
        <h3 className="text-xl font-bold">{initial ? "Edit Update" : "Add Update"}</h3>

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
          <label className="text-sm text-white/80">Content</label>
          <textarea
            className="w-full min-h-24 bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
            value={form.content}
            onChange={(e) => setForm({ ...form, content: e.target.value })}
          />
        </div>

        <div>
          <label className="text-sm text-white/80">Link</label>
          <input
            className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
            value={form.link}
            onChange={(e) => setForm({ ...form, link: e.target.value })}
          />
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
            {initial ? "Save Changes" : "Add Update"}
          </button>
        </div>
      </form>
    </div>
  );
}
