package fr.univartois.butinfo.ihm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

public class Player extends AbstractCharacter {
    private final ObservableList<AbstractBomb> bombs;
    private final IntegerProperty bombCount;

    public Player(GameFacade facade) {
        super(3, facade);
        this.bombs = FXCollections.observableArrayList();
        this.bombCount = new SimpleIntegerProperty(0);
        
        // Ajouter un listener pour mettre à jour le compteur quand la liste change
        this.bombs.addListener((ListChangeListener<AbstractBomb>) change -> {
            bombCount.set(bombs.size());
        });
        
        // Ajout des 20 bombes initiales
        for (int i = 0; i < 8; i++) {
            bombs.add(new Bomb(facade));
        }
        for (int i = 0; i < 4; i++) {
            bombs.add(new RowBomb(facade));
        }
        for (int i = 0; i < 4; i++) {
            bombs.add(new ColumnBomb(facade));
        }
        for (int i = 0; i < 4; i++) {
            bombs.add(new LargeBomb(facade));
        }
    }

    public ObservableList<AbstractBomb> getBombs() {
        return bombs;
    }

    public void removeBomb(int index) {
        if (index >= 0 && index < bombs.size()) {
            bombs.remove(index);
        }
    }

    public IntegerProperty bombCountProperty() {
        return bombCount;
    }

    @Override
    public String getName() {
        return "guy";
    }
}