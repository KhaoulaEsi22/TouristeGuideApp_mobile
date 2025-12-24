import axios from "axios";
import AsyncStorage from "@react-native-async-storage/async-storage";

const apiClient = axios.create({
  baseURL: "http://192.168.0.103:8085/api",
  headers: { "Content-Type": "application/json" },
});

apiClient.interceptors.request.use(async (config) => {
  if (!config.url.includes("/auth")) {
    const token = await AsyncStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

export default apiClient;
