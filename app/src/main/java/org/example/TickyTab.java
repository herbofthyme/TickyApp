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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class TickyTab {

  ListView<String> tagsView; 
  ListView<Prompt> promptsView;
  MultipleSelectionModel<Prompt> promptSelectionModel;
  MultipleSelectionModel<String> tagSelectionModel;


  Button submitButton, deleteButton;

  TextField promptInputArea;


  public TickyTab() {
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
    promptInputArea.clear();
  }

  private void deletePrompt() {
    //TODO : finish
    ObservableList<Prompt> toRemove = promptSelectionModel.getSelectedItems();
    App.tickyboxing.removePrompts(toRemove);
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
        return new PromptCellFormat();
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
      if(!prompt.tags.contains(tag)) {
        prompt.addTag(tag);
        prompt.print();
      }
      else {
        prompt.removeTag(tag);
        prompt.print();
      }
      tagsView.refresh();
    }
  }

  private class PromptCellFormat extends ListCell<Prompt> {
    public PromptCellFormat() {    }
       
    @Override protected void updateItem(Prompt item, boolean empty) {
        // calling super here is very important - don't skip this!
        super.updateItem(item, empty);
          
        setText(item == null ? "" : item.getText());
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
          if(prompt.tags.contains(item)) {
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
            if(!prompt.tags.contains(tag)) {
              prompt.addTag(tag);
              prompt.print();
            }
            else {
              prompt.removeTag(tag);
              prompt.print();
            }
            tagsView.refresh();
          }

        }
      }
    
    }
  }
}

