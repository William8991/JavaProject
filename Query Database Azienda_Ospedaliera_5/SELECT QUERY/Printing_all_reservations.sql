SELECT pr.id_prenotazione 'ID prenotazione', p.nome_paziente 'Nome paziente', p.cognome_paziente 'Cognome paziente', pr.tipo_visita 'Tipo di visita', d.nome_medico 'Nome medico', d.cognome_medico 'Congome medico', d.professione_medico 'Professione del medico', pr.data_ora_visita 'Data e ora della visita', pr.costo_visita 'Costo della visita', pr.stato_pagamento 'Stato pagamento'
FROM prenotazioni pr
JOIN dipendente d  ON pr.codice_fiscale_dipendente = d.codice_fiscale
JOIN paziente p ON pr.codice_fiscale_paziente = p.codice_fiscale;
