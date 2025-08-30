package org.example;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class ResultsTab implements EventHandler<ActionEvent> {
  Window window;

  ListView<Prompt> promptsView;

  Button copyButton;

  Label outputLabel;


  public ResultsTab(Window window) {
    this.window = window;
    setupButtons();
    setupListView();
  }

  
  public Pane layout() {

    HBox root = new HBox(5, promptsPane(), outputPane());
    root.setAlignment(Pos.CENTER);
    root.setFillHeight(true);

    return root;
  }

  private Region promptsPane() {
    //updatePrompts();
    promptsView.setPrefWidth(App.bounds.getWidth()/2);
    HBox.setMargin(promptsView, new Insets(15));
    return promptsView;
  }

  private Pane outputPane() {
    Label copyLabel = new Label("Select Prompt to Copy Tags");
    copyLabel.setFont(Font.font(Constants.FONT_SIZE_1));
    outputLabel = new Label("");
    outputLabel.setWrapText(true);

    ScrollPane scrollPane = new ScrollPane(outputLabel);
    scrollPane.setPrefWidth(App.bounds.getWidth()/2);
    scrollPane.setPrefHeight(App.bounds.getHeight()/2);
    scrollPane.setPannable(true);
    scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    scrollPane.setFitToWidth(true);

    
    //tagInputLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    //tagInputLabel.setPrefSize(Window.bounds.getWidth()/2, Window.bounds.getHeight()/2);

    VBox.setMargin(scrollPane, new Insets(30));

    

    VBox addTags = new VBox(5, copyLabel, scrollPane, copyButton);
    addTags.setAlignment(Pos.CENTER);
    
    //outputLabel.setPrefWidth(scrollPane.getViewportBounds().getWidth());


    return addTags;
  }


  public void updatePrompts() {
    promptsView.setItems(FXCollections.observableArrayList(window.tickyBoxing.getPrompts()));
  }

  private void copy() {
    ClipboardContent content = new ClipboardContent();
    String tags = outputLabel.getText();
    if(tags != null) {
      content.putString(tags);
    }
    App.clipBoard.setContent(content);
  }

  private void setupButtons() {
    copyButton = new Button("Copy to Clipboard");
    copyButton.setFont(Font.font(Constants.FONT_SIZE_2));
    copyButton.setOnAction(this);
  }

  private void setupListView() {
    promptsView = new ListView<Prompt>(FXCollections.observableArrayList());

    promptsView.setCellFactory(new Callback<ListView<Prompt>, ListCell<Prompt>>() {
      @Override public ListCell<Prompt> call(ListView<Prompt> list) {
        return new Prompt.PromptCellFormat();
      }
    });

    promptsView.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Prompt prompt = promptsView.getSelectionModel().getSelectedItem();
        if(prompt != null) {
          outputLabel.setText(prompt.makeCommaString());
        }
      }
    });
  }

  @Override
  public void handle(ActionEvent event) {
    if(event.getSource() == copyButton) {
      copy();
    }

  }

}
