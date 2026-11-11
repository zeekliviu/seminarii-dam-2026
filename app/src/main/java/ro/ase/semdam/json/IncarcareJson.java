package ro.ase.semdam.json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ro.ase.semdam.domeniu.Anunt;
import ro.ase.semdam.domeniu.Categorie;
import ro.ase.semdam.retea.ClientHttp;

public class IncarcareJson {

    private static final String URL_JSON = "https://api.jsonbin.io/v3/b/6ad34e67ffd5d16053733bd7/latest?meta=false";

    private IncarcareJson() {}

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
            if(descriere.isEmpty())
                continue;
            double pret = o.getDouble("pret");
            int cantitate = o.getInt("cantitate");
            Categorie categorie = Categorie.dinEticheta(o.getString("categorie"));
            boolean nou = o.getBoolean("nou");
            LocalDate dataPublicare = LocalDate.parse(o.getString("dataPublicare"));
            Anunt anunt = new Anunt(titlu, descriere, pret, cantitate, categorie, nou, dataPublicare);
            rezultat.add(anunt);
        }
        return rezultat;
    }
}
