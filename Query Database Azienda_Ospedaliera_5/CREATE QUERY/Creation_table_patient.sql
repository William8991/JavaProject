CREATE TABLE IF NOT EXISTS paziente(
	-- id_paziente INT not null PRIMARY KEY AUTO_INCREMENT,  
  codice_fiscale char(16) not null PRIMARY KEY, 
  nome_paziente varchar(30) not null,
	cognome_paziente varchar(30) not null,
  cartella_clinica varchar(255) DEFAULT 'Nessuna patologia nota',
  FOREIGN KEY (codice_fiscale_paziente) REFERENCES persona(codice_fiscale)
);
