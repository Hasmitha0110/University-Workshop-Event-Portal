import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../bkendintigration/api";
import { useAuth } from "../bkendintigration/AuthContext";

export default function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [err, setErr] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setErr("");
    try {
      const res = await api.post("/api/auth/login", { email, password });
      
      login({
        token: res.data.token,
        name: res.data.name,
        adminId: res.data.adminId,
      });
      navigate("/admin");
    } catch (e) {
      setErr(e?.response?.data || "Login failed");
    }
  }

  return (
    <main className="max-w-lg mx-auto px-1 py-15">
      <h1 className="text-3xl font-bold mb-1">Admin Login</h1>
      <p className="text-sm text-white/60 mb-6">Enter your credentials to access the admin privileges.</p>

      <form onSubmit={handleSubmit} className="bg-ink/60 border border-white/10 rounded-xl p-6 space-y-4">

        <div className="space-y-1">
          <label className="text-sm text-white/80">Email</label>
          <input
            type="email"
            className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="you@university.edu"
            required
          />
        </div>

        <div className="space-y-1">
          <label className="text-sm text-white/80">Password</label>
          <input
            type="password"
            className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
            required
          />
        </div>

        <button
          type="submit"
          className="w-full bg-gold text-ink font-semibold rounded-lg py-2 hover:opacity-90 hover:scale-[0.98] transition hover:text-maroon"
        >
          Login
        </button>

        {err && <div className="text-red-400 text-sm">{String(err)}</div>}

        <p className="text-sm text-white/80">
        Don't have access?{" "}
        <Link className="text-gold hover:text-maroon hover:underline" to="/register">Register here.</Link>
        </p>
      </form>
    </main>
  );
}
