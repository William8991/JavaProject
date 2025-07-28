/* NUOVA PRENOTAZIONE */
INSERT INTO prenotazioni(codice_fiscale_paziente,codice_fiscale_dipendente,data_ora_visita,sede_ospedale,tipo_visita,costo_visita,stato_pagamento)
VALUES 
('PLNMRZ90C15F205N','VRDLGI82C15D869P','2025-01-30 09:00:00','Papardo','Visita di analisi della risonanza magnetica del paziente',80,'da pagare');

/* ORA RECUPERO SOLO L'ORA DAL CAMPO DATETIME della data scelta*/

# SELECT TIME(data_ora_visita)
# FROM prenotazioni
# WHERE id_prenotazione = (SELECT id_prenotazione 
#        									 FROM prenotazioni 
#        									 ORDER BY id_prenotazione DESC 
#        									 LIMIT 1); /*recupero l'ultima prenotazione fatta*/


/* IL RISULTATO DI QUESTA QUERY SARÀ L'ORA DELLA PRENOTAZIONE APPENA RICHIESTA, faccio esempio con le 9 e quindi scrivo "ora9"*/
/* ricordo che ora devo selezionare non l'orario dell'ultima prenotazione, ma il codice_fiscale_dipendente*/

UPDATE tabella_VRDLGI82C15D869P
SET ora9 = 'x'
WHERE data_disp = (SELECT DATE(data_ora_visita) /* faccio queste 2 subquery per selezionare la data della prenotazione in questione*/
									 FROM prenotazioni
									 WHERE id_prenotazione = (SELECT id_prenotazione 
       									 									  FROM prenotazioni 
       									 									  ORDER BY id_prenotazione DESC 
                           								  LIMIT 1));

/*SELECT id_prenotazione 
FROM prenotazioni 
ORDER BY id_prenotazione DESC 
LIMIT 1
*/





