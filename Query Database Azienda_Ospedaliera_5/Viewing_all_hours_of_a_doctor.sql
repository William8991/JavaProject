/* VISUALIZZAZIONE di TUTTI GLI ORARI LIBERI PER PRENOTARE DI UN MEDICO IN QUESTIONE */

-- selezionare il numero di dentisti ---> con il select count
-- selezione il codice fiscale di questi dentisti
-- for di questa query


SELECT d.nome_medico, d.cognome_medico, o.ora8 '08:00', o.ora9 '09:00', o.ora10 '10:00', o.ora11 '11:00', o.ora12 '12:00', o.ora15 '15:00', o.ora16 '16:00', o.ora17 '17:00', o.ora18 '18:00', o.ora19 '19:00'
FROM tabella_BNCTRS95L12C123Z o
JOIN dipendente d 
WHERE data_disp= '2025-01-26' AND d.codice_fiscale='BNCTRS95L12C123Z';









