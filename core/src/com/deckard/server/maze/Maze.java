package com.deckard.server.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa el laberinto completo de un piso
 */
public class Maze {
    private final List<MazeNode> nodes;
    private final MazeNode startNode;
    private MazeNode currentNode;
    private final int floor;
    private final Random random;

    private static final int NODES_PER_ROW = 3;
    private static final int ROWS_PER_FLOOR = 4; // 3 filas normales + 1 jefe

    public Maze(int floor) {
        this.floor = floor;
        this.nodes = new ArrayList<>();
        this.random = new Random();

        // Generar el laberinto
        generateMaze();

        // Establecer nodo inicial
        this.startNode = nodes.get(0);
        this.currentNode = startNode;
        this.startNode.markVisited();
    }

    private void generateMaze() {
        int nodeId = 0;

        // Crear nodo inicial
        MazeNode start = new MazeNode(nodeId++, NodeType.START, 1, 0);
        nodes.add(start);

        List<MazeNode> previousRow = new ArrayList<>();
        previousRow.add(start);

        // Generar filas intermedias
        for (int row = 1; row < ROWS_PER_FLOOR; row++) {
            List<MazeNode> currentRow = new ArrayList<>();

            for (int col = 0; col < NODES_PER_ROW; col++) {
                NodeType type = generateNodeType(row);
                MazeNode node = new MazeNode(nodeId++, type, col, row);
                nodes.add(node);
                currentRow.add(node);
            }

            // Conectar con la fila anterior
            connectRows(previousRow, currentRow);
            previousRow = currentRow;
        }

        // Crear nodo jefe
        MazeNode boss = new MazeNode(nodeId++, NodeType.BOSS, 1, ROWS_PER_FLOOR);
        nodes.add(boss);

        // Conectar todos los nodos de la última fila con el jefe
        for (MazeNode node : previousRow) {
            node.addConnection(boss);
        }
    }

    private NodeType generateNodeType(int row) {
        int rand = random.nextInt(100);

        // Probabilidades ajustadas por fila
        if (rand < 60) {
            return NodeType.CHALLENGE;
        } else if (rand < 85) {
            return NodeType.EVENT;
        } else {
            return NodeType.REST;
        }
    }

    private void connectRows(List<MazeNode> previousRow, List<MazeNode> currentRow) {
        // Cada nodo de la fila anterior se conecta con 1-3 nodos de la fila actual
        for (MazeNode prevNode : previousRow) {
            int connections = 1 + random.nextInt(2); // 1 o 2 conexiones

            for (int i = 0; i < connections && i < currentRow.size(); i++) {
                int targetIndex = random.nextInt(currentRow.size());
                prevNode.addConnection(currentRow.get(targetIndex));
            }

            // Asegurar al menos una conexión
            if (prevNode.getConnections().isEmpty()) {
                prevNode.addConnection(currentRow.get(random.nextInt(currentRow.size())));
            }
        }
    }

    public void moveToNode(MazeNode node) {
        if (currentNode.getConnections().contains(node)) {
            currentNode = node;
            currentNode.markVisited();
        }
    }

    public boolean isFloorComplete() {
        return currentNode.getType() == NodeType.BOSS && currentNode.isCompleted();
    }

    // Getters
    public List<MazeNode> getNodes() {
        return nodes;
    }

    public MazeNode getStartNode() {
        return startNode;
    }

    public MazeNode getCurrentNode() {
        return currentNode;
    }

    public int getFloor() {
        return floor;
    }
}
