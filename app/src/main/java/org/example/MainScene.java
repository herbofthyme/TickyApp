package org.example;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainScene {
  Stage stage;
  Scene scene;

  Tab tagsTab, tickyTab, resultsTab;

  AddTagsTab tagTabLogic;
  TickyTab tickyTabLogic;
  
  public MainScene(Stage stage) {
    initialize();

    TabPane tabPane = tabs();

    BorderPane borderPane = new BorderPane(tabPane);

    scene = new Scene(borderPane);
  }

  private TabPane tabs() {
    TabPane tabPane = new TabPane();
    tagsTab = new Tab("Tags", tagTabLogic.layout());
    tagsTab.setClosable(false);

    tickyTab = new Tab("Ticky Page", tickyTabLogic.layout());
    tickyTab.setClosable(false);
    tickyTab.setOnSelectionChanged(e -> {
            tickyTabLogic.updateTags();
        });

    resultsTab = new Tab("Results"  , new Label("Show all cars available"));
    resultsTab.setClosable(false);

    tabPane.getTabs().add(tagsTab);
    tabPane.getTabs().add(tickyTab);
    tabPane.getTabs().add(resultsTab);
    
    return tabPane;
  }
 
  private void initialize() {
    tagTabLogic = new AddTagsTab();
    tickyTabLogic = new TickyTab();   
    };
}
