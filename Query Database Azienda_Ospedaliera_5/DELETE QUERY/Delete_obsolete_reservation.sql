# SELECT p.id_prenotazione, p.data_ora_visita
# FROM prenotazioni p
# ORDER BY p.data_ora_visita;

/*						
            ROUTINE DI ELIMINAZIONE DELLE PRENOTAZIONI OBSOLETE:
*/
            
SELECT p.id_prenotazione
FROM prenotazioni p
WHERE p.data_ora_visita < CONVERT_TZ(NOW(), 'UTC', 'Europe/Rome');
-- gli id me li metto in una lista


DELETE 
FROM prenotazioni p
WHERE p.data_ora_visita < CONVERT_TZ(NOW(), 'UTC', 'Europe/Rome');
    
   
    



