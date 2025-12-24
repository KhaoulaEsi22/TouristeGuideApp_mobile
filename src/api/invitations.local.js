import AsyncStorage from "@react-native-async-storage/async-storage";

const STORAGE_KEY = "INVITATIONS";

export const addInvitation = async (invitation) => {
  const existing = await getInvitations();
  const updated = [...existing, invitation];
  await AsyncStorage.setItem(STORAGE_KEY, JSON.stringify(updated));
};

export const getInvitations = async () => {
  const data = await AsyncStorage.getItem(STORAGE_KEY);
  return data ? JSON.parse(data) : [];
};

export const updateInvitationStatus = async (id, statut) => {
  const invitations = await getInvitations();
  const updated = invitations.map((inv) =>
    inv.id === id ? { ...inv, statut } : inv
  );
  await AsyncStorage.setItem(STORAGE_KEY, JSON.stringify(updated));
};
