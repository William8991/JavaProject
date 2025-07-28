CREATE TRIGGER before_insert_dipendente
BEFORE INSERT ON dipendente
FOR EACH ROW
BEGIN
  SET NEW.email_ospedaliera = CONCAT(NEW.codice_fiscale, '@osp.it');
END;
