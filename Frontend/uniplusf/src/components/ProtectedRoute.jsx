import { Navigate } from "react-router-dom";
import { useAuth } from "../bkendintigration/AuthContext";

export default function ProtectedRoute({ children }) {
  const { isAuthed } = useAuth();
  if (!isAuthed) return <Navigate to="/login" replace />;
  return children;
}
