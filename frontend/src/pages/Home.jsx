import React from "react";
import "../styles/Home.css";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="home-container fade-in">
      <h1 className="home-title">🕵️ Bienvenido a Detectivesoft</h1>
      <p className="home-subtitle">
        Resuelve misterios, encuentra al culpable y demuestra tus dotes de detective.
      </p>
      <Link to="/login">
        <button className="home-button">Comenzar</button>
      </Link>
    </div>
  );
}
