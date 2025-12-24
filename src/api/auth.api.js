import apiClient from "./apiClient";

export const loginApi = async (email, password) => {
  const res = await apiClient.post("/auth/login", { email, password });
  return { user: res.data.user, token: res.data.token };
};

export const registerApi = async (email, password, role) => {
  const res = await apiClient.post("/auth/register", {
    email,
    motDePasse: password,
    role: role === "GUIDE" ? "ROLE_GUIDE" : "ROLE_TOURISTE",
  });
  return res.data.user;
};






