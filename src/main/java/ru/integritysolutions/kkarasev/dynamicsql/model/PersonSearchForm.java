package ru.integritysolutions.kkarasev.dynamicsql.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.EntityFilter;

@Data
public class PersonSearchForm {
  private String search;
  private List<EntityFilter> filters;
  private List<String> city;
  private List<String> region;
  private Integer minAge;
  private boolean fridayBorn;

  public boolean isFiltered() {
    prepareFilters();
    return !filters.isEmpty();
  }

  public List<EntityFilter> getFilters() {
    prepareFilters();
    return filters;
  }

  private void prepareFilters() {
    if (filters == null) {
      filters = new ArrayList<>();
      if (!StringUtils.isEmpty(search)) {
        filters.add(new EntityFilter(PersonFilterEnum.SEARCH, search));
      }
      if (!CollectionUtils.isEmpty(city)) {
        filters.add(new EntityFilter(PersonFilterEnum.CITY, city));
      }
      if (!CollectionUtils.isEmpty(region)) {
        filters.add(new EntityFilter(PersonFilterEnum.REGION, region));
      }
      if (minAge != null) {
        filters.add(new EntityFilter(PersonFilterEnum.MIN_AGE, "" + minAge));
      }
      if (fridayBorn) {
        filters.add(new EntityFilter(PersonFilterEnum.FRIDAY_BORN));
      }
    }
  }

  public boolean contains(String name, String value) {
    prepareFilters();
    return filters.stream()
        .filter(f -> name.equalsIgnoreCase(f.getName()))
        .anyMatch(f -> f.getValues().contains(value));

  }
}
