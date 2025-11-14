package com.deckard.server.boss;

import com.deckard.server.decision.DecisionCard;
import com.deckard.server.decision.DecisionCategory;
import com.deckard.server.decision.DecisionOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un jefe al final de cada piso
 */
public class Boss {
    private final String name;
    private final String description;
    private final int floor;
    private final List<DecisionCard> challenges;
    private int currentChallengeIndex;
    private boolean defeated;

    public Boss(String name, String description, int floor) {
        this.name = name;
        this.description = description;
        this.floor = floor;
        this.challenges = new ArrayList<>();
        this.currentChallengeIndex = 0;
        this.defeated = false;
    }

    public void addChallenge(DecisionCard challenge) {
        challenges.add(challenge);
    }

    public DecisionCard getCurrentChallenge() {
        if (currentChallengeIndex < challenges.size()) {
            return challenges.get(currentChallengeIndex);
        }
        return null;
    }

    public boolean nextChallenge() {
        currentChallengeIndex++;
        if (currentChallengeIndex >= challenges.size()) {
            defeated = true;
            return false;
        }
        return true;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getFloor() {
        return floor;
    }

    public int getRemainingChallenges() {
        return Math.max(0, challenges.size() - currentChallengeIndex);
    }
}

