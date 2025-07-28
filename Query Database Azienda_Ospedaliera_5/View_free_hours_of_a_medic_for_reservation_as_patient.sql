SELECT d.codice_fiscale
FROM dipendente d
WHERE d.professione_medico='Oncologo';
-- e ottengo un arraylist di codici fiscali degli oncologi
-- l'utente inserisce la data (Es: 2025-01-13)

-- for(i=0,i<size;i++)
SELECT d.codice_fiscale, o.ora8 -- o.ora9, o.ora10, o.ora11, o.ora12, o.ora15, o.ora16, o.ora17, o.ora18, o.ora19
FROM tabella_RMNFNC02P08A638Z o
JOIN dipendente d 
WHERE data_disp= '2025-01-13' AND d.codice_fiscale='RMNFNC02P08A638Z';
-- vedrò a sinistra il nome e a destra tutti gli orari (liberi o occupati)
