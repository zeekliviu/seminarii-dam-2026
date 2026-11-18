# 08 - Proba practică 1

Ponderea și condițiile sunt în [README](../README.md): **15%** din nota finală, **prezență fizică**, pe calculatoarele din laborator (săptămâna **8**). Parola arhivei se comunică **live, la oră**.

## Ce trebuie să știți dinainte

Lucrați pe un **schelet Android** (proiect Gradle) pe care îl primiți ca arhivă offline. În lab, după ce începe modul examen, **nu mai aveți internet general** (fără Wi‑Fi, fără USB): rămâne acces doar la **online.ase.ro** (Moodle). De aceea arhiva trebuie deja pe **ThawSpace** înainte de lockdown.

## Fluxul din laborator

### 1. Înainte de modul examen (încă aveți net / sau doar Moodle)

1. Descărcați arhiva grupei de pe Moodle (link comunicat la oră) **direct pe ThawSpace** (ex. `T:\` / spațiul care **supraviețuiește restartului** Deep Freeze).
2. Nu lăsați arhiva doar pe Desktop-ul de pe disc înghețat: la restart dispare.

### 2. După restart / modul examen

1. Primiți **parola** arhivei live.
2. Dezarhivați pe ThawSpace (sau unde vi se indică la oră) cu **7‑Zip** / WinRAR.
3. Rulați **`START-EXAMEN.bat`** din rădăcina arhivei (setează `GRADLE_USER_HOME` local și deschide Android Studio pe proiect).
4. În Android Studio: **Settings → Build, Execution, Deployment → Gradle → Offline work** = ON.
5. Sync / Build. Lucrați **doar** din acest proiect; nu contați pe download Gradle de pe internet.

### 3. În timpul probei

- Enunțul e în **`ENUNT.md`** (în proiectul din arhivă).
- Nu aveți USB, Wi‑Fi sau net în afara Moodle (`online.ase.ro`).
- Păstrați proiectul pe ThawSpace ca să nu îl pierdeți.

### 4. Predarea (fereastră scurtă)

La finalul timpului de lucru:

1. În proiect, rulați task-ul Gradle **`zipPentruMoodle`** (grupa `examen` în fereastra Gradle, sau din terminalul proiectului).
2. În `app/build.gradle.kts`, la task-ul `zipPentruMoodle`, puneți în `archiveFileName` numele cerut la oră (ex. `Solutie_Nume_Prenume.zip`), apoi regenerați arhiva.
3. Arhiva apare tipic în folderul **`moodle/`** din rădăcina proiectului (doar `app/src/main` — fără `build/`, fără teste).
4. Pe Moodle se deschide **tema de încărcare** la **sfârșitul probei + ~5 minute**, cu **o singură încercare**.
5. Încărcați zip-ul în acel interval. **Dacă nu încărcați la timp, nu există punctaj** pe probă.

## Ce se evaluează

Cerințele concrete (model, validare, parse, UI etc.) sunt în **`ENUNT.md`**. Punctajul maxim al variantei de grupă este **10 puncte**; baremul e în același fișier.

## Checklist rapid

- [ ] Arhiva e pe **ThawSpace** înainte de lockdown
- [ ] Am rulat **`START-EXAMEN.bat`**, nu am deschis proiectul „din întâmplare” pe alt cache Gradle
- [ ] **Offline work** e ON în Studio
- [ ] Am redenumit zip-ul de predare cum s-a cerut
- [ ] Am încărcat pe Moodle în fereastra de **+5 minute**, din prima (și unica) încercare
