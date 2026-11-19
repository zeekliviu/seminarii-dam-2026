# Proba practică 1 - grupa 1088
## Timp de lucru: 60 de minute

Nota obținută în testele unitare este **orientativă**. Nota finală va fi apreciată după o **verificare manuală** a fiecărei soluții.

Completați metodele și golurile din proiect până când trec cât mai multe dintre teste.

## Despre aplicație

Aplicația gestionează cheltuielile unui student. O cheltuială are:

- `denumire` - `String`
- `nota` - `String` (poate fi gol)
- `suma` - `double`
- `nrRate` - `int`
- `categorie` - `Categorie` (enum; etichetele apar în Spinner)
- `platita` - `boolean`
- `dataCheltuiala` - `LocalDate`

Interfața (layout-uri, meniu de bază, formularul de adăugare) este deja pregătită. La pornire, lista încearcă să încarce datele din `assets` prin parserul JSON pe care îl completați - când parsarea e corectă, vedeți cheltuielile în ListView (culori, long-press, sumă din meniu).

## Cerințe

**1. Model (1p)**  
Definiți câmpurile, constructorul, accesoriile și `toString()`.

**2. Validare (1p)**  
Denumirea nu poate fi goală (nici `null`), suma trebuie să fie strict pozitivă, iar numărul de rate nu poate fi negativ. O cheltuială `null` nu este validă.

**3. Import JSON (1,5p)**  
Parsați vectorul JSON și construiți lista de cheltuieli. Săriți înregistrările cu nota goală. Aveți grijă la câmpul de tip `enum`. Fișierul din `assets` e folosit automat de lista din `MainActivity`.

**4. Interogări pe listă (1p)**  
Returnați doar cheltuielile plătite (listă goală dacă sursa e goală sau `null`), respectiv suma totală (0 dacă lista e goală sau `null`).

**5. Aspectul rândului din listă (1,5p)**  
Textul cu denumirea ocupă toată lățimea disponibilă a rândului, este aldin (bold) și la 20sp. Dacă cheltuiala este plătită, denumirea apare cu cyan; altfel cu roșu.

**6. Meniu (1p)**  
Opțiune de meniu prin care utilizatorul vede suma totală a cheltuielilor din listă.

**7. Ștergere (1,5p)**  
Long-press pe un element → confirmare da/nu → ștergere din colecție și din listă.

**8. Salvare cu validare (1,5p)**  
La salvare: validare; pe invalid, mesaj de eroare pe câmpul de denumire; pe valid, trimiteți obiectul înapoi la listă și încheiați cu succes.

Mult succes! Lucrați liniștit, întrebați-mă dacă ceva din enunț e neclar, și nu uitați să încărcați arhiva la final.
