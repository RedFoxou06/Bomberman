




// Enemy.java
package fr.univartois.butinfo.ihm.model;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Random;

public class Enemy extends AbstractCharacter {
    private final String name;
    private Timeline timeline;

    public Enemy(String name, GameFacade facade) {
        super(1, facade);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void moveRandomly() {
        Random random = new Random();
        int direction = random.nextInt(4);

        switch (direction) {
            case 0: getFacade().moveUp(this); break;
            case 1: getFacade().moveDown(this); break;
            case 2: getFacade().moveLeft(this); break;
            case 3: getFacade().moveRight(this); break;
        }
    }

    public void animate() {
        this.timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> moveRandomly())
        );
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
    }
}