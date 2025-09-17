
// AbstractCharacter.java
package fr.univartois.butinfo.ihm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class AbstractCharacter {
    private final IntegerProperty row = new SimpleIntegerProperty();
    private final IntegerProperty column = new SimpleIntegerProperty();
    private final int life;
    private GameFacade facade;

    public AbstractCharacter(int life, GameFacade facade) {
        this.life = life;
        this.facade = facade;
    }

    public abstract String getName();

    public int getRow() { return row.get(); }
    public void setRow(int row) { this.row.set(row); }
    public IntegerProperty rowProperty() { return row; }

    public int getColumn() { return column.get(); }
    public void setColumn(int column) { this.column.set(column); }
    public IntegerProperty columnProperty() { return column; }

    public void setPosition(int row, int column) {
        setRow(row);
        setColumn(column);
    }

    public GameFacade getFacade() { return facade; }
    public int getLife() { return life; }
}