package org.example;

import java.io.File;

import javafx.stage.Stage;

public class Window {

  private Stage stage;

  private TickyBoxing tickyBoxing;

  private StartScene startScene;
  private MainScene mainScene;
  private boolean saved = false;

  private File file;


  public Window(Stage stage) {
    this.stage = stage;    
    startScene = new StartScene(this);
    mainScene = new MainScene(this);
    tickyBoxing = new TickyBoxing(this);

    setTitle();
  }

  public void start() {

    stage.setScene(startScene.scene());

    stage.setX(App.bounds.getMinX());
    stage.setY(App.bounds.getMinY());
    stage.setWidth(App.bounds.getWidth());
    stage.setHeight(App.bounds.getHeight());
    stage.show();
  }

  public void setTitle() {
    String title;
    if(file != null) {
        title = file.getName() + " — " + (saved ? "saved" : " modified");
    }
    else {
        title = "New Tickysheet — modified";
    }
    stage.setTitle(title);
  }

  public Stage getStage() {return stage;}

  public TickyBoxing getTicky() {return tickyBoxing;}
  public void setTicky(TickyBoxing ticky) {tickyBoxing = ticky;}

  public MainScene getMainScene() {return mainScene;}

  public void setFile(File file) {this.file = file;}
  public File getFile() {return file;}

  public void setSaved(boolean bool) {saved = bool;}
}
