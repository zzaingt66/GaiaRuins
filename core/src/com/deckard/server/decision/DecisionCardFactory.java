package com.deckard.server.decision;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory para crear cartas de decision con preguntas verdadero/falso
 */
public class DecisionCardFactory {
    private final Random random = new Random();
    private final List<DecisionCard> consumoCards;
    private final List<DecisionCard> climaCards;

    public DecisionCardFactory() {
        consumoCards = new ArrayList<>();
        climaCards = new ArrayList<>();
        initializeCards();
    }

    private void initializeCards() {
        // Cartas de Consumo Responsable (ODS 12) - Preguntas difíciles con información engañosa
        DecisionCard card1 = new DecisionCard(
            "Bolsas Reutilizables vs Plastico",
            "Estudios recientes muestran que producir una bolsa reutilizable consume 7 veces mas energia que una bolsa plastica. Aunque puede usarse 130 veces, la energia utilizada en su produccion requiere 130 usos solo para romper paridad ambiental.",
            DecisionCategory.CONSUMO_RESPONSABLE,
            true,
            "VERDADERO: Aunque consume mas energia en produccion, una bolsa reutilizable si compensa su impacto ambiental si se usa regularmente por años, a diferencia de bolsas desechables"
        );
        card1.addOption(new DecisionOption("VERDADERO", 10, 8, "Correcto! La reutilizacion sigue siendo mejor a largo plazo"));
        card1.addOption(new DecisionOption("FALSO", -8, -6, "Incorrecto. Los numeros muestran el break-even, pero si se usa correctamente compensa"));
        consumoCards.add(card1);

        DecisionCard card2 = new DecisionCard(
            "Alimentos Organicos y Sostenibilidad",
            "Los alimentos organicos tienen menor rendimiento por hectarea que los convencionales. Para producir la misma cantidad de comida, se necesita mas tierra organica, lo que requiere mas deforestacion.",
            DecisionCategory.CONSUMO_RESPONSABLE,
            false,
            "FALSO: Aunque los organicos tienen menor rendimiento inicial, recuperan fertilidad del suelo a largo plazo. Ademas, evitan pesticidas que contaminan agua y ecosistemas, compensando el impacto territorial"
        );
        card2.addOption(new DecisionOption("VERDADERO", -10, -8, "Incorrecto. La deforestacion es un problema real, pero los organicos tienen beneficios a largo plazo"));
        card2.addOption(new DecisionOption("FALSO", 10, 8, "Correcto! Los organicos son mas sostenibles pese a menor rendimiento inicial"));
        consumoCards.add(card2);

        DecisionCard card3 = new DecisionCard(
            "Transporte de Alimentos Importados",
            "Un kilo de fresas importadas por avion desde otro continente produce 5 kilos de CO2. Pero las fresas locales de invernadero con calefaccion produce 1.5 kilos de CO2. El transporte aereo es mucho peor.",
            DecisionCategory.CONSUMO_RESPONSABLE,
            true,
            "VERDADERO: El transporte aereo es extremadamente contaminante. Aunque los invernaderos consumen energia, es menos que el transporte intercontinental"
        );
        card3.addOption(new DecisionOption("VERDADERO", 10, 9, "Correcto! El transporte aereo es el peor problema ambiental"));
        card3.addOption(new DecisionOption("FALSO", -8, -7, "Incorrecto. Las matemáticas de CO2 muestran que el transporte es peor"));
        consumoCards.add(card3);

        DecisionCard card4 = new DecisionCard(
            "Reciclaje Electronico",
            "Reciclar un telefono requiere procesos quimicos que generan tanto CO2 como fabricar uno nuevo. Ademas, China e India importan residuo electronico que no se recicla, generando contaminacion toxica.",
            DecisionCategory.CONSUMO_RESPONSABLE,
            false,
            "FALSO: El reciclaje electronico, aunque complejo, recupera materiales valiosos y evita que toxinas contaminen agua subterranea. Es mejor que tirar a basura aunque no es perfecto"
        );
        card4.addOption(new DecisionOption("VERDADERO", -10, -8, "Incorrecto. El reciclaje sigue siendo mejor opcion que vertederos"));
        card4.addOption(new DecisionOption("FALSO", 10, 8, "Correcto! A pesar de los desafios, reciclar es mejor que contaminar"));
        consumoCards.add(card4);

        DecisionCard card5 = new DecisionCard(
            "Consumo de Carne vs Plantas",
            "Un kilo de carne de res requiere 15000 litros de agua. Un kilo de papas requiere 500 litros. Sin embargo, la carne proporciona mas proteina y nutrientes que las papas, por lo que es mas eficiente por nutriente.",
            DecisionCategory.CONSUMO_RESPONSABLE,
            false,
            "FALSO: Aunque la carne tiene mas nutrientes por kilo, las plantas pueden combinarse para lograr nutricion completa con mucha menos agua. La eficiencia total sigue siendo peor para la carne"
        );
        card5.addOption(new DecisionOption("VERDADERO", -10, -9, "Incorrecto. La carne sigue siendo muy ineficiente incluso por nutriente"));
        card5.addOption(new DecisionOption("FALSO", 10, 9, "Correcto! Las plantas son mas sostenibles nutricionalmente"));
        consumoCards.add(card5);

        DecisionCard card6 = new DecisionCard(
            "Ropa Sintética vs Algodon",
            "El algodon es biodegradable, pero su produccion usa 10000 litros de agua por kilo. La ropa sintetica es duradera y no necesita reemplazo frecuente. Aunque genera microplasticos, se desperdicia menos ropa total.",
            DecisionCategory.CONSUMO_RESPONSABLE,
            false,
            "FALSO: El algodon, aunque requiere agua, es biodegradable y no libera microplasticos. La ropa sintetica genera problemas de microcontaminacion por 500 años. Lo biodegradable es mejor a largo plazo"
        );
        card6.addOption(new DecisionOption("VERDADERO", -10, -8, "Incorrecto. Los microplasticos son un problema mas grave que el agua"));
        card6.addOption(new DecisionOption("FALSO", 10, 8, "Correcto! Los materiales biodegradables son mas sostenibles"));
        consumoCards.add(card6);

        // Cartas de Accion por el Clima (ODS 13) - Preguntas difíciles con información engañosa
        DecisionCard card7 = new DecisionCard(
            "Energia Eolica y Impacto Ambiental",
            "Los molinos eolicos matan aves migratorias. Estudios muestran que 1 millon de aves mueren anualmente por molinos. Ademas requieren hormigon para bases, generando 5% del CO2 mundial. La energia fossil es mejor.",
            DecisionCategory.ACCION_CLIMATICA,
            false,
            "FALSO: Aunque los molinos tienen impacto, el cambio climatico mata billones de aves anualmente. Los beneficios de energia limpia superan los 1 millon anuales. Los combustibles fosiles son mucho peor"
        );
        card7.addOption(new DecisionOption("VERDADERO", -10, -12, "Incorrecto. El daño de eolicos es minimo comparado con cambio climatico"));
        card7.addOption(new DecisionOption("FALSO", 10, 12, "Correcto! A pesar de los impactos locales, la energia limpia es necesaria"));
        climaCards.add(card7);

        DecisionCard card8 = new DecisionCard(
            "Autos Electricos y Baterias",
            "Fabricar una bateria de auto electrico emite 10 toneladas de CO2. Un auto electrico necesita 50000 km de conduccion para compensar ese CO2 por emissions savings. Muchas personas conducen menos, por lo que nunca compensa.",
            DecisionCategory.ACCION_CLIMATICA,
            false,
            "FALSO: Aunque las baterias contaminan, 50000 km se completan en 3-5 años de conduccion normal. Luego, cada km en electrico es limpio vs gasolina. A vida util (200000 km), el auto electrico contamina 80% menos"
        );
        card8.addOption(new DecisionOption("VERDADERO", -10, -12, "Incorrecto. El auto electrico compensa su deuda de carbono rapidamente"));
        card8.addOption(new DecisionOption("FALSO", 10, 12, "Correcto! Los autos electricos son mas limpios a largo plazo"));
        climaCards.add(card8);

        DecisionCard card9 = new DecisionCard(
            "Arboles y Absorcion de CO2",
            "Un arbol absorbe aproximadamente 20 kilos de CO2 en su vida util (50 anos). Eso es muy poco comparado con las 5 toneladas de CO2 que una persona produce anualmente. Plantar arboles es simbolico, no soluciona nada.",
            DecisionCategory.ACCION_CLIMATICA,
            false,
            "FALSO: Aunque los numeros parecen pequenos, los arboles tambien previenen erosion, regulan temperatura local, y recargan agua subterranea. Plantar bosques masivamente SI ayuda climáticamente y ecologicamente"
        );
        card9.addOption(new DecisionOption("VERDADERO", -10, -13, "Incorrecto. Los bosques tienen multiples beneficios ambientales"));
        card9.addOption(new DecisionOption("FALSO", 10, 13, "Correcto! Los arboles son parte de la solucion climatica"));
        climaCards.add(card9);

        DecisionCard card10 = new DecisionCard(
            "Energia Nuclear y Residuos",
            "La energia nuclear genera 12000 toneladas de residuo radioactivo al ano mundialmente. Este residuo permanece toxico por 10000 anos. El almacenamiento falla y contamina agua subterranea. Es peor que combustibles fosiles.",
            DecisionCategory.ACCION_CLIMATICA,
            false,
            "FALSO: Aunque los residuos son peligrosos, la energia nuclear emite 10 veces menos CO2 que fossil. El CO2 contamina la atmosfera de todos. Los residuos se contienen en sitios seguros. Nuclear es necesaria para clima"
        );
        card10.addOption(new DecisionOption("VERDADERO", -10, -12, "Incorrecto. Nuclear es parte necesaria de transicion climatica"));
        card10.addOption(new DecisionOption("FALSO", 10, 12, "Correcto! Nuclear es mejor para el clima que fossil"));
        climaCards.add(card10);

        DecisionCard card11 = new DecisionCard(
            "Viajar en Avion y Compensacion de Carbono",
            "Un vuelo internacional emite 2 toneladas de CO2. Comprar creditos de compensacion plantando arboles cuesta 20 dolares. Pero los arboles tardan 30 anos en absorber ese CO2. Es mejor no volar.",
            DecisionCategory.ACCION_CLIMATICA,
            true,
            "VERDADERO: No volar es mejor que volar y compensar. Sin embargo, si debes viajar, comprar creditos es mejor que nada. Pero el ideal es reducir viajes, no confiar en compensacion"
        );
        card11.addOption(new DecisionOption("VERDADERO", 10, 12, "Correcto! Reducir es mejor que compensar"));
        card11.addOption(new DecisionOption("FALSO", -8, -10, "Incorrecto. La reduccion de emisiones es mejor que compensacion"));
        climaCards.add(card11);

        DecisionCard card12 = new DecisionCard(
            "Calentamiento Global y Ciclos Naturales",
            "La tierra ha tenido ciclos de calentamiento y enfriamiento naturales por millones de anos. El calentamiento actual podria ser parte de un ciclo natural, no causado por humanos. La accion climatica es innecesaria.",
            DecisionCategory.ACCION_CLIMATICA,
            false,
            "FALSO: Aunque existen ciclos naturales, la actual velocidad de calentamiento es 10 veces mas rapida que ciclos historicos naturales. Los cientificos confirman 97% que el humano causa el actual. La accion es urgente"
        );
        card12.addOption(new DecisionOption("VERDADERO", -10, -13, "Incorrecto. Los datos muestran cambio antropogenico acelerado"));
        card12.addOption(new DecisionOption("FALSO", 10, 13, "Correcto! El calentamiento actual es principalmente humano"));
        climaCards.add(card12);
    }

    public DecisionCard createRandomCard() {
        boolean isConsumo = random.nextBoolean();

        if (isConsumo && !consumoCards.isEmpty()) {
            return consumoCards.get(random.nextInt(consumoCards.size()));
        } else if (!climaCards.isEmpty()) {
            return climaCards.get(random.nextInt(climaCards.size()));
        }

        return null;
    }

    public DecisionCard createCardByCategory(DecisionCategory category) {
        List<DecisionCard> cards = category == DecisionCategory.CONSUMO_RESPONSABLE
            ? consumoCards : climaCards;

        if (!cards.isEmpty()) {
            return cards.get(random.nextInt(cards.size()));
        }

        return null;
    }
}
