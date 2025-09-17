package fr.univartois.butinfo.ihm.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class Tile {
    private final int row;
    private final int column;
    private final ObjectProperty<TileContent> content = new SimpleObjectProperty<>();
    private final BooleanProperty exploding = new SimpleBooleanProperty(false);

    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.content.set(TileContent.LAWN); // valeur par défaut
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }

    public TileContent getContent() { return content.get(); }
    public void setContent(TileContent content) { this.content.set(content); }
    public ObjectProperty<TileContent> contentProperty() { return content; }

    public boolean isEmpty() { return getContent().isEmpty(); }
    
    public BooleanProperty explodingProperty() {
        return exploding;
    }

    public void explode() {
        if (getContent().isDestroyableByExplosion()) {
            exploding.set(true);
            
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> {
                    exploding.set(false);
                    setContent(TileContent.LAWN);
                })
            );
            timeline.play();
        }
    }
}