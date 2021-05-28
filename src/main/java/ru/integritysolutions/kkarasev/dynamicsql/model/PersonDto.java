package ru.integritysolutions.kkarasev.dynamicsql.model;

import java.util.Date;
import lombok.Data;

@Data
public class PersonDto {
  private String name;
  private String middleName;
  private String surname;
  private Date birthDate;
  private String region;
  private String city;
  private String university;
  private Date graduateDate;

}
