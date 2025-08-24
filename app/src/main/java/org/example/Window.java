package org.example;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Window {

  Stage stage;

  Scene firstScene;

  static Rectangle2D bounds;


  public Window(Stage stage) {
    Screen screen = Screen.getPrimary();
    bounds = screen.getVisualBounds();

    
    this.stage = stage;    

    stage.setScene(firstScene);

    stage.setX(bounds.getMinX());
    stage.setY(bounds.getMinY());
    stage.setWidth(bounds.getWidth());
    stage.setHeight(bounds.getHeight());
    stage.show();
  }

  public void start() {
    this.firstScene = App.startScene.scene();

    stage.setScene(firstScene);

    stage.setX(bounds.getMinX());
    stage.setY(bounds.getMinY());
    stage.setWidth(bounds.getWidth());
    stage.setHeight(bounds.getHeight());
    stage.show();
  }
}
