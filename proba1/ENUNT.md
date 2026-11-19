# Proba practică 1 - grupa 1086
## Timp de lucru: 60 de minute

Nota obținută în testele unitare este **orientativă**. Nota finală va fi apreciată după o **verificare manuală** a fiecărei soluții.

Completați metodele și golurile din proiect până când trec cât mai multe dintre teste.

## Despre aplicație

Aplicația gestionează rezervările de săli din ASE. O rezervare are:

- `titlu` - `String`
- `observatii` - `String` (poate fi gol)
- `pret` - `double`
- `nrOre` - `int`
- `tipSala` - `TipSala` (enum; etichetele apar în Spinner)
- `confirmata` - `boolean`
- `dataRezervare` - `LocalDate`

Interfața (layout-uri, meniu de bază, formularul de adăugare) este deja pregătită. La pornire, lista încearcă să încarce datele din `assets` prin parserul XML pe care îl completați - când parsarea e corectă, vedeți rezervările în ListView (culori, long-press, sumă din meniu).

## Cerințe

**1. Model (1p)**  
Definiți câmpurile, constructorul, accesoriile și `toString()`.

**2. Validare (1p)**  
Titlul nu poate fi gol (nici `null`), prețul trebuie să fie ≥ 0, iar numărul de ore trebuie să fie strict pozitiv. O rezervare `null` nu este validă.

**3. Import XML (1,5p)**  
Parsați documentul XML și construiți lista de rezervări. Săriți înregistrările cu observații goale. Aveți grijă la câmpul de tip `enum`. Fișierul din `assets` e folosit automat de lista din `MainActivity`.

**4. Interogări pe listă (1p)**  
Returnați doar rezervările confirmate (listă goală dacă sursa e goală sau `null`), respectiv suma prețurilor (0 dacă lista e goală sau `null`).

**5. Aspectul rândului din listă (1,5p)**  
Textul cu titlul ocupă toată lățimea disponibilă a rândului, este italic și la 16sp. Dacă rezervarea este confirmată, titlul apare cu albastru; altfel cu magenta.

**6. Meniu (1p)**  
Opțiune de meniu prin care utilizatorul vede suma prețurilor rezervărilor din listă.

**7. Ștergere (1,5p)**  
Long-press pe un element → confirmare da/nu → ștergere din colecție și din listă.

**8. Salvare cu validare (1,5p)**  
La salvare: validare; pe invalid, mesaj de eroare pe câmpul de titlu; pe valid, trimiteți obiectul înapoi la listă și încheiați cu succes.

Mult succes! Lucrați liniștit, întrebați-mă dacă ceva din enunț e neclar, și nu uitați să încărcați arhiva la final.
