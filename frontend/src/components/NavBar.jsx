import { Link, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";

function Navbar({ isAuthenticated, setIsAuthenticated }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsAuthenticated(false);
    navigate("/");
  };

  return (
    <nav className="navbar">
      <Link to="/" className="navbar-brand">🕵️ DETECTIVE SOFT</Link>
      <div>
        {isAuthenticated ? (
          <button className="nav-button" onClick={handleLogout}>Cerrar sesión</button>
        ) : (
          <>
            <Link to="/login" className="nav-link">Iniciar sesión</Link>
            <Link to="/register" className="nav-link">Registrarse</Link>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
