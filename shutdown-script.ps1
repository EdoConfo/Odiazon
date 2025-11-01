# Sovrascrive il file con l'intestazione
Set-Content -Path "src\main\resources\data.sql" -Value "/* Dati iniziali aggiornati il $(Get-Date) */"

# Aggiunge una riga vuota dopo l'intestazione
Add-Content -Path "src\main\resources\data.sql" -Value ""

# Accoda gli statement INSERT dal dump del database
pg_dump.exe --username postgres --dbname Odiazon --host localhost --port 5432 --table public.prodotto --data-only --column-inserts | Select-String -Pattern "^INSERT" | Add-Content -Path "src\main\resources\data.sql"

# Accoda una riga vuota e i comandi SQL finali
Add-Content -Path "src\main\resources\data.sql" -Value ""
Add-Content -Path "src\main\resources\data.sql" -Value "-- Aggiorna la sequenza degli ID in modo dinamico"
Add-Content -Path "src\main\resources\data.sql" -Value "SELECT pg_catalog.setval('public.prodotto_id_seq', (SELECT MAX(id) FROM public.prodotto), true);"