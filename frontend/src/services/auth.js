import axios from "axios";

const API_URL = "http://localhost:8082/api/auth";

export const register = async (user) => {
  try {
    const res = await axios.post(`${API_URL}/register`, user);
    return res.data;
  } catch (err) {
    throw err.response?.data || err;
  }
};

export const login = async (credentials) => {
  try {
    const res = await axios.post(`${API_URL}/login`, credentials);
    return res.data;
  } catch (err) {
    throw err.response?.data || err;
  }
};

export const getCurrentUser = async (token) => {
  try {
    const res = await axios.get(`${API_URL}/me`, {
      headers: { Authorization: `Bearer ${token}` }
    });
    return res.data;
  } catch (err) {
    throw err.response?.data || err;
  }
};
