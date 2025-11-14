package com.deckard.server.maze;

/**
 * Tipos de nodos en el laberinto
 */
public enum NodeType {
    CHALLENGE,  // Nodo de desafío con carta de decisión
    EVENT,      // Nodo de evento aleatorio
    REST,       // Nodo de descanso
    BOSS,       // Nodo de jefe (final de piso)
    START       // Nodo inicial
}

