import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../services/api";
import "../styles/login.css";

function Login({ setIsAuthenticated }) {
  const [form, setForm] = useState({ email: "", password: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    try {
      const response = await loginUser(form);

      if (response.token) {
        localStorage.setItem("token", response.token);
        setIsAuthenticated(true);
        navigate("/dashboard");
      }
    } catch (err) {
      setMessage("❌ Error al iniciar sesión. Verifica tus datos.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h2>Inicio de Sesión</h2>
        <form onSubmit={handleSubmit}>
          <input name="email" type="email" placeholder="Correo electrónico" onChange={handleChange} required />
          <input name="password" type="password" placeholder="Contraseña" onChange={handleChange} required />
          <button type="submit">Iniciar Sesión</button>
        </form>
        {message && <p style={{ marginTop: "1rem" }}>{message}</p>}
      </div>
    </div>
  );
}

export default Login;
