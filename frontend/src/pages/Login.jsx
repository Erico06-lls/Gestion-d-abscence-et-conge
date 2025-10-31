import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/auth";
import { toast, ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = await login(form);
      localStorage.setItem("token", data.token); // stocke le token
      toast.success("Connexion réussie !");
      setTimeout(() => navigate("/dashboard"), 1500); // redirige vers dashboard
    } catch (err) {
      toast.error(err.message || "Erreur de connexion");
    }
  };

  return (
    <div className="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8 bg-gray-900">
      <ToastContainer position="top-right" autoClose={3000} />
      <div className="sm:mx-auto sm:w-full sm:max-w-sm">
        <img
          alt="Logo"
          src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=500"
          className="mx-auto h-10 w-auto"
        />
        <h2 className="mt-10 text-center text-2xl font-bold tracking-tight text-white">
          Sign in to your account
        </h2>
      </div>

      <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label htmlFor="username" className="block text-sm font-medium text-gray-100">Username</label>
            <input id="username" name="username" type="text" required
              value={form.username} onChange={handleChange}
              className="block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white placeholder:text-gray-500 focus:outline-indigo-500 sm:text-sm"
            />
          </div>

          <div>
            <label htmlFor="password" className="block text-sm font-medium text-gray-100">Password</label>
            <input id="password" name="password" type="password" required
              value={form.password} onChange={handleChange}
              className="block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white placeholder:text-gray-500 focus:outline-indigo-500 sm:text-sm"
            />
          </div>

          <button type="submit"
            className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold text-white hover:bg-indigo-400"
          >
            Sign in
          </button>
        </form>

        <p className="mt-10 text-center text-sm text-gray-400">
          Not a member?{' '}
          <a href="/register" className="font-semibold text-indigo-400 hover:text-indigo-300">
            Sign up
          </a>
        </p>
      </div>
    </div>
  );
}
