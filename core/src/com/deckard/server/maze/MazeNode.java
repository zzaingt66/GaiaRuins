package com.deckard.server.maze;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nodo en el laberinto
 */
public class MazeNode {
    private final NodeType type;
    private final int id;
    private final int x, y; // Posición en el laberinto
    private final List<MazeNode> connections; // Nodos conectados
    private boolean completed;
    private boolean visited;

    public MazeNode(int id, NodeType type, int x, int y) {
        this.id = id;
        this.type = type;
        this.x = x;
        this.y = y;
        this.connections = new ArrayList<>();
        this.completed = false;
        this.visited = false;
    }

    public void addConnection(MazeNode node) {
        if (!connections.contains(node)) {
            connections.add(node);
        }
    }

    public void markCompleted() {
        this.completed = true;
    }

    public void markVisited() {
        this.visited = true;
    }

    // Getters
    public NodeType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<MazeNode> getConnections() {
        return connections;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isVisited() {
        return visited;
    }
}

