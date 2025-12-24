import React, { useContext } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";

import LoginScreen from "../screens/LoginScreen";
import RegisterScreen from "../screens/RegisterScreen";
import TouristHomeScreen from "../screens/TouristHomeScreen";
import GuideHomeScreen from "../screens/GuideHomeScreen";
import { AuthContext } from "../context/AuthContext";

const Stack = createNativeStackNavigator();

export default function AppNavigator() {
  // utilisateur connecté depuis le contexte
  const { user } = useContext(AuthContext);

  return (
    <NavigationContainer>
      <Stack.Navigator screenOptions={{ headerShown: false }}>
        {/* Si pas connecté */}
        {!user ? (
          <>
            <Stack.Screen name="Login" component={LoginScreen} />
            <Stack.Screen name="Register" component={RegisterScreen} />
          </>
        ) : (
          // Si connecté, redirection selon le rôle
          user.role === "ROLE_GUIDE" ? (
            <Stack.Screen name="GuideHome" component={GuideHomeScreen} />
          ) : (
            <Stack.Screen name="TouristHome" component={TouristHomeScreen} />
          )
        )}
      </Stack.Navigator>
    </NavigationContainer>
  );
}






