# Seminarii DAM 2026

Repository pentru seminariile de **Dispozitive și Aplicații Mobile**.
Link: https://github.com/zeekliviu/seminarii-dam-2026

Construim **o singură aplicație Android** pe parcursul semestrului. Domeniul diferă pe grupe (din Seminarul 2):

| Grupa    | Domeniu                                              | Clasă model |
| -------- | ---------------------------------------------------- | ----------- |
| **1086** | Anunțuri second-hand (gen Vinted: cămin / campus)    | `Anunt`     |
| **1088** | Evidența abonamentelor (Spotify, YouTube, sală etc.) | `Abonament` |

Repository-ul este **public**: îl puteți clona fără invitație.

Grupele din acest laborator: **1086** și **1088**.

Ghidurile pe seminar sunt în folderul `seminarii/`. Fiecare ghid îmbină noțiuni teoretice cu pași practici. Dacă nu ați fost la curs, citiți explicațiile din ghid înainte de a scrie codul.

## Evaluare

Punctajul disciplinei este împărțit în două părți egale:

| Pondere | Componentă |
| ------- | ---------- |
| **50%** | Seminar    |
| **50%** | Examen     |

### Structura punctajului de seminar (50%)

| Pondere din notă | Ce reprezintă                       | Cum se evaluează                                                                                                                                                                                                                                    |
| ---------------- | ----------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **15%**          | Proba practică 1                    | La calculator, **prezență fizică** în laborator (săptămâna **8**; data exactă se confirmă la oră)                                                                                                                                                   |
| **15%**          | Proba practică 2                    | La calculator, **prezență fizică** în laborator (săptămâna **12**)                                                                                                                                                                                  |
| **20%**          | Implicare activă (live assignments) | **4** exerciții scurte la oră, câte **5%** fiecare. La finalul seminarului verific oral, pe calculatorul vostru. **Totul sau nimic** pe fiecare exercițiu: ori primiți cei 5%, ori 0; nu există punctaj intermediar. Cele 4 bife însumează cei 20%. |

Seminariile cu live assignments: **3, 5, 7** și **10** (câte 5% fiecare). Fiecare live assignment este **totul sau nimic** (fără fracțiuni de punct).

### Condiții

**Intrare în examen** (toate, cumulativ):

1. cel puțin **70% prezențe** la seminar (din **14** seminarii → cel puțin **10** prezențe);
2. cel puțin **4 prezențe** la curs;
3. cel puțin **15%** din nota finală provenită din seminar.

**Promovarea disciplinei** (ambele, cumulativ):

1. îndeplinirea condițiilor de intrare în examen;
2. nota finală cel puțin **5,00** (seminar 50% + examen grilă 50%).

## Cum lucrăm la seminar

La oră demonstrăm și scriem împreună pe aplicația comună. Pe alocuri (live assignments) vă cer să faceți **o variantă puțin diferită** pe calculatorul vostru. La finalul orei trec pe la fiecare și verific pe loc. Live assignment-urile sunt **totul sau nimic**: nu se acordă punctaj parțial pe un exercițiu.

### Git: ce există pe server

| Branch / tag                            | Rol                                                                                                                                 |
| --------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------- |
| `main`                                  | **doar** materialul de seminar: `README`, ghiduri din `seminarii/` (md + imagini), `.gitignore`. Fără proiectul Android.             |
| `grupa/1086` sau `grupa/1088`           | ghidurile **plus** aplicația **la finalul orei**, pentru grupa voastră                                                              |
| tag `Seminar_N-grupa1086` (sau `…1088`) | punct fix: starea (ghiduri + cod) de la finalul seminarului N                                                                      |

Proiectul Android stă pe branch-urile de **grupă** (și în tag-uri), în **rădăcina** repository-ului (`app/`, `settings.gradle.kts` etc.). Pe `main` nu există `app/`.

### Înainte de fiecare seminar

Porniți de la ce am publicat pentru grupa voastră după ora anterioară. Alegeți **una** din variante.

#### Varianta A - Android Studio (recomandată dacă nu aveți încă repository-ul sau vreți o copie curată)

1. Deschideți **Android Studio**.
2. Alegeți **Clone Repository** (sau **File → New → Project from Version Control**).
3. La **URL** introduceți `https://github.com/zeekliviu/seminarii-dam-2026`. Alegeți o locație de bun simț (de exemplu Desktop). Verificați că numele folderului local este `seminarii-dam-2026` (completați-l dacă nu se precompletează).
4. Apăsați **Clone**.
5. După deschidere, în stânga sus, lângă numele proiectului, apare branch-ul `main`. Apăsați pe el → coborâți la **Remote** → **origin** → **grupa** → alegeți `1086` sau `1088` (grupa voastră).
6. Apăsați **Checkout** ca să aduceți codul grupei.
7. **File → Close Project**, apoi **Open** pe același folder `seminarii-dam-2026` (acum pe branch-ul grupei). Așteptați Sync-ul Gradle până apare folderul `app` în fereastra **Project**.

#### Varianta B - terminal (dacă aveți deja repository-ul clonat)

```powershell
git fetch
git switch grupa/1086
git pull
```

(Pentru grupa **1088**, înlocuiți `1086` cu `1088`.)

Deschideți folderul rădăcină în Android Studio cu **Open** (nu New Project), exceptând Seminarul 1, când creați proiectul.

### Dacă ați ratat un seminar

Puteți reveni la starea de la finalul unui seminar prin **tag**, ca să parcurgeți singuri ghidul:

```powershell
git fetch --tags
git tag -l
```

Exemplu: pentru a studia materialul Seminarului 3, porniți de la starea Seminarului 2:

```powershell
git switch --detach Seminar_2-grupa1086
```

Sau creați un branch local temporar de studiu:

```powershell
git switch -c studiu-sem2 Seminar_2-grupa1086
```

Nu dezvoltați permanent pe un tag: tag-ul este doar un reper. Când reveniți la oră, treceți din nou pe `grupa/1086` (sau `1088`) și faceți `git pull`.

### Seminarul 1 și `.gitignore`

La crearea proiectului, Android Studio poate rescrie `.gitignore`. Readuceți varianta din repository, ca să nu rămâneți cu modificări locale care încurcă un `git pull` ulterior:

```powershell
git fetch
git restore --source=origin/grupa/1086 -- .gitignore
```

(Pentru grupa 1088, folosiți `origin/grupa/1088`.)

## Mediul de lucru

- **Android Studio** (versiunea din laborator sau o versiune recentă stabilă)
- **Git pentru Windows**
- Dispozitiv virtual din **Device Manager**, sau telefonul propriu prin Wi-Fi (secțiunea de mai jos)

La primul **Gradle Sync** este nevoie de internet; poate dura câteva minute.

### Configurație pe tot semestrul

| Setare                     | Valoare                            | Motiv pe scurt                                 |
| -------------------------- | ---------------------------------- | ---------------------------------------------- |
| Limbaj                     | Java 17 + `java.time` (desugaring) | Compatibil AGP 8.x; `LocalDate` pe `minSdk` 24 |
| Interfață                  | Views și XML                       | Nu folosim Jetpack Compose                     |
| Șablon proiect             | Empty Views Activity               | „Empty Activity” este pentru Compose           |
| `minSdk`                   | 24                                 | Compatibilitate laborator                      |
| `compileSdk` / `targetSdk` | minim 36                           | Cerința Google Play (din 31 august 2026)       |
| Acces la view-uri          | View Binding                       | În loc de `findViewById` repetat               |

Etichetele din wizard pot diferi ușor între versiuni de Android Studio. Alegeți șablonul cu **Views** (XML), nu **Compose**.

## Testarea pe telefonul propriu, prin Wi-Fi

O să vedeți că emulatorul cere multe resurse de la calculator. Așadar, dacă aveți un telefon cu **Android 11** sau mai nou, puteți rula aplicația pe el, pe **același Wi-Fi**, cu `adb pair` și `adb connect` (fără cablu).

| Pas                             | De unde citiți adresa                   | Comandă       |
| ------------------------------- | --------------------------------------- | ------------- |
| Asociere (o dată pe calculator) | fereastra cu codul de 6 cifre           | `adb pair`    |
| Conectare                       | ecranul principal **Depanare wireless** | `adb connect` |

1. Activați **Opțiuni pentru dezvoltatori** (7 apăsări pe Număr de compilare) și **Depanare wireless**.
2. În Android Studio: **View → Tool Windows → Terminal**.
3. Intrați în SDK:

```powershell
cd "$env:LOCALAPPDATA\Android\Sdk\platform-tools"
```

Dacă lipsește, în **Settings → Languages & Frameworks → Android SDK** copiați calea SDK; `platform-tools` e înăuntru. Pe tab-ul **SDK Tools**, bifați **Android SDK Platform-Tools** dacă e nevoie.

1. Pe telefon: **Pair device with pairing code**. În terminal (portul din fereastra cu cod):

```powershell
.\adb.exe pair 192.168.1.20:37123
```

1. Apoi, cu portul de pe ecranul principal Depanare wireless:

```powershell
.\adb.exe connect 192.168.1.20:41234
.\adb.exe devices
```

La următoarea sesiune, de obicei e suficient `adb connect` cu portul **actual** (se schimbă des). Dacă rețeaua din laborator izolează dispozitivele, încercați hotspot pe telefon.

Documentație: [Connect to a device over Wi-Fi](https://developer.android.com/tools/adb#connect-to-a-device-over-wi-fi)

## Indexul ghidurilor

| Fișier                                                                               | Temă                                    |
| ------------------------------------------------------------------------------------ | --------------------------------------- |
| [01-proiect-activity-view-binding.md](seminarii/01-proiect-activity-view-binding.md) | Proiect, Activity, View Binding, Intent |
| [02-formular-si-model.md](seminarii/02-formular-si-model.md)                         | Formular, controale, model (POJO)       |
| [03-validare-activity-result.md](seminarii/03-validare-activity-result.md)           | Validare, Activity Result API           |
| [04-lista-crud-memorie.md](seminarii/04-lista-crud-memorie.md)                       | ListView, CRUD în memorie               |
| [05-adapter-meniu-fragmente.md](seminarii/05-adapter-meniu-fragmente.md)             | Adapter personalizat, meniu, fragmente  |
| [06-retea-xml-bnr.md](seminarii/06-retea-xml-bnr.md)                                 | Rețea asincronă, XML BNR                |
| [07-json-sharedpreferences.md](seminarii/07-json-sharedpreferences.md)               | JSON, SharedPreferences                 |
| [08-room.md](seminarii/08-room.md)                                                   | Room (SQLite)                           |
| [09-firebase.md](seminarii/09-firebase.md)                                           | Firebase Realtime Database              |
| [10-grafica-canvas.md](seminarii/10-grafica-canvas.md)                               | Grafică 2D pe Canvas                    |
| [11-recapitulare-publicare.md](seminarii/11-recapitulare-publicare.md)               | Recapitulare, hărți (demo), publicare   |
