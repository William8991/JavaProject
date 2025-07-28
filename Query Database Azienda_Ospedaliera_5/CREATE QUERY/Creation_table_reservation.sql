CREATE TABLE IF NOT EXISTS prenotazioni(
	id_prenotazione INT not null PRIMARY KEY AUTO_INCREMENT,
  codice_fiscale_paziente char(16) not null, 
  codice_fiscale_dipendente char(16) not null,
  data_ora_visita DATETIME not null, -- campo per data e ora
  sede_ospedale varchar (30) not null, 
  tipo_visita varchar(100) not null,
  costo_visita int not null CHECK (costo_visita >= 0),
  stato_pagamento varchar(10) not null CHECK (stato_pagamento IN ('da pagare','pagato')), 
  FOREIGN KEY (codice_fiscale_paziente) REFERENCES paziente(codice_fiscale_paziente),
  FOREIGN KEY (codice_fiscale_dipendente) REFERENCES dipendente(codice_fiscale_dipendente)
);

