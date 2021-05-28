package ru.integritysolutions.kkarasev.dynamicsql.model;

import lombok.Getter;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.Field;

@Getter
public enum PersonField implements Field {
  NAME("name"),
  MIDDLE_NAME("person.middle_name", "middleName"),
  SURNAME("surname"),
  BIRTH_DATE("person.birth_date", "birthDate"),
  CITY("city.name", "city"),
  REGION("region.name", "region"),
  UNIVERSITY("person.education->>'university'", "university"),
  GRADUATE_DATE("to_date(education->>'date','YYYY-MM-ddTHH:MI:SS')",
      "graduateDate");

  private final String expression;
  private final String beanField;

  PersonField(String field) {
    this.expression = "person." + field;
    this.beanField = field;
  }

  PersonField(String expression, String beanField) {
    this.expression = expression;
    this.beanField = beanField;
  }

  public String getBeanField() {
    return beanField;
  }
}
