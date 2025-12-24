import React, { useState } from "react";
import { View, Text, TextInput, TouchableOpacity, StyleSheet } from "react-native";
import { registerApi } from "../api/auth.api";
import { COLORS } from "../theme/colors";

export default function RegisterScreen({ navigation }) {
  const [email, setEmail] = useState("");
  const [motDePasse, setMotDePasse] = useState("");
  const [role, setRole] = useState("ROLE_TOURISTE");
  const [error, setError] = useState("");

  const handleRegister = async () => {
    setError("");
    try {
      await registerApi(email, motDePasse, role);
      alert("Compte créé avec succès. Connectez-vous.");
      navigation.replace("Login");
    } catch (err) {
      console.log("REGISTER ERROR:", err?.response?.data || err);
      setError(err?.response?.data?.message || "Erreur lors de l'inscription");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Créer un compte</Text>

      <TextInput
        placeholder="Email"
        style={styles.input}
        value={email}
        onChangeText={setEmail}
        autoCapitalize="none"
      />

      <TextInput
        placeholder="Mot de passe"
        style={styles.input}
        value={motDePasse}
        onChangeText={setMotDePasse}
        secureTextEntry
      />

      <View style={styles.roles}>
        <TouchableOpacity
          style={[styles.roleButton, role === "ROLE_TOURISTE" && styles.roleSelected]}
          onPress={() => setRole("ROLE_TOURISTE")}
        >
          <Text style={[styles.roleText, role === "ROLE_TOURISTE" && styles.roleTextSelected]}>
            Touriste
          </Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={[styles.roleButton, role === "ROLE_GUIDE" && styles.roleSelected]}
          onPress={() => setRole("ROLE_GUIDE")}
        >
          <Text style={[styles.roleText, role === "ROLE_GUIDE" && styles.roleTextSelected]}>
            Guide
          </Text>
        </TouchableOpacity>
      </View>

      {error !== "" && <Text style={styles.error}>{error}</Text>}

      <TouchableOpacity style={styles.button} onPress={handleRegister}>
        <Text style={styles.buttonText}>S'inscrire</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: COLORS.background, padding: 24, justifyContent: "center" },
  title: { fontSize: 24, fontWeight: "bold", marginBottom: 20, textAlign: "center", color: COLORS.secondary },
  input: { backgroundColor: COLORS.card, padding: 14, borderRadius: 10, marginBottom: 15 },
  roles: { flexDirection: "row", justifyContent: "space-between", marginBottom: 15 },
  roleButton: { flex: 1, padding: 12, borderRadius: 8, backgroundColor: "#E5E7EB", marginHorizontal: 5, alignItems: "center" },
  roleSelected: { backgroundColor: COLORS.primary },
  roleText: { color: "#000", fontWeight: "bold" },
  roleTextSelected: { color: "#fff" },
  button: { backgroundColor: COLORS.primary, padding: 15, borderRadius: 10, marginTop: 10 },
  buttonText: { color: "white", textAlign: "center", fontWeight: "bold" },
  error: { color: "red", marginBottom: 10, textAlign: "center" },
});



