package org.example;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {

  private Stage stage;

  private Scene firstScene;



  public Window(Stage stage) {
    this.stage = stage;    
  }

  public void start() {
    this.firstScene = App.startScene.scene();

    stage.setScene(firstScene);

    stage.setX(App.bounds.getMinX());
    stage.setY(App.bounds.getMinY());
    stage.setWidth(App.bounds.getWidth());
    stage.setHeight(App.bounds.getHeight());
    stage.show();
  }

  public Stage getStage() {return stage;}
}
