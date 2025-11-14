# 🚀 GAIA RUINS - Guía de Instalación y Uso

## 📖 Tabla de Contenidos
1. [Requisitos del Sistema](#requisitos-del-sistema)
2. [Instalación Paso a Paso](#instalación-paso-a-paso)
3. [Verificación de Instalación](#verificación-de-instalación)
4. [Ejecución del Juego](#ejecución-del-juego)
5. [Compilación y Build](#compilación-y-build)
6. [Pruebas y Debugging](#pruebas-y-debugging)
7. [Solución de Problemas](#solución-de-problemas)
8. [Comandos Útiles](#comandos-útiles)
9. [Estructura del Proyecto](#estructura-del-proyecto)
10. [Desarrollo y Modificaciones](#desarrollo-y-modificaciones)

---

## 💻 Requisitos del Sistema

### Requisitos Mínimos
```
Procesador:      Intel/AMD 2.0 GHz (64-bit)
RAM:             2 GB mínimo, 4 GB recomendado
Almacenamiento:  500 MB de espacio libre
Pantalla:        1280×720 resolución mínima
Conexión:        Internet (para descargar dependencias)
```

### Requisitos de Software

#### Java 17 (Obligatorio)
```
Versión:         Java 17 o superior
Tipo:            JDK (Java Development Kit)
Arquitectura:    64-bit
```

#### Gradle (Incluido en el Proyecto)
```
Versión:         7.5.1 (incluido)
Rol:             Herramienta de build y compilación
```

#### Git (Opcional pero recomendado)
```
Versión:         2.0 o superior
Rol:             Control de versiones
```

---

## 📥 Instalación Paso a Paso

### Paso 1: Descargar e Instalar Java 17

#### En Windows

**Opción A: Instalador Oficial**

1. Ve a: https://www.oracle.com/java/technologies/downloads/#java17
2. Descarga: "Windows Installer (x64)"
3. Ejecuta el instalador
4. Sigue los pasos del asistente
5. Acepta la ubicación por defecto o elige una personalizada
   ```
   Típicamente: C:\Program Files\Java\jdk-17.x.x\
   ```

**Opción B: AdoptOpenJDK (alternativa gratuita)**

1. Ve a: https://adoptopenjdk.net/
2. Descarga: "OpenJDK 17 (LTS)"
3. Ejecuta el instalador
4. Sigue los pasos normales

#### En macOS

```bash
# Usando Homebrew
brew install openjdk@17

# O descargar directamente de Oracle
# Visita: https://www.oracle.com/java/technologies/downloads/#java17
```

#### En Linux

```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# Fedora/RHEL
sudo dnf install java-17-openjdk-devel

# Arch
sudo pacman -S jdk17-openjdk
```

---

### Paso 2: Verificar Instalación de Java

Abre una terminal/CMD y ejecuta:

```bash
java -version
```

**Resultado esperado:**
```
java version "17.0.x" 2021-09-14
Java(TM) SE Runtime Environment (build 17.0.x+8-LTS-xxxxx)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.x+8-LTS-xxxxx, mixed mode, sharing)
```

Si ves un número menor a 17, debes actualizar Java.

---

### Paso 3: Configurar Variables de Entorno (JAVA_HOME)

#### En Windows

**Paso 1: Encontrar la ruta de Java**
```
Típicamente: C:\Program Files\Java\jdk-17.x.x\
```

**Paso 2: Abrir variables de entorno**
1. Presiona: `Win + X` → Selecciona "Sistema"
2. O: Panel de Control → Sistema y Seguridad → Sistema → Configuración avanzada del sistema
3. Haz clic en "Variables de entorno"

**Paso 3: Crear variable JAVA_HOME**
1. Haz clic en "Nueva" (bajo "Variables de usuario")
2. Nombre de variable: `JAVA_HOME`
3. Valor de variable: `C:\Program Files\Java\jdk-17.x.x\`
4. Haz clic "Aceptar"

**Paso 4: Actualizar PATH**
1. Selecciona "Path" → Haz clic "Editar"
2. Haz clic "Nuevo"
3. Añade: `%JAVA_HOME%\bin`
4. Haz clic "Aceptar"

**Paso 5: Reinicia tu terminal/CMD**

Verifica nuevamente:
```cmd
java -version
```

#### En macOS/Linux

Abre tu archivo de configuración de shell:

```bash
# Si usas zsh (macOS 10.15+)
nano ~/.zshrc

# Si usas bash
nano ~/.bash_profile
```

Añade estas líneas:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH
```

Guarda y cierra (Ctrl+O, Enter, Ctrl+X en nano)

Recarga el archivo:
```bash
source ~/.zshrc   # o ~/.bash_profile
```

---

### Paso 4: Descargar el Proyecto Gaia Ruins

#### Opción A: Descargar ZIP

1. Ve al repositorio del proyecto
2. Haz clic en "Code" → "Download ZIP"
3. Extrae el archivo en tu carpeta deseada

#### Opción B: Clonar con Git

```bash
cd tu_carpeta_deseada
git clone [URL_del_repositorio]
cd GaiaRuins
```

---

### Paso 5: Abrir el Proyecto en un IDE (Recomendado)

#### Con IntelliJ IDEA (Recomendado)

1. Abre IntelliJ IDEA
2. "File" → "Open" → Selecciona la carpeta `GaiaRuins`
3. Espera a que IntelliJ configure el proyecto
4. IDE detectará automáticamente Gradle

#### Con Eclipse

1. "File" → "Import" → "Gradle" → "Existing Gradle Project"
2. Selecciona la carpeta `GaiaRuins`
3. Haz clic "Finish"

#### Con VS Code

1. Abre la carpeta `GaiaRuins`
2. Instala extensiones:
   - "Extension Pack for Java"
   - "Gradle for Java"
3. VS Code configurará automáticamente

---

## ✅ Verificación de Instalación

### Verificar Gradle

En la carpeta del proyecto, ejecuta:

```bash
# Windows (CMD)
gradlew.bat -v

# macOS/Linux
./gradlew -v
```

**Resultado esperado:**
```
Gradle 7.5.1
```

### Verificar Estructura del Proyecto

En la carpeta `GaiaRuins`, debes ver:

```
✓ core/                    (módulo core)
✓ desktop/                 (módulo desktop)
✓ gradle/                  (wrapper de Gradle)
✓ build.gradle             (archivo de construcción)
✓ settings.gradle          (configuración de Gradle)
✓ gradlew                  (ejecutable Gradle - macOS/Linux)
✓ gradlew.bat              (ejecutable Gradle - Windows)
✓ assets/                  (recursos del juego)
```

Si faltan estos archivos, vuelve a descargar el proyecto.

---

## 🎮 Ejecución del Juego

### Opción 1: Desde CMD/Terminal (Más Simple)

#### En Windows (CMD o PowerShell)

```cmd
cd C:\ruta\a\GaiaRuins
gradlew.bat desktop:run
```

#### En macOS/Linux (Terminal)

```bash
cd /ruta/a/GaiaRuins
./gradlew desktop:run
```

**Tiempo de espera:** 10-20 segundos en primera ejecución (descarga dependencias)

**Resultado esperado:**
```
> Task :desktop:run

> BUILD SUCCESSFUL

[Ventana del juego se abre]
```

---

### Opción 2: Desde IntelliJ IDEA

**Paso 1: Configurar Run Configuration**

1. "Run" → "Edit Configurations"
2. Haz clic en "+" → Selecciona "Gradle"
3. Configura:
   - Name: `Gaia Ruins`
   - Gradle project: `desktop`
   - Tasks: `run`
4. Haz clic "OK"

**Paso 2: Ejecutar**

1. Selecciona la configuración "Gaia Ruins"
2. Presiona "Shift + F10" o haz clic en el botón "Run"
3. Espera a que compila y se ejecute

---

### Opción 3: Desde VS Code

**Paso 1: Abrir terminal integrada**

Presiona: `Ctrl + `` ` (backtick)

**Paso 2: Ejecutar comando**

```bash
./gradlew desktop:run
```

---

## 🔨 Compilación y Build

### Compilar sin Ejecutar

```bash
# Windows
gradlew.bat core:compileJava
gradlew.bat desktop:compileJava

# macOS/Linux
./gradlew core:compileJava
./gradlew desktop:compileJava
```

### Build Completo

```bash
# Windows
gradlew.bat desktop:build

# macOS/Linux
./gradlew desktop:build
```

**Resultado:** Crea `desktop/build/libs/desktop-1.0.jar` (archivo ejecutable)

### Ejecutar el JAR Compilado

```bash
# Ir a la carpeta del build
cd desktop/build/libs

# Ejecutar el JAR
java -jar desktop-1.0.jar
```

---

## 🧪 Pruebas y Debugging

### Opción 1: Modo Debug en IDE

#### En IntelliJ IDEA

1. Coloca un breakpoint haciendo clic en el número de línea
2. Presiona "Shift + F9" (o "Run" → "Debug")
3. El programa se pausará en el breakpoint

#### En VS Code

1. Coloca un breakpoint
2. Presiona "Ctrl + Shift + D" (Debug)
3. Selecciona "Java"
4. Selecciona tu configuración

### Opción 2: Logs en Consola

En cualquier archivo `.java`, agregar logs:

```java
System.out.println("Debug: " + variable);
System.err.println("Error: " + mensaje);
```

Los logs aparecerán en la consola cuando ejecutes con `./gradlew desktop:run`

### Opción 3: Inspeccionar GameState

En `GaiaRuinsGame.java`, agrega:

```java
public void printState() {
    System.out.println("=== GAME STATE ===");
    System.out.println("Consumo: " + gameState.getConsumoIndicator());
    System.out.println("Clima: " + gameState.getClimaIndicator());
    System.out.println("Vida: " + gameState.getPlayerHealth());
    System.out.println("Contaminación: " + gameState.getPollutionLevel());
    System.out.println("Piso: " + gameState.getCurrentFloor());
    System.out.println("=================");
}
```

Llama `gaiaGame.printState()` donde necesites

---

## 🛠️ Solución de Problemas

### Problema: "El término 'gradlew' no se reconoce"

**Causa:** Windows no reconoce el comando

**Solución:**
```cmd
# En Windows, usa .bat
gradlew.bat desktop:run

# O con PowerShell
.\gradlew desktop:run
```

---

### Problema: "JAVA_HOME no encontrado"

**Causa:** Variable de entorno no configurada

**Solución:**
1. Verifica que Java esté instalado: `java -version`
2. Configura JAVA_HOME (ver Paso 3 de instalación)
3. Reinicia tu terminal

---

### Problema: "Error: Task ':core:compileJava' failed"

**Causa:** Error de compilación en el código

**Solución:**
1. Lee el error completo (scroll arriba)
2. Verifica la línea mencionada
3. Si es en `DecisionCardFactory.java`, revisa que todas las preguntas tengan paréntesis cerrados

---

### Problema: "La ventana del juego no se abre"

**Causa:** Puede ser problema de gráficos

**Soluciones:**
1. Espera 15 segundos (a veces tarda en cargar)
2. Actualiza drivers de gráficos
3. Intenta ejecutar desde terminal para ver errores:
   ```bash
   ./gradlew desktop:run --info
   ```

---

### Problema: "java.lang.UnsupportedClassVersionError"

**Causa:** Versión de Java incompatible

**Solución:**
1. Verifica Java: `java -version`
2. Debe ser 17 o superior
3. Si es 11 o 8, actualiza Java

---

### Problema: "Port 5005 already in use" (al debuggear)

**Causa:** Puerto de debug ya está en uso

**Solución:**
```bash
# En Windows - mata el proceso
netstat -ano | findstr :5005
taskkill /PID [PID] /F

# En macOS/Linux
lsof -i :5005
kill -9 [PID]
```

---

## 💻 Comandos Útiles

### Comando Rápido - Ejecutar Juego

```bash
# Windows
gradlew.bat desktop:run

# macOS/Linux
./gradlew desktop:run
```

### Limpiar y Reconstruir

```bash
# Windows
gradlew.bat clean desktop:build

# macOS/Linux
./gradlew clean desktop:build
```

### Solo Compilar sin Ejecutar

```bash
# Windows
gradlew.bat build

# macOS/Linux
./gradlew build
```

### Ver Información de Gradle

```bash
# Windows
gradlew.bat -v

# macOS/Linux
./gradlew -v
```

### Ejecutar Tests (si existen)

```bash
# Windows
gradlew.bat test

# macOS/Linux
./gradlew test
```

### Limpiar Cachés

```bash
# Windows
gradlew.bat clean

# macOS/Linux
./gradlew clean
```

---

## 📁 Estructura del Proyecto

### Carpetas Principales

```
GaiaRuins/
├── core/                              # Lógica y gráficos
│   ├── src/com/deckard/
│   │   ├── client/                   # Interfaz gráfica
│   │   └── server/                   # Lógica del juego
│   └── build/                        # Compilados
│
├── desktop/                           # Punto de entrada
│   ├── src/com/deckard/
│   │   └── DesktopLauncher.java      # main()
│   └── build/
│       └── libs/
│           └── desktop-1.0.jar       # JAR ejecutable
│
├── gradle/                            # Gradle wrapper
├── assets/                            # Recursos (PNG, WAV, JSON)
├── build.gradle                       # Configuración de build
├── settings.gradle                    # Configuración de módulos
└── gradlew / gradlew.bat              # Ejecutables de Gradle
```

### Archivos Importantes

```
DesktopLauncher.java              Punto de entrada
GameScreen.java                   Gestor principal
GaiaRuinsGame.java                Lógica del juego
DecisionCardFactory.java          Generador de preguntas
Maze.java                         Generador de laberinto
GuiParams.java                    Parámetros visuales (1280×720)
```

---

## 🔧 Desarrollo y Modificaciones

### Modificar Resolución

Archivo: `core/src/com/deckard/client/core/GuiParams.java`

```java
private static final int DEFAULT_WIDTH = 1280;   // Cambiar aquí
private static final int DEFAULT_HEIGHT = 720;   // Cambiar aquí
```

### Agregar Nuevas Preguntas

Archivo: `core/src/com/deckard/server/decision/DecisionCardFactory.java`

```java
DecisionCard newCard = new DecisionCard(
    "Título",
    "Pregunta con información engañosa",
    DecisionCategory.CONSUMO_RESPONSABLE,
    true,  // o false
    "Explicación"
);
newCard.addOption(new DecisionOption("VERDADERO", 10, 8, "Mensaje"));
newCard.addOption(new DecisionOption("FALSO", -8, -6, "Mensaje"));
consumoCards.add(newCard);
```

### Cambiar Puntuaciones

Archivo: `core/src/com/deckard/server/decision/DecisionCardFactory.java`

```java
// En DecisionOption
new DecisionOption("VERDADERO", 10, 8, "Mensaje")
//                              ↑   ↑
//                      consumo clima
```

### Depurar Indicadores

Añade en `GameScreen.java`:

```java
@Override
public void render(float delta) {
    // Mostrar indicadores en consola
    if (gaiaGame != null) {
        System.out.println("Consumo: " + 
            gaiaGame.getGameState().getConsumoIndicator());
    }
    // ...
}
```

---

## 📚 Recursos Adicionales

### Documentación Oficial

- **LibGDX:** https://libgdx.com/wiki/
- **Gradle:** https://gradle.org/documentation/
- **Java 17:** https://docs.oracle.com/en/java/javase/17/

### READMEs del Proyecto

- **README_JUEGO.md** - Cómo jugar
- **README_CODIGO.md** - Arquitectura técnica
- **README_USO.md** - Este archivo

---

## ✅ Checklist de Instalación

- [ ] Java 17 instalado (`java -version` muestra 17.x.x)
- [ ] JAVA_HOME configurado (en variables de entorno)
- [ ] Gradle funciona (`gradlew -v` muestra 7.5.1)
- [ ] Proyecto descargado en carpeta local
- [ ] IDE configurado (IntelliJ/VS Code/Eclipse)
- [ ] Primera ejecución completada
- [ ] Juego abre correctamente
- [ ] Puedes jugar una partida completa

---

## 🎯 Próximos Pasos

Después de instalar:

1. **Lee README_JUEGO.md** para entender mecánicas
2. **Lee README_CODIGO.md** para entender arquitectura
3. **Juega una partida completa** para sentir la experiencia
4. **Modifica preguntas** si lo deseas
5. **Experimenta con cambios** en GuiParams

---

## 📞 Soporte

Si encuentras problemas:

1. **Verifica Java:** `java -version`
2. **Verifica Gradle:** `gradlew -v`
3. **Lee logs completos** (no solo última línea)
4. **Busca el error** en Google/Stack Overflow
5. **Borra cache y reconstruye:** `gradlew clean desktop:build`

---

**Versión:** 1.1.1
**Última actualización:** 2025-11-14
**Estado:** Guía Completa de Instalación

