const API_URL = "http://localhost:8080/api";

export async function registerUser(userData) {
  const response = await fetch(`${API_URL}/jugadores/registrar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  if (!response.ok) {
    throw new Error("Error al registrarse");
  }
  return response.json();
}

export async function loginUser(credentials) {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(credentials),
  });
  if (!response.ok) {
    throw new Error("Usuario o contraseña incorrectos");
  }
  return response.json();
}
