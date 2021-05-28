package ru.integritysolutions.kkarasev.dynamicsql.services;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.languages.Dialect;
import java.util.Locale;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
@Slf4j
public class QueryService {

  private final TemplateEngine templateEngine;
  private final SqlFormatter.Formatter formatter;

  public QueryService() {
    templateEngine = new TemplateEngine();
    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
    resolver.setPrefix("templates/sql/");
    resolver.setSuffix(".sql");
    resolver.setTemplateMode(TemplateMode.TEXT);
    templateEngine.setTemplateResolver(resolver);

    formatter = SqlFormatter
        .of(Dialect.PostgreSql)
        .extend(cfg -> cfg.plusOperators("||", "@@"));
  }

  public String getQuery(String key, Map<String, Object> contextMap) {
    IContext context = new Context(Locale.getDefault(), contextMap);
    String query = templateEngine.process(key, context);
    String formatted = formatter.format(query);
    log.info(formatted);
    return formatted;
  }
}
