package org.example;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainScene {
  Stage stage;
  Scene scene;

  Tab tagsTab, tickyTab, resultsTab;

  Button saveButton;

  AddTagsTab tagTabLogic;
  TickyTab tickyTabLogic;
  ResultsTab resultsTabLogic;
  
  public MainScene(Stage stage) {
    initialize();
    setupButton();

    TabPane tabPane = tabs();

    
    tabPane.setTabMaxHeight(25);
    tabPane.setTabMinHeight(25);
    
    StackPane.setMargin(saveButton, new Insets(tabPane.getTabMinHeight() + 10, 10, 10, 10));


    StackPane borderPane = new StackPane(tabPane, saveButton);

    scene = new Scene(borderPane);

  }

  private void setupButton() {
    EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        StateSaver saver = new StateSaver(App.tickyboxing.tagSet, App.tickyboxing.prompts);
        if(App.file == null) {
          saver.save(fileSaveDialog());
        }
        else {
          saver.save(App.file);
        }
      }
      
    };

    saveButton = new Button("Save");
    saveButton.setOnAction(handler);
    saveButton.setFont(Font.font(Constants.FONT_SIZE_2));
    StackPane.setAlignment(saveButton, Pos.TOP_RIGHT);
  }

  private File fileSaveDialog() {
    FileChooser fc = new FileChooser();
    FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Ticky files", "*.ticky");
    fc.setTitle("Where do you want to save this file?");
    fc.getExtensionFilters().add(extension);
    fc.setSelectedExtensionFilter(extension);
    File file = fc.showSaveDialog(stage);
    App.file = file;
    return file;
  }

  private TabPane tabs() {
    TabPane tabPane = new TabPane();
    tagsTab = new Tab("Tags", tagTabLogic.layout());
    tagsTab.setClosable(false);

    tickyTab = new Tab("Ticky Page", tickyTabLogic.layout());
    tickyTab.setClosable(false);
    tickyTab.setOnSelectionChanged(e -> {
    tickyTabLogic.updateLists();
    });

    resultsTab = new Tab("Results"  , resultsTabLogic.layout());
    resultsTab.setClosable(false);
    resultsTab.setOnSelectionChanged(e -> {
    resultsTabLogic.updatePrompts();
    });

    tabPane.getTabs().add(tagsTab);
    tabPane.getTabs().add(tickyTab);
    tabPane.getTabs().add(resultsTab);
    
    return tabPane;
  }
 
  private void initialize() {
    tagTabLogic = new AddTagsTab();
    tickyTabLogic = new TickyTab();   
    resultsTabLogic = new ResultsTab();
    };
}
