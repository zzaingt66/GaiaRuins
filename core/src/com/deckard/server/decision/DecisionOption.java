package com.deckard.server.decision;

/**
 * Representa una opción de decisión en una carta
 */
public class DecisionOption {
    private final String text;
    private final int consumoImpact; // Impacto en indicador de consumo (-100 a +100)
    private final int climaImpact;   // Impacto en indicador de clima (-100 a +100)
    private final String resultText; // Texto que se muestra después de elegir

    public DecisionOption(String text, int consumoImpact, int climaImpact, String resultText) {
        this.text = text;
        this.consumoImpact = consumoImpact;
        this.climaImpact = climaImpact;
        this.resultText = resultText;
    }

    public String getText() {
        return text;
    }

    public int getConsumoImpact() {
        return consumoImpact;
    }

    public int getClimaImpact() {
        return climaImpact;
    }

    public String getResultText() {
        return resultText;
    }
}

