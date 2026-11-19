package ro.ase.dam.examen.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.ase.dam.examen.domeniu.Categorie;
import ro.ase.dam.examen.domeniu.Cheltuiala;

public final class ParserJson {

    private ParserJson() {}

    public static List<Cheltuiala> dinText(String text) throws Exception {
        List<Cheltuiala> rezultat = new ArrayList<>();
        JSONArray array = new JSONArray(text);
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            String denumire = o.getString("denumire");
            String nota = o.getString("nota");
            if (nota.isEmpty()) continue;
            double suma = o.getDouble("suma");
            int nrRate = o.getInt("nrRate");
            Categorie categorie = Categorie.dinEticheta(o.getString("categorie"));
            boolean platita = o.getBoolean("platita");
            LocalDate dataCheltuiala = LocalDate.parse(o.getString("dataCheltuiala"));
            rezultat.add(new Cheltuiala(denumire, nota, suma, nrRate, categorie, platita, dataCheltuiala));
        }
        return rezultat;
    }
}
