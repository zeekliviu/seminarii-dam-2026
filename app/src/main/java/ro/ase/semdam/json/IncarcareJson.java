package ro.ase.semdam.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.ase.semdam.domeniu.Abonament;
import ro.ase.semdam.domeniu.Frecventa;
import ro.ase.semdam.retea.ClientHttp;

public class IncarcareJson {

    private static final String URL_JSON = "https://api.jsonbin.io/v3/b/6ad34e67ffd5d16053733bd8/latest?meta=false";

    private IncarcareJson() {}

    public static String descarca() throws Exception {
        String text = ClientHttp.citeste(URL_JSON);
        return text.isEmpty() ? "[]" : text;
    }

    public static List<Abonament> dinText(String text) throws Exception {
        List<Abonament> rezultat = new ArrayList<>();
        JSONArray array = new JSONArray(text);
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            String nume = o.getString("nume");
            String observatii = o.getString("observatii");
            if(observatii.isEmpty())
                continue;
            double cost = o.getDouble("cost");
            int nrPersoane = o.getInt("nrPersoane");
            Frecventa frecventa = Frecventa.dinEticheta(o.getString("frecventa"));
            boolean activ = o.getBoolean("activ");
            LocalDate dataReinnoire = LocalDate.parse(o.getString("dataReinnoire"));

            Abonament abonament = new Abonament(nume, observatii, cost, nrPersoane, frecventa, activ, dataReinnoire);
            rezultat.add(abonament);
        }
        return rezultat;
    }
}
