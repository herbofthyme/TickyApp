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

  private static final String COMMA = ",";
  private static final String TAB = "\t";
  private static final String NEWLINE = "\n";

  TickyBoxing() {
    tagSet = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    prompts = new ArrayList<Prompt>();
    tagsList = FXCollections.observableArrayList();
  }

  void addTags(String string) {
    String delimiter;

    if(string.contains(COMMA)) delimiter = COMMA;
    else if(string.contains(TAB)) delimiter = TAB;
    else delimiter = NEWLINE;

    String[] addedTags = string.split(delimiter);

    for (int i=0; i<addedTags.length; i++) {
      tagSet.add(addedTags[i]);
    }
    tagSet.remove("");
    updateTags();
  }

  void removeTags(Collection<String> c) {
    tagSet.removeAll(c);
    updateTags();
  }

  void updateTags() {
    tagsList = FXCollections.observableArrayList(tagSet);
  }
  

  void addPrompt(String string) {
    prompts.add(new Prompt(string));
  }

  void removePrompts(Collection<Prompt> c) {
    prompts.removeAll(c);
  }

}
