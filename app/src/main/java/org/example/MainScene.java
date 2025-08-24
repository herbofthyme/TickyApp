package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainScene implements EventHandler<ActionEvent> {
  Stage stage;
  Scene scene;
  TickyBoxing tickyboxing;

  ObservableList<String> tagsList;

  Button removeTagsButton, pasteButton, submitButton;
  String input = "";
  ListView<String> tagsViewAdd, tagsViewTicky;

  MultipleSelectionModel<String> selectionModel;
  
  Label tagInputLabel;

  final Clipboard clipBoard;

  public MainScene(Stage stage) {
    clipBoard = Clipboard.getSystemClipboard();
    initialize();

    TabPane tabPane = tabs();

    BorderPane borderPane = new BorderPane(tabPane);

    scene = new Scene(borderPane);
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

  private TabPane tabs() {
    TabPane tabPane = new TabPane();
    Tab tab1 = new Tab("Tags", tagsTab());
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

  private HBox tagsTab() {
    //ObservableList<String> tagsList = FXCollections.observableList(tickyboxing.tags);
    tagsList = FXCollections.observableArrayList(tickyboxing.tags);
    tagsViewAdd.setItems(tagsList);
    tagsViewAdd.setPrefWidth(Window.bounds.getWidth()/2);
    tagsViewAdd.setPrefHeight(Window.bounds.getHeight());
    selectionModel = tagsViewAdd.getSelectionModel();
    selectionModel.setSelectionMode(SelectionMode.MULTIPLE);
    //VBox.setMargin(tagsView, new Insets(15));
    //VBox.setMargin(removeTagsButton, new Insets(15, 0, 30, 0));

    VBox tagsPane = new VBox(tagsViewAdd, removeTagsButton);
    tagsPane.setAlignment(Pos.CENTER);


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

    
    HBox root = new HBox(5, tagsPane, addTags);
    root.setAlignment(Pos.CENTER);
    root.setFillHeight(true);


    return root;
  }

  @Override
  public void handle(ActionEvent event) {
    if(event.getSource() == pasteButton) {
      paste();
    }

    if(event.getSource() == submitButton) {
      tickyboxing.addTags(input);
      tagsList = FXCollections.observableArrayList(tickyboxing.tags);
      tagsViewAdd.setItems(tagsList);
      tagsViewTicky.setItems(tagsList);
      tagInputLabel.setText("");
    }

    if(event.getSource() == removeTagsButton) {
    }
  }

  private HBox tickyTab() {
    tagsList = FXCollections.observableArrayList(tickyboxing.tags);
    tagsViewTicky.setItems(tagsList);

    ObservableList<Prompt> promptsList = FXCollections.observableList(tickyboxing.prompts);
    ListView<Prompt> promptsPane = new ListView<Prompt>(promptsList);

    HBox root = new HBox(10, tagsViewTicky, promptsPane);

    return root;
  }

  private void paste() {
    String richText = clipBoard.getRtf();

      String[] list = richText.split("(?!\\\\'92)((((\\{? *\\\\\\S+ ?(\\S*})?)+\\\\?)+\\n?)|\\})");
      String fullOfEmpties = String.join("", list);
      fullOfEmpties = fullOfEmpties.replaceAll("\\\\'92", "’");
      list = fullOfEmpties.split("\\s\\s+");

      input = String.join("\n", list);

      tagInputLabel.setText(input);
  }

  private void deleteTags() {
    //TODO : finish
    ObservableList<String> toRemove = selectionModel.getSelectedItems();

  }
  
  private void initialize() {
    tickyboxing = new TickyBoxing();
    tagsList = FXCollections.observableArrayList();
    tagsViewAdd = new ListView<String>(tagsList);
    tagsViewTicky = new ListView<String>(tagsList);

    setupButtons();
      
    };
}
