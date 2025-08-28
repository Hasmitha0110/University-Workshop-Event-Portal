import { createContext, useContext, useEffect, useState } from "react";


const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem("token"));
  const [name, setName]   = useState(() => localStorage.getItem("name"));
  const [adminId, setAdminId] = useState(() => localStorage.getItem("adminId"));

  const isAuthed = !!token;

  function login({ token, name, adminId }) {
    setToken(token);
    setName(name);
    setAdminId(adminId);
    localStorage.setItem("token", token);
    localStorage.setItem("name", name);
    localStorage.setItem("adminId", String(adminId));
  }

  function logout() {
    setToken(null);
    setName(null);
    setAdminId(null);
    localStorage.removeItem("token");
    localStorage.removeItem("name");
    localStorage.removeItem("adminId");
  }

  useEffect(() => {
    const onStorage = () => {
      setToken(localStorage.getItem("token"));
      setName(localStorage.getItem("name"));
      setAdminId(localStorage.getItem("adminId"));
    };
    window.addEventListener("storage", onStorage);
    return () => window.removeEventListener("storage", onStorage);
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthed, token, name, adminId, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
