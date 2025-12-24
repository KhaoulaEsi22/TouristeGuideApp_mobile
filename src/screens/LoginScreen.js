import React, { useContext, useState } from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Image,
} from "react-native";
import { AuthContext } from "../context/AuthContext";
import { loginApi } from "../api/auth.api";
import { COLORS } from "../theme/colors";
import { useAuth } from "../context/AuthContext";


export default function LoginScreen({ navigation }) {
  const { login } = useAuth();


  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async () => {
    setError("");
    try {
      const { user, token } = await loginApi(email, password);
      await login(user, token);
    } catch (err) {
      console.log(err);
      setError("Email ou mot de passe incorrect");
    }
  };

  return (
    <View style={styles.container}>
      <Image source={require("../assets/logo.png")} style={styles.logo} />

      <Text style={styles.title}>Tourist Guide</Text>

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
        value={password}
        onChangeText={setPassword}
        secureTextEntry
      />

      {error !== "" && <Text style={styles.error}>{error}</Text>}

      <TouchableOpacity style={styles.button} onPress={handleLogin}>
        <Text style={styles.buttonText}>Se connecter</Text>
      </TouchableOpacity>

      <Text
        style={styles.registerLink}
        onPress={() => navigation.navigate("Register")}
      >
        Créer un compte
      </Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    padding: 24,
    backgroundColor: COLORS.background,
  },
  logo: {
    width: 110,
    height: 110,
    marginBottom: 20,
  },
  title: {
    fontSize: 26,
    fontWeight: "bold",
    marginBottom: 20,
    color: COLORS.secondary,
  },
  input: {
    width: "100%",
    backgroundColor: COLORS.card,
    padding: 14,
    borderRadius: 10,
    marginBottom: 15,
  },
  button: {
    backgroundColor: COLORS.primary,
    width: "100%",
    padding: 15,
    borderRadius: 10,
  },
  buttonText: {
    color: "white",
    textAlign: "center",
    fontWeight: "bold",
  },
  error: {
    color: "red",
    marginTop: 10,
  },
  registerLink: {
    marginTop: 20,
    color: COLORS.primary,
    fontWeight: "bold",
  },
});




