package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class AddTagsTab implements EventHandler<ActionEvent> {

  //ObservableList<String> tagsList;
  ListView<String> tagsView;

  Button removeTagsButton, pasteButton, submitButton;
  String input = "";

  Label tagInputLabel;


  MultipleSelectionModel<String> selectionModel;

  public AddTagsTab() {
    tagsView = new ListView<String>(FXCollections.observableArrayList());
    setupButtons();
  }

  
  public Pane layout() {

    HBox root = new HBox(5, tagsPane(), inputPane());
    root.setAlignment(Pos.CENTER);
    root.setFillHeight(true);

    return root;
  }


  //The left side of the screen, where the tabs are shown
  private Pane tagsPane() {
    updateTags();
    tagsView.setPrefWidth(Window.bounds.getWidth()/2);
    tagsView.setPrefHeight(Window.bounds.getHeight());
    selectionModel = tagsView.getSelectionModel();
    selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    //VBox.setMargin(tagsView, new Insets(15));
    //VBox.setMargin(removeTagsButton, new Insets(15, 0, 30, 0));

    VBox tagsPane = new VBox(tagsView, removeTagsButton);
    tagsPane.setAlignment(Pos.CENTER);

    return tagsPane;
  }

  //the right side of the screen, where user can paste tags
  private Pane inputPane() {
    Label pasteLabel = new Label("Paste Tags Here");
    pasteLabel.setFont(Font.font(Constants.FONT_SIZE_1));
    Label details = new Label("Duplicates will be ignored");
    details.setFont(Font.font(Constants.FONT_SIZE_3));

    tagInputLabel = new Label(input);

    ScrollPane scrollPane = new ScrollPane(tagInputLabel);
    scrollPane.setPrefWidth(Window.bounds.getWidth()/2);
    scrollPane.setPrefHeight(Window.bounds.getHeight()/2);
    scrollPane.setPannable(true);
    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

    //tagInputLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    //tagInputLabel.setPrefSize(Window.bounds.getWidth()/2, Window.bounds.getHeight()/2);

    VBox.setMargin(scrollPane, new Insets(30));

    HBox buttons = new HBox(5, pasteButton, submitButton);
    buttons.setAlignment(Pos.CENTER);

    VBox addTags = new VBox(5, pasteLabel, details, scrollPane, buttons);
    addTags.setAlignment(Pos.CENTER);

    return addTags;
  }


  private void deleteTags() {
    //TODO : finish
    ObservableList<String> toRemove = selectionModel.getSelectedItems();
    App.tickyboxing.removeTags(toRemove);
  }

  @Override
  public void handle(ActionEvent event) {
    if(event.getSource() == pasteButton) {
      paste();
    }

    if(event.getSource() == submitButton) {
      App.tickyboxing.addTags(input);
      //tagsList = FXCollections.observableArrayList(App.tickyboxing.tags);
      //tagsView.setItems(tagsList);
      updateTags();
      tagInputLabel.setText("");
    }

    if(event.getSource() == removeTagsButton) {
      deleteTags();
      updateTags();
    }
  }
  
  private void paste() {
    String richText = App.clipBoard.getRtf();

      String[] list = richText.split("(?!\\\\'92)((((\\{? *\\\\\\S+ ?(\\S*})?)+\\\\?)+\\n?)|\\})");
      String fullOfEmpties = String.join("", list);
      fullOfEmpties = fullOfEmpties.replaceAll("\\\\'92", "’");
      list = fullOfEmpties.split("\\s\\s+|\\\\\\s");

      input = String.join("\n", list);

      tagInputLabel.setText(input);
  }

  public void updateTags() {
    tagsView.setItems(App.tickyboxing.tagsList);
  }

  private void setupButtons() {
    removeTagsButton = new Button("Remove");
    removeTagsButton.setFont(Font.font(Constants.FONT_SIZE_2));
    removeTagsButton.setOnAction(this);

    pasteButton = new Button("Paste from Clipboard");
    pasteButton.setWrapText(true);
    pasteButton.setFont(Font.font(Constants.FONT_SIZE_2));
    pasteButton.setOnAction(this);

    submitButton = new Button("Submit");
    submitButton.setFont(Font.font(Constants.FONT_SIZE_2));
    submitButton.setOnAction(this);
  }


}
