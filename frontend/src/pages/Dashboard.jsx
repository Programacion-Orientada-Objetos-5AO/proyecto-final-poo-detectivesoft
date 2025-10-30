import "../styles/dashboard.css";

function Dashboard() {
  return (
    <div className="dashboard-container">
      <h1>Bienvenido a DetectiveSoft</h1>
      <p>Selecciona una opción para continuar:</p>
      <div className="dashboard-buttons">
        <button className="btn-create">🕹️ Crear partida</button>
        <button className="btn-join">👥 Unirse a partida</button>
      </div>
    </div>
  );
}

export default Dashboard;
