package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainScene implements EventHandler<ActionEvent> {
  Stage stage;
  Scene scene;

  ObservableList<String> tagsList;

  ListView<String> tagsViewTicky;

  MultipleSelectionModel<String> selectionModel;
  
  Label tagInputLabel;

  public MainScene(Stage stage) {
    initialize();

    TabPane tabPane = tabs();

    BorderPane borderPane = new BorderPane(tabPane);

    scene = new Scene(borderPane);
  }

  private TabPane tabs() {
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("Tags", new AddTagsTab().tagsTab());
    tab1.setClosable(false);
    Tab tab2 = new Tab("Ticky Page"  , tickyTab());
    tab2.setClosable(false);
    Tab tab3 = new Tab("Results"  , new Label("Show all cars available"));
    tab3.setClosable(false);

    tabPane.getTabs().add(tab1);
    tabPane.getTabs().add(tab2);
    tabPane.getTabs().add(tab3);
    
    return tabPane;
  }

  @Override
  public void handle(ActionEvent event) {

  }

  private HBox tickyTab() {
    tagsList = FXCollections.observableArrayList(App.tickyboxing.tags);
    tagsViewTicky.setItems(tagsList);

    ObservableList<Prompt> promptsList = FXCollections.observableList(App.tickyboxing.prompts);
    ListView<Prompt> promptsPane = new ListView<Prompt>(promptsList);

    HBox root = new HBox(10, tagsViewTicky, promptsPane);

    return root;
  }


  
  private void initialize() {
    tagsList = FXCollections.observableArrayList();
    tagsViewTicky = new ListView<String>(tagsList);      
    };
}
