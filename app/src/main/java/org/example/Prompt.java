package org.example;

import java.util.ArrayList;

import javafx.scene.control.ListCell;

public class Prompt {
  private String text;
  private ArrayList<String> tags;
  private int tagCount = 0;
  private String commaTags = "";

  Prompt(String string) {
    text = string;
    tags = new ArrayList<String>();
  }

  public String getText() { return text; }

  public void addTag(String tag) {
    tags.add(tag);
    tagCount = tags.size();
  }

  public void removeTag(String tag) {
    tags.remove(tag);
    tagCount = tags.size();
  }

  public String updateTags() {
    StringBuilder sb = new StringBuilder();
    for(int i=0; i<tagCount; i++) {
      sb.append(",");
      sb.append(tags.get(i));
    }
    sb.deleteCharAt(0);

    commaTags = sb.toString();
    return commaTags;
  }

  public String makeCommaString() {
    return String.join(", ", tags);
  }

  public void print() {
    System.out.println(makeCommaString());
  }

    public static class PromptCellFormat extends ListCell<Prompt> {
      public PromptCellFormat() {    }
        
      @Override protected void updateItem(Prompt item, boolean empty) {
          // calling super here is very important - don't skip this!
          super.updateItem(item, empty);
            
          setText(item == null ? "" : item.getText());
      }
  }

  public void changeName(String s) {
    text = s;
  }
  
  public ArrayList<String> getTags() {return tags;}
  public int getCount() {return tagCount;}
  public String getCommaTags() {return commaTags;}
}
