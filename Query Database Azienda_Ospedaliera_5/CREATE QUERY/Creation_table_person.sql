CREATE TABLE IF NOT EXISTS persona(
	codice_fiscale char(16) not null PRIMARY KEY,
  nome varchar(30) not null,
	cognome varchar(30) not null,
  data_nascita DATE not null,
  luogo_nascita varchar(30) not null,
  residenza varchar(30) not null,
  email varchar(30) not null,
  telefono varchar(10) not null  
);
