CREATE TABLE IF NOT EXISTS utente(
  codice_fiscale char(16) not null PRIMARY KEY,
  username varchar(30) not null UNIQUE,
  password_utente varchar(16) not null CHECK (LENGTH(password_utente) >= 8), 
  FOREIGN KEY (codice_fiscale) REFERENCES persona(codice_fiscale)
);
