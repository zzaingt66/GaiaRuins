package com.deckard.client.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deckard.server.game.GaiaRuinsGame;
import com.deckard.server.maze.MazeNode;
import com.deckard.server.maze.NodeType;

/**
 * Pantalla del mapa del laberinto
 */
public class MazeScreen implements Screen {
    private final GameScreen game;
    private final GaiaRuinsGame gaiaGame;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;
    private final ShapeRenderer shapeRenderer;
    private Table indicatorsTable;
    private Label consumoLabel;
    private Label climaLabel;
    private Label floorLabel;

    public MazeScreen(GameScreen game, GaiaRuinsGame gaiaGame) {
        this.game = game;
        this.gaiaGame = gaiaGame;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GuiParams.WIDTH, GuiParams.HEIGHT);

        stage = new Stage(new ScreenViewport(camera), game.getBatch());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        shapeRenderer = new ShapeRenderer();

        createUI();
        Gdx.input.setInputProcessor(stage);
    }

    private void createUI() {
        // Panel de indicadores
        indicatorsTable = new Table();
        indicatorsTable.setFillParent(false);
        indicatorsTable.top().left();
        indicatorsTable.setPosition(20, GuiParams.HEIGHT - 20);

        floorLabel = new Label("Piso: " + gaiaGame.getGameState().getCurrentFloor(), skin);
        floorLabel.setFontScale(1.5f);

        // Barras de vida
        Label heroLabel = new Label("Heroe: " + gaiaGame.getGameState().getPlayerHealth() + "%", skin);
        heroLabel.setFontScale(1.3f);
        heroLabel.setColor(getHealthColor(gaiaGame.getGameState().getPlayerHealth()));

        Label pollutionLabel = new Label("Contaminacion: " + gaiaGame.getGameState().getPollutionLevel() + "%", skin);
        pollutionLabel.setFontScale(1.3f);
        pollutionLabel.setColor(getPollutionColor(gaiaGame.getGameState().getPollutionLevel()));

        consumoLabel = new Label("Consumo: " + gaiaGame.getGameState().getConsumoIndicator() + "%", skin);
        consumoLabel.setFontScale(1.2f);
        consumoLabel.setColor(Color.GREEN);

        climaLabel = new Label("Clima: " + gaiaGame.getGameState().getClimaIndicator() + "%", skin);
        climaLabel.setFontScale(1.2f);
        climaLabel.setColor(Color.CYAN);

        indicatorsTable.add(floorLabel).left().padBottom(15);
        indicatorsTable.row();
        indicatorsTable.add(heroLabel).left().padBottom(5);
        indicatorsTable.row();
        indicatorsTable.add(pollutionLabel).left().padBottom(10);
        indicatorsTable.row();
        indicatorsTable.add(consumoLabel).left().padBottom(5);
        indicatorsTable.row();
        indicatorsTable.add(climaLabel).left();

        stage.addActor(indicatorsTable);

        // Boton de abandonar partida en esquina superior derecha
        TextButton abandonButton = new TextButton("ABANDONAR", skin);
        abandonButton.getLabel().setFontScale(1f);
        abandonButton.setPosition(GuiParams.WIDTH - 150, GuiParams.HEIGHT - 50);
        abandonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
        stage.addActor(abandonButton);

        // Botones de nodos
        createNodeButtons();
    }

    private Color getHealthColor(int health) {
        if (health > 60) return Color.GREEN;
        if (health > 30) return Color.YELLOW;
        return Color.RED;
    }

    private Color getPollutionColor(int pollution) {
        if (pollution < 30) return Color.GREEN;
        if (pollution < 60) return Color.YELLOW;
        return Color.RED;
    }

    private void createNodeButtons() {
        MazeNode currentNode = gaiaGame.getCurrentMaze().getCurrentNode();

        // Mostrar nodos disponibles
        int buttonY = 400;
        for (MazeNode node : currentNode.getConnections()) {
            TextButton nodeButton = createNodeButton(node);
            nodeButton.setPosition(800, buttonY);
            stage.addActor(nodeButton);
            buttonY -= 100;
        }

        // Info del nodo actual
        if (currentNode.getType() != NodeType.START) {
            Label currentNodeLabel = new Label("Nodo actual: " + getNodeTypeName(currentNode.getType()), skin);
            currentNodeLabel.setFontScale(1.2f);
            currentNodeLabel.setPosition(400, 600);
            stage.addActor(currentNodeLabel);
        }
    }

    private TextButton createNodeButton(MazeNode node) {
        String buttonText = getNodeTypeName(node.getType());
        TextButton button = new TextButton(buttonText, skin);
        button.getLabel().setFontScale(1.2f);

        // Color según tipo
        Color buttonColor = getNodeColor(node.getType());
        button.setColor(buttonColor);

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                enterNode(node);
            }
        });

        return button;
    }

    private String getNodeTypeName(NodeType type) {
        switch (type) {
            case CHALLENGE: return "DESAFIO";
            case EVENT: return "EVENTO";
            case REST: return "DESCANSO";
            case BOSS: return "JEFE";
            case START: return "INICIO";
            default: return "NODO";
        }
    }

    private Color getNodeColor(NodeType type) {
        switch (type) {
            case CHALLENGE: return Color.ORANGE;
            case EVENT: return Color.PURPLE;
            case REST: return Color.BLUE;
            case BOSS: return Color.RED;
            default: return Color.WHITE;
        }
    }

    private void enterNode(MazeNode node) {
        gaiaGame.enterNode(node);

        // Cambiar a la pantalla correspondiente
        switch (gaiaGame.getCurrentPhase()) {
            case CHALLENGE:
                game.setScreen(new ChallengeScreen(game, gaiaGame));
                break;
            case EVENT:
                game.setScreen(new EventScreen(game, gaiaGame));
                break;
            case REST:
                game.setScreen(new RestScreen(game, gaiaGame));
                break;
            case BOSS_FIGHT:
                game.setScreen(new BossScreen(game, gaiaGame));
                break;
        }

        dispose();
    }

    private void updateIndicators() {
        consumoLabel.setText("Consumo: " + gaiaGame.getGameState().getConsumoIndicator() + "%");
        climaLabel.setText("Clima: " + gaiaGame.getGameState().getClimaIndicator() + "%");
        floorLabel.setText("Piso: " + gaiaGame.getGameState().getCurrentFloor());

        // Cambiar color según valor
        int consumo = gaiaGame.getGameState().getConsumoIndicator();
        if (consumo > 50) {
            consumoLabel.setColor(Color.GREEN);
        } else if (consumo > 25) {
            consumoLabel.setColor(Color.YELLOW);
        } else {
            consumoLabel.setColor(Color.RED);
        }

        int clima = gaiaGame.getGameState().getClimaIndicator();
        if (clima > 50) {
            climaLabel.setColor(Color.CYAN);
        } else if (clima > 25) {
            climaLabel.setColor(Color.YELLOW);
        } else {
            climaLabel.setColor(Color.RED);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1);

        camera.update();
        updateIndicators();

        stage.act(delta);
        stage.draw();

        // Dibujar conexiones del mapa (opcional, visual simple)
        drawMazeConnections();
    }

    private void drawMazeConnections() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        MazeNode current = gaiaGame.getCurrentMaze().getCurrentNode();
        float currentX = 400;
        float currentY = 400;

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.circle(currentX, currentY, 20);

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}

