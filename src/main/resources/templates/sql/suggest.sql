[# th:each="field,iterator: ${fields}"]
  (SELECT '[(${field.name})]' AS name,
         [(${field.expression})]::TEXT AS value,
         [(${field.readable})] AS readable,
         count(person.id) AS count
      [# th:insert="joinsAndFilters"] [/]
         GROUP BY [(${field.expression})])
  [# th:unless="${iterator.last}"] UNION ALL [/]
[/]