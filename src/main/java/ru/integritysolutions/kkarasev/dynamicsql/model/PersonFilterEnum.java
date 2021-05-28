package ru.integritysolutions.kkarasev.dynamicsql.model;

import static ru.integritysolutions.kkarasev.dynamicsql.mapping.FilterTypeEnum.BOOLEAN;
import static ru.integritysolutions.kkarasev.dynamicsql.mapping.FilterTypeEnum.EQUAL;
import static ru.integritysolutions.kkarasev.dynamicsql.mapping.FilterTypeEnum.MIN;
import static ru.integritysolutions.kkarasev.dynamicsql.mapping.FilterTypeEnum.TSV;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.FilterField;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.FilterTypeEnum;

@RequiredArgsConstructor
public enum PersonFilterEnum implements FilterField {
  SEARCH(TSV, "full_data"),
  CITY(EQUAL, "city.id", "city.name"),
  REGION(EQUAL, "region.id", "region.name"),
  MIN_AGE(MIN, "EXTRACT(YEAR FROM justify_interval(now() - birth_date))"),
  FRIDAY_BORN(BOOLEAN, "EXTRACT(DOW FROM birth_date) = 5");

  private PersonFilterEnum(FilterTypeEnum type, String expression) {
    this.type = type;
    this.expression = expression;
    this.readable = null;
  }

  @Getter
  private final FilterTypeEnum type;

  @Getter
  private final String expression;

  @Getter
  private final String readable;
}
