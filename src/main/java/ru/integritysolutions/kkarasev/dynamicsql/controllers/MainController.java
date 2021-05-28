package ru.integritysolutions.kkarasev.dynamicsql.controllers;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.integritysolutions.kkarasev.dynamicsql.mapping.Suggestion;
import ru.integritysolutions.kkarasev.dynamicsql.model.PersonSearchForm;
import ru.integritysolutions.kkarasev.dynamicsql.services.PersonService;

@Controller
public class MainController {

  @Autowired
  PersonService service;

  @GetMapping("/")
  public ModelAndView getMainPage(Pageable pageable, PersonSearchForm searchForm) {
    if (pageable == null) {
      pageable = PageRequest.of(0, 10);
    }
    if (searchForm == null) {
      searchForm = new PersonSearchForm();
    }
    return getModelAndView(pageable, searchForm);
  }

  private ModelAndView getModelAndView(Pageable pageable, PersonSearchForm searchForm) {
    List<Suggestion> suggestions = service.getSuggestions();
    return new ModelAndView("mainPage",
        Map.of("page", service.getPersons(pageable, searchForm),
            "pageable", pageable,
            "searchForm", searchForm,
            "suggestions", suggestions)
    );
  }
}
