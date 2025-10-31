import { useState } from "react";
import { useNavigate } from "react-router-dom"; // pour redirection
import { register } from "../services/auth";
import { toast, ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

export default function Register() {
  const [form, setForm] = useState({ username: "", password: "", role: "", email: "" });
  const navigate = useNavigate(); // hook pour redirection

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await register(form);
      toast.success("Inscription réussie ! Vous allez être redirigé vers login...");
      setTimeout(() => navigate("/login"), 2000); // redirige après 2s
    } catch (err) {
      toast.error(err.message || "Erreur lors de l'inscription");
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
          Create your account
        </h2>
      </div>

      <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form onSubmit={handleSubmit} className="space-y-6">
          {/* champs username, password, role, email */}
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

          <div>
            <label htmlFor="role" className="block text-sm font-medium text-gray-100">Role</label>
            <input id="role" name="role" type="text" required
              value={form.role} onChange={handleChange}
              className="block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white placeholder:text-gray-500 focus:outline-indigo-500 sm:text-sm"
            />
          </div>

          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-100">Email</label>
            <input id="email" name="email" type="email"
              value={form.email} onChange={handleChange}
              className="block w-full rounded-md bg-white/5 px-3 py-1.5 text-base text-white placeholder:text-gray-500 focus:outline-indigo-500 sm:text-sm"
            />
          </div>

          <button type="submit"
            className="flex w-full justify-center rounded-md bg-indigo-500 px-3 py-1.5 text-sm font-semibold text-white hover:bg-indigo-400"
          >
            Sign up
          </button>
        </form>

        <p className="mt-10 text-center text-sm text-gray-400">
          Already have an account?{' '}
          <a href="/login" className="font-semibold text-indigo-400 hover:text-indigo-300">Sign in</a>
        </p>
      </div>
    </div>
  );
}
