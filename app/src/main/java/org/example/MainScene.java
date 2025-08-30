package org.example;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainScene {
  Stage stage;
  Window window;
  Scene scene;

  Tab tagsTab, tickyTab, resultsTab;

  Button newWindowButton, saveButton, mainMenuButton;
  HBox buttons;

  AddTagsTab tagTabLogic;
  TickyTab tickyTabLogic;
  ResultsTab resultsTabLogic;
  
  public MainScene(Window window) {
    this.window = window;
    initialize();

    TabPane tabPane = tabs();

    
    tabPane.setTabMaxHeight(25);
    tabPane.setTabMinHeight(25);
    
    //AnchorPane.setMargin(buttons, new Insets(tabPane.getTabMinHeight() + 10, 10, 10, 10));

    AnchorPane.setTopAnchor(tabPane, 0.0);
    AnchorPane.setRightAnchor(tabPane, 0.0);
    AnchorPane.setLeftAnchor(tabPane, 0.0);
    AnchorPane.setBottomAnchor(tabPane, 0.0);


    AnchorPane.setTopAnchor(buttons, tabPane.getTabMinHeight() + 10);
    AnchorPane.setRightAnchor(buttons, 10.0);



    AnchorPane root = new AnchorPane(tabPane, buttons);

    scene = new Scene(root);

  }

  private void setupButtons() {
    EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        if(e.getSource() == saveButton) {
          StateSaver saver = new StateSaver(window, window.getTicky().tagSet, window.getTicky().prompts);
          if(window.getFile() == null) {
            saver.save(fileSaveDialog());
          }
          else {
            saver.save();
          }
        }
        if(e.getSource() == mainMenuButton) {
          startScene();
        }
        if(e.getSource() == newWindowButton) {

        }
      }
      
    };

    saveButton = new Button("Save");
    saveButton.setOnAction(handler);
    saveButton.setFont(Font.font(Constants.FONT_SIZE_2));
    mainMenuButton = new Button("Exit");
    mainMenuButton.setOnAction(handler);
    mainMenuButton.setFont(Font.font(Constants.FONT_SIZE_2));
    newWindowButton = new Button("New Window");
    newWindowButton.setOnAction(handler);
    newWindowButton.setFont(Font.font(Constants.FONT_SIZE_2));


    buttons = new HBox(10,newWindowButton, mainMenuButton, saveButton);
    buttons.setMaxHeight(saveButton.getPrefHeight());

    //StackPane.setAlignment(buttons, Pos.TOP_RIGHT);
    buttons.setAlignment(Pos.TOP_RIGHT);

  }

  private File fileSaveDialog() {
    FileChooser fc = new FileChooser();
    FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Ticky files", "*.ticky");
    fc.setTitle("Where do you want to save this file?");
    fc.getExtensionFilters().add(extension);
    fc.setSelectedExtensionFilter(extension);
    File file = fc.showSaveDialog(stage);
    window.setFile(file);
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
    tagTabLogic = new AddTagsTab(window);
    tickyTabLogic = new TickyTab(window);   
    resultsTabLogic = new ResultsTab(window);
    setupButtons();

  };

  private void startScene() {
    App.reset();
  }
}
