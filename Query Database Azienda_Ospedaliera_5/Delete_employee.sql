/* codice fiscale da parametrizzare */

DROP table tabella_BRSGRL88D22H501P;
DELETE FROM prenotazioni WHERE codice_fiscale_dipendente='BRSGRL88D22H501P';
DELETE FROM utente WHERE codice_fiscale='BRSGRL88D22H501P';
DELETE FROM dipendente WHERE codice_fiscale='BRSGRL88D22H501P';
DELETE FROM persona WHERE codice_fiscale='BRSGRL88D22H501P';
