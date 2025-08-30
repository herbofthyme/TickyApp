package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

public class TickyTab {
  Window window;

  ListView<String> tagsView; 
  ListView<Prompt> promptsView;
  MultipleSelectionModel<Prompt> promptSelectionModel;
  MultipleSelectionModel<String> tagSelectionModel;

  private static final String[] ILLEGAL_PROMPTS = {"", " ", Constants.TAGS_END, Constants.TAGS_START, Constants.PROMPT_END, Constants.PROMPT_START, Constants.PROMPT_TAGS_END, Constants.PROMPT_TAGS_START};


  Button submitButton, deleteButton;

  TextField promptInputArea;


  public TickyTab(Window window) {
    this.window = window;
    textFieldSetup();
    setupButtons();
    listViewSetup();
  }

  public Pane layout() {
    
    Region tagsPane = tagsPane();
    Region promptsPane = promptsPane();

    HBox root = new HBox(5, tagsPane, promptsPane);
    root.setAlignment(Pos.CENTER);

    return root;
  }

  private Region tagsPane() {
    //updateLists();
    tagsView.setPrefWidth(App.bounds.getWidth()/2);
    tagsView.setPrefHeight(App.bounds.getHeight());
    VBox.setMargin(tagsView, new Insets(15));

    return new VBox(tagsView);
  }

  private Region promptsPane() {
    ObservableList<Prompt> promptsList = FXCollections.observableArrayList();
    promptsView.setItems(promptsList);

    Label promptLabel = new Label("Add prompt names below");
    promptLabel.setFont(Font.font(Constants.FONT_SIZE_1));
    Label detailsLabel = new Label("When a prompt is selected: double-click, press enter, or press space to add tags to that prompt.\nYou can also double-click a prompt to add the selected tag.");
    detailsLabel.setFont(Font.font(Constants.FONT_SIZE_3));
    detailsLabel.setWrapText(true);
    detailsLabel.setTextAlignment(TextAlignment.CENTER);

    VBox.setMargin(promptInputArea, new Insets(15, 30, 30, 30));
    VBox.setMargin(promptsView, new Insets(30, 30, 15, 30));
    promptsView.setPrefWidth(App.bounds.getWidth()/2);
    promptInputArea.setPrefWidth(App.bounds.getWidth()/2);


    HBox buttons = new HBox(5, submitButton, deleteButton);
    buttons.setAlignment(Pos.CENTER);

    VBox promptsPane = new VBox(10, promptLabel, detailsLabel, promptsView, promptInputArea, buttons);
    promptsPane.setAlignment(Pos.CENTER);

    detailsLabel.maxWidthProperty().bind(promptsView.widthProperty());

    return promptsPane;
  }

  public void updateLists() {
    tagsView.setItems(window.tickyBoxing.getObservableList());
    promptsView.setItems(FXCollections.observableArrayList(window.tickyBoxing.getPrompts()));
  }
  
  private void addPrompt() {
    String input = promptInputArea.getText();
    if(!contains(ILLEGAL_PROMPTS, input)) {
      window.tickyBoxing.addPrompt(input);
      updateLists();
    }
    promptInputArea.clear();
  }

  private boolean contains(String[] array, String s) {
    for(int i=0; i<array.length; i++){
      if(array[i].equals(s)) {return true;}
    }
    return false;
  }

  private void deletePrompt() {
    ObservableList<Prompt> toRemove = promptSelectionModel.getSelectedItems();
    window.tickyBoxing.removePrompts(toRemove);
  }

  private void setupButtons() {  
    deleteButton = new Button("Remove");
    deleteButton.setFont(Font.font(Constants.FONT_SIZE_2));
    deleteButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        deletePrompt();
        updateLists();
      }
    });

    submitButton = new Button("Submit");
    submitButton.setFont(Font.font(Constants.FONT_SIZE_2));
    submitButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        addPrompt();
      }
    });
  }

  private void listViewSetup() {
    promptsView = new ListView<Prompt>(FXCollections.observableArrayList());
    tagsView = new ListView<String>(FXCollections.observableArrayList());

    KeyEventHandler keyEvent = new KeyEventHandler();
    tagsView.setOnKeyPressed(keyEvent);


    promptSelectionModel = promptsView.getSelectionModel();
    tagSelectionModel = tagsView.getSelectionModel();

    promptsView.setCellFactory(new Callback<ListView<Prompt>, ListCell<Prompt>>() {
      @Override public ListCell<Prompt> call(ListView<Prompt> list) {
        return new Prompt.PromptCellFormat();
      }
    });

    tagsView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
      @Override public ListCell<String> call(ListView<String> list) {
        return new TagCellFormat();
      }
    });

    promptsView.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        Prompt prompt = promptsView.getSelectionModel().getSelectedItem();
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
          String tag = tagSelectionModel.getSelectedItem();
          addTagToPrompt(tag, prompt);
        }
        tagsView.refresh();
      }
    });

    tagsView.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        String tag = tagsView.getSelectionModel().getSelectedItem();
        if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount()==2){
          Prompt prompt = promptSelectionModel.getSelectedItem();
          addTagToPrompt(tag, prompt);
        }
      }
    });
  }

  private void textFieldSetup() {
    promptInputArea = new TextField();
    promptInputArea.setPromptText("Type prompt name/description here");
    promptInputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
          addPrompt();
        }
      }

    });
  }

  private void addTagToPrompt(String tag, Prompt prompt) {
    if(prompt != null && tag!= null) {
      if(!prompt.getTags().contains(tag)) {
        prompt.addTag(tag);
      }
      else {
        prompt.removeTag(tag);
      }
      tagsView.refresh();
    }
  }

  private class TagCellFormat extends ListCell<String> {
    public TagCellFormat() {    }
       
    @Override protected void updateItem(String item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
        setText(item);
        
        Prompt prompt = promptSelectionModel.getSelectedItem();
                
        if (prompt != null){
          if(prompt.getTags().contains(item)) {
            setStyle("-fx-background-color: rgb(149, 198, 70); -fx-text-fill: black");
            if(isSelected()) {
              setStyle( "-fx-background-color: rgb(12, 141, 120); -fx-text-fill: white");
            }
          }
          else {
            setStyle(null);
          }
        }
        
    }
  }

  private class KeyEventHandler implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent event) {
      if(event.getEventType() == KeyEvent.KEY_PRESSED) {
        if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.SPACE) {
          Prompt prompt = promptSelectionModel.getSelectedItem();
          String tag = tagSelectionModel.getSelectedItem();
          if(prompt != null) {
            if(!prompt.getTags().contains(tag)) {
              prompt.addTag(tag);
            }
            else {
              prompt.removeTag(tag);
            }
            tagsView.refresh();
          }

        }
      }
    
    }
  }
}

