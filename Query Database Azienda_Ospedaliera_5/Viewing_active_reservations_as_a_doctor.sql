/*Visualizzare tutte le prenotazioni in corso che dovrà svolgere il medico*/
SELECT paziente.nome_paziente, paziente.cognome_paziente, prenotazioni.tipo_visita
FROM prenotazioni 
JOIN dipendente  ON prenotazioni.codice_fiscale_dipendente = dipendente.codice_fiscale
JOIN paziente ON prenotazioni.codice_fiscale_paziente = paziente.codice_fiscale
WHERE dipendente.nome_medico='Lorenzo' AND dipendente.cognome_medico= 'Rossi';
