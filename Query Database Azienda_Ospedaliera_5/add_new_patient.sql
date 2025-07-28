/* Aggiunta nuovo paziente */
INSERT INTO persona(codice_fiscale, nome, cognome, data_nascita, luogo_nascita, residenza, email, telefono)
VALUES
("PLLLRA90C20E789X", "Lara", "Pellegrini", "1990-03-20", "Firenze", "Torino", "lara.pellegrini@gmail.com", "3409876543");

INSERT INTO utente
VALUES
("PLLLRA90C20E789X","PLLLRA90C20E789X","passlaura");

/*inserimento nella tabella pazienti*/
INSERT INTO paziente
VALUES 
('PLLLRA90C20E789X', 'Lara', 'Pellegrini', 'frattura del femore');








