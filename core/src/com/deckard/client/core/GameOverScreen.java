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

/**
 * Pantalla de Game Over - se muestra cuando algún indicador llega a cero
 */
public class GameOverScreen implements Screen {
    private final GameScreen game;
    private final GaiaRuinsGame gaiaGame;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;

    public GameOverScreen(GameScreen game, GaiaRuinsGame gaiaGame) {
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

        Label gameOverLabel = new Label("GAME OVER", skin);
        gameOverLabel.setFontScale(4f);
        gameOverLabel.setColor(Color.RED);

        // Determinar que indicador llego a cero
        int consumo = gaiaGame.getGameState().getConsumoIndicator();
        int clima = gaiaGame.getGameState().getClimaIndicator();
        int playerHealth = gaiaGame.getGameState().getPlayerHealth();
        int pollution = gaiaGame.getGameState().getPollutionLevel();

        String failureReason;
        if (playerHealth <= 0) {
            failureReason = "Tu heroe ha caido ante la contaminacion!";
        } else if (pollution >= 100) {
            failureReason = "La contaminacion ha alcanzado niveles catastroficos!";
        } else if (consumo <= 0 && clima <= 0) {
            failureReason = "Colapso total! Tanto el consumo como el clima colapsaron.";
        } else if (consumo <= 0) {
            failureReason = "El consumo insostenible ha destruido los recursos de Gaia!";
        } else {
            failureReason = "El cambio climatico ha devastado Gaia!";
        }

        Label reasonLabel = new Label(failureReason, skin);
        reasonLabel.setFontScale(1.8f);
        reasonLabel.setWrap(true);
        reasonLabel.setColor(Color.ORANGE);

        Label messageLabel = new Label(
            "Las decisiones que tomamos tienen consecuencias.\n" +
            "El equilibrio entre desarrollo y sostenibilidad es fragil.\n\n" +
            "Cada accion cuenta. Cada eleccion importa.",
            skin
        );
        messageLabel.setFontScale(1.3f);
        messageLabel.setWrap(true);
        messageLabel.setColor(Color.LIGHT_GRAY);

        // Estadisticas finales
        Label statsLabel = new Label(
            "Pisos completados: " + gaiaGame.getGameState().getCurrentFloor() + "/3\n" +
            "Vida del heroe: " + playerHealth + "%\n" +
            "Contaminacion: " + pollution + "%\n" +
            "Consumo final: " + consumo + "%\n" +
            "Clima final: " + clima + "%",
            skin
        );
        statsLabel.setFontScale(1.5f);
        statsLabel.setColor(Color.CYAN);

        TextButton tryAgainButton = new TextButton("INTENTAR DE NUEVO", skin);
        tryAgainButton.getLabel().setFontScale(1.5f);
        tryAgainButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tryAgain();
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

        mainTable.add(gameOverLabel).padBottom(30);
        mainTable.row();
        mainTable.add(reasonLabel).width(800).padBottom(30);
        mainTable.row();
        mainTable.add(messageLabel).width(900).padBottom(40);
        mainTable.row();
        mainTable.add(statsLabel).padBottom(50);
        mainTable.row();
        mainTable.add(tryAgainButton).width(350).height(60).padBottom(20);
        mainTable.row();
        mainTable.add(menuButton).width(300).height(50);

        stage.addActor(mainTable);
    }

    private void tryAgain() {
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
        ScreenUtils.clear(0.1f, 0.0f, 0.0f, 1);

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

