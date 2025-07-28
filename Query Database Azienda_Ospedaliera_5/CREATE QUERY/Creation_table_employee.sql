CREATE TABLE IF NOT EXISTS dipendente(
	-- id_dipendente INT not null PRIMARY KEY AUTO_INCREMENT,  
  codice_fiscale char(16) not null PRIMARY KEY, 
  nome_medico varchar(30) not null,
	cognome_medico varchar(30) not null,
  email_ospedaliera varchar(30) not null,
  stipendio INT not null,
  sede_ospedale varchar (30) not null,
  professione_medico varchar(30) not null,
  data_termine_contratto DATE not null,
  FOREIGN KEY (codice_fiscale_dipendente) REFERENCES persona(codice_fiscale)
);
