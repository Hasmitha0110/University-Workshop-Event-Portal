import { NavLink, Link, useNavigate } from "react-router-dom";
import { useAuth } from "../bkendintigration/AuthContext";
import logoUrl from "../assets/LogoUL.png"; 

export default function Navbar() {
  const { isAuthed, name, logout } = useAuth();
  const navigate = useNavigate();

  const linkBase =
    "px-4 py-1 text-sm font-medium hover:text-gold transition-colors";
  const activeLink =
    "text-gold border-b-2 border-gold";
  const inactiveLink =
    "text-white";

  return (
    <header className="w-full bg-ink text-paper shadow-md">
      
      <div className="h-2 bg-maroon" />

      <nav className="max-w-8xl mx-auto flex items-center justify-between gap-6 px-6 py-1">
        
        <Link to="/" className="flex items-center gap-3">
          <img src={logoUrl} alt="UNI+ logo" className="h-16 w-auto" />
        </Link>

        <ul className="flex items-center gap-2">
          <li>
            <NavLink
              to="/"
              end
              className={({ isActive }) =>
                `${linkBase} ${isActive ? activeLink : inactiveLink}`
              }
            >
              Home
            </NavLink>
          </li>
          <li>
            <NavLink
              to="/browse"
              className={({ isActive }) =>`${linkBase} ${isActive ? activeLink : inactiveLink}`}>Browse Events</NavLink>
          </li>
          <li>
            <NavLink
              to="/admin"
              className={({ isActive }) =>`${linkBase} ${isActive ? activeLink : inactiveLink}`}>Admin Dashboard</NavLink>
          </li>
        </ul>

        <div className="flex items-center gap-3">
          {!isAuthed ? (
            <Link
              to="/login"
              className="bg-gold text-ink px-4 py-2 rounded-lg font-semibold hover:opacity-90 hover:text-maroon">Login as Admin
            </Link>
          ) : (
            <>
              <span className="text-sm text-white/80">
                Welcome, <span className="font-semibold text-gold">{name}</span>
              </span>
              <button
                onClick={() => {
                  logout();
                  navigate("/");
                }}
                className="bg-maroon text-paper px-3 py-2 rounded-lg font-semibold hover:opacity-90 hover:text-gold">Logout
              </button>
            </>
          )}
        </div>
      </nav>

      <div className="h-[3px] bg-gold" />
    </header>
  );
}
