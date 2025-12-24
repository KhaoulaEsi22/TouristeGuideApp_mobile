import apiClient from "./apiClient";

export const getAllGuides = async () => {
  const response = await apiClient.get("/guides");
  return response.data;
};

export const updateGuideDetails = async (guideId, payload) => {
  const response = await apiClient.put(`/guides/${guideId}/details`, payload);
  return response.data;
};



