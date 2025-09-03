// src/pages/AdminDashboard.jsx
import { useAuth } from "../bkendintigration/AuthContext";
import { Link } from "react-router-dom";
import AdminPanel from "../pages/AdminPanel";

export default function AdminDashboard() {
  const { isAuthed } = useAuth();

  if (!isAuthed) {
    return (
      <main className="max-w-9xl mx-auto px-6 py-16">
        <div className="bg-gradient-to-br from-maroon/90 to-maroon rounded-2xl p-10 relative overflow-hidden">
          
          <div className="relative z-10">
            <h1 className="text-5xl font-extrabold text-gold mb-4">Become a Campus Leader</h1>
            <p className="text-xl text-white/90 mb-6 max-w-6xl">
              Join the Uni+ Admin team to create, manage, and promote events that shape our university community. 
              As an admin, you'll have the tools to coordinate workshops, seminars, and activities that engage students and faculty.
            </p>
            
            <div className="mb-8">
              <h2 className="text-2xl font-bold text-gold mb-3">Admin Privileges</h2>
              <div className="grid grid-cols-1 gap-3 text-white/80">
                <p >
                  <span className="text-gold mr-2">✓</span> Create and manage university events
                  </p>
                  <p>
                    <span className="text-gold mr-2">✓</span> Post real-time updates and announcements
                </p>
              
              </div>
            </div>
            
            <div className="mt-8 flex gap-4 flex-wrap">
              <Link
                to="/register"
                className="bg-gold text-ink px-6 py-3 rounded-lg font-semibold hover:bg-gold/90 hover:text-maroon transition-all duration-300 transform hover:scale-105 shadow-lg"
              >
                Register
              </Link>
              <Link
                to="/login"
                className="bg-transparent border-2 border-gold text-gold px-6 py-3 rounded-lg font-semibold hover:bg-gold/10 transition-all duration-300 transform hover:scale-105"
              >
                Admin Login
              </Link>
            </div>
            
            <p className="text-white/60 mt-6 text-sm">
              Admin registration requires verification with university credentials.
            </p>
          </div>
        </div>
        
        
      </main>
    );
  }

  
  return <AdminPanel />;
}
