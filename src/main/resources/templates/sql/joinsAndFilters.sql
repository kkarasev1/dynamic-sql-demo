  FROM person
  LEFT OUTER JOIN city ON person.city_id = city.id
  LEFT OUTER JOIN region ON region.id = city.region_id
  [# th:if="${searchForm.filtered}"]
    WHERE
    [# th:each="filter,iter: ${searchForm.filters}" ]
      (
      [# th:switch="${filter.type.name()}"]
        [# th:case="'TSV'"]
          to_tsquery('[# th:each="value,iter2: ${filter.values}"][(${value})][# th:unless="${iter2.last}"]|[/][/]:*')
             @@
          [(${filter.expression})]
        [/]
        [# th:case="'EQUAL'"]
          [(${filter.expression})] IN (
          [# th:each="value,iter2: ${filter.values}"]
            '[(${value})]'
            [# th:unless="${iter2.last}"],[/]
          [/])
        [/]
        [# th:case="'MIN'"]
          [(${filter.expression})] >= [(${filter.value})]
        [/]
        [# th:case="'BOOLEAN'"]
          [(${filter.expression})]
        [/]
      [/]
      )
      [# th:unless="${iter.last}"]AND[/]
    [/]
  [/]