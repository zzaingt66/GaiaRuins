package com.deckard.server.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory para crear eventos aleatorios
 */
public class RandomEventFactory {
    private final List<RandomEvent> positiveEvents;
    private final List<RandomEvent> negativeEvents;
    private final Random random;

    public RandomEventFactory() {
        this.positiveEvents = new ArrayList<>();
        this.negativeEvents = new ArrayList<>();
        this.random = new Random();
        initializeEvents();
    }

    private void initializeEvents() {
        // Eventos positivos
        positiveEvents.add(new RandomEvent(
            "Comunidad Solidaria",
            "Una comunidad local organiza un proyecto de reciclaje. ¡Te sumas!",
            RandomEvent.EventEffect.GAIN_CONSUMO
        ));

        positiveEvents.add(new RandomEvent(
            "Energía Renovable",
            "Tu zona instala paneles solares comunitarios. ¡Menos emisiones!",
            RandomEvent.EventEffect.GAIN_CLIMA
        ));

        positiveEvents.add(new RandomEvent(
            "Día de Acción Ambiental",
            "Participas en jornada de limpieza y plantación de árboles.",
            RandomEvent.EventEffect.BALANCE_BOOST
        ));

        positiveEvents.add(new RandomEvent(
            "Subsidio Verde",
            "Recibes incentivos por prácticas sostenibles.",
            RandomEvent.EventEffect.BALANCE_BOOST
        ));

        // Eventos negativos
        negativeEvents.add(new RandomEvent(
            "Ola de Consumismo",
            "Temporada de rebajas te tienta a comprar innecesariamente.",
            RandomEvent.EventEffect.LOSE_CONSUMO
        ));

        negativeEvents.add(new RandomEvent(
            "Ola de Calor",
            "Temperaturas extremas te obligan a usar más energía.",
            RandomEvent.EventEffect.LOSE_CLIMA
        ));

        negativeEvents.add(new RandomEvent(
            "Día Difícil",
            "El estrés te lleva a malas decisiones ambientales.",
            RandomEvent.EventEffect.MINOR_PENALTY
        ));

        negativeEvents.add(new RandomEvent(
            "Corte de Servicios",
            "Problemas de infraestructura afectan tus planes sostenibles.",
            RandomEvent.EventEffect.MINOR_PENALTY
        ));
    }

    public RandomEvent createRandomEvent() {
        // 60% positivo, 40% negativo
        boolean isPositive = random.nextInt(100) < 60;

        if (isPositive) {
            return positiveEvents.get(random.nextInt(positiveEvents.size()));
        } else {
            return negativeEvents.get(random.nextInt(negativeEvents.size()));
        }
    }
}

