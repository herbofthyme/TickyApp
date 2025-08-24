package org.example;

import java.util.ArrayList;

public class Prompt {
  public String prompt;
  ArrayList<String> tags;
  int tagCount = 0;
  String commaTags = "";

  Prompt(String string) {
    prompt = string;
    tags = new ArrayList<String>();
  }

  void addTag(String tag) {
    tags.add(tag);
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
}
