package org.example;

import java.io.File;

import javafx.stage.Stage;

public class Window {

  private Stage stage;

  public TickyBoxing tickyBoxing;

  private StartScene startScene;
  private MainScene mainScene;
  private boolean saved = false;

  private File file;


  public Window(Stage stage) {
    this.stage = stage; 
    tickyBoxing = new TickyBoxing(this);

    start();
  }

  public void start() {
    startScene = new StartScene(this);
    mainScene = new MainScene(this);

    setTitle();

    stage.setScene(startScene.scene());

    stage.setX(App.bounds.getMinX());
    stage.setY(App.bounds.getMinY());
    stage.setWidth(App.bounds.getWidth());
    stage.setHeight(App.bounds.getHeight());
    stage.show();
  }

  public void mainScene() {
    mainScene = new MainScene(this);
    stage.setScene(mainScene.scene);
  }

  public void setTitle() {
    String title;
    if(file != null) {
        title = file.getName() + " — " + (saved ? "saved" : " modified");
    }
    else {
        title = "New Tickysheet — modified";
    }
    System.out.println("tags " + tickyBoxing.getTags().size());
    stage.setTitle(title);
  }

  public Stage getStage() {return stage;}

  public MainScene getMainScene() {return mainScene;}

  public void setFile(File file) {this.file = file;}
  public File getFile() {return file;}

  public void setSaved(boolean bool) {saved = bool;}
}
