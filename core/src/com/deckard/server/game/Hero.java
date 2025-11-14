package com.deckard.server.game;

/**
 * Representa un heroe elemental que el jugador puede seleccionar
 */
public class Hero {
    private final String name;
    private final ElementType element;
    private final String description;
    private final int maxHealth;
    private int currentHealth;

    // Bonus especiales por elemento
    private final int consumoBonusPercent;
    private final int climaBonusPercent;

    public enum ElementType {
        TIERRA("Tierra", "Protector del suelo y la biodiversidad", 10, 5),
        AGUA("Agua", "Guardian de los oceanos y rios", 5, 10),
        FUEGO("Fuego", "Defensor de la energia renovable", 15, 0),
        AIRE("Aire", "Custodio de la atmosfera limpia", 0, 15);

        private final String displayName;
        private final String description;
        private final int consumoBonus;
        private final int climaBonus;

        ElementType(String displayName, String description, int consumoBonus, int climaBonus) {
            this.displayName = displayName;
            this.description = description;
            this.consumoBonus = consumoBonus;
            this.climaBonus = climaBonus;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDescription() {
            return description;
        }

        public int getConsumoBonus() {
            return consumoBonus;
        }

        public int getClimaBonus() {
            return climaBonus;
        }
    }

    public Hero(ElementType element) {
        this.element = element;
        this.name = "Heroe " + element.getDisplayName();
        this.description = element.getDescription();
        this.maxHealth = 100;
        this.currentHealth = 100;
        this.consumoBonusPercent = element.getConsumoBonus();
        this.climaBonusPercent = element.getClimaBonus();
    }

    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
    }

    public void heal(int amount) {
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }

    public int getHealthPercent() {
        return (currentHealth * 100) / maxHealth;
    }

    // Getters
    public String getName() {
        return name;
    }

    public ElementType getElement() {
        return element;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getConsumoBonusPercent() {
        return consumoBonusPercent;
    }

    public int getClimaBonusPercent() {
        return climaBonusPercent;
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(maxHealth, health));
    }
}

