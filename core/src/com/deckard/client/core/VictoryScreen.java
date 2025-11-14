package com.deckard.client.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deckard.server.game.GaiaRuinsGame;
import com.deckard.server.game.GameState;

/**
 * Pantalla de Victoria
 */
public class VictoryScreen implements Screen {
    private final GameScreen game;
    private final GaiaRuinsGame gaiaGame;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;

    public VictoryScreen(GameScreen game, GaiaRuinsGame gaiaGame) {
        this.game = game;
        this.gaiaGame = gaiaGame;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GuiParams.WIDTH, GuiParams.HEIGHT);

        stage = new Stage(new ScreenViewport(camera), game.getBatch());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        createUI();
        Gdx.input.setInputProcessor(stage);
    }

    private void createUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Label victoryLabel = new Label("!!! VICTORIA !!!", skin);
        victoryLabel.setFontScale(3.5f);
        victoryLabel.setColor(Color.GOLD);

        Label congratsLabel = new Label(
            "Has salvado a Gaia!\n" +
            "Completaste los 3 pisos del laberinto",
            skin
        );
        congratsLabel.setFontScale(1.8f);
        congratsLabel.setWrap(true);
        congratsLabel.setColor(Color.GREEN);

        Label messageLabel = new Label(
            "Con tus decisiones responsables, lograste mantener\n" +
            "el equilibrio entre consumo sostenible y accion climatica.\n\n" +
            "Cada eleccion cuenta. Cada accion importa.\n" +
            "El futuro del planeta esta en nuestras manos.",
            skin
        );
        messageLabel.setFontScale(1.3f);
        messageLabel.setWrap(true);
        messageLabel.setColor(Color.LIGHT_GRAY);

        // Estadisticas finales
        Label statsLabel = new Label(
            "Vida del heroe: " + gaiaGame.getGameState().getPlayerHealth() + "%\n" +
            "Contaminacion: " + gaiaGame.getGameState().getPollutionLevel() + "%\n" +
            "Consumo final: " + gaiaGame.getGameState().getConsumoIndicator() + "%\n" +
            "Clima final: " + gaiaGame.getGameState().getClimaIndicator() + "%",
            skin
        );
        statsLabel.setFontScale(1.5f);
        statsLabel.setColor(Color.CYAN);

        Label odsLabel = new Label(
            "ODS 12: Consumo Responsable ✓\n" +
            "ODS 13: Accion por el Clima ✓",
            skin
        );
        odsLabel.setFontScale(1.3f);
        odsLabel.setColor(Color.YELLOW);

        TextButton playAgainButton = new TextButton("JUGAR DE NUEVO", skin);
        playAgainButton.getLabel().setFontScale(1.5f);
        playAgainButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                playAgain();
            }
        });

        TextButton menuButton = new TextButton("MENU PRINCIPAL", skin);
        menuButton.getLabel().setFontScale(1.2f);
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                returnToMenu();
            }
        });

        mainTable.add(victoryLabel).padBottom(30);
        mainTable.row();
        mainTable.add(congratsLabel).width(800).padBottom(30);
        mainTable.row();
        mainTable.add(messageLabel).width(900).padBottom(40);
        mainTable.row();
        mainTable.add(statsLabel).padBottom(20);
        mainTable.row();
        mainTable.add(odsLabel).padBottom(50);
        mainTable.row();
        mainTable.add(playAgainButton).width(350).height(60).padBottom(20);
        mainTable.row();
        mainTable.add(menuButton).width(300).height(50);

        // Boton de abandonar partida en esquina superior derecha
        TextButton abandonButton = new TextButton("ABANDONAR", skin);
        abandonButton.getLabel().setFontScale(0.9f);
        abandonButton.setPosition(GuiParams.WIDTH - 140, GuiParams.HEIGHT - 50);
        abandonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        stage.addActor(abandonButton);

        stage.addActor(mainTable);
    }

    private void playAgain() {
        GaiaRuinsGame newGame = new GaiaRuinsGame();
        newGame.startNewGame();
        game.setScreen(new MazeScreen(game, newGame));
        dispose();
    }

    private void returnToMenu() {
        game.setScreen(new MainMenuScreen(game));
        dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.0f, 0.1f, 0.05f, 1);

        camera.update();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
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
