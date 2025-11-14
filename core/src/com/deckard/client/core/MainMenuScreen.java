package com.deckard.client.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deckard.server.game.GaiaRuinsGame;

public class MainMenuScreen implements Screen {

    private final GameScreen game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;

    public MainMenuScreen(GameScreen game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GuiParams.WIDTH, GuiParams.HEIGHT);

        stage = new Stage(new ScreenViewport(camera), game.getBatch());
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        createUI();

        Gdx.input.setInputProcessor(stage);
    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);

        Label titleLabel = new Label("GAIA RUINS", skin);
        titleLabel.setFontScale(3f);
        titleLabel.setColor(Color.GREEN);

        Label subtitleLabel = new Label("Mini Roguelike - Maze Builder", skin);
        subtitleLabel.setFontScale(1.5f);

        Label descLabel = new Label("Consumo Responsable & Accion por el Clima", skin);
        descLabel.setFontScale(1.2f);
        descLabel.setColor(Color.CYAN);

        TextButton startButton = new TextButton("NUEVA PARTIDA", skin);
        startButton.getLabel().setFontScale(1.5f);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startNewGame();
            }
        });

        TextButton exitButton = new TextButton("SALIR", skin);
        exitButton.getLabel().setFontScale(1.2f);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(titleLabel).padBottom(20);
        table.row();
        table.add(subtitleLabel).padBottom(10);
        table.row();
        table.add(descLabel).padBottom(50);
        table.row();
        table.add(startButton).width(300).height(60).padBottom(20);
        table.row();
        table.add(exitButton).width(200).height(50);

        stage.addActor(table);
    }

    private void startNewGame() {
        GaiaRuinsGame gaiaGame = new GaiaRuinsGame();
        gaiaGame.startNewGame();
        game.setScreen(new HeroSelectionScreen(game, gaiaGame));
        dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1);

        camera.update();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

