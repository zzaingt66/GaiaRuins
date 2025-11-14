package com.deckard.server.game;

import com.deckard.server.boss.Boss;
import com.deckard.server.boss.BossFactory;
import com.deckard.server.decision.DecisionCard;
import com.deckard.server.decision.DecisionCardFactory;
import com.deckard.server.decision.DecisionOption;
import com.deckard.server.event.RandomEvent;
import com.deckard.server.event.RandomEventFactory;
import com.deckard.server.maze.Maze;
import com.deckard.server.maze.MazeNode;
import com.deckard.server.maze.NodeType;

/**
 * Controlador principal del juego Gaia Ruins
 */
public class GaiaRuinsGame {
    private GameState gameState;
    private Maze currentMaze;
    private DecisionCardFactory cardFactory;
    private RandomEventFactory eventFactory;
    private BossFactory bossFactory;
    private Boss currentBoss;
    private DecisionCard currentCard;
    private RandomEvent currentEvent;
    private GamePhase currentPhase;

    public enum GamePhase {
        MENU,
        MAP_VIEW,
        CHALLENGE,
        EVENT,
        REST,
        BOSS_FIGHT,
        GAME_OVER,
        VICTORY
    }

    public GaiaRuinsGame() {
        this.gameState = new GameState();
        this.cardFactory = new DecisionCardFactory();
        this.eventFactory = new RandomEventFactory();
        this.bossFactory = new BossFactory();
        this.currentPhase = GamePhase.MENU;
    }

    public void selectHero(Hero hero) {
        gameState.setSelectedHero(hero);
    }

    public void startNewGame() {
        gameState = new GameState();
        currentMaze = new Maze(1);
        currentPhase = GamePhase.MAP_VIEW;
        currentBoss = null;
        currentCard = null;
        currentEvent = null;
    }

    public void enterNode(MazeNode node) {
        if (!currentMaze.getCurrentNode().getConnections().contains(node)) {
            return; // No se puede mover a este nodo
        }

        currentMaze.moveToNode(node);

        switch (node.getType()) {
            case CHALLENGE:
                startChallenge();
                break;
            case EVENT:
                startEvent();
                break;
            case REST:
                startRest();
                break;
            case BOSS:
                startBossFight();
                break;
            case START:
                currentPhase = GamePhase.MAP_VIEW;
                break;
        }
    }

    private void startChallenge() {
        currentCard = cardFactory.createRandomCard();
        currentPhase = GamePhase.CHALLENGE;
    }

    public void selectOption(int optionIndex) {
        if (currentPhase == GamePhase.CHALLENGE && currentCard != null) {
            DecisionOption option = currentCard.getOption(optionIndex);
            if (option != null) {
                applyDecision(option);
                completeNode();
            }
        } else if (currentPhase == GamePhase.BOSS_FIGHT && currentBoss != null) {
            DecisionCard bossChallenge = currentBoss.getCurrentChallenge();
            if (bossChallenge != null) {
                DecisionOption option = bossChallenge.getOption(optionIndex);
                if (option != null) {
                    applyDecision(option);

                    if (!currentBoss.nextChallenge()) {
                        // Jefe derrotado
                        completeBoss();
                    }

                    checkGameOver();
                }
            }
        }
    }

    private void applyDecision(DecisionOption option) {
        gameState.modifyConsumo(option.getConsumoImpact());
        gameState.modifyClima(option.getClimaImpact());

        // Si es decision negativa, aumenta contaminacion y dana al jugador
        int totalImpact = option.getConsumoImpact() + option.getClimaImpact();
        if (totalImpact < 0) {
            int pollutionIncrease = Math.abs(totalImpact) / 3;
            gameState.modifyPollutionLevel(pollutionIncrease);
            gameState.modifyPlayerHealth(totalImpact / 4); // Dano proporcional
        } else if (totalImpact > 0) {
            // Decision positiva: reduce contaminacion y cura
            int pollutionDecrease = -totalImpact / 4;
            gameState.modifyPollutionLevel(pollutionDecrease);
            gameState.modifyPlayerHealth(totalImpact / 5); // Curacion proporcional
        }
    }

    private void startEvent() {
        currentEvent = eventFactory.createRandomEvent();
        currentPhase = GamePhase.EVENT;
    }

    public void acceptEvent() {
        if (currentEvent != null) {
            currentEvent.apply(gameState);
            completeNode();
        }
    }

    private void startRest() {
        currentPhase = GamePhase.REST;
        // Recuperar indicadores
        gameState.modifyConsumo(15);
        gameState.modifyClima(15);
    }

    public void leaveRest() {
        completeNode();
    }

    private void startBossFight() {
        currentBoss = bossFactory.createBoss(gameState.getCurrentFloor());
        currentPhase = GamePhase.BOSS_FIGHT;
    }

    private void completeBoss() {
        currentMaze.getCurrentNode().markCompleted();

        if (gameState.getCurrentFloor() >= 3) {
            // Victoria total
            currentPhase = GamePhase.VICTORY;
        } else {
            // Siguiente piso
            gameState.nextFloor();
            currentMaze = new Maze(gameState.getCurrentFloor());
            currentPhase = GamePhase.MAP_VIEW;
        }

        currentBoss = null;
    }

    private void completeNode() {
        currentMaze.getCurrentNode().markCompleted();
        gameState.completeNode();

        checkGameOver();

        if (currentPhase != GamePhase.GAME_OVER) {
            currentPhase = GamePhase.MAP_VIEW;
        }

        currentCard = null;
        currentEvent = null;
    }

    private void checkGameOver() {
        if (gameState.isGameOver()) {
            currentPhase = GamePhase.GAME_OVER;
        }
    }

    // Getters
    public GameState getGameState() {
        return gameState;
    }

    public Maze getCurrentMaze() {
        return currentMaze;
    }

    public DecisionCard getCurrentCard() {
        return currentCard;
    }

    public RandomEvent getCurrentEvent() {
        return currentEvent;
    }

    public Boss getCurrentBoss() {
        return currentBoss;
    }

    public GamePhase getCurrentPhase() {
        return currentPhase;
    }

    public void setPhase(GamePhase phase) {
        this.currentPhase = phase;
    }
}

