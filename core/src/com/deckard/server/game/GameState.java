package com.deckard.server.game;

/**
 * Representa el estado global del juego Gaia Ruins
 */
public class GameState {
    private int consumoIndicator; // 0-100
    private int climaIndicator; // 0-100
    private int currentFloor; // Piso actual (1-3)
    private int nodesCompleted; // Nodos completados en el piso actual

    // Sistema de vida: Jugador vs Contaminacion
    private Hero selectedHero;
    private int playerHealth; // Vida del jugador (0-100)
    private int pollutionLevel; // Nivel de contaminacion (0-100)

    public GameState() {
        this.consumoIndicator = 100;
        this.climaIndicator = 100;
        this.currentFloor = 1;
        this.nodesCompleted = 0;
        this.playerHealth = 100;
        this.pollutionLevel = 0;
        this.selectedHero = null;
    }

    public void setSelectedHero(Hero hero) {
        this.selectedHero = hero;
        this.playerHealth = hero.getCurrentHealth();
    }

    public void modifyConsumo(int amount) {
        consumoIndicator = Math.max(0, Math.min(100, consumoIndicator + amount));
        // Si baja el consumo, aumenta la contaminacion
        if (amount < 0) {
            pollutionLevel = Math.min(100, pollutionLevel + Math.abs(amount) / 2);
        }
    }

    public void modifyClima(int amount) {
        climaIndicator = Math.max(0, Math.min(100, climaIndicator + amount));
        // Si baja el clima, aumenta la contaminacion
        if (amount < 0) {
            pollutionLevel = Math.min(100, pollutionLevel + Math.abs(amount) / 2);
        }
    }

    public void modifyPlayerHealth(int amount) {
        playerHealth = Math.max(0, Math.min(100, playerHealth + amount));
        if (selectedHero != null) {
            selectedHero.setCurrentHealth(playerHealth);
        }
    }

    public void modifyPollutionLevel(int amount) {
        pollutionLevel = Math.max(0, Math.min(100, pollutionLevel + amount));
        // Si la contaminacion es muy alta, dana al jugador
        if (pollutionLevel >= 75) {
            modifyPlayerHealth(-5);
        }
    }

    public boolean isGameOver() {
        return consumoIndicator <= 0 || climaIndicator <= 0 || playerHealth <= 0 || pollutionLevel >= 100;
    }

    public boolean isVictory() {
        return currentFloor > 3;
    }

    public void nextFloor() {
        currentFloor++;
        nodesCompleted = 0;
    }

    public void completeNode() {
        nodesCompleted++;
    }

    // Getters
    public int getConsumoIndicator() {
        return consumoIndicator;
    }

    public int getClimaIndicator() {
        return climaIndicator;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getNodesCompleted() {
        return nodesCompleted;
    }

    public void setConsumoIndicator(int consumoIndicator) {
        this.consumoIndicator = Math.max(0, Math.min(100, consumoIndicator));
    }

    public void setClimaIndicator(int climaIndicator) {
        this.climaIndicator = Math.max(0, Math.min(100, climaIndicator));
    }

    public Hero getSelectedHero() {
        return selectedHero;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public int getPollutionLevel() {
        return pollutionLevel;
    }

    public void setPlayerHealth(int health) {
        this.playerHealth = Math.max(0, Math.min(100, health));
        if (selectedHero != null) {
            selectedHero.setCurrentHealth(health);
        }
    }

    public void setPollutionLevel(int level) {
        this.pollutionLevel = Math.max(0, Math.min(100, level));
    }
}
