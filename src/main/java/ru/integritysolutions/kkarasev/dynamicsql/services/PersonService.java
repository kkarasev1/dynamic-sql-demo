package ru.integritysolutions.kkarasev.dynamicsql.services;

import static java.util.Collections.EMPTY_LIST;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.FilterField;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.FilterTypeEnum;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.NamedRowMapper;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.Suggestion;
import ru.integritysolutions.kkarasev.dynamicsql.model.PersonDto;
import ru.integritysolutions.kkarasev.dynamicsql.model.PersonField;
import ru.integritysolutions.kkarasev.dynamicsql.model.PersonFilterEnum;
import ru.integritysolutions.kkarasev.dynamicsql.model.PersonSearchForm;

@Service
@RequiredArgsConstructor
public class PersonService {

  private static final PersonSearchForm TOTAL = new PersonSearchForm();

  private final QueryService queryService;
  private final JdbcTemplate jdbcTemplate;


  private final RowMapper<PersonDto> personRowMapper = new NamedRowMapper<>(
      Arrays.asList(PersonField.values()), PersonDto.class);

  private final List<FilterField> suggestFields =
      Arrays.stream(PersonFilterEnum.values())
      .filter(f -> f.getType() == FilterTypeEnum.EQUAL)
      .collect(Collectors.toList());
  private final RowMapper<Suggestion> suggestionRowMapper =
      new NamedRowMapper<>(Suggestion.class);


  public Page<PersonDto> getPersons(Pageable pageable, PersonSearchForm searchForm) {
    long count = count(searchForm);
    Page<PersonDto> page;
    if (count > 0) {
      List<PersonDto> persons = getList(pageable, searchForm);
      page = new PageImpl<>(persons, pageable, count);
    } else {
      page = new PageImpl<>(EMPTY_LIST, pageable, 0L);
    }
    return page;
  }

  @SneakyThrows
  private List<PersonDto> getList(Pageable pageable, PersonSearchForm searchForm) {
    String query = queryService.getQuery("list",
        Map.of("searchForm", searchForm,
            "fields", PersonField.values(),
            "pageable", pageable)
    );
    return jdbcTemplate.query(query, personRowMapper);
  }

  @SneakyThrows
  private Long count(PersonSearchForm searchForm) {
    String query = queryService.getQuery("count",
        Map.of("searchForm", searchForm)
    );
    return jdbcTemplate.queryForObject(query, Long.class);
  }

  public List<Suggestion> getSuggestions() {
    String query = queryService.getQuery("suggest",
        Map.of("searchForm", TOTAL,
            "fields", suggestFields)
    );
    return jdbcTemplate.query(query, suggestionRowMapper);
  }

}
