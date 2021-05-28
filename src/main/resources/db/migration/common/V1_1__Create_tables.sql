CREATE TABLE IF NOT EXISTS region (
  id INTEGER PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE IF NOT EXISTS city (
  id SERIAL PRIMARY KEY,
  region_id INTEGER,
  name VARCHAR
);

CREATE TABLE IF NOT EXISTS person(
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  middle_name VARCHAR,
  surname VARCHAR,
  city_id INTEGER,
  birth_date DATE,
  education JSONB,
  full_data TSVECTOR
);