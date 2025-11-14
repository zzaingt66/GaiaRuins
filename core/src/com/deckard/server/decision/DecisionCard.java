package com.deckard.server.decision;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una carta de decision con una pregunta de verdadero/falso
 */
public class DecisionCard {
    private final String title;
    private final String question; // Pregunta de verdadero/falso
    private final DecisionCategory category;
    private final boolean correctAnswer; // true = Verdadero, false = Falso
    private final String explanation; // Explicacion de la respuesta correcta
    private final List<DecisionOption> options; // 2 opciones: Verdadero y Falso

    public DecisionCard(String title, String question, DecisionCategory category, boolean correctAnswer, String explanation) {
        this.title = title;
        this.question = question;
        this.category = category;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.options = new ArrayList<>();
    }

    public void addOption(DecisionOption option) {
        if (options.size() < 2) {
            options.add(option);
        }
    }

    public boolean isCorrectAnswer(boolean answer) {
        return answer == correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getTitle() {
        return title;
    }


    public DecisionCategory getCategory() {
        return category;
    }

    public List<DecisionOption> getOptions() {
        return options;
    }

    public DecisionOption getOption(int index) {
        if (index >= 0 && index < options.size()) {
            return options.get(index);
        }
        return null;
    }
}

