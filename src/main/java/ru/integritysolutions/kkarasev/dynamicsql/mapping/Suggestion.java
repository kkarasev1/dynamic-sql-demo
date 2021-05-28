package ru.integritysolutions.kkarasev.dynamicsql.mapping;

import lombok.Data;

@Data
public class Suggestion {

  private String name;
  private String value;
  private Long count;
  private String readable;

  public void setName(String name) {
    this.name = name.toLowerCase();
  }
}
