import apiClient from "./apiClient";

export const createDemande = async (demande) => {
  const response = await apiClient.post("/demandes", demande);
  return response.data;
};
