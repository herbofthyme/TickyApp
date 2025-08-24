package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class TickyTab {

  ListView<String> tagsView;

  public TickyTab() {
    tagsView = new ListView<String>(FXCollections.observableArrayList());
  }

  public Pane layout() {
    updateTags();
    tagsView.setItems(FXCollections.observableArrayList(App.tickyboxing.tags));

    ObservableList<Prompt> promptsList = FXCollections.observableList(App.tickyboxing.prompts);
    ListView<Prompt> promptsPane = new ListView<Prompt>(promptsList);

    HBox root = new HBox(10, tagsView, promptsPane);

    return root;
  }

  public void updateTags() {
    tagsView.setItems(FXCollections.observableArrayList(App.tickyboxing.tags));
  }
  
}
