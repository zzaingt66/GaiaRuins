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
import com.deckard.server.game.Hero;

/**
 * Pantalla de seleccion de heroe
 */
public class HeroSelectionScreen implements Screen {
    private final GameScreen game;
    private final GaiaRuinsGame gaiaGame;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;

    public HeroSelectionScreen(GameScreen game, GaiaRuinsGame gaiaGame) {
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

        Label titleLabel = new Label("SELECCIONA TU HEROE", skin);
        titleLabel.setFontScale(2.5f);
        titleLabel.setColor(Color.GOLD);

        Label subtitleLabel = new Label("Cada heroe representa un elemento natural con habilidades unicas", skin);
        subtitleLabel.setFontScale(1.2f);
        subtitleLabel.setColor(Color.CYAN);

        mainTable.add(titleLabel).padBottom(20).colspan(2);
        mainTable.row();
        mainTable.add(subtitleLabel).padBottom(40).colspan(2);
        mainTable.row();

        // Fila 1: Tierra y Agua
        Table row1 = new Table();
        row1.add(createHeroCard(Hero.ElementType.TIERRA)).width(450).height(300).pad(15);
        row1.add(createHeroCard(Hero.ElementType.AGUA)).width(450).height(300).pad(15);

        // Fila 2: Fuego y Aire
        Table row2 = new Table();
        row2.add(createHeroCard(Hero.ElementType.FUEGO)).width(450).height(300).pad(15);
        row2.add(createHeroCard(Hero.ElementType.AIRE)).width(450).height(300).pad(15);

        mainTable.add(row1).colspan(2);
        mainTable.row();
        mainTable.add(row2).colspan(2);

        stage.addActor(mainTable);
    }

    private Table createHeroCard(Hero.ElementType elementType) {
        Table card = new Table();
        card.setBackground(skin.getDrawable("default-round"));

        Color elementColor = getElementColor(elementType);

        Label nameLabel = new Label(elementType.getDisplayName().toUpperCase(), skin);
        nameLabel.setFontScale(1.8f);
        nameLabel.setColor(elementColor);

        Label descLabel = new Label(elementType.getDescription(), skin);
        descLabel.setFontScale(1.0f);
        descLabel.setWrap(true);

        String bonusText = "";
        if (elementType.getConsumoBonus() > 0) {
            bonusText += "+"+elementType.getConsumoBonus()+"% Consumo Sostenible\n";
        }
        if (elementType.getClimaBonus() > 0) {
            bonusText += "+"+elementType.getClimaBonus()+"% Accion Climatica\n";
        }

        Label bonusLabel = new Label(bonusText, skin);
        bonusLabel.setFontScale(1.0f);
        bonusLabel.setColor(Color.GREEN);

        TextButton selectButton = new TextButton("SELECCIONAR", skin);
        selectButton.getLabel().setFontScale(1.3f);
        selectButton.setColor(elementColor);
        selectButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectHero(elementType);
            }
        });

        card.add(nameLabel).padTop(10).padBottom(15);
        card.row();
        card.add(descLabel).width(400).padBottom(15);
        card.row();
        card.add(bonusLabel).padBottom(20);
        card.row();
        card.add(selectButton).width(250).height(50).padBottom(10);

        return card;
    }

    private Color getElementColor(Hero.ElementType element) {
        switch (element) {
            case TIERRA:
                return new Color(0.6f, 0.4f, 0.2f, 1f); // Marron
            case AGUA:
                return new Color(0.2f, 0.5f, 1f, 1f); // Azul
            case FUEGO:
                return new Color(1f, 0.3f, 0.1f, 1f); // Rojo/Naranja
            case AIRE:
                return new Color(0.8f, 0.9f, 1f, 1f); // Celeste claro
            default:
                return Color.WHITE;
        }
    }

    private void selectHero(Hero.ElementType elementType) {
        Hero selectedHero = new Hero(elementType);
        gaiaGame.selectHero(selectedHero);
        game.setScreen(new MazeScreen(game, gaiaGame));
        dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.05f, 0.05f, 0.1f, 1);

        camera.update();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}

