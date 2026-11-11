# 07 - JSON și SharedPreferences

## Obiective

- să deosebiți **JSON** de XML-ul BNR și să îl parsați cu `JSONObject` / `JSONArray`;
- să descărcați un vector de `Anunt` / `Abonament` cu `ClientHttp` din Seminarul 6 și să îl puneți în lista din `MainActivity`, pe **executor**;
- să păstrați o valoare mică în **SharedPreferences** (nu toată colecția).

## De unde porniți

- Starea de la finalul Seminarului 6 (tag `Seminar_6-grupa1086` sau `Seminar_6-grupa1088`, ori branch-ul grupei: `git pull` sau **Clone Repository** din Android Studio - vezi [README](../README.md)).
- Lista din memorie, adapterul, meniul din toolbar, `Executor` + `Handler` din `BnrActivity`.

## 1. JSON față de XML

**JSON** (JavaScript Object Notation) e text structurat: obiecte `{ ... }` și vectori `[ ... ]`. Pe Android, pachetul `org.json` e deja în platformă: `JSONObject` și `JSONArray`.

La BNR ați parsat XML (`Rate`, atribut `currency`). Aici formatul e altul, dar ideea rămâne aceeași: citiți textul pe executor, actualizați lista pe main thread.

Cum arată un exemplu de JSON, pentru fiecare grupă în parte:

### 1086

```json
[
  {
    "titlu": "Laptop",
    "descriere": "i5, 16GB",
    "pret": 2500.0,
    "cantitate": 1,
    "categorie": "Electronice",
    "nou": true,
    "dataPublicare": "2026-03-01"
  }
]
```

### 1088

```json
[
  {
    "nume": "Spotify",
    "observatii": "familie",
    "cost": 30.0,
    "nrPersoane": 4,
    "frecventa": "Lunar",
    "activ": true,
    "dataReinnoire": "2026-11-01"
  }
]
```

## 2. JSON-ul din rețea

Am pregătit pentru voi câte un vector cu 16 obiecte pentru fiecare grupă publicat pe JSONBin ca să exersați ce am învățat seminarul trecut cu apelurile în rețea. Permisiunea `INTERNET` e deja în Manifest din Seminarul 6. Fără ea, nu avem acces la internet, deci nici la JSONBin. Emulatorul sau telefonul trebuie să aibă acces la rețea.

Adresa fiecărei grupe este redată mai jos. Query param-ul `meta=false` face ca răspunsul să fie **doar vectorul**, nu un obiect cu metadate în jurul lui. Cu el, aveți direct vectorul de obiecte JSON.

- **1086:** `https://api.jsonbin.io/v3/b/6ad34e67ffd5d16053733bd7/latest?meta=false`
- **1088:** `https://api.jsonbin.io/v3/b/6ad34e67ffd5d16053733bd8/latest?meta=false`

Puteți deschide adresa în browser înainte de a scrie codul: Veți vedea răspunsul de JSON raw, care începe cu `[` și se termină cu `]`.

Descărcarea o face `ClientHttp.citeste` (Seminarul 6), de pe **executor**. `IncarcareJson` (secțiunea 3) doar parsează textul.

## 3. Parsare în pachetul `json`

Pe `ro.ase.semdam`: **New → Package** → `json`. Acolo, **New → Java Class** → `IncarcareJson`.

Clasa primește textul JSON și întoarce `List`. Eticheta din JSON trece prin `dinEticheta`, ca la Spinner.

1086:

```java
package ro.ase.semdam.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.ase.semdam.domeniu.Anunt;
import ro.ase.semdam.domeniu.Categorie;
import ro.ase.semdam.retea.ClientHttp;

public final class IncarcareJson {

    private static final String URL_JSON =
            "https://api.jsonbin.io/v3/b/6ad34e67ffd5d16053733bd7/latest?meta=false";

    private IncarcareJson() { }

    public static String descarca() throws Exception {
        String text = ClientHttp.citeste(URL_JSON);
        return text.isEmpty() ? "[]" : text;
    }

    public static List<Anunt> dinText(String text) throws Exception {
        List<Anunt> rezultat = new ArrayList<>();
        JSONArray array = new JSONArray(text);
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            String titlu = o.getString("titlu");
            String descriere = o.getString("descriere");
            double pret = o.getDouble("pret");
            int cantitate = o.getInt("cantitate");
            Categorie categorie = Categorie.dinEticheta(o.getString("categorie"));
            boolean nou = o.getBoolean("nou");
            LocalDate dataPublicare = LocalDate.parse(o.getString("dataPublicare"));
            rezultat.add(new Anunt(titlu, descriere, pret, cantitate, categorie, nou, dataPublicare));
        }
        return rezultat;
    }
}
```

1088: aceeași clasă, cu adresa bin-ului de abonamente, `Abonament`, `Frecventa.dinEticheta(...)` și câmpurile `nume`, `observatii`, `cost`, `nrPersoane`, `frecventa`, `activ`, `dataReinnoire`. `descarca()` rămâne la fel: doar constanta `URL_JSON` se schimbă.

`ClientHttp.citeste` aduce textul (citirea e în Seminarul 6). Dacă răspunsul e gol, `descarca()` întoarce `"[]"`, ca `dinText` să primească un vector gol.

### Câmpurile din fiecare obiect

`new JSONArray(text)` citește vectorul dintre `[` și `]`. `length()` este numărul de elemente. `getJSONObject(i)` ia obiectul de la poziția `i`, adică un `{ ... }`.

Pe `JSONObject`, metoda depinde de tipul valorii din JSON. Cheia este exact șirul din fișier (`"titlu"`, nu `"Titlu"`):

| Metodă                | Ce întoarce             | În JSON-ul 1086                                    |
| --------------------- | ----------------------- | -------------------------------------------------- |
| `getString("titlu")`  | textul dintre ghilimele | `titlu`, `descriere`, `categorie`, `dataPublicare` |
| `getDouble("pret")`   | `double`                | `pret` (`2500.0`)                                  |
| `getInt("cantitate")` | `int`                   | `cantitate`                                        |
| `getBoolean("nou")`   | `boolean`               | `nou` (`true` / `false`, fără ghilimele în JSON)   |

`getString`, `getDouble`, `getInt` și `getBoolean` aruncă `JSONException` dacă cheia lipsește sau valoarea nu e de tipul cerut (de exemplu `getInt` pe un text). De aceea `dinText` stă în `try` pe executor, iar pe ecran ajunge Toast, ca la eroarea de rețea din `BnrActivity`.

Data nu are tip propriu în JSON. O citiți tot cu `getString`, apoi `LocalDate.parse`, în formatul `yyyy-MM-dd` de la formular.

`categorie` e tot un șir. `Categorie.dinEticheta(...)` îl transformă în constanta enum, ca la Spinner. Șirul trebuie să coincidă cu eticheta (`"Electronice"`, `"Carti"`, `"Imbracaminte"`, `"Altele"`).

`new Anunt(...)` primește valorile în ordinea constructorului din Seminarul 2.

Pe 1088, aceleași metode, cu cheile modelului: `getString("nume")`, `getString("observatii")`, `getDouble("cost")`, `getInt("nrPersoane")`, `Frecventa.dinEticheta(o.getString("frecventa"))`, `getBoolean("activ")`, `LocalDate.parse(o.getString("dataReinnoire"))`.

## 4. Meniul „Încarcă JSON”

În `res/menu/meniu_principal.xml`, un item nou:

```xml
<item
    android:id="@+id/action_json"
    android:title="Încarcă JSON"
    app:showAsAction="never" />
```

În `MainActivity`, păstrați `executor` și `mainHandler` ca în seminarul 6 (câmpuri, nu `newSingleThreadExecutor()` la fiecare click). În `onOptionsItemSelected`:

```java
if (id == R.id.action_json) {
    executor.execute(() -> {
        try {
            String text = IncarcareJson.descarca();
            List<Anunt> citite = IncarcareJson.dinText(text);
            mainHandler.post(() -> {
                anunturi.clear();
                anunturi.addAll(citite);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Încărcat: " + citite.size(), Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            mainHandler.post(() ->
                    Toast.makeText(this, "Eroare JSON: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    });
    return true;
}
```

Pe 1088, lista se numește cum ați numit-o voi (`abonamente`). `notifyDataSetChanged()` e pe main thread, după `clear` / `addAll`. Dacă telefonul nu are rețea, `getInputStream()` aruncă excepție și ajungeți pe Toast, ca la BNR.

Documentație: [JSONObject](https://developer.android.com/reference/org/json/JSONObject), [JSONArray](https://developer.android.com/reference/org/json/JSONArray)

## 5. SharedPreferences

Lista din `MainActivity` stă în memorie. Închideți aplicația și lista devine goală la următoarea deschidere: nimic nu scrie obiectele pe disc. Un extra din Intent, ca la `AddActivity`, ține o valoare doar pe drumul spre ecranul următor. Nici el nu rămâne după ce procesul moare.

**SharedPreferences** scrie perechi cheie-valoare (șir, număr, boolean) într-un fișier privat al aplicației. E potrivit pentru un aspect mic al aplicației, nu pentru cele 16 anunțuri sau abonamente. Aspectul pe care putem să-l păstrăm în SharedPreferences este numărul de obiecte aduse la ultima încărcare reușită. În aplicațiile web, **SharedPreferences** este echivalentul **localStorage**.

### Scrierea, după încărcare

În `mainHandler.post`, imediat după `addAll` și `notifyDataSetChanged`:

```java
SharedPreferences prefs = getSharedPreferences("setari_semdam", MODE_PRIVATE);
prefs.edit()
        .putInt("nr_incarcate", citite.size())
        .apply();
```

`getSharedPreferences("setari_semdam", MODE_PRIVATE)` deschide fișierul cu acel nume. `edit()` pregătește o modificare, `putInt` pune întregul sub cheia `"nr_incarcate"`, `apply()` o scrie pe disc în fundal. `commit()` scrie pe loc și întoarce `boolean`. Pe un click de meniu, `apply()` e de ajuns: nu blochează interfața.

Al doilea argument este modul de deschidere, o constantă din `Context`:

| Mod                    | Ce face                                                                                                                                                                                         |
| ---------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `MODE_PRIVATE`         | Fișierul e specific aplicației. Alte aplicații nu îl pot citi și nici să scrie în el. E modul pe care îl folosiți. Valoarea lui numerică e `0`.                                                 |
| `MODE_WORLD_READABLE`  | Deprecated din API 17. Lăsa orice aplicație instalată să citească fișierul. Din Android 7 (API 24) apelul aruncă `SecurityException`. `minSdk`-ul vostru e 24, deci modul nici nu mai pornește. |
| `MODE_WORLD_WRITEABLE` | La fel, deprecated din API 17 și `SecurityException` din API 24. În plus, altă aplicație putea și scrie în fișier.                                                                              |
| `MODE_MULTI_PROCESS`   | Deprecated din API 23. Era o încercare ca două procese ale aceleiași aplicații să vadă scrierile celeilalte. Nu era sigur: un proces putea citi o copie veche. Nu îl folosiți.                  |

`MODE_APPEND` există tot pe `Context`, dar e pentru `openFileOutput` (adaugă octeți la sfârșitul unui fișier), nu pentru SharedPreferences.

Dacă două aplicații trebuie să schimbe un fișier, nu deschideți prefs „pentru toată lumea”. Se folosește un `FileProvider` și o permisiune acordată punctual pe un `Uri`. Aici nu e cazul: numărul rămâne în aplicație.

Cheia și numele fișierului sunt șiruri alese de voi. Trebuie să fie **aceleași** la citire.

### Citirea, în `SecondActivity`

`SecondActivity` e ecranul din Seminarul 1. Butonul care o deschidea a ieșit de pe `MainActivity` când a apărut lista. O vom redeschide din toolbar, ca pe `BnrActivity`.

În `meniu_principal.xml`:

```xml
<item
    android:id="@+id/action_ultima"
    android:title="Ultima încărcare"
    app:showAsAction="never" />
```

În `onOptionsItemSelected`:

```java
if (id == R.id.action_ultima) {
    startActivity(new Intent(this, SecondActivity.class));
    return true;
}
```

Nu puneți `nr_incarcate` în Intent. Ecranul citește fișierul singur. Așa vedeți diferența față de extra: valoarea nu călătorește cu `startActivity`, stă în aplicație.

În `activity_second.xml`, un `TextView` cu `android:id="@+id/tvUltimaIncarcare"`. În `SecondActivity.onCreate`, după binding:

```java
SharedPreferences prefs = getSharedPreferences("setari_semdam", MODE_PRIVATE);
int n = prefs.getInt("nr_incarcate", -1);
binding.tvUltimaIncarcare.setText("Ultima încărcare: " + n);
```

Al doilea argument al lui `getInt` este valoarea când cheia lipsește. Înainte de prima încărcare reușită, TextView-ul arată **-1**. După „Încarcă JSON”, deschideți „Ultima încărcare”: arată câte obiecte au intrat în listă. Închideți aplicația din aplicațiile recente, redeschideți-o: lista e din nou goală, iar `SecondActivity` arată tot numărul salvat.

### De ce DataStore în proiecte noi

Clasa `SharedPreferences` **nu** e marcată `@Deprecated`, spre deosebire de `AsyncTask`, dar documentația oficială recomandă să nu o folosiți într-un proiect nou: în locul ei stă [DataStore](https://developer.android.com/topic/libraries/architecture/datastore). Marcate efectiv ca depreciate sunt modurile de mai sus (`MODE_WORLD_READABLE`, `MODE_WORLD_WRITEABLE`, `MODE_MULTI_PROCESS`), nu API-ul întreg.

Neajunsurile pentru care Google militează pentru DataStore:

- Prima citire încarcă **tot** fișierul XML în memorie, pe thread-ul care apelează `getSharedPreferences`. Un fișier mare, citit pe firul UI, poate îngheța ecranul.
- `commit()` scrie tot fișierul pe thread-ul curent și poate provoca ANR. `apply()` scrie în fundal, dar nu vă spune dacă scrierea a reușit. Uneori `apply()` se poate bloca în `onPause` / `onStop`, până se golesc scrierile rămase.
- Două `edit()` pornite în același timp creează un race condition. Una poate suprascrie cheile celeilalte.
- Tipurile sunt doar primitive, `String` și `Set<String>`. Nu aveți un semnal clar când fișierul de pe disc e corupt.

DataStore scrie asincron, fiecare `edit` e o tranzacție (ori intră toate cheile, ori niciuna) și poate semnala o corupere. Are două forme: **Preferences DataStore** (cheie-valoare, ca aici) și **Proto DataStore** (un obiect cu schemă în spate). Citirea e un `Flow`: ecranul primește valoarea nouă când se schimbă, fără să mai deschideți Activity-ul ca să faceți `getInt`.

API-ul e pe Kotlin și coroutines. În laboratorul Java rămânem pe SharedPreferences.

Documentație: [Save key-value data](https://developer.android.com/training/data-storage/shared-preferences), [DataStore](https://developer.android.com/topic/libraries/architecture/datastore), [Context.MODE_PRIVATE](https://developer.android.com/reference/android/content/Context#MODE_PRIVATE)

### Live assignment

Al treilea live assignment din semestru (seminariile cu live assignments: 3, 5, 7 și 11; vezi [README](../README.md)). Extindeți **parsarea JSON** și **SharedPreferences**, pe baza meniului și a executorului din acest seminar. **Cerința exactă se anunță la oră** pe Moodle, separat pe fiecare grupă.

### Evaluare

- **Pondere:** 5% din nota finală (unul din cele patru live assignments; împreună 20%).
- **Mod:** verificare pe calculatorul vostru, la oră, cu un demo propus de mine.
- **Condiție pentru a primi punctaj:** treceți demo-ul (rândurile cerute ajung în listă; valoarea din SharedPreferences e cea așteptată, inclusiv înainte de prima încărcare).
- **Fără punctaj intermediar:** live assignment-ul este totul sau nimic. Ori primiți cei 5%, ori 0 pentru acest exercițiu.

## Verificare

- [ ] `IncarcareJson` e în pachetul `json`, apelează `ClientHttp.citeste` și folosește adresa bin-ului grupei (`meta=false`).
- [ ] „Încarcă JSON” descarcă vectorul și umple lista fără să blocheze interfața; eroarea de rețea ajunge în Toast.
- [ ] Eticheta din JSON trece prin `dinEticheta` (aceleași șiruri ca în Spinner).
- [ ] „Ultima încărcare” deschide `SecondActivity`. Înainte de prima încărcare, TextView-ul arată 0. După „Încarcă JSON”, arată numărul de obiecte. După închiderea aplicației, numărul rămâne, lista din memorie nu.
- [ ] Live assignment (anunțat la oră): regulile de pe Moodle, pe grupa voastră.

## Bibliografie

- [JSONObject](https://developer.android.com/reference/org/json/JSONObject)
- [JSONArray](https://developer.android.com/reference/org/json/JSONArray)
- [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences)
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)

## Indexul ghidurilor

| Fișier                                                                     | Temă                                    |
| -------------------------------------------------------------------------- | --------------------------------------- |
| [01-proiect-activity-view-binding.md](01-proiect-activity-view-binding.md) | Proiect, Activity, View Binding, Intent |
| [02-formular-si-model.md](02-formular-si-model.md)                         | Formular, controale, model (POJO)       |
| [03-validare-activity-result.md](03-validare-activity-result.md)           | Validare, Activity Result API           |
| [04-lista-crud-memorie.md](04-lista-crud-memorie.md)                       | ListView, CRUD în memorie               |
| [05-adapter-meniu-fragmente.md](05-adapter-meniu-fragmente.md)             | Adapter personalizat, meniu, fragmente  |
| [06-retea-xml-bnr.md](06-retea-xml-bnr.md)                                 | Rețea asincronă, XML BNR                |
| [07-json-sharedpreferences.md](07-json-sharedpreferences.md)               | JSON, SharedPreferences                 |
| [08-proba1.md](08-proba1.md)                                               | Proba practică 1                        |
| [09-room.md](09-room.md)                                                   | Room (SQLite)                           |
| [10-firebase.md](10-firebase.md)                                           | Firebase Realtime Database              |
| [11-grafica-canvas.md](11-grafica-canvas.md)                               | Grafică 2D pe Canvas                    |
| [12-proba2.md](12-proba2.md)                                               | Proba practică 2                        |
| [13-recapitulare-publicare.md](13-recapitulare-publicare.md)               | Recapitulare, hărți (demo), publicare   |
