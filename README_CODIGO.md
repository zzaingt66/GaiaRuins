# 💻 GAIA RUINS - Guía Técnica del Código

## 📖 Tabla de Contenidos
1. [Arquitectura General](#arquitectura-general)
2. [Estructura de Carpetas](#estructura-de-carpetas)
3. [Componentes Principales](#componentes-principales)
4. [Flujo del Código](#flujo-del-código)
5. [Clases Importantes](#clases-importantes)
6. [Sistema de Decisiones](#sistema-de-decisiones)
7. [Sistema de Laberinto](#sistema-de-laberinto)
8. [Sistema de Juego](#sistema-de-juego)
9. [Sistema de Pantallas](#sistema-de-pantallas)
10. [Patrones de Diseño](#patrones-de-diseño)

---

## 🏗️ Arquitectura General

Gaia Ruins utiliza una arquitectura **Cliente-Servidor** con la siguiente estructura:

```
┌─────────────────────────────────────────────┐
│         CAPA PRESENTACIÓN (CLIENT)           │
│                                             │
│  • GameScreen (gestor principal)            │
│  • MainMenuScreen (menú)                    │
│  • HeroSelectionScreen (selección héroe)    │
│  • MazeScreen (laberinto)                   │
│  • ChallengeScreen (desafío)                │
│  • EventScreen (evento)                     │
│  • RestScreen (descanso)                    │
│  • BossScreen (jefe)                        │
│  • VictoryScreen (victoria)                 │
│  • GameOverScreen (derrota)                 │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│      CAPA LÓGICA (SERVER / CORE LOGIC)      │
│                                             │
│  • GaiaRuinsGame (gestor de juego)          │
│  • GameState (estado del juego)             │
│  • Maze (generador de laberinto)            │
│  • DecisionCardFactory (preguntas)          │
│  • BossFactory (jefes)                      │
│  • RandomEventFactory (eventos)             │
└──────────────────┬──────────────────────────┘
                   │
                   ▼
┌─────────────────────────────────────────────┐
│       CAPA DE DATOS (ENTIDADES)             │
│                                             │
│  • MazeNode (nodo del laberinto)            │
│  • DecisionCard (pregunta)                  │
│  • Boss (jefe)                              │
│  • RandomEvent (evento)                     │
│  • GamePhase (fase del juego)               │
└─────────────────────────────────────────────┘
```

---

## 📁 Estructura de Carpetas

```
GaiaRuins/
├── core/src/com/deckard/
│   ├── client/                          # Capa de presentación
│   │   ├── actor/                      # Actores visuales
│   │   │   ├── CardActor.java
│   │   │   ├── HandGroup.java
│   │   │   ├── MinionBodyActor.java
│   │   │   ├── MinionGroup.java
│   │   │   ├── TeamGroup.java
│   │   │   └── TeamGroupFactory.java
│   │   ├── animation/                  # Sistema de animación
│   │   │   ├── AnimationCommand.java
│   │   │   ├── AnimationManager.java
│   │   │   ├── CountDownAnimation.java
│   │   │   └── ShowAnimation.java
│   │   └── core/                       # Pantallas principales
│   │       ├── GameScreen.java         # Gestor principal
│   │       ├── GuiParams.java          # Parámetros visuales
│   │       ├── MainMenuScreen.java     # Menú principal
│   │       ├── HeroSelectionScreen.java
│   │       ├── MazeScreen.java         # Pantalla del laberinto
│   │       ├── ChallengeScreen.java    # Pantalla de desafío
│   │       ├── EventScreen.java        # Pantalla de evento
│   │       ├── RestScreen.java         # Pantalla de descanso
│   │       ├── BossScreen.java         # Pantalla de jefe
│   │       ├── VictoryScreen.java      # Pantalla de victoria
│   │       └── GameOverScreen.java     # Pantalla de derrota
│   │
│   └── server/                          # Lógica de juego
│       ├── boss/                       # Jefes
│       │   ├── Boss.java
│       │   ├── BossFactory.java
│       │   └── BossType.java
│       ├── card/                       # Sistema de cartas (legacy)
│       ├── combat/                     # Sistema de combate (legacy)
│       ├── decision/                   # Sistema de decisiones
│       │   ├── DecisionCard.java
│       │   ├── DecisionCardFactory.java
│       │   ├── DecisionCategory.java
│       │   ├── DecisionOption.java
│       │   └── DecisionResult.java
│       ├── event/                      # Sistema de eventos
│       │   ├── RandomEvent.java
│       │   └── RandomEventFactory.java
│       ├── game/                       # Gestor principal del juego
│       │   ├── GamePhase.java
│       │   ├── GameState.java
│       │   └── GaiaRuinsGame.java
│       ├── leader/                     # Sistema de líderes (legacy)
│       ├── maze/                       # Sistema de laberinto
│       │   ├── Maze.java
│       │   ├── MazeNode.java
│       │   ├── MazeNodeFactory.java
│       │   └── NodeType.java
│       ├── minion/                     # Sistema de minions (legacy)
│       └── team/                       # Sistema de equipos (legacy)
│
├── desktop/src/com/deckard/
│   └── DesktopLauncher.java           # Punto de entrada
│
├── assets/                             # Recursos
│   ├── *.png (imágenes)
│   ├── *.wav (sonidos)
│   ├── *.json (configuración)
│   └── *.fnt (fuentes)
│
├── build.gradle                        # Configuración Gradle
├── settings.gradle
└── gradle.properties
```

---

## 🔧 Componentes Principales

### 1. GameScreen (Gestor Principal)
**Ubicación:** `core/src/com/deckard/client/core/GameScreen.java`

```java
public class GameScreen implements Screen {
    private SpriteBatch batch;
    private GaiaRuinsGame gaiaRuinsGame;
    
    // Gestiona las pantallas del juego
    // - Inicialización
    // - Cambios entre pantallas
    // - Recursos gráficos
}
```

**Responsabilidades:**
- Punto de entrada central del juego
- Gestión de pantallas
- Gestión de recursos gráficos (batch)

---

### 2. GaiaRuinsGame (Lógica Principal)
**Ubicación:** `core/src/com/deckard/server/game/GaiaRuinsGame.java`

```java
public class GaiaRuinsGame {
    private GameState gameState;
    private Maze currentMaze;
    private DecisionCard currentCard;
    private RandomEvent currentEvent;
    private Boss currentBoss;
    private GamePhase currentPhase;
    
    // Métodos principales
    public void startNewGame()
    public void enterNode(MazeNode node)
    public void selectOption(int index)
    public void leaveRest()
    public void nextFloor()
}
```

**Responsabilidades:**
- Orquestar flujo del juego
- Gestionar estado global
- Coordinar sistemas (laberinto, decisiones, eventos)

---

### 3. GameState (Estado del Juego)
**Ubicación:** `core/src/com/deckard/server/game/GameState.java`

```java
public class GameState {
    private int playerHealth;           // 0-100%
    private int pollutionLevel;         // 0-100%
    private int consumoIndicator;       // 0-100%
    private int climaIndicator;         // 0-100%
    private int currentFloor;           // 1-3
    private LocalDateTime timestamp;
    
    // Getters y setters para los indicadores
}
```

**Datos que mantiene:**
- Vida del héroe
- Nivel de contaminación
- Indicador de Consumo Responsable
- Indicador de Acción Climática
- Piso actual

---

### 4. Maze (Generador de Laberinto)
**Ubicación:** `core/src/com/deckard/server/maze/Maze.java`

```java
public class Maze {
    private List<MazeNode> nodes;
    private MazeNode currentNode;
    private Random random;
    
    // Métodos
    public void generateFloor(int floor)
    public MazeNode getCurrentNode()
    public void moveToNode(MazeNode node)
}
```

**Responsabilidades:**
- Generar laberinto procedural
- Crear conexiones entre nodos
- Rastrear posición actual

---

### 5. DecisionCardFactory (Generador de Preguntas)
**Ubicación:** `core/src/com/deckard/server/decision/DecisionCardFactory.java`

```java
public class DecisionCardFactory {
    private List<DecisionCard> consumoCards;  // 6 preguntas ODS 12
    private List<DecisionCard> climaCards;    // 6 preguntas ODS 13
    private Random random;
    
    // Métodos
    public DecisionCard createRandomCard()
    public DecisionCard createCardByCategory(DecisionCategory category)
}
```

**Responsabilidades:**
- Inicializar 12 preguntas
- Seleccionar preguntas aleatoriamente
- Separar por categoría (Consumo/Clima)

---

### 6. BossFactory (Generador de Jefes)
**Ubicación:** `core/src/com/deckard/server/boss/BossFactory.java`

```java
public class BossFactory {
    private DecisionCardFactory cardFactory;
    private Random random;
    
    // Métodos
    public Boss createBoss(int floor)
    public Boss createBossByType(BossType type)
}
```

**Responsabilidades:**
- Crear jefes temáticos
- Asignar preguntas al jefe
- Variar dificultad por piso

---

### 7. RandomEventFactory (Generador de Eventos)
**Ubicación:** `core/src/com/deckard/server/event/RandomEventFactory.java`

```java
public class RandomEventFactory {
    private List<RandomEvent> positiveEvents;
    private List<RandomEvent> negativeEvents;
    private Random random;
    
    // Métodos
    public RandomEvent createRandomEvent()
}
```

**Responsabilidades:**
- Crear eventos aleatorios
- Mezclar eventos positivos y negativos
- Aplicar efectos a indicadores

---

## 🔄 Flujo del Código

### Secuencia de Inicio

```
1. DesktopLauncher.main()
   └─> Crea Lwjgl3Application con GameScreen
   
2. GameScreen.create()
   └─> Inicializa recursos gráficos
   └─> Abre MainMenuScreen

3. MainMenuScreen
   └─> Usuario presiona "NUEVA PARTIDA"
   └─> Crea GaiaRuinsGame.startNewGame()
   └─> Abre HeroSelectionScreen

4. HeroSelectionScreen
   └─> Usuario selecciona héroe
   └─> Crea primer piso del laberinto
   └─> Abre MazeScreen
```

### Secuencia de Desafío

```
1. MazeScreen
   └─> Usuario selecciona nodo DESAFÍO
   └─> Llama GaiaRuinsGame.enterNode(node)

2. GaiaRuinsGame.enterNode()
   └─> Crea DecisionCard con DecisionCardFactory
   └─> Abre ChallengeScreen
   └─> currentPhase = GamePhase.CHALLENGE

3. ChallengeScreen
   └─> Muestra pregunta y opciones
   └─> Usuario responde (VERDADERO/FALSO)
   └─> Llama GaiaRuinsGame.selectOption(index)

4. GaiaRuinsGame.selectOption()
   └─> Valida respuesta
   └─> Actualiza indicadores
   └─> currentCard = null
   └─> Abre MazeScreen nuevamente
```

### Secuencia de Jefe

```
1. MazeScreen
   └─> Usuario selecciona nodo JEFE
   └─> Llama GaiaRuinsGame.enterNode(node)

2. GaiaRuinsGame.enterNode()
   └─> Crea Boss con BossFactory
   └─> Abre BossScreen
   └─> currentPhase = GamePhase.BOSS_FIGHT

3. BossScreen
   └─> Muestra 3 desafíos consecutivos
   └─> Usuario responde cada pregunta
   └─> Actualiza indicadores

4. BossScreen - Resultado
   └─> Si gana: GaiaRuinsGame.nextFloor()
   └─> Si pierde: Abre GameOverScreen
```

---

## 📚 Clases Importantes

### DecisionCard
```java
public class DecisionCard {
    private String title;
    private String question;
    private DecisionCategory category;
    private boolean correctAnswer;
    private String explanation;
    private List<DecisionOption> options;
    
    public boolean isCorrectAnswer(boolean playerAnswer)
    public void apply(GameState state, boolean correct)
}
```

**Representa:** Una pregunta con información engañosa y dos opciones.

---

### DecisionOption
```java
public class DecisionOption {
    private String text;              // "VERDADERO" o "FALSO"
    private int consumoChange;        // Cambio en indicador
    private int climaChange;          // Cambio en indicador
    private String resultMessage;     // Mensaje de resultado
}
```

**Representa:** Una opción de respuesta con su impacto.

---

### MazeNode
```java
public class MazeNode {
    private NodeType type;
    private List<MazeNode> connections;
    private int x, y;                 // Coordenadas
    private boolean visited;
    
    public NodeType getType()         // CHALLENGE, EVENT, REST, BOSS, START
    public List<MazeNode> getConnections()
}
```

**Representa:** Un nodo en el laberinto con sus conexiones.

---

### Boss
```java
public class Boss {
    private String name;
    private String description;
    private BossType type;
    private List<DecisionCard> challenges;
    private int remainingChallenges;
    
    public DecisionCard getCurrentChallenge()
    public void nextChallenge()
    public boolean isDefeated()
}
```

**Representa:** Un jefe del laberinto con múltiples preguntas.

---

### RandomEvent
```java
public class RandomEvent {
    private String title;
    private String description;
    private EventEffect effect;       // GAIN_CONSUMO, LOSE_CLIMA, etc.
    private int magnitude;            // Cantidad de cambio
    
    public void apply(GameState state)
}
```

**Representa:** Un evento aleatorio que afecta indicadores.

---

## 🎲 Sistema de Decisiones

### Flujo de Decisión

```
1. DecisionCardFactory.createRandomCard()
   ├─> Selecciona categoría aleatoria (50% cada una)
   └─> Retorna tarjeta aleatoria

2. DecisionCard muestra pregunta
   ├─> Título temático
   ├─> Enunciado con información engañosa
   └─> Dos opciones (VERDADERO/FALSO)

3. Usuario elige opción
   └─> Llama GaiaRuinsGame.selectOption(index)

4. Validación y Penalización
   ├─> Compara respuesta con correctAnswer
   ├─> Si correcto → +puntos
   └─> Si incorrecto → -puntos

5. Actualización de GameState
   ├─> Modifica consumoIndicator
   ├─> Modifica climaIndicator
   ├─> Verifica si llega a 0
   └─> Devuelve a MazeScreen
```

### Cálculo de Puntos

**Respuesta Correcta:**
```java
consumoIndicator += option.getConsumoChange();  // +10 a +13
climaIndicator += option.getClimaChange();      // +8 a +13
```

**Respuesta Incorrecta:**
```java
consumoIndicator -= Math.abs(option.getConsumoChange());  // -7 a -10
climaIndicator -= Math.abs(option.getClimaChange());      // -10 a -13
playerHealth -= 5;                                         // -5 a -8
```

---

## 🗺️ Sistema de Laberinto

### Generación Procedural

```java
public class Maze {
    public void generateFloor(int floor) {
        // 1. Crear lista de nodos vacía
        nodes.clear();
        
        // 2. Crear nodo inicial
        MazeNode startNode = new MazeNode(NodeType.START, 0, 0);
        nodes.add(startNode);
        
        // 3. Distribuir nodos por tipo
        // - 40% Desafío (4-5 nodos)
        // - 30% Evento (3 nodos)
        // - 20% Descanso (2 nodos)
        // - 10% Jefe (1 nodo)
        
        // 4. Conectar nodos aleatoriamente
        for (MazeNode node : nodes) {
            int connections = random.nextInt(2) + 1; // 1-2 conexiones
            for (int i = 0; i < connections; i++) {
                MazeNode target = nodes.get(random.nextInt(nodes.size()));
                node.connect(target);
            }
        }
    }
}
```

### Tipos de Nodos

```java
public enum NodeType {
    START,      // Inicio del piso
    CHALLENGE,  // Pregunta (40%)
    EVENT,      // Evento aleatorio (30%)
    REST,       // Descanso (20%)
    BOSS        // Jefe final (10%)
}
```

---

## 🎮 Sistema de Juego

### GamePhase (Fases del Juego)

```java
public enum GamePhase {
    MENU,           // Menú principal
    HERO_SELECTION, // Selección de héroe
    MAZE,           // Exploración del laberinto
    CHALLENGE,      // Respondiendo pregunta
    EVENT,          // Evento aleatorio
    REST,           // Descanso
    BOSS_FIGHT,     // Combate contra jefe
    VICTORY,        // Victoria
    GAME_OVER       // Derrota
}
```

### GameState (Validaciones)

```java
public boolean isGameOver() {
    return consumoIndicator <= 0 || 
           climaIndicator <= 0 || 
           playerHealth <= 0 ||
           pollutionLevel >= 100;
}

public boolean isVictory() {
    return currentFloor > 3;
}
```

---

## 📺 Sistema de Pantallas

### Jerarquía de Pantallas

```
Screen (LibGDX Interface)
├── MainMenuScreen
│   └─ Menú principal
├── HeroSelectionScreen
│   └─ Selección de héroe
├── MazeScreen
│   └─ Exploración del laberinto
├── ChallengeScreen
│   └─ Responder preguntas
├── EventScreen
│   └─ Evento aleatorio
├── RestScreen
│   └─ Descanso
├── BossScreen
│   └─ Combate contra jefe
├── VictoryScreen
│   └─ Pantalla de victoria
└── GameOverScreen
    └─ Pantalla de derrota
```

### Transiciones de Pantallas

```
MainMenuScreen
    ↓
HeroSelectionScreen
    ↓
MazeScreen ←─────┐
    ├─→ ChallengeScreen ──────┐
    ├─→ EventScreen ──────────┤
    ├─→ RestScreen ───────────┤
    ├─→ BossScreen ──────┐    │
    │                    ↓    │
    │            VictoryScreen (si gana piso 3)
    │            o
    │            MazeScreen (siguiente piso)
    │                    │    │
    └────────────────────┴────┘
    
Cualquier pantalla →→ GameOverScreen (si indicador = 0)
```

---

## 🎨 Patrones de Diseño

### 1. Factory Pattern

**DecisionCardFactory**
```java
public class DecisionCardFactory {
    public DecisionCard createRandomCard() {
        if (random.nextBoolean()) {
            return consumoCards.get(random.nextInt(consumoCards.size()));
        } else {
            return climaCards.get(random.nextInt(climaCards.size()));
        }
    }
}
```

**BossFactory**
```java
public class BossFactory {
    public Boss createBoss(int floor) {
        BossType type = (floor == 1) ? BossType.MINOR :
                       (floor == 2) ? BossType.MAJOR : BossType.ULTIMATE;
        return new Boss(type, generateChallenges());
    }
}
```

---

### 2. State Pattern

**GamePhase**
```java
public enum GamePhase {
    MAZE,
    CHALLENGE,
    EVENT,
    REST,
    BOSS_FIGHT,
    VICTORY,
    GAME_OVER
}

// Uso en GameScreen
switch (gaiaGame.getCurrentPhase()) {
    case CHALLENGE:
        game.setScreen(new ChallengeScreen(game, gaiaGame));
        break;
    case EVENT:
        game.setScreen(new EventScreen(game, gaiaGame));
        break;
    // ...
}
```

---

### 3. Observer Pattern

**Screen Interface (LibGDX)**
```java
public interface Screen {
    void show();        // Cuando se muestra
    void render();      // Cada frame
    void resize();      // Al redimensionar
    void pause();       // Pausa
    void resume();      // Resume
    void hide();        // Cuando se oculta
    void dispose();     // Limpieza
}
```

---

### 4. MVC (Model-View-Controller)

```
MODEL (Server)
├── GameState (datos)
├── GaiaRuinsGame (lógica)
├── DecisionCardFactory
└── Maze

VIEW (Client)
├── MainMenuScreen
├── ChallengeScreen
├── MazeScreen
└── Otros screens

CONTROLLER
├── GameScreen (orquestador)
└── Input handlers
```

---

## 🔗 Flujo de Datos

### Request-Response Cycle

```
1. USER INPUT (MazeScreen)
   └─> Selecciona nodo

2. MODEL UPDATE (GaiaRuinsGame)
   └─> enterNode(node)
   └─> Crea DecisionCard
   └─> Actualiza GameState

3. VIEW UPDATE (ChallengeScreen)
   └─> Muestra pregunta
   └─> Espera respuesta

4. USER DECISION (ChallengeScreen)
   └─> Usuario responde

5. MODEL UPDATE (GaiaRuinsGame)
   └─> selectOption(index)
   └─> Valida respuesta
   └─> Actualiza indicadores

6. VIEW UPDATE (MazeScreen)
   └─> Regresa al laberinto
   └─> Muestra nuevos indicadores
```

---

## 📊 Diagrama de Clases Principal

```
┌─────────────────────┐
│   GaiaRuinsGame     │ (Orquestador)
├─────────────────────┤
│ - gameState         │──┐
│ - currentMaze       │  │
│ - currentCard       │  │
│ - currentEvent      │  ├─→ Datos del juego
│ - currentBoss       │  │
│ - currentPhase      │  │
└─────────────────────┘  │
         ↓                │
┌────────────────────────▼──────┐
│      Componentes Clave         │
├──────────────────────────────┤
│ • GameState (estado)         │
│ • Maze (laberinto)           │
│ • DecisionCard (preguntas)   │
│ • RandomEvent (eventos)      │
│ • Boss (jefes)               │
└──────────────────────────────┘
         ↓
┌──────────────────────────────┐
│   Pantallas de LibGDX        │
├──────────────────────────────┤
│ • MainMenuScreen             │
│ • HeroSelectionScreen        │
│ • MazeScreen                 │
│ • ChallengeScreen            │
│ • EventScreen                │
│ • RestScreen                 │
│ • BossScreen                 │
│ • VictoryScreen              │
│ • GameOverScreen             │
└──────────────────────────────┘
```

---

## 🚀 Optimizaciones

### 1. Caché de Preguntas
```java
// Las preguntas se cargan una vez en initializeCards()
// No se regeneran cada vez
```

### 2. Generación Lazy del Laberinto
```java
// El laberinto se genera cuando es necesario
// No se precalcula todo
```

### 3. Gestión de Memoria
```java
// dispose() limpia recursos en cada transición
// Evita memory leaks
```

---

## 🔍 Debugging

### Puntos de Interés

**1. Validación de Respuesta**
```java
// ChallengeScreen.selectOption()
boolean isCorrect = card.isCorrectAnswer(
    option.getText().equals("VERDADERO")
);
```

**2. Actualización de Indicadores**
```java
// GaiaRuinsGame.selectOption()
gameState.setConsumoIndicator(
    gameState.getConsumoIndicator() + change
);
```

**3. Condición de Victoria**
```java
// GameOverScreen.applyEventAndContinue()
if (gameState.getConsumoIndicator() <= 0 ||
    gameState.getClimaIndicator() <= 0) {
    game.setScreen(new GameOverScreen(...));
}
```

---

## 📝 Convenciones de Código

### Nomenclatura
- **Clases:** PascalCase (`GameScreen`, `DecisionCard`)
- **Métodos:** camelCase (`getCurrentCard()`, `selectOption()`)
- **Constantes:** UPPER_SNAKE_CASE (`DEFAULT_WIDTH`, `ODS_12`)
- **Variables:** camelCase (`playerHealth`, `currentMaze`)

### Estructuras de Control
```java
// IF para lógica simple
if (consumoIndicator <= 0) {
    // ...
}

// SWITCH para múltiples estados
switch (currentPhase) {
    case MAZE:
        // ...
        break;
    case CHALLENGE:
        // ...
        break;
}

// ENUMS para tipos fijos
public enum NodeType {
    START, CHALLENGE, EVENT, REST, BOSS
}
```

---

## 📚 Recursos Externos

- **LibGDX:** Framework de juegos Java
- **Gradle:** Sistema de build
- **Java 17:** Lenguaje de programación

---

**Versión:** 1.1.1
**Última actualización:** 2025-11-14
**Estado:** Código Completamente Documentado

