INSERT INTO region(id, name) VALUES
  (77, 'Москва'),
  (50, 'Московская область'),
  (40, 'Калужская область'),
  (69, 'Тверская область');

INSERT INTO city(region_id, name) VALUES
  (77, 'Москва'),
  (77, 'Щербинка'),
  (77, 'Московский'),
  (77, 'Троицк'),
  (50, 'Балашиха'),
  (50, 'Подольск'),
  (50, 'Мытищи'),
  (50, 'Щёлково'),
  (40, 'Калуга'),
  (40, 'Обнинск'),
  (40, 'Боровск'),
  (40, 'Малоярославец'),
  (69, 'Тверь'),
  (69, 'Удомля'),
  (69, 'Торжок'),
  (69, 'Осташков');


DO $$
DECLARE
  names varchar[];
  middlenames varchar[];
  surnames varchar[];
  universities varchar[];
  i INTEGER;
  name varchar;
  middlename varchar;
  surname varchar;
  birthday DATE;
  city_id INTEGER;
  region_id INTEGER;
  city_name varchar;
  region_name varchar;
  education jsonb;
  fields varchar[];
  tsv tsvector;
 BEGIN
   names = '{Алексей, Борис, Виктор, Геннадий, Егор, Жорес, Зиновий, Игорь, Кирилл, Леонид, Михаил, Николай}';
   middlenames = '{Алексеевич, Борисович, Викторович, Геннадьевич, Егорович, Жоресович, Зиновьевич, Игоревич, Кириллович, Леонидович, Михайлович, Николаевич}';
   surnames = '{Алексеев, Борисов, Викторов, Геннадьев, Егоров, Жуков, Зиновьев, Иванов, Кириллов, Леонидов, Михайлов, Николаев, Осипов, Попов, Рябов}';
   universities = '{"Московский государственный университет им. М.В.Ломоносова", "Тверской государственный университет", "Московский государственный технологический университет СТАНКИН"}';

   for i in 1..150 LOOP
   	  name = names[trunc(1 + RANDOM() * 12)];
	  middlename = middlenames[trunc(1 + RANDOM() * 12)];
	  surname = surnames[trunc(1 + RANDOM() * 15)];

   	  birthday = DATE '1980-01-01' + CAST (trunc(RANDOM() * 6000)||' day' AS INTERVAL);
	  city_id = trunc(1 + RANDOM() * 16);
	  SELECT city.name, city.region_id FROM city WHERE id = city_id INTO city_name, region_id;
      SELECT region.name FROM region WHERE id = region_id INTO region_name;

 	  IF random() < 0.3 THEN
	  	education = jsonb_build_object('date', birthday + INTERVAL '20 year' + CAST (trunc(RANDOM() * 600)||' day' AS INTERVAL),
									  'university', universities[trunc(1 + RANDOM() * 3 )]
									  );
	  ELSE
	  	education = null;
	  END IF;

	  fields = ARRAY[name, middlename, surname, birthday::varchar, city_name, region_name,
					 coalesce(education->>'date', ''),
					 coalesce(education->>'university', '')
					];
	  tsv = to_tsvector(array_to_string(fields, ' '));
      RAISE NOTICE '% %',
		  tsv,
		  to_tsquery('''Моск'':*') @@ tsv;

    INSERT INTO person(name, middle_name, surname, city_id, birth_date, education, full_data)
      VALUES (name, middlename, surname, city_id, birthday, education, tsv);

  END LOOP;

 END $$;
