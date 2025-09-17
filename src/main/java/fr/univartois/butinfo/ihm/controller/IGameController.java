package fr.univartois.butinfo.ihm.controller;

import fr.univartois.butinfo.ihm.model.GameMap;
import fr.univartois.butinfo.ihm.model.GameFacade;
import fr.univartois.butinfo.ihm.model.AbstractCharacter;
import fr.univartois.butinfo.ihm.model.AbstractBomb;
import fr.univartois.butinfo.ihm.model.TileContent;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Scene;

public interface IGameController {
    void setGameFacade(GameFacade facade);
    void initializeMap(GameMap map);
    void linkCharacter(AbstractCharacter character);
    void setScene(Scene scene);
    void setImageForTile(TileContent content, int col, int row);
    void bindBombCount(IntegerProperty bombCount);
    void displayBomb(AbstractBomb bomb, int row, int column);  // Ajout de cette méthode
}