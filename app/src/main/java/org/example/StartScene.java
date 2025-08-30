package org.example;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Pos;


public class StartScene implements EventHandler<ActionEvent> {

  Button newButton;
  Button uploadButton;

  Stage stage;
  Scene scene;

  Window window;

  public StartScene(Window window) {
    this.window = window;
    stage = window.getStage();

    setupButtons();
    makeScene();
  }

  public Scene scene() {
    return scene;
  }

  @Override
  public void handle(ActionEvent event) {

    if(event.getSource() == newButton) {
      window.setTicky( new TickyBoxing(window));
    }

    if(event.getSource() == uploadButton) {
      new StateSaver(window).load(fileChooserDialog());
    }
    mainScene();
  }

  private void setupButtons() {
    newButton = new Button("New Tickysheet");
    newButton.setFont(Font.font(Constants.FONT_SIZE_2));
    newButton.setOnAction(this);

    uploadButton = new Button("Open Tickysheet");
    uploadButton.setFont(Font.font(Constants.FONT_SIZE_2));
    uploadButton.setOnAction(this);
  }


  private void makeScene() {

    HBox root = new HBox(30, newButton, uploadButton);
    root.setAlignment(Pos.CENTER);
    scene = new Scene(root);
  }

  private File fileChooserDialog() {
    FileChooser fc = new FileChooser();
    FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Ticky files", "*.ticky");
    fc.setTitle("Select Tickysheet File");
    fc.getExtensionFilters().add(extension);
    fc.setSelectedExtensionFilter(extension);
    File file = fc.showOpenDialog(stage);
    window.setFile(file);
    return file;
  }

  private void mainScene() {
    stage.setScene(window.getMainScene().scene);
  }
}
