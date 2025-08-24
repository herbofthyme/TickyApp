package org.example;

import java.util.ArrayList;
import java.util.TreeSet;

public class TickyBoxing {
  //ArrayList<String> tags;
  TreeSet<String> tags;
  ArrayList<Prompt> prompts;

  private static final String COMMA = ",";
  private static final String TAB = "\t";
  private static final String NEWLINE = "\n";

  TickyBoxing() {
    tags = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
    prompts = new ArrayList<Prompt>();
  }


  void addTags(String string) {
    String delimiter;

    if(string.contains(COMMA)) delimiter = COMMA;
    else if(string.contains(TAB)) delimiter = TAB;
    else delimiter = NEWLINE;

    String[] addedTags = string.split(delimiter);

    for (int i=0; i<addedTags.length; i++) {
      tags.add(addedTags[i]);
    }
    System.out.println(tags.size());
  }
  

  void addPrompt(String string) {
    prompts.add(new Prompt(string));
  }

}
