/*
Comando per esportare i dati della tabella prodotto in formato SQL INSERT:

psql -d Odiazon -U postgres -t -A -o /Users/edoardoconforti/Documents/GitHub/Repositories/Odiazon/src/main/resources/import.sql -c "SELECT 'INSERT INTO prodotto (nome, descrizione, prezzo, immagine_url) VALUES (''' || replace(nome, '''', '''''') || ''', ''' || replace(descrizione, '''', '''''') || ''', ' || prezzo || ', ''/uploads/' || substring(immagine_url from '[^/]+$') || ''');' FROM prodotto;"
*/

INSERT INTO prodotto (nome, descrizione, prezzo, immagine_url) VALUES ('Il Signore degli Anelli', 'Un classico della letteratura fantasy', 25.50, '/uploads/il_signore_degli_anelli.jpg');
INSERT INTO prodotto (nome, descrizione, prezzo, immagine_url) VALUES ('Set di Cacciaviti', 'Set professionale 10 pezzi', 42.00, '/uploads/set_di_cacciaviti.jpg');
INSERT INTO prodotto (nome, descrizione, prezzo, immagine_url) VALUES ('Star Wars', 'Tanto tempo fa in una galassia lontana lontana ...', 24.50, '/uploads/star_wars.jpg');
INSERT INTO prodotto (nome, descrizione, prezzo, immagine_url) VALUES ('Delitto e Castigo', 'Un classico della letteratura russa', 15.40, '/uploads/delitto_e_castigo.jpg');
