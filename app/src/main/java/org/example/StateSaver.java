package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class StateSaver {

  private ArrayList<String> tags;
  private ArrayList<Prompt> prompts;

  StateSaver(TreeSet<String> tagset, ArrayList<Prompt> promptList) {
    if(tagset != null && promptList != null) {
      tags = new ArrayList<String>(tagset);
      this.prompts = promptList;
    }
  }

  StateSaver() {
  }

  public void save(File file) {
    try {
      FileWriter writer = new FileWriter(file);
      
      //OutputStream out = new FileOutputStream(file); 
      //Writer writer = new OutputStreamWriter(out,"UTF-8");
      writer.write(Constants.TAGS_START + "\n");
      for(int i=0; i<tags.size(); i++) {
        writer.write(tags.get(i) + "\n");
      }
      writer.write(Constants.TAGS_END + "\n");

      writer.write(Constants.PROMPT_START + "\n");
      for(int i=0; i<prompts.size(); i++) {
        Prompt prompt = prompts.get(i);
        writer.write(prompt.getText() + "\n");
        writer.write(Constants.PROMPT_TAGS_START + "\n");
        ArrayList<String> tagsList = prompt.getTags();
        for(int j=0; j<tagsList.size(); j++) {
          writer.write(tags.indexOf(tagsList.get(j))+"\n");
        }
        writer.write(Constants.PROMPT_TAGS_END + "\n");
      }
      writer.write(Constants.PROMPT_END + "\n");
      writer.close();

      App.saved = true;
      App.setTitle();
    }
    catch (Exception e){
      System.out.println(e.getMessage());
    }
    
  }


  private void read(File file) throws IOException {
    tags = new ArrayList<String>();
    List<String> lines = Files.readAllLines(file.toPath());


    try {
      tags.addAll(lines.subList(lines.indexOf(Constants.TAGS_START) +1, lines.indexOf(Constants.TAGS_END)-1));
    }
    catch (IllegalArgumentException e) {    }
     
    //try {
      boolean gettingTags = false;
      prompts = new ArrayList<Prompt>();
      Prompt prompt = new Prompt("");
      for (int i=lines.indexOf(Constants.PROMPT_START) +1; i<lines.indexOf(Constants.PROMPT_END); i++) {
        String s = lines.get(i);
        if(s.equals(Constants.PROMPT_TAGS_START)) {
          gettingTags = true;
          continue;
        }
        if(s.equals(Constants.PROMPT_TAGS_END)) {
          gettingTags = false;
          prompts.add(prompt);
          System.out.println("found prompt");
          continue;
        }

        if(!gettingTags) {
          prompt = new Prompt(s);
        }
        else {
          prompt.addTag(tags.get(Integer.parseInt(s)));
        }
      }
    /* }
    catch (Exception e) {
      System.out.println("ERROR " + e.getClass().toString());
      System.out.println(e.getMessage());
    }*/
  }

  public void load(File file)  {
    try {
      read(file);
      App.tickyboxing = new TickyBoxing(tags, prompts);
      App.saved = true;
      App.setTitle();
    }
    catch (IOException e) {    }
  }
}
