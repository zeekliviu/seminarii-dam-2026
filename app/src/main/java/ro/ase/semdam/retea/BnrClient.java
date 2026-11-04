package ro.ase.semdam.retea;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

public class BnrClient {

    private static final String URL_BNR = "https://curs.bnr.ro/nbrfxrates.xml";

    private BnrClient () {}

    public static List<CursValutar> descarca() throws Exception {
        String xml = ClientHttp.citeste(URL_BNR);
        try (StringReader reader = new StringReader(xml)) {
            Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(new InputSource(reader));
            return extrageRate(document);
        }
    }

    private static List<CursValutar> extrageRate(Document document) {
        List<CursValutar> rezultat = new ArrayList<>();
        NodeList noduri = document.getElementsByTagName("Rate");
        for (int i = 0; i < noduri.getLength(); i++) {
            Element element = (Element) noduri.item(i);
            String moneda = element.getAttribute("currency");
            String textValoare = element.getTextContent().trim();
            if(moneda.isEmpty() || textValoare.isEmpty())
                continue;
            double valoare = Double.parseDouble(textValoare);
            rezultat.add(new CursValutar(moneda, valoare));
        }
        return rezultat;
    }
}
