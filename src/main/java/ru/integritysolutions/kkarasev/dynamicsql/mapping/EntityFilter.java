package ru.integritysolutions.kkarasev.dynamicsql.mapping;

import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EntityFilter {

  private final FilterTypeEnum type;

  private Collection<String> values;

  private final String expression;

  private String name;

  public EntityFilter(FilterField filterField, String value) {
    this.type = filterField.getType();
    this.expression = filterField.getExpression();
    this.values = Collections.singleton(value);
    this.name = filterField.name();
  }

  public EntityFilter(FilterField filterField, Collection<String> values) {
    this.type = filterField.getType();
    this.expression = filterField.getExpression();
    this.values = values;
    this.name = filterField.name();
  }


  public EntityFilter(FilterField filterField) {
    this.type = filterField.getType();
    this.expression = filterField.getExpression();
  }

  public String getValue() {
    return values.iterator().next();
  }
}
