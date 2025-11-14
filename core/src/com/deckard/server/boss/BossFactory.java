package com.deckard.server.boss;

import com.deckard.server.decision.DecisionCard;
import com.deckard.server.decision.DecisionCategory;
import com.deckard.server.decision.DecisionOption;

/**
 * Factory para crear jefes
 */
public class BossFactory {

    public Boss createBoss(int floor) {
        switch (floor) {
            case 1:
                return createFloor1Boss();
            case 2:
                return createFloor2Boss();
            case 3:
                return createFloor3Boss();
            default:
                return createFloor1Boss();
        }
    }

    private Boss createFloor1Boss() {
        Boss boss = new Boss(
            "La Montana de Residuos",
            "Un enorme acumulacion de basura bloquea tu camino. Representa el consumo desmedido.",
            1
        );

        // Desafio 1
        DecisionCard challenge1 = new DecisionCard(
            "Crisis Global del Plastico",
            "Para 2050, habra mas plastico que peces en los oceanos (en peso). Actualmente, 8 millones de toneladas de plastico entran al mar cada ano, formando islas flotantes del tamano de paises enteros como el Gran Parche de Basura del Pacifico",
            DecisionCategory.CONSUMO_RESPONSABLE,
            true,
            "VERDADERO: A este ritmo, en 2050 los oceanos tendran mas plastico que vida marina. Es una crisis planetaria"
        );
        challenge1.addOption(new DecisionOption("VERDADERO", 28, 20, "Correcto! Debemos detener la marea de plastico"));
        challenge1.addOption(new DecisionOption("FALSO", -25, -18, "Incorrecto. Los oceanos estan ahogandose en plastico"));
        boss.addChallenge(challenge1);

        // Desafio 2
        DecisionCard challenge2 = new DecisionCard(
            "Extraccion de Recursos Minerales",
            "La humanidad extrae 100 mil millones de toneladas de materiales del planeta cada ano. Al ritmo actual, necesitariamos 1.75 planetas Tierra para sostener nuestro consumo. La sobreexplotacion minera causa deforestacion, contaminacion y conflictos armados",
            DecisionCategory.CONSUMO_RESPONSABLE,
            true,
            "VERDADERO: Consumimos recursos 75% mas rapido de lo que el planeta puede regenerar. Vivimos a credito ecologico"
        );
        challenge2.addOption(new DecisionOption("VERDADERO", 32, 24, "Correcto! Estamos agotando el planeta"));
        challenge2.addOption(new DecisionOption("FALSO", -28, -20, "Incorrecto. Excedemos la capacidad planetaria"));
        boss.addChallenge(challenge2);

        return boss;
    }

    private Boss createFloor2Boss() {
        Boss boss = new Boss(
            "La Tormenta de Carbono",
            "Una nube oscura de emisiones contamina el cielo. El cambio climatico acelera.",
            2
        );

        DecisionCard challenge1 = new DecisionCard(
            "Retroalimentacion del Metano Submarino",
            "En el fondo oceanico hay billones de toneladas de hidratos de metano congelados. El calentamiento del agua libera este metano, un gas 84 veces mas potente que el CO2 en 20 anos. Esta liberacion masiva ya comenzo y es irreversible a corto plazo",
            DecisionCategory.ACCION_CLIMATICA,
            true,
            "VERDADERO: Los hidratos de metano submarino son una bomba climatica que ya esta activandose debido al calentamiento oceanico"
        );
        challenge1.addOption(new DecisionOption("VERDADERO", 30, 40, "Correcto! El metano submarino es una amenaza real"));
        challenge1.addOption(new DecisionOption("FALSO", -28, -35, "Incorrecto. Esta retroalimentacion ya esta en marcha"));
        boss.addChallenge(challenge1);

        DecisionCard challenge2 = new DecisionCard(
            "Colapso de la Corriente del Golfo",
            "El deshielo de Groenlandia esta diluyendo el agua salada del Atlantico Norte, debilitando la Corriente del Golfo en un 15%. Si colapsa completamente, Europa podria enfriarse drasticamente mientras el resto del mundo se calienta, causando caos climatico global",
            DecisionCategory.ACCION_CLIMATICA,
            true,
            "VERDADERO: La Corriente del Golfo esta en su punto mas debil en 1,600 anos. Su colapso causaria un caos climatico sin precedentes"
        );
        challenge2.addOption(new DecisionOption("VERDADERO", 32, 42, "Correcto! Las corrientes oceanicas estan en peligro"));
        challenge2.addOption(new DecisionOption("FALSO", -30, -38, "Incorrecto. Los oceanografos confirman este riesgo"));
        boss.addChallenge(challenge2);

        return boss;
    }

    private Boss createFloor3Boss() {
        Boss boss = new Boss(
            "El Colapso Final",
            "Consumo y clima convergen en crisis sistemica. El desafio definitivo.",
            3
        );

        DecisionCard challenge1 = new DecisionCard(
            "Cascada de Puntos de Inflexion",
            "Cientificos identifican 15 puntos de inflexion climaticos interconectados. Activar uno puede desencadenar otros en cascada: deshielo artico → cambio corrientes → muerte Amazonia → liberacion metano. Una vez iniciado, el proceso es imparable por siglos",
            DecisionCategory.ACCION_CLIMATICA,
            true,
            "VERDADERO: Los puntos de inflexion forman un efecto domino. Estamos peligrosamente cerca de activar esta cascada irreversible"
        );
        challenge1.addOption(new DecisionOption("VERDADERO", 35, 50, "Correcto! Debemos evitar la cascada climatica"));
        challenge1.addOption(new DecisionOption("FALSO", -40, -45, "Incorrecto. La ciencia confirma este riesgo existencial"));
        boss.addChallenge(challenge1);

        DecisionCard challenge2 = new DecisionCard(
            "Colapso Ecologico y Economico Simultaneo",
            "El 75% de cultivos alimentarios dependen de polinizadores que estan extinguiendose. Simultaneamente, el 90% de poblacion mundial respira aire contaminado. La combinacion de colapso ecologico, escasez de alimentos y crisis sanitaria podria causar migraciones masivas de 1,000 millones de personas",
            DecisionCategory.CONSUMO_RESPONSABLE,
            true,
            "VERDADERO: La interconexion de crisis ecologicas, alimentarias y sanitarias amenaza la estabilidad civilizatoria global"
        );
        challenge2.addOption(new DecisionOption("VERDADERO", 38, 45, "Correcto! Las crisis estan interconectadas"));
        challenge2.addOption(new DecisionOption("FALSO", -42, -40, "Incorrecto. Multiples crisis convergen simultaneamente"));
        boss.addChallenge(challenge2);

        DecisionCard challenge3 = new DecisionCard(
            "Ventana de Accion: Ultima Decada",
            "El IPCC confirma: tenemos hasta 2030 para reducir emisiones 50% y evitar catastrofe. Cada decima de grado importa: 1.5°C vs 2°C significa 10 millones mas de personas en pobreza, 100 millones mas con escasez de agua, extincion masiva de corales. Esta es nuestra ultima oportunidad",
            DecisionCategory.ACCION_CLIMATICA,
            true,
            "VERDADERO: La decada 2020-2030 es decisiva. Lo que hagamos ahora determinara el futuro de la humanidad por milenios"
        );
        challenge3.addOption(new DecisionOption("VERDADERO", 50, 60, "VICTORIA! Gaia se salva con tus decisiones"));
        challenge3.addOption(new DecisionOption("FALSO", -50, -50, "Incorrecto. El tiempo se agota para actuar"));
        boss.addChallenge(challenge3);

        return boss;
    }
}
