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
import com.deckard.server.boss.Boss;
import com.deckard.server.decision.DecisionCard;
import com.deckard.server.decision.DecisionOption;
import com.deckard.server.game.GaiaRuinsGame;
import com.deckard.server.game.GameState;

/**
 * Pantalla de combate contra jefe
 */
public class BossScreen implements Screen {
    private final GameScreen game;
    private final GaiaRuinsGame gaiaGame;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Skin skin;
    private boolean optionSelected = false;
    private String resultText = "";

    public BossScreen(GameScreen game, GaiaRuinsGame gaiaGame) {
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

        Boss boss = gaiaGame.getCurrentBoss();

        if (boss == null) {
            returnToMaze();
            return;
        }

        // Título
        Label titleLabel = new Label("¡¡¡ JEFE !!!", skin);
        titleLabel.setFontScale(2.5f);
        titleLabel.setColor(Color.RED);

        // Nombre del jefe
        Label bossNameLabel = new Label(boss.getName(), skin);
        bossNameLabel.setFontScale(2f);
        bossNameLabel.setColor(Color.FIREBRICK);

        // Descripción
        Label bossDescLabel = new Label(boss.getDescription(), skin);
        bossDescLabel.setFontScale(1.3f);
        bossDescLabel.setWrap(true);

        // Desafíos restantes
        Label challengesLabel = new Label(
            "Desafios restantes: " + boss.getRemainingChallenges(),
            skin
        );
        challengesLabel.setFontScale(1.2f);
        challengesLabel.setColor(Color.YELLOW);

        mainTable.add(titleLabel).padBottom(20);
        mainTable.row();
        mainTable.add(bossNameLabel).padBottom(15);
        mainTable.row();
        mainTable.add(bossDescLabel).width(800).padBottom(20);
        mainTable.row();
        mainTable.add(challengesLabel).padBottom(30);
        mainTable.row();

        // Desafío actual
        DecisionCard challenge = boss.getCurrentChallenge();
        if (challenge != null) {
            createChallengeUI(mainTable, challenge);
        }

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

    private void createChallengeUI(Table mainTable, DecisionCard challenge) {
        // Titulo del desafio
        Label challengeTitleLabel = new Label(challenge.getTitle(), skin);
        challengeTitleLabel.setFontScale(1.5f);
        challengeTitleLabel.setColor(Color.ORANGE);

        // Pregunta verdadero/falso
        Label questionLabel = new Label(challenge.getQuestion(), skin);
        questionLabel.setFontScale(1.2f);
        questionLabel.setWrap(true);

        // Barras de vida
        Table healthBarsTable = createHealthBars();

        mainTable.add(challengeTitleLabel).padBottom(15);
        mainTable.row();
        mainTable.add(questionLabel).width(800).padBottom(20);
        mainTable.row();
        mainTable.add(healthBarsTable).width(900).padBottom(30);
        mainTable.row();

        // Opciones
        Table optionsTable = new Table();
        for (int i = 0; i < challenge.getOptions().size(); i++) {
            DecisionOption option = challenge.getOptions().get(i);
            TextButton optionButton = createOptionButton(option, i);
            optionsTable.add(optionButton).width(350).height(100).pad(15);
        }

        mainTable.add(optionsTable);
    }

    private TextButton createOptionButton(DecisionOption option, int index) {
        TextButton button = new TextButton(option.getText(), skin);
        button.getLabel().setFontScale(1.8f);

        // Color segun si es verdadero o falso
        if (option.getText().equals("VERDADERO")) {
            button.setColor(0.5f, 1f, 0.5f, 1f); // Verde
        } else {
            button.setColor(1f, 0.5f, 0.5f, 1f); // Rojo
        }

        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!optionSelected) {
                    selectOption(index, option);
                }
            }
        });

        return button;
    }

    private void selectOption(int index, DecisionOption option) {
        optionSelected = true;

        // Determinar si es correcta
        DecisionCard challenge = gaiaGame.getCurrentBoss().getCurrentChallenge();
        boolean isCorrect = challenge.isCorrectAnswer(option.getText().equals("VERDADERO"));

        if (isCorrect) {
            resultText = "CORRECTO! " + challenge.getExplanation();
        } else {
            resultText = "INCORRECTO. " + challenge.getExplanation();
        }

        gaiaGame.selectOption(index);

        // Mostrar resultado
        showResult();
    }

    private Table createHealthBars() {
        Table table = new Table();

        // Barra de vida del jugador
        Label playerLabel = new Label("HEROE:", skin);
        playerLabel.setFontScale(1.1f);
        playerLabel.setColor(Color.GREEN);

        int playerHealth = gaiaGame.getGameState().getPlayerHealth();
        Label playerHealthLabel = new Label(playerHealth + "%", skin);
        playerHealthLabel.setFontScale(1.1f);
        playerHealthLabel.setColor(getHealthColor(playerHealth));

        ProgressBar playerBar = new ProgressBar(0, 100, 1, false, skin);
        playerBar.setValue(playerHealth);

        // Barra de contaminacion
        Label pollutionLabel = new Label("CONTAMINACION:", skin);
        pollutionLabel.setFontScale(1.1f);
        pollutionLabel.setColor(Color.RED);

        int pollutionLevel = gaiaGame.getGameState().getPollutionLevel();
        Label pollutionLevelLabel = new Label(pollutionLevel + "%", skin);
        pollutionLevelLabel.setFontScale(1.1f);
        pollutionLevelLabel.setColor(getPollutionColor(pollutionLevel));

        ProgressBar pollutionBar = new ProgressBar(0, 100, 1, false, skin);
        pollutionBar.setValue(pollutionLevel);

        // Indicadores ODS
        Label indicatorsLabel = new Label(
            "Consumo: " + gaiaGame.getGameState().getConsumoIndicator() + "% | " +
            "Clima: " + gaiaGame.getGameState().getClimaIndicator() + "%",
            skin
        );
        indicatorsLabel.setFontScale(1.0f);
        indicatorsLabel.setColor(Color.CYAN);

        table.add(playerLabel).padRight(10);
        table.add(playerBar).width(250).height(25).padRight(10);
        table.add(playerHealthLabel).padRight(40);
        table.add(pollutionLabel).padRight(10);
        table.add(pollutionBar).width(250).height(25).padRight(10);
        table.add(pollutionLevelLabel);
        table.row();
        table.add(indicatorsLabel).colspan(6).padTop(10);

        return table;
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

    private void showResult() {
        stage.clear();

        Table resultTable = new Table();
        resultTable.setFillParent(true);

        Label resultLabel = new Label(resultText, skin);
        resultLabel.setFontScale(1.3f);
        resultLabel.setWrap(true);

        // Barras de vida actualizadas
        Table healthBarsTable = createHealthBars();

        TextButton continueButton = new TextButton("CONTINUAR", skin);
        continueButton.getLabel().setFontScale(1.5f);
        continueButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                checkGameState();
            }
        });

        resultTable.add(resultLabel).width(800).padBottom(30);
        resultTable.row();
        resultTable.add(healthBarsTable).width(900).padBottom(40);
        resultTable.row();
        resultTable.add(continueButton).width(300).height(60);

        stage.addActor(resultTable);
    }

    private void checkGameState() {
        if (gaiaGame.getCurrentPhase() == GaiaRuinsGame.GamePhase.GAME_OVER) {
            game.setScreen(new GameOverScreen(game, gaiaGame));
        } else if (gaiaGame.getCurrentPhase() == GaiaRuinsGame.GamePhase.VICTORY) {
            game.setScreen(new VictoryScreen(game, gaiaGame));
        } else {
            // Continuar con el siguiente desafío del jefe o volver al mapa
            Boss boss = gaiaGame.getCurrentBoss();
            if (boss != null && !boss.isDefeated()) {
                // Recargar la pantalla del jefe con el siguiente desafío
                game.setScreen(new BossScreen(game, gaiaGame));
            } else {
                returnToMaze();
            }
        }
        dispose();
    }

    private void returnToMaze() {
        game.setScreen(new MazeScreen(game, gaiaGame));
        dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.05f, 0.05f, 1);

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
