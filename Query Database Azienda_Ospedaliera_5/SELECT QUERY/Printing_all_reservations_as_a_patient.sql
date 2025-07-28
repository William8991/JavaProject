/* Stampa prenotazioni paziente */

SELECT t.id_prenotazione 'Id prenotazione', d.nome_medico 'Nome medico', d.cognome_medico 'Cognome medico', d.professione_medico Professione, t.data_ora_visita 'Data e ora della visita', t.sede_ospedale 'Sede ospedale', t.tipo_visita 'Tipo visita', t.costo_visita 'Costo visita', t.stato_pagamento 'Stato pagamento'
FROM prenotazioni t
JOIN paziente p ON t.codice_fiscale_paziente= p.codice_fiscale
JOIN dipendente d ON t.codice_fiscale_dipendente= d.codice_fiscale
WHERE t.codice_fiscale_paziente = 'VRDGPP20E12P123G';
