

// bombermanApplication.java
package fr.univartois.butinfo.ihm;

import fr.univartois.butinfo.ihm.controller.BombermanController;
import fr.univartois.butinfo.ihm.model.GameFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class bombermanApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/univartois/butinfo/ihm/bomberman.fxml"));
        Scene scene = new Scene(loader.load(),500,500);

        BombermanController controller = loader.getController();
        GameFacade facade = new GameFacade();

        controller.setGameFacade(facade);
        facade.setController(controller);
        controller.setScene(scene);

        facade.startNewGame();

        stage.setTitle("Bomberman");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}