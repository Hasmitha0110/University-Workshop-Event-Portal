// src/pages/AdminDashboard.jsx
import { useAuth } from "../bkendintigration/AuthContext";
import { Link } from "react-router-dom";
import AdminPanel from "../pages/AdminPanel";

export default function AdminDashboard() {
  const { isAuthed } = useAuth();

  if (!isAuthed) {
    return (
      <main className="max-w-6xl mx-auto px-6 py-16">
        <div className="bg-black/40 border border-white/10 rounded-2xl p-10">
          <h1 className="text-4xl font-extrabold text-gold">Be a Leader.</h1>
          <p className="mt-3 text-white/80">
            To plan, coordinate, and add events, become an admin.
          </p>
          <div className="mt-8 flex gap-4">
            <Link
              to="/register"
              className="bg-gold text-ink px-5 py-2 rounded-lg font-semibold hover:opacity-90 hover:text-maroon"
            >
              Register
            </Link>
            <Link
              to="/login"
              className="bg-maroon text-paper px-5 py-2 rounded-lg font-semibold hover:opacity-90 hover:text-gold"
            >
              Login
            </Link>
          </div>
        </div>
      </main>
    );
  }

  
  return <AdminPanel />;
}
