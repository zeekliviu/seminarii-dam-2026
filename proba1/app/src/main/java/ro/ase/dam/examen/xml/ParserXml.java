package ro.ase.dam.examen.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import ro.ase.dam.examen.domeniu.Rezervare;
import ro.ase.dam.examen.domeniu.TipSala;

public final class ParserXml {

    private ParserXml() {}

    public static List<Rezervare> dinText(String text) throws Exception {
        List<Rezervare> rezultat = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = dbf.newDocumentBuilder().parse(
                new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
        NodeList nodes = doc.getElementsByTagName("rezervare");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            String titlu = getChild(el, "titlu");
            String observatii = getChild(el, "observatii");
            if (observatii.isEmpty()) continue;
            double pret = Double.parseDouble(getChild(el, "pret"));
            int nrOre = Integer.parseInt(getChild(el, "nrOre"));
            TipSala tipSala = TipSala.dinEticheta(getChild(el, "tipSala"));
            boolean confirmata = Boolean.parseBoolean(getChild(el, "confirmata"));
            LocalDate dataRezervare = LocalDate.parse(getChild(el, "dataRezervare"));
            rezultat.add(new Rezervare(titlu, observatii, pret, nrOre, tipSala, confirmata, dataRezervare));
        }
        return rezultat;
    }

    private static String getChild(Element el, String tag) {
        NodeList nl = el.getElementsByTagName(tag);
        if (nl.getLength() == 0) return "";
        return nl.item(0).getTextContent().trim();
    }
}
