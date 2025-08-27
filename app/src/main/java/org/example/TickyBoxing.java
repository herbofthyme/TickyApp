package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TickyBoxing {
  //ArrayList<String> tags;
  TreeSet<String> tagSet;
  ArrayList<Prompt> prompts;
  ObservableList<String> tagsList;

  private static final String NEWLINE = "\n";

  TickyBoxing() {
    tagSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    prompts = new ArrayList<Prompt>();
    tagsList = FXCollections.observableArrayList();
  }

  TickyBoxing(ArrayList<String> tags, ArrayList<Prompt> prompts) {
    tagSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER); 
    tagSet.addAll(tags);
    this.prompts = prompts;
    tagsList = FXCollections.observableArrayList(tagSet);
  }

  void addTags(String string) {

    String[] addedTags = string.split(NEWLINE);

    for (int i=0; i<addedTags.length; i++) {
      tagSet.add(addedTags[i]);
    }
    tagSet.remove("");
    updateTags();
    modified();
  }

  void removeTags(Collection<String> c) {
    tagSet.removeAll(c);
    updateTags();
    modified();
  }

  void updateTags() {
    tagsList = FXCollections.observableArrayList(tagSet);
  }
  

  void addPrompt(String string) {
    prompts.add(new Prompt(string));
    modified();
  }

  void removePrompts(Collection<Prompt> c) {
    prompts.removeAll(c);
    modified();
  }


  private void modified() {
    App.saved = false;
    App.setTitle();
  }

}
