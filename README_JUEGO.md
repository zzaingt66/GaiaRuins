# 🎮 GAIA RUINS - Guía del Juego

## 📖 Tabla de Contenidos
1. [Visión y Objetivo](#visión-y-objetivo)
2. [¿Qué es Gaia Ruins?](#qué-es-gaia-ruins)
3. [Mecánicas Principales](#mecánicas-principales)
4. [Flujo del Juego](#flujo-del-juego)
5. [Indicadores](#indicadores)
6. [Tipos de Nodos](#tipos-de-nodos)
7. [Sistema de Combate](#sistema-de-combate)
8. [Objetivos y Victoria](#objetivos-y-victoria)
9. [Estrategia y Tips](#estrategia-y-tips)

---

## 🌍 Visión y Objetivo

### Visión
**Gaia Ruins** busca crear conciencia ambiental a través de la gamificación, enseñando a los jugadores sobre los Objetivos de Desarrollo Sostenible (ODS) de las Naciones Unidas, específicamente:

- **ODS 12:** Consumo Responsable
- **ODS 13:** Acción por el Clima

### Objetivo Principal
Integrar educación ambiental con entretenimiento, permitiendo que los jugadores aprendan sobre sostenibilidad mientras juegan un roguelike desafiante.

---

## ❓ ¿Qué es Gaia Ruins?

**Gaia Ruins** es un **mini roguelike de construcción de mazmorras** donde:

- 🎲 Cada partida genera un laberinto único y aleatorio
- 📚 El jugador responde preguntas sobre sostenibilidad ambiental
- 🗺️ Debe navegar 3 pisos de dificultad progresiva
- 👹 Enfrenta jefes temáticos al final de cada piso
- 🎯 Mantiene dos indicadores (Consumo y Clima) por encima de 0
- 🏆 Gana completando los 3 pisos sin que los indicadores colapsen

---

## ⚙️ Mecánicas Principales

### 1. Sistema de Indicadores (Dual)
El juego mantiene dos indicadores que deben estar siempre > 0:

```
CONSUMO SOSTENIBLE (ODS 12)    ACCIÓN CLIMÁTICA (ODS 13)
    Barra 1: 0-100%                  Barra 2: 0-100%
    
Comienza en: 100%              Comienza en: 100%
Objetivo: Mantener > 0         Objetivo: Mantener > 0
```

### 2. Sistema Dual de Vida
Además de los indicadores ODS, el juego también rastrea:

```
VIDA DEL HÉROE          CONTAMINACIÓN
  0-100%                  0-100%
  
Comienza: 100%          Comienza: 0%
Decrece: Por errores    Aumenta: Por errores
Objetivo: Mantener >0   Objetivo: Mantener <100%
```

### 3. Laberinto Procedural
Cada partida genera un laberinto aleatorio con:
- 3 pisos de dificultad progresiva
- Nodos conectados aleatoriamente
- Tipos diversos: Desafío, Evento, Descanso, Jefe

### 4. Sistema de Preguntas Verdadero/Falso
- 12 preguntas totales (6 de Consumo, 6 de Clima)
- Seleccionadas aleatoriamente en cada partida
- Información engañosa para hacerlas desafiantes
- Respuestas incorrectas penalizan los indicadores

---

## 🎮 Flujo del Juego

### Fase 1: Inicio
```
┌─────────────────────────┐
│  PANTALLA PRINCIPAL      │
│  • NUEVA PARTIDA         │
│  • SALIR                 │
└──────────┬──────────────┘
           │
           ▼
┌─────────────────────────┐
│  SELECCIÓN DE HÉROE      │
│  • 4 opciones (elementos)
│  • Cada héroe es único   │
└──────────┬──────────────┘
```

### Fase 2: Exploración del Laberinto
```
┌──────────────────────────┐
│  PANTALLA DE LABERINTO   │
│                          │
│  Nodo Actual: INICIO     │
│  Nodos Conectados:       │
│  • Desafío               │
│  • Evento                │
│  • Descanso              │
│                          │
│  Indicadores:            │
│  Consumo: 100%           │
│  Clima: 100%             │
│  Vida: 100%              │
│  Contaminación: 0%       │
└──────────┬───────────────┘
           │
        Elige nodo
           ▼
```

### Fase 3: Encuentro con Nodo
Según el tipo de nodo:

**A. NODO DE DESAFÍO**
```
┌─────────────────────────────────┐
│  PANTALLA DE DESAFÍO             │
│                                 │
│  "Consumo Responsable"          │
│  Pregunta: [texto del desafío]  │
│                                 │
│  ¿VERDADERO o FALSO?            │
│  [Botón V]  [Botón F]           │
│                                 │
│  Resultado:                     │
│  ✓ Correcto → +consumo +clima   │
│  ✗ Incorrecto → -consumo -clima │
└──────────┬──────────────────────┘
           │
        Volver al laberinto
           ▼
```

**B. NODO DE EVENTO**
```
┌──────────────────────────┐
│  PANTALLA DE EVENTO      │
│                          │
│  Evento Aleatorio:       │
│  • Mejora (Bonificación) │
│  • Penalización          │
│  • Modificador           │
│                          │
│  Efecto aplicado:        │
│  +15% Consumo            │
│  +20% Clima              │
│                          │
│  [CONTINUAR]             │
└──────────┬───────────────┘
           │
        Volver al laberinto
           ▼
```

**C. NODO DE DESCANSO**
```
┌──────────────────────────┐
│  PANTALLA DE DESCANSO    │
│                          │
│  Recuperación:           │
│  +15% Consumo            │
│  +15% Clima              │
│  +10% Vida               │
│                          │
│  Nuevos Indicadores:     │
│  Consumo: 115%           │
│  Clima: 115%             │
│  Vida: 110%              │
│                          │
│  [CONTINUAR]             │
└──────────┬───────────────┘
           │
        Volver al laberinto
           ▼
```

**D. NODO DE JEFE**
```
┌──────────────────────────┐
│  PANTALLA DE JEFE        │
│  ¡¡¡ JEFE !!!            │
│                          │
│  Nombre: [Tema Ambiental]│
│  Descripción del peligro │
│                          │
│  Reto: Responder 3       │
│  preguntas difíciles     │
│                          │
│  Si ganas → Siguiente    │
│  Si pierdes → Game Over  │
└──────────┬───────────────┘
           │
        Resultado
           ▼
```

### Fase 4: Progresión a través de Pisos
```
PISO 1 → Completa nodos → Derrota JEFE 1 → PISO 2
PISO 2 → Completa nodos → Derrota JEFE 2 → PISO 3
PISO 3 → Completa nodos → Derrota JEFE 3 → VICTORIA
```

### Fase 5: Final del Juego
```
┌─────────────────────────┐
│  VICTORIA                │
│  ¡Salvaste a Gaia!       │
│  Completaste 3 pisos     │
│  Indicadores positivos   │
└─────────────────────────┘

O

┌─────────────────────────┐
│  DERROTA                 │
│  Game Over               │
│  Un indicador = 0        │
│  O contaminación = 100%  │
└─────────────────────────┘
```

---

## 📊 Indicadores

### 1. Consumo Sostenible (ODS 12)
```
Rango: 0-100%
Inicio: 100%
Cambios:
  ✓ Respuesta correcta: +10 a +13
  ✗ Respuesta incorrecta: -7 a -10
  🎲 Evento: Aleatorio
  🛌 Descanso: +15%

Si llega a 0 → GAME OVER
```

**Qué representa:** Capacidad del planeta para satisfacer consumo humano sin agotarse.

### 2. Clima (ODS 13)
```
Rango: 0-100%
Inicio: 100%
Cambios:
  ✓ Respuesta correcta: +12 a +13
  ✗ Respuesta incorrecta: -10 a -13
  🎲 Evento: Aleatorio
  🛌 Descanso: +15%

Si llega a 0 → GAME OVER
```

**Qué representa:** Estabilidad climática del planeta.

### 3. Vida del Héroe
```
Rango: 0-100%
Inicio: 100%
Cambios:
  ✗ Respuesta incorrecta: -5 a -8
  🛌 Descanso: +10%

Si llega a 0 → GAME OVER (combinado con indicadores)
```

**Qué representa:** Salud del personaje jugable.

### 4. Contaminación
```
Rango: 0-100%
Inicio: 0%
Cambios:
  ✗ Respuesta incorrecta: +10%
  🎲 Evento: Puede aumentar
  
Si llega a 100% → GAME OVER
```

**Qué representa:** Nivel de contaminación ambiental acumulada.

---

## 🗺️ Tipos de Nodos

### Nodo de Desafío (Naranja)
```
Descripción: Pregunta de Verdadero/Falso
Frecuencia: 40% de nodos
Recompensa:
  ✓ Correcto: +10-13 en un indicador, +8-13 en otro
  ✗ Incorrecto: -7-10 en un indicador, -5-13 en otro
Riesgo: Alto (penalizaciones significativas)
Objetivo: Enseñar decisiones sostenibles
```

### Nodo de Evento (Púrpura)
```
Descripción: Evento aleatorio inesperado
Frecuencia: 30% de nodos
Tipos:
  • Ganancia de Consumo
  • Ganancia de Clima
  • Pérdida de Consumo
  • Pérdida de Clima
  • Bonificación de ambos
  • Penalización de ambos
Recompensa: Variable
Riesgo: Variable
Objetivo: Agregar aleatoriedad y emoción
```

### Nodo de Descanso (Azul)
```
Descripción: Área segura para recuperarse
Frecuencia: 20% de nodos
Recompensa:
  • +15% Consumo
  • +15% Clima
  • +10% Vida
  • -5% Contaminación
Riesgo: Ninguno
Objetivo: Dar respiro estratégico
```

### Nodo de Jefe (Rojo)
```
Descripción: Enfrentamiento temático
Frecuencia: 1 por piso (final)
Dificultad: 3 preguntas difíciles consecutivas
Recompensa: Acceso a siguiente piso
Riesgo: Muy Alto
Objetivo: Demostración de aprendizaje
```

### Nodo de Inicio (Verde)
```
Descripción: Punto de partida
Frecuencia: 1 por partida
Recompensa: Ninguna
Riesgo: Ninguno
Objetivo: Punto de comienzo
```

---

## ⚔️ Sistema de Combate

El "combate" en Gaia Ruins es mental, no físico. Se basa en responder preguntas correctamente.

### Estructura de Pregunta
```
PREGUNTA
├─ Título temático
├─ Enunciado (con información engañosa)
├─ Categoría (Consumo o Clima)
└─ Dos opciones
   ├─ VERDADERO
   └─ FALSO

RESULTADO
├─ Si correcto → +puntos
└─ Si incorrecto → -puntos
```

### Ejemplo de Pregunta Desafiante
```
TÍTULO: "Alimentos Orgánicos y Sostenibilidad"

PREGUNTA: 
"Los alimentos orgánicos tienen menor rendimiento 
por hectárea que los convencionales. Para producir 
la misma cantidad de comida, se necesita más tierra 
orgánica, lo que requiere más deforestación."

OPCIONES:
A) VERDADERO (Incorrecto - es un engaño)
B) FALSO (Correcto - aunque usan más tierra, recuperan 
   fertilidad y evitan pesticidas)

PENALIDAD SI FALLAS:
• Consumo: -8 a -10
• Clima: -10 a -13
```

---

## 🏆 Objetivos y Victoria

### Objetivo Primario
**Completar los 3 pisos del laberinto sin que ningún indicador llegue a 0**

### Condiciones de Victoria
✅ Derrotar JEFE 3 (final del piso 3)
✅ Mantener Consumo > 0
✅ Mantener Clima > 0
✅ Mantener Vida > 0
✅ Mantener Contaminación < 100%

### Condiciones de Derrota
❌ Consumo llega a 0
❌ Clima llega a 0
❌ Vida llega a 0
❌ Contaminación llega a 100%
❌ Abandonar el juego antes de terminar

### Puntuación Final
```
Estadísticas mostradas al ganar:
• Pisos completados: X/3
• Vida final del héroe: X%
• Contaminación final: X%
• Consumo final: X%
• Clima final: X%
• ODS completados: 12 ✓ y 13 ✓
```

---

## 💡 Estrategia y Tips

### Tips Generales

**1. Analiza las Preguntas Cuidadosamente**
- La pregunta tiene información engañosa
- No es obvio cuál es la respuesta
- Lee todos los detalles antes de responder

**2. Aprende de Tus Errores**
- Cada pregunta incorrecta te enseña
- Las explicaciones dan contexto ambiental
- Usa este conocimiento en futuras partidas

**3. Gestiona Tus Indicadores**
- No te obsesiones con mantener ambos al máximo
- Busca descansos cuando baje mucho uno
- Los eventos pueden ser aliados o enemigos

**4. Planifica tu Ruta**
- Decide qué nodos visitar según tu estado
- Descanso cuando baja la vida
- Desafío cuando estés seguro

### Estrategia Avanzada

**Fase 1: Consolidación**
```
Objetivo: Llegar a mitad del piso 1 con indicadores altos
Acciones:
  • Evita desafíos si estás bajo
  • Busca descansos temprano
  • Acumula puntos de ventaja
```

**Fase 2: Progresión**
```
Objetivo: Llegar a jefe con indicadores positivos
Acciones:
  • Desafíos calculados
  • Gestión eficiente
  • Preparación mental para jefe
```

**Fase 3: Conquista**
```
Objetivo: Derrotar al jefe
Acciones:
  • Máxima concentración
  • Respuestas cuidadosas
  • No pierdas la fe
```

### Errores Comunes

❌ **Responder sin leer completamente**
→ Hay trampa en el enunciado

❌ **Ignorar eventos negativos**
→ Pueden arruinar tu progresión

❌ **No descansar a tiempo**
→ Los indicadores bajos en jefe = derrota

❌ **Confundir el flujo**
→ Sigue el laberinto, no intentes "romper" el juego

---

## 🎓 Aprendizaje Ambiental

### ODS 12: Consumo Responsable
El juego enseña:
- Impacto ambiental de productos cotidianos
- Importancia de compra consciente
- Ciclo de vida de productos
- Alternativas sostenibles

### ODS 13: Acción por el Clima
El juego enseña:
- Cambio climático y sus causas
- Energías renovables vs fósiles
- Impacto personal en el clima
- Soluciones viables

---

## 📊 Estadísticas Generales

```
Duración promedio por partida:  15-30 minutos
Preguntas totales:             12 (6+6)
Pisos:                         3
Héroes disponibles:            4
Resolución:                    1280×720 píxeles
FPS:                           60
Dificultad promedio:           Media-Alta
```

---

## 🎯 Conclusión

**Gaia Ruins** es un juego educativo que combina:
- 🎮 Entretenimiento roguelike
- 📚 Educación ambiental
- 🌍 Conciencia sobre ODS
- 💭 Pensamiento crítico

Cada partida es única y desafiante. ¡Sal a salvar Gaia con tus decisiones!

---

**Versión:** 1.1.1
**Última actualización:** 2025-11-14
**Estado:** Completamente Jugable

