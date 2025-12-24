import React, { useEffect, useState, useContext } from "react";
import {
  View,
  Text,
  FlatList,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import { AuthContext } from "../context/AuthContext";
import { COLORS } from "../theme/colors";
import {
  getInvitations,
  updateInvitationStatus,
} from "../api/invitations.local";

export default function GuideHomeScreen() {
  // invitations reçues par le guide
  const [invitations, setInvitations] = useState([]);

  // utilisateur connecté + logout
  const { user, logout } = useContext(AuthContext);

  // charger les invitations au chargement de l'écran
  useEffect(() => {
    loadInvitations();
  }, []);

  // récupérer les invitations liées à ce guide
  const loadInvitations = async () => {
    const data = await getInvitations(user.id);
    setInvitations(data);
  };

  // accepter une invitation
  const handleAccept = async (invitationId) => {
    await updateInvitationStatus(invitationId, "ACCEPTEE");
    loadInvitations();
  };

  // refuser une invitation
  const handleRefuse = async (invitationId) => {
    await updateInvitationStatus(invitationId, "REFUSEE");
    loadInvitations();
  };

  return (
    <View style={styles.container}>
      {/* Titre avec espace en haut */}
      <View style={styles.header}>
        <Text style={styles.title}>Invitations des touristes</Text>
      </View>

      {/* Liste des invitations */}
      {invitations.length === 0 ? (
        <View style={styles.center}>
          <Text style={styles.emptyText}>Aucune invitation reçue</Text>
        </View>
      ) : (
        <FlatList
          data={invitations}
          keyExtractor={(item) => item.id}
          renderItem={({ item }) => (
            <View style={styles.card}>
              <Text style={styles.name}>
                Touriste : {item.touristeName}
              </Text>

              <Text style={styles.status}>
                Statut : {item.statut}
              </Text>

              {/* Boutons seulement si la demande est en attente */}
              {item.statut === "EN_ATTENTE" && (
                <View style={styles.actions}>
                  <TouchableOpacity
                    style={styles.accept}
                    onPress={() => handleAccept(item.id)}
                  >
                    <Text style={styles.btnText}>Accepter</Text>
                  </TouchableOpacity>

                  <TouchableOpacity
                    style={styles.refuse}
                    onPress={() => handleRefuse(item.id)}
                  >
                    <Text style={styles.btnText}>Refuser</Text>
                  </TouchableOpacity>
                </View>
              )}
            </View>
          )}
        />
      )}

      {/* Bouton de déconnexion en bas */}
      <TouchableOpacity style={styles.logout} onPress={logout}>
        <Text style={styles.logoutText}>Déconnexion</Text>
      </TouchableOpacity>
    </View>
  );
}

// styles de l'écran guide
const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.background,
    paddingTop: 50,
    paddingHorizontal: 16,
  },
  header: {
    marginBottom: 16,
  },
  title: {
    fontSize: 22,
    fontWeight: "bold",
    color: COLORS.secondary,
  },
  card: {
    backgroundColor: COLORS.card,
    padding: 16,
    borderRadius: 12,
    marginBottom: 12,
    borderWidth: 1,
    borderColor: "#E5E7EB",
  },
  name: {
    fontWeight: "bold",
    marginBottom: 6,
  },
  status: {
    color: COLORS.muted,
    marginBottom: 10,
  },
  actions: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  accept: {
    backgroundColor: COLORS.primary,
    padding: 10,
    borderRadius: 8,
    width: "48%",
    alignItems: "center",
  },
  refuse: {
    backgroundColor: "#DC2626",
    padding: 10,
    borderRadius: 8,
    width: "48%",
    alignItems: "center",
  },
  btnText: {
    color: "white",
    fontWeight: "bold",
  },
  logout: {
    marginTop: 10,
    padding: 14,
    backgroundColor: "#111827",
    borderRadius: 10,
    alignItems: "center",
  },
  logoutText: {
    color: "white",
    fontWeight: "bold",
  },
  center: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  emptyText: {
    color: COLORS.muted,
  },
});






