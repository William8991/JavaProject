/* DINAMICITA' TABELLA ORARI  MEDICI */


/*
      ROUTINE DI AGGIORNAMENTO DELLE TABELLE ORARIE DEI MEDICI:
System.out.println("Current date and time in Italy: " + currentDateTimeInItaly);

if(currentDateTimeInItaly > SELECT MAX(data_disp) FROM tabella_BNCTRS95L12C123Z)
// esegui le query qua sotto

*/


-- elimino dalla tabella il primo record 
DELETE FROM tabella_BNCTRS95L12C123Z
ORDER BY data_disp ASC
LIMIT 1; 
  


-- inserisco nella tabella il record inerente alla prossima data     
INSERT INTO tabella_BNCTRS95L12C123Z (data_disp)
SELECT DATE_ADD(MAX(data_disp), INTERVAL 1 DAY) 
FROM tabella_BNCTRS95L12C123Z;
