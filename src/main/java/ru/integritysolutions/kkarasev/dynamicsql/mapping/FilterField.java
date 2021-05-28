package ru.integritysolutions.kkarasev.dynamicsql.mapping;

public interface FilterField {

  String getExpression();

  FilterTypeEnum getType();

  String getReadable();

  String name();
}
