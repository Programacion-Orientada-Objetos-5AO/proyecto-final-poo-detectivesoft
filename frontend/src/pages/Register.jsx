import { useState } from "react";
import { registerUser } from "../services/api";
import "../styles/register.css";

function Register() {
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validar contraseñas iguales antes de enviar
    if (form.password !== form.confirmPassword) {
      setMessage("❌ Las contraseñas no coinciden.");
      return;
    }

    try {
      const userData = {
        username: form.username,
        email: form.email,
        password: form.password,
        verificacionPassword: form.confirmPassword,
      };

      await registerUser(userData);
      setMessage("✅ Registro exitoso. Ahora puedes iniciar sesión.");
      setForm({ username: "", email: "", password: "", confirmPassword: "" });
    } catch (err) {
      setMessage("❌ Error al registrarse. Verifica los datos.");
    }
  };

  return (
    <div className="register-container">
      <div className="register-card">
        <h2>Registro</h2>
        <form onSubmit={handleSubmit}>
          <input
            name="username"
            type="text"
            placeholder="Usuario"
            value={form.username}
            onChange={handleChange}
            required
          />
          <input
            name="email"
            type="email"
            placeholder="Correo electrónico"
            value={form.email}
            onChange={handleChange}
            required
          />
          <input
            name="password"
            type="password"
            placeholder="Contraseña"
            value={form.password}
            onChange={handleChange}
            required
          />
          <input
            name="confirmPassword"
            type="password"
            placeholder="Confirmar contraseña"
            value={form.confirmPassword}
            onChange={handleChange}
            required
          />
          <button type="submit">Registrarse</button>
        </form>
        {message && <p style={{ marginTop: "1rem" }}>{message}</p>}
      </div>
    </div>
  );
}

export default Register;