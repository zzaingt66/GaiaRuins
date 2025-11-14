package com.deckard.server.event;

import com.deckard.server.game.GameState;
import java.util.Random;

/**
 * Representa un evento aleatorio que ocurre en nodos de evento
 */
public class RandomEvent {
    private final String title;
    private final String description;
    private final EventEffect effect;

    public enum EventEffect {
        GAIN_CONSUMO,
        GAIN_CLIMA,
        LOSE_CONSUMO,
        LOSE_CLIMA,
        BALANCE_BOOST,
        MINOR_PENALTY
    }

    public RandomEvent(String title, String description, EventEffect effect) {
        this.title = title;
        this.description = description;
        this.effect = effect;
    }

    public void apply(GameState gameState) {
        Random random = new Random();
        int amount = 10 + random.nextInt(11); // 10-20

        switch (effect) {
            case GAIN_CONSUMO:
                gameState.modifyConsumo(amount);
                break;
            case GAIN_CLIMA:
                gameState.modifyClima(amount);
                break;
            case LOSE_CONSUMO:
                gameState.modifyConsumo(-amount);
                break;
            case LOSE_CLIMA:
                gameState.modifyClima(-amount);
                break;
            case BALANCE_BOOST:
                gameState.modifyConsumo(amount / 2);
                gameState.modifyClima(amount / 2);
                break;
            case MINOR_PENALTY:
                gameState.modifyConsumo(-5);
                gameState.modifyClima(-5);
                break;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public EventEffect getEffect() {
        return effect;
    }
}
