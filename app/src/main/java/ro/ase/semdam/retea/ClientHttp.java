package ro.ase.semdam.retea;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClientHttp {
    private ClientHttp() {}

    public static String citeste(String adresa) throws Exception {
        HttpURLConnection conexiune = null;
        try {
            URL url = new URL(adresa);
            conexiune = (HttpURLConnection) url.openConnection();

            conexiune.setConnectTimeout(10000);
            conexiune.setReadTimeout(10000);
            conexiune.setRequestMethod("GET");

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(conexiune.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder text = new StringBuilder();
                String linie;
                while((linie = reader.readLine())!=null) {
                    text.append(linie);
                }
                return text.toString();
            }
        } finally {
            if(conexiune != null)
                conexiune.disconnect();
        }
    }
}
