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
import com.deckard.server.event.RandomEvent;
import com.deckard.server.game.GaiaRuinsGame;

/**
 * Pantalla de Evento Aleatorio - muestra eventos inesperados en el laberinto
 */
public class EventScreen implements Screen {
    private final GameScreen game;
    private final GaiaRuinsGame gaiaGame;
    private final RandomEvent event;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;

    public EventScreen(GameScreen game, GaiaRuinsGame gaiaGame) {
        this.game = game;
        this.gaiaGame = gaiaGame;
        this.event = gaiaGame.getCurrentEvent();

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

        Label titleLabel = new Label("¡EVENTO ALEATORIO!", skin);
        titleLabel.setFontScale(3f);
        titleLabel.setColor(Color.YELLOW);

        Label eventTitleLabel = new Label(event.getTitle(), skin);
        eventTitleLabel.setFontScale(2f);
        eventTitleLabel.setColor(Color.CYAN);

        Label descriptionLabel = new Label(event.getDescription(), skin);
        descriptionLabel.setFontScale(1.5f);
        descriptionLabel.setWrap(true);
        descriptionLabel.setColor(Color.LIGHT_GRAY);

        // Mostrar el efecto del evento
        String effectDescription = getEffectDescription(event.getEffect());
        Label effectLabel = new Label(effectDescription, skin);
        effectLabel.setFontScale(1.3f);
        effectLabel.setWrap(true);
        effectLabel.setColor(getEffectColor(event.getEffect()));

        // Indicadores actuales
        Label indicatorsLabel = new Label(
            "Consumo: " + gaiaGame.getGameState().getConsumoIndicator() + "%  |  " +
            "Clima: " + gaiaGame.getGameState().getClimaIndicator() + "%",
            skin
        );
        indicatorsLabel.setFontScale(1.5f);
        indicatorsLabel.setColor(Color.WHITE);

        TextButton continueButton = new TextButton("CONTINUAR", skin);
        continueButton.getLabel().setFontScale(1.8f);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                applyEventAndContinue();
            }
        });

        mainTable.add(titleLabel).padBottom(30);
        mainTable.row();
        mainTable.add(eventTitleLabel).padBottom(20);
        mainTable.row();
        mainTable.add(descriptionLabel).width(800).padBottom(30);
        mainTable.row();
        mainTable.add(effectLabel).width(700).padBottom(40);
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

    private String getEffectDescription(RandomEvent.EventEffect effect) {
        switch (effect) {
            case GAIN_CONSUMO:
                return "Mejora el indicador de Consumo Sostenible!";
            case GAIN_CLIMA:
                return "Mejora el indicador de Clima!";
            case LOSE_CONSUMO:
                return "Penalizacion al indicador de Consumo Sostenible";
            case LOSE_CLIMA:
                return "Penalizacion al indicador de Clima";
            case BALANCE_BOOST:
                return "Mejora ambos indicadores!";
            case MINOR_PENALTY:
                return "Pequena penalizacion en ambos indicadores";
            default:
                return "Efecto desconocido";
        }
    }

    private Color getEffectColor(RandomEvent.EventEffect effect) {
        switch (effect) {
            case GAIN_CONSUMO:
            case GAIN_CLIMA:
            case BALANCE_BOOST:
                return Color.GREEN;
            case LOSE_CONSUMO:
            case LOSE_CLIMA:
            case MINOR_PENALTY:
                return Color.ORANGE;
            default:
                return Color.WHITE;
        }
    }

    private void applyEventAndContinue() {
        // Aplicar el efecto del evento
        event.apply(gaiaGame.getGameState());

        // Verificar si algun indicador llego a cero
        if (gaiaGame.getGameState().getConsumoIndicator() <= 0 ||
            gaiaGame.getGameState().getClimaIndicator() <= 0) {
            game.setScreen(new GameOverScreen(game, gaiaGame));
        } else {
            // Volver al laberinto
            game.setScreen(new MazeScreen(game, gaiaGame));
        }
        dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.05f, 0.05f, 0.15f, 1);

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

