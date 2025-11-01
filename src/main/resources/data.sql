/* Dati iniziali aggiornati il 11/01/2025 22:46:35 */

INSERT INTO public.prodotto (id, nome, descrizione, prezzo, immagine_url) VALUES (1, 'Il Signore degli Anelli', 'Un classico della letteratura fantasy', 25.50, '/uploads/il_signore_degli_anelli.jpg');
INSERT INTO public.prodotto (id, nome, descrizione, prezzo, immagine_url) VALUES (2, 'Set di Cacciaviti', 'Set professionale 10 pezzi', 42.00, '/uploads/set_di_cacciaviti.jpg');
INSERT INTO public.prodotto (id, nome, descrizione, prezzo, immagine_url) VALUES (3, 'Star Wars', 'Tanto tempo fa in una galassia lontana lontana ...', 24.50, '/uploads/star_wars.jpg');
INSERT INTO public.prodotto (id, nome, descrizione, prezzo, immagine_url) VALUES (4, 'Delitto e Castigo', 'Un classico della letteratura russa', 15.40, '/uploads/delitto_e_castigo.jpg');
INSERT INTO public.prodotto (id, nome, descrizione, prezzo, immagine_url) VALUES (5, 'Meisterstuck', 'Penna elegante ad inchiostro', 520.00, '/uploads/meisterstuck.jpg');

-- Aggiorna la sequenza degli ID in modo dinamico
SELECT pg_catalog.setval('public.prodotto_id_seq', (SELECT MAX(id) FROM public.prodotto), true);
