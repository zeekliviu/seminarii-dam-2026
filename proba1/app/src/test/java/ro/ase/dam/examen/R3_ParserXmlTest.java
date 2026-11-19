package ro.ase.dam.examen;

import org.junit.Test;

import java.util.List;

import ro.ase.dam.examen.domeniu.Rezervare;
import ro.ase.dam.examen.domeniu.TipSala;
import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;
import ro.ase.dam.examen.xml.ParserXml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class R3_ParserXmlTest extends GradedTest {

    private static final String XML = """
            <?xml version="1.0" encoding="utf-8"?>
            <rezervari>
                <rezervare>
                    <titlu>Sala examen</titlu>
                    <observatii>DAM 2026</observatii>
                    <pret>200.0</pret>
                    <nrOre>2</nrOre>
                    <tipSala>Amfiteatru</tipSala>
                    <confirmata>true</confirmata>
                    <dataRezervare>2026-11-01</dataRezervare>
                </rezervare>
                <rezervare>
                    <titlu>Fara obs</titlu>
                    <observatii></observatii>
                    <pret>50.0</pret>
                    <nrOre>1</nrOre>
                    <tipSala>Laborator</tipSala>
                    <confirmata>false</confirmata>
                    <dataRezervare>2026-11-02</dataRezervare>
                </rezervare>
                <rezervare>
                    <titlu>Seminar B</titlu>
                    <observatii>Grup B1</observatii>
                    <pret>150.0</pret>
                    <nrOre>3</nrOre>
                    <tipSala>Sală curs</tipSala>
                    <confirmata>false</confirmata>
                    <dataRezervare>2026-10-15</dataRezervare>
                </rezervare>
            </rezervari>
            """;

    private static final String XML_ALT = """
            <?xml version="1.0" encoding="utf-8"?>
            <rezervari>
                <rezervare>
                    <titlu>A</titlu>
                    <observatii></observatii>
                    <pret>10.0</pret>
                    <nrOre>1</nrOre>
                    <tipSala>Altul</tipSala>
                    <confirmata>false</confirmata>
                    <dataRezervare>2026-01-01</dataRezervare>
                </rezervare>
                <rezervare>
                    <titlu>B</titlu>
                    <observatii>nota B</observatii>
                    <pret>20.0</pret>
                    <nrOre>2</nrOre>
                    <tipSala>Amfiteatru</tipSala>
                    <confirmata>true</confirmata>
                    <dataRezervare>2026-01-02</dataRezervare>
                </rezervare>
                <rezervare>
                    <titlu>C</titlu>
                    <observatii></observatii>
                    <pret>30.0</pret>
                    <nrOre>1</nrOre>
                    <tipSala>Laborator</tipSala>
                    <confirmata>false</confirmata>
                    <dataRezervare>2026-01-03</dataRezervare>
                </rezervare>
                <rezervare>
                    <titlu>D</titlu>
                    <observatii>obs D</observatii>
                    <pret>40.0</pret>
                    <nrOre>2</nrOre>
                    <tipSala>Sală curs</tipSala>
                    <confirmata>false</confirmata>
                    <dataRezervare>2026-01-04</dataRezervare>
                </rezervare>
            </rezervari>
            """;

    private static final String XML_EMPTY = """
            <?xml version="1.0" encoding="utf-8"?>
            <rezervari></rezervari>
            """;

    @Test
    @Punctaj(puncte = 0.75)
    public void r3a_ignoraFaraObservatii() throws Exception {
        assertEquals(2, ParserXml.dinText(XML).size());
        // al doilea fixture: 4 rezervari, 2 cu observatii goale → 2 pastrate (anti-hardcode)
        assertEquals(2, ParserXml.dinText(XML_ALT).size());
        assertEquals("B", ParserXml.dinText(XML_ALT).get(0).getTitlu());
        assertEquals("D", ParserXml.dinText(XML_ALT).get(1).getTitlu());
        assertEquals(0, ParserXml.dinText(XML_EMPTY).size());
    }

    @Test
    @Punctaj(puncte = 0.75)
    public void r3b_mapeazaCampurile() throws Exception {
        List<Rezervare> list = ParserXml.dinText(XML);
        Rezervare first = list.get(0);
        assertEquals("Sala examen", first.getTitlu());
        assertEquals("DAM 2026", first.getObservatii());
        assertEquals(TipSala.AMFITEATRU, first.getTipSala());
        assertTrue(first.isConfirmata());
        assertEquals(200.0, first.getPret(), 0.001);
        assertEquals(2, first.getNrOre());
        assertEquals("2026-11-01", first.getDataRezervare().toString());

        Rezervare second = list.get(1);
        assertEquals("Seminar B", second.getTitlu());
        assertEquals("Grup B1", second.getObservatii());
        assertEquals(TipSala.SALA_CURS, second.getTipSala());
        assertFalse(second.isConfirmata());
        assertEquals(150.0, second.getPret(), 0.001);
        assertEquals(3, second.getNrOre());
        assertEquals("2026-10-15", second.getDataRezervare().toString());

        // mapare etichete UI → enum (fixture alternativ)
        List<Rezervare> alt = ParserXml.dinText(XML_ALT);
        assertEquals(TipSala.AMFITEATRU, alt.get(0).getTipSala());
        assertEquals(TipSala.SALA_CURS, alt.get(1).getTipSala());
        assertEquals(20.0, alt.get(0).getPret(), 0.001);
        assertEquals(40.0, alt.get(1).getPret(), 0.001);
    }
}
