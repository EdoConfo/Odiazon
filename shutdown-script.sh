#!/bin/bash

# Definisce il percorso del file SQL per chiarezza
SQL_FILE="src/main/resources/data.sql"

# Sovrascrive il file con l'intestazione. Nota: il formato di `date` è diverso da quello di PowerShell.
echo "/* Dati iniziali aggiornati il $(date) */" > "$SQL_FILE"
echo "" >> "$SQL_FILE"

# Accoda gli statement INSERT dal dump del database
# Nota: Su Mac/Linux, pg_dump non ha .exe e deve essere nel PATH
pg_dump --username postgres --dbname Odiazon --host localhost --port 5432 --table public.prodotto --data-only --column-inserts | grep '^INSERT' >> "$SQL_FILE"

# Accoda una riga vuota e i comandi SQL finali usando un blocco per efficienza
{
  echo ""
  echo "-- Aggiorna la sequenza degli ID in modo dinamico"
  echo "SELECT pg_catalog.setval('public.prodotto_id_seq', (SELECT MAX(id) FROM public.prodotto), true);"
} >> "$SQL_FILE"
