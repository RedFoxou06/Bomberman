// bombermanController.java
package fr.univartois.butinfo.ihm.controller;

import fr.univartois.butinfo.ihm.model.GameMap;
import fr.univartois.butinfo.ihm.model.Tile;
import fr.univartois.butinfo.ihm.model.AbstractBomb;
import javafx.beans.binding.Bindings;
import java.net.URL;
import java.util.NoSuchElementException;

import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import fr.univartois.butinfo.ihm.model.GameFacade;
import fr.univartois.butinfo.ihm.model.TileContent;
import fr.univartois.butinfo.ihm.model.AbstractCharacter;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class BombermanController implements IGameController {

    private static final int MAP_WIDTH  = 11;
    private static final int MAP_HEIGHT = 11;

    @FXML private GridPane gameGrid;
    @FXML private javafx.scene.control.Label lifeLabel;
    @FXML private javafx.scene.control.Label bombLabel;
    @FXML private javafx.scene.control.Label enemyLabel;

    private GameMap map;
    private GameFacade gameFacade;
    private Scene scene;

    @Override
    public void setGameFacade(GameFacade facade) {
        this.gameFacade = facade;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.setOnKeyPressed(this::handleKeyPressed);
    }

    @Override
    public void bindBombCount(IntegerProperty bombCount) {
        bombLabel.textProperty().bind(bombCount.asString());
    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                gameFacade.movePlayerUp();
                break;
            case DOWN:
                gameFacade.movePlayerDown();
                break;
            case LEFT:
                gameFacade.movePlayerLeft();
                break;
            case RIGHT:
                gameFacade.movePlayerRight();
                break;
            case SPACE:
                gameFacade.playerDropBomb();
                break;
        }
    }

    @Override
    public void initializeMap(GameMap map) {
        this.map = map;
        gameGrid.getChildren().clear();

        for (int row = 0; row < map.getHeight(); row++) {
            for (int col = 0; col < map.getWidth(); col++) {
                Tile tile = map.getTile(row, col);

                ImageView imageView = new ImageView();
                imageView.setFitWidth(64);
                imageView.setFitHeight(64);

                // Binding pour le contenu normal
                imageView.imageProperty().bind(
                    Bindings.createObjectBinding(() -> {
                        if (tile.explodingProperty().get()) {
                            return loadImage("/fr/univartois/butinfo/ihm/images/explosion.png");
                        } else {
                            return loadImage("/fr/univartois/butinfo/ihm/images/" + tile.getContent().getName() + ".png");
                        }
                    }, tile.contentProperty(), tile.explodingProperty())
                );

                gameGrid.add(imageView, col, row);
            }
        }
    }

    @Override
    public void linkCharacter(AbstractCharacter character) {
        ImageView characterView = new ImageView();
        characterView.setFitWidth(64);
        characterView.setFitHeight(64);

        characterView.imageProperty().bind(
                Bindings.createObjectBinding(() -> loadImage("/fr/univartois/butinfo/ihm/images/" + character.getName() + ".png"))
        );

        character.rowProperty().addListener((obs, oldVal, newVal) -> {
            GridPane.setConstraints(characterView, character.getColumn(), newVal.intValue());
        });

        character.columnProperty().addListener((obs, oldVal, newVal) -> {
            GridPane.setConstraints(characterView, newVal.intValue(), character.getRow());
        });

        GridPane.setConstraints(characterView, character.getColumn(), character.getRow());
        gameGrid.getChildren().add(characterView);
    }

    public void initialize() {
        buildEmptyMap();
        lifeLabel.setText("3");
        bombLabel.setText("1");
        enemyLabel.setText("—");
    }

    private void buildEmptyMap() {
        for (int row = 0; row < MAP_HEIGHT; row++) {
            for (int col = 0; col < MAP_WIDTH; col++) {
                ImageView iv;

                if (row == 0 || col == 0 || row == MAP_HEIGHT - 1 || col == MAP_WIDTH - 1) {
                    iv = new ImageView(loadImage("/fr/univartois/butinfo/ihm/images/wall.png"));
                }
                else if (row % 2 == 0 && col % 2 == 0) {
                    iv = new ImageView(loadImage("/fr/univartois/butinfo/ihm/images/wall.png"));
                }
                else if ((row <= 1 && col <= 1) || (row >= MAP_HEIGHT - 2 && col >= MAP_WIDTH - 2)) {
                    iv = new ImageView(loadImage("/fr/univartois/butinfo/ihm/images/lawn.png"));
                }
                else {
                    iv = new ImageView(loadImage("/fr/univartois/butinfo/ihm/images/lawn.png"));
                }

                iv.setFitWidth(64);
                iv.setFitHeight(64);
                gameGrid.add(iv, col, row);
            }
        }
    }

    public void afficherCarte(GameMap map) {
        gameGrid.getChildren().clear();

        for (int row = 0; row < map.getHeight(); row++) {
            for (int col = 0; col < map.getWidth(); col++) {
                Tile tile = map.getTile(row, col);
                ImageView iv = new ImageView();
                iv.setFitWidth(64);
                iv.setFitHeight(64);

                iv.imageProperty().bind(Bindings.createObjectBinding(
                        () -> loadImage("/fr/univartois/butinfo/ihm/images/"
                                + tile.getContent().getName() + ".png"),
                        tile.contentProperty()
                ));

                gameGrid.add(iv, col, row);
            }
        }
    }

    private Image loadImage(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            throw new NoSuchElementException("Could not load image " + path);
        }
        return new Image(url.toExternalForm(), 64, 64, true, true);
    }

    @Override
    public void setImageForTile(TileContent content, int x, int y) {
        String imagePath;
        switch (content) {
            case LAWN:
                imagePath = "/fr/univartois/butinfo/ihm/images/lawn.png";
                break;
            case BRICK_WALL:
                imagePath = "/fr/univartois/butinfo/ihm/images/bricks.png";
                break;
            case SOLID_WALL:
                imagePath = "/fr/univartois/butinfo/ihm/images/wall.png";
                break;
            default:
                imagePath = "/fr/univartois/butinfo/ihm/images/lawn.png";
        }

        ImageView iv = new ImageView(loadImage(imagePath));
        iv.setFitWidth(64);
        iv.setFitHeight(64);
        gameGrid.add(iv, x, y);
    }
    
    public void displayBomb(AbstractBomb bomb, int row, int column) {
        ImageView bombView = new ImageView();
        bombView.setFitWidth(64);
        bombView.setFitHeight(64);
        
        bombView.setImage(loadImage("/fr/univartois/butinfo/ihm/images/" + bomb.getName() + ".png"));
        
        GridPane.setConstraints(bombView, column, row);
        gameGrid.getChildren().add(bombView);
        
        // Retirer la bombe quand elle explose
        bomb.explodedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                gameGrid.getChildren().remove(bombView);
            }
        });
    }
}