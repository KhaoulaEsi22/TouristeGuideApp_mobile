import React, { useEffect, useState, useContext } from "react";
import {
  View,
  Text,
  FlatList,
  StyleSheet,
  ActivityIndicator,
  TouchableOpacity,
} from "react-native";
import { getAllGuides } from "../api/guide.api";
import { addInvitation } from "../api/invitations.local";
import { AuthContext } from "../context/AuthContext";
import { COLORS } from "../theme/colors";

export default function TouristHomeScreen() {
  const [guides, setGuides] = useState([]);
  const [loading, setLoading] = useState(true);
  const { user, logout } = useContext(AuthContext);

  useEffect(() => {
    loadGuides();
  }, []);

  const loadGuides = async () => {
    try {
      const data = await getAllGuides();
      setGuides(data);
    } catch (error) {
      console.log("Erreur chargement guides :", error);
    } finally {
      setLoading(false);
    }
  };

  const handleInvite = async (guide) => {
    await addInvitation({
      id: Date.now().toString(),
      title: "Demande de visite",
      description: "Visite touristique",
      guideId: guide.id,
      guideName: "Guide",
      touristeName: user.email,
      statut: "EN_ATTENTE",
    });
    alert("Invitation envoyée avec succès");
  };

  if (loading) {
    return <ActivityIndicator size="large" style={{ marginTop: 80 }} />;
  }

  return (
    <View style={styles.container}>
      {/* HEADER AVEC ESPACE EN HAUT */}
      <View style={styles.header}>
        <Text style={styles.headerTitle}>Guides disponibles</Text>
        <TouchableOpacity onPress={logout}>
          <Text style={styles.logout}>Déconnexion</Text>
        </TouchableOpacity>
      </View>

      {guides.length === 0 ? (
        <View style={styles.center}>
          <Text style={styles.emptyText}>Aucun guide disponible</Text>
        </View>
      ) : (
        <FlatList
          data={guides}
          keyExtractor={(item) => item.id}
          contentContainerStyle={{ paddingBottom: 20 }}
          renderItem={({ item }) => (
            <View style={styles.card}>
              <Text style={styles.bio}>
                {item.bio || "Guide touristique"}
              </Text>

              <Text style={styles.info}>
                Langues : {item.languesParlees || "Non renseigné"}
              </Text>

              <Text style={styles.price}>
                Tarif : {item.tarifParHeure || 0} DH / heure
              </Text>

              <TouchableOpacity
                style={styles.button}
                onPress={() => handleInvite(item)}
              >
                <Text style={styles.buttonText}>Inviter ce guide</Text>
              </TouchableOpacity>
            </View>
          )}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.background,
    paddingHorizontal: 16,
    paddingTop: 50, // ESPACE EN HAUT IMPORTANT
  },
  header: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: 16,
  },
  headerTitle: {
    fontSize: 22,
    fontWeight: "bold",
    color: COLORS.secondary,
  },
  logout: {
    color: COLORS.primary,
    fontWeight: "bold",
  },
  card: {
    backgroundColor: COLORS.card,
    padding: 16,
    borderRadius: 12,
    marginBottom: 12,
    borderWidth: 1,
    borderColor: "#E5E7EB",
  },
  bio: {
    fontSize: 16,
    fontWeight: "600",
    marginBottom: 6,
  },
  info: {
    color: COLORS.muted,
    marginBottom: 4,
  },
  price: {
    fontWeight: "bold",
    marginTop: 4,
    marginBottom: 10,
  },
  button: {
    backgroundColor: COLORS.primary,
    padding: 10,
    borderRadius: 8,
    alignItems: "center",
  },
  buttonText: {
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
    fontSize: 16,
  },
});


