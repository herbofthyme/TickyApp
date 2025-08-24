package org.example;

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
  private Scene scene;

  public StartScene(Stage stage) {
    this.stage = stage;

    setupButtons();
    makeScene();
  }

  public Scene scene() {
    return scene;
  }

  @Override
  public void handle(ActionEvent event) {
    if(event.getSource() == newButton) {
      mainScene();
    }

    if(event.getSource() == uploadButton) {
      newButton.setText(fileChooserDialog());
      mainScene();
    }
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

  private String fileChooserDialog() {
    FileChooser fc = new FileChooser();
    FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter(".ticky extension", "*.ticky");
    fc.setTitle("Select Tickysheet File");
    fc.setSelectedExtensionFilter(extension);
    App.file = fc.showOpenDialog(stage);

    return App.file.getName();
  }

  private void mainScene() {
    stage.setScene(App.mainScene.scene);
  }
}
