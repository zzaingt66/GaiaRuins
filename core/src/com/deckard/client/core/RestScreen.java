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
 * Pantalla de descanso
 */
public class RestScreen implements Screen {
    private final GameScreen game;
    private final GaiaRuinsGame gaiaGame;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;

    public RestScreen(GameScreen game, GaiaRuinsGame gaiaGame) {
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

        Label titleLabel = new Label("DESCANSO", skin);
        titleLabel.setFontScale(2.5f);
        titleLabel.setColor(Color.BLUE);

        Label descLabel = new Label(
            "Encuentras un lugar tranquilo para reflexionar.\n" +
            "Recuperas +15% en ambos indicadores.",
            skin
        );
        descLabel.setFontScale(1.4f);
        descLabel.setWrap(true);
        descLabel.setColor(Color.LIGHT_GRAY);

        Label beforeLabel = new Label("Antes del descanso:", skin);
        beforeLabel.setFontScale(1.2f);

        // Mostrar indicadores actuales (ya con el bonus aplicado)
        Label indicatorsLabel = new Label(
            "Consumo: " + gaiaGame.getGameState().getConsumoIndicator() + "% | " +
            "Clima: " + gaiaGame.getGameState().getClimaIndicator() + "%",
            skin
        );
        indicatorsLabel.setFontScale(1.4f);
        indicatorsLabel.setColor(Color.GREEN);

        TextButton continueButton = new TextButton("CONTINUAR", skin);
        continueButton.getLabel().setFontScale(1.5f);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                leaveRest();
            }
        });

        mainTable.add(titleLabel).padBottom(40);
        mainTable.row();
        mainTable.add(descLabel).width(700).padBottom(30);
        mainTable.row();
        mainTable.add(beforeLabel).padBottom(10);
        mainTable.row();
        mainTable.add(indicatorsLabel).padBottom(50);
        mainTable.row();
        mainTable.add(continueButton).width(300).height(60);

        // Boton de abandonar partida en esquina superior derecha
        TextButton abandonButton = new TextButton("ABANDONAR", skin);
        abandonButton.getLabel().setFontScale(0.9f);
        abandonButton.setPosition(GuiParams.WIDTH - 140, GuiParams.HEIGHT - 50);
        abandonButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
        stage.addActor(abandonButton);

        stage.addActor(mainTable);
    }

    private void leaveRest() {
        gaiaGame.leaveRest();
        returnToMaze();
    }

    private void returnToMaze() {
        game.setScreen(new MazeScreen(game, gaiaGame));
        dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.05f, 0.1f, 0.2f, 1);

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

