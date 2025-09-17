// GameFacade.java
package fr.univartois.butinfo.ihm.model;

import fr.univartois.butinfo.ihm.controller.IGameController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import fr.univartois.butinfo.ihm.view.GameMapFactory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class GameFacade {

    private static final int ENEMY_COUNT = 3;

    private GameMap map;
    private IGameController controller;
    private Player player;
    private List<Enemy> enemies;

    public GameFacade() {
        this.enemies = new ArrayList<>();
    }

    public void setController(IGameController controller) {
        this.controller = controller;
    }

    public void startNewGame() {
        map = GameMapFactory.createRandomMap(GameMapFactory.MAP_WIDTH, GameMapFactory.MAP_HEIGHT);

        controller.initializeMap(map);

        player = new Player(this);
        placeCharacter(player);
        controller.linkCharacter(player);
        controller.bindBombCount(player.bombCountProperty());  // Ajoutez cette ligne

        enemies.clear();
        String[] enemyNames = {"agent", "agent", "agent"};

        for (int i = 0; i < ENEMY_COUNT; i++) {
            Enemy enemy = new Enemy(enemyNames[i % enemyNames.length], this);
            placeCharacter(enemy);
            controller.linkCharacter(enemy);
            enemies.add(enemy);
            enemy.animate();
        }
    }

    private void placeCharacter(AbstractCharacter character) {
        List<Tile> emptyTiles = map.getEmptyTiles();
        Random random = new Random();
        Tile selectedTile = emptyTiles.get(random.nextInt(emptyTiles.size()));
        character.setPosition(selectedTile.getRow(), selectedTile.getColumn());
    }

    public void moveUp(AbstractCharacter character) {
        int newRow = character.getRow() - 1;
        if (newRow >= 0 && map.getTile(newRow, character.getColumn()).isEmpty()) {
            character.setPosition(newRow, character.getColumn());
        }
    }

    public void moveDown(AbstractCharacter character) {
        int newRow = character.getRow() + 1;
        if (newRow < map.getHeight() && map.getTile(newRow, character.getColumn()).isEmpty()) {
            character.setPosition(newRow, character.getColumn());
        }
    }

    public void moveLeft(AbstractCharacter character) {
        int newCol = character.getColumn() - 1;
        if (newCol >= 0 && map.getTile(character.getRow(), newCol).isEmpty()) {
            character.setPosition(character.getRow(), newCol);
        }
    }

    public void moveRight(AbstractCharacter character) {
        int newCol = character.getColumn() + 1;
        if (newCol < map.getWidth() && map.getTile(character.getRow(), newCol).isEmpty()) {
            character.setPosition(character.getRow(), newCol);
        }
    }

    public void movePlayerUp() {
        moveUp(player);
    }

    public void movePlayerDown() {
        moveDown(player);
    }

    public void movePlayerLeft() {
        moveLeft(player);
    }

    public void movePlayerRight() {
        moveRight(player);
    }

    public GameMap getMap() {
        return map;
    }

    public void playerDropBomb() {
        ObservableList<AbstractBomb> bombs = player.getBombs();
        if (!bombs.isEmpty()) {
            // On récupère la première bombe et on la retire de l'inventaire
            AbstractBomb bomb = bombs.remove(0);  // Changement ici : on utilise remove() directement
        
            // On définit sa position
            int row = player.getRow();
            int column = player.getColumn();
            bomb.setPosition(row, column);
        
            // On l'affiche
            controller.displayBomb(bomb, row, column);
        
            // Déclencher l'explosion après le délai
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(bomb.getDelay()), 
                    e -> bomb.explode())
            );
            timeline.play();
        }
    }

    public void explode(int row, int column) {
        if (row >= 0 && row < map.getHeight() && column >= 0 && column < map.getWidth()) {
            map.getTile(row, column).explode();
        }
    }
}