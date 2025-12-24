import React, { useContext, useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert,
} from "react-native";
import apiClient from "../api/apiClient";
import { AuthContext } from "../context/AuthContext";
import { COLORS } from "../theme/colors";

export default function GuideProfileScreen({ navigation }) {
  const { user } = useContext(AuthContext);

  const [bio, setBio] = useState("");
  const [languesParlees, setLanguesParlees] = useState("");
  const [tarif, setTarif] = useState("0");

  const saveProfile = async () => {
    try {
      await apiClient.put(`/guides/${user.id}/details`, {
        bio,
        languesParlees,
        tarifParHeure: Number(tarif) || 0,
      });

      Alert.alert("Succès", "Profil mis à jour");
      navigation.goBack();
    } catch (e) {
      console.log(e);
      Alert.alert("Erreur", "Mise à jour impossible");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Mon profil guide</Text>

      <TextInput
        style={styles.input}
        placeholder="Bio"
        value={bio}
        onChangeText={setBio}
      />

      <TextInput
        style={styles.input}
        placeholder="Langues parlées"
        value={languesParlees}
        onChangeText={setLanguesParlees}
      />

      <TextInput
        style={styles.input}
        placeholder="Tarif par heure"
        value={tarif}
        onChangeText={setTarif}
        keyboardType="numeric"
      />

      <TouchableOpacity style={styles.button} onPress={saveProfile}>
        <Text style={styles.buttonText}>Enregistrer</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    padding: 24,
    backgroundColor: COLORS.background,
  },
  title: {
    fontSize: 22,
    fontWeight: "bold",
    color: COLORS.secondary,
    marginBottom: 20,
    textAlign: "center",
  },
  input: {
    backgroundColor: COLORS.card,
    padding: 14,
    borderRadius: 10,
    marginBottom: 12,
  },
  button: {
    backgroundColor: COLORS.primary,
    padding: 15,
    borderRadius: 10,
  },
  buttonText: {
    color: "white",
    textAlign: "center",
    fontWeight: "bold",
  },
});

