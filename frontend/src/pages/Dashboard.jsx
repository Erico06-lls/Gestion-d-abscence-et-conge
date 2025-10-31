import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Dashboard() {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login"); // redirige si pas connecté
    } else {
      // Optionnel : tu peux appeler /me pour récupérer les infos utilisateur
      setUsername("Utilisateur"); 
    }
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-900 text-white">
      <h1 className="text-3xl font-bold mb-4">Bienvenue, {username} !</h1>
      <p className="mb-6">Ceci est un dashboard minimal protégé.</p>
      <button
        onClick={handleLogout}
        className="px-4 py-2 bg-indigo-500 rounded hover:bg-indigo-400"
      >
        Logout
      </button>
    </div>
  );
}
