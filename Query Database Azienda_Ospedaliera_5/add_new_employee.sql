/* INSERIMENTO NUOVO DIPENDENTE */
INSERT INTO persona(codice_fiscale,nome,cognome,data_nascita,luogo_nascita,residenza,email,telefono)
VALUES
("BRSGRL88D22H501P","Giorgio","Borselli","1988-04-22","Milano","Torino","giorgio.borselli@hospital.it","3425678932");

INSERT INTO utente
VALUES
("BRSGRL88D22H501P","BRSGRL88D22H501P","passgiorgio");

/*la mail ospedaliera gli viene fornita dal sistema*/
INSERT INTO dipendente(codice_fiscale, nome_medico, cognome_medico, stipendio, sede_ospedale, professione_medico, data_termine_contratto)
VALUES 
('BRSGRL88D22H501P', 'Giorgio', 'Borselli', 3200, 'Papardo', 'Oculista', '2026-01-01');

CREATE TABLE tabella_BRSGRL88D22H501P(
  data_disp DATE not null PRIMARY KEY,
  ora8 varchar(1) not null DEFAULT '' CHECK (ora8 IN ('', 'x')),
  ora9 varchar(1) not null DEFAULT '' CHECK (ora9 IN ('', 'x')),
  ora10 varchar(1) not null DEFAULT '' CHECK (ora10 IN ('', 'x')),
  ora11 varchar(1) not null DEFAULT '' CHECK (ora11 IN ('', 'x')),
  ora12 varchar(1) not null DEFAULT '' CHECK (ora12 IN ('', 'x')),
  ora15 varchar(1) not null DEFAULT '' CHECK (ora15 IN ('', 'x')),
  ora16 varchar(1) not null DEFAULT '' CHECK (ora16 IN ('', 'x')),
  ora17 varchar(1) not null DEFAULT '' CHECK (ora17 IN ('', 'x')),
  ora18 varchar(1) not null DEFAULT '' CHECK (ora18 IN ('', 'x')),
  ora19 varchar(1) not null DEFAULT '' CHECK (ora19 IN ('', 'x'))
 );

INSERT INTO tabella_BRSGRL88D22H501P(data_disp)
VALUES
('2025-01-01'),
('2025-01-02'),
('2025-01-03'),
('2025-01-04'),
('2025-01-05'),
('2025-01-06'),
('2025-01-07'),
('2025-01-08'),
('2025-01-09'),
('2025-01-10'),
('2025-01-11'),
('2025-01-12'),
('2025-01-13'),
('2025-01-14'),
('2025-01-15'),
('2025-01-16'),
('2025-01-17'),
('2025-01-18'),
('2025-01-19'),
('2025-01-20'),
('2025-01-21'),
('2025-01-22'),
('2025-01-23'),
('2025-01-24'),
('2025-01-25'),
('2025-01-26'),
('2025-01-27'),
('2025-01-28'),
('2025-01-29'),
('2025-01-30'),
('2025-01-31');










