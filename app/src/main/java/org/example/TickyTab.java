package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class TickyTab implements EventHandler<ActionEvent> {

  ListView<String> tagsView; 
  ListView<Prompt> promptsView;
  MultipleSelectionModel<Prompt> promptSelectionModel;

  Button submitButton, deleteButton;

  TextField promptInputArea;


  public TickyTab() {
    promptInputArea = new TextField();
    promptInputArea.setPromptText("Type prompt name/description here");
    setupButtons();
    listViewSetup();
  }


  @Override
  public void handle(ActionEvent event) {
    if(event.getSource() == submitButton) {
      addPrompt();
    }
    
    if(event.getSource() == deleteButton) {
      deletePrompt();
      updateLists();
    }
  }

  public Pane layout() {
    
    Region tagsPane = tagsPane();
    Region promptsPane = promptsPane();

    HBox root = new HBox(5, tagsPane, promptsPane);
    root.setAlignment(Pos.CENTER);

    return root;
  }

  private Region tagsPane() {
    updateLists();
    tagsView.setItems(FXCollections.observableArrayList(App.tickyboxing.tagSet));
    tagsView.setPrefWidth(Window.bounds.getWidth());
    HBox.setMargin(tagsView, new Insets(15));


    return tagsView;
  }

  private Region promptsPane() {
    ObservableList<Prompt> promptsList = FXCollections.observableList(App.tickyboxing.prompts);
    promptsView.setItems(promptsList);

    VBox.setMargin(promptInputArea, new Insets(15, 15, 30, 15));
    VBox.setMargin(promptsView, new Insets(15));

    HBox buttons = new HBox(5, submitButton, deleteButton);
    buttons.setAlignment(Pos.CENTER);

    VBox promptsPane = new VBox(10, promptsView, promptInputArea, buttons);
    promptsPane.setPrefWidth(Window.bounds.getWidth());
    promptsPane.setAlignment(Pos.CENTER);

    return promptsPane;
  }

  public void updateLists() {
    tagsView.setItems(App.tickyboxing.tagsList);
    promptsView.setItems(FXCollections.observableArrayList(App.tickyboxing.prompts));
  }
  
  private void addPrompt() {
    String input = promptInputArea.getText();
    App.tickyboxing.addPrompt(input);
    updateLists();
  }


  private void deletePrompt() {
    //TODO : finish
    ObservableList<Prompt> toRemove = promptSelectionModel.getSelectedItems();
    App.tickyboxing.removePrompts(toRemove);
  }

  private void setupButtons() {
    deleteButton = new Button("Remove");
    deleteButton.setFont(Font.font(Constants.FONT_SIZE_2));
    deleteButton.setOnAction(this);

    submitButton = new Button("Submit");
    submitButton.setFont(Font.font(Constants.FONT_SIZE_2));
    submitButton.setOnAction(this);
  }

  private void listViewSetup() {
    promptsView = new ListView<Prompt>(FXCollections.observableArrayList());
    promptsView.setCellFactory(new Callback<ListView<Prompt>, ListCell<Prompt>>() {
      @Override public ListCell<Prompt> call(ListView<Prompt> list) {
        return new PromptCellFormat();
      }
    });
    promptsView.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        System.out.println("clicked on " + promptsView.getSelectionModel().getSelectedItem().getText());
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
          System.out.println("Double clicked");
        
        }
      }
    });

    promptSelectionModel = promptsView.getSelectionModel();

    tagsView = new ListView<String>(FXCollections.observableArrayList());
    tagsView.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        System.out.println("clicked on " + tagsView.getSelectionModel().getSelectedItem());
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
          System.out.println("Double clicked");
        
        }
      }
    });
  }

  private class PromptCellFormat extends ListCell<Prompt> {
    public PromptCellFormat() {    }
       
    @Override protected void updateItem(Prompt item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
          
        setText(item == null ? "" : item.getText());
    }
  }
}

