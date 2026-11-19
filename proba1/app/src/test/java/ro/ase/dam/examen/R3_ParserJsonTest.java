package ro.ase.dam.examen;

import org.junit.Test;

import java.util.List;

import ro.ase.dam.examen.domeniu.Categorie;
import ro.ase.dam.examen.domeniu.Cheltuiala;
import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;
import ro.ase.dam.examen.json.ParserJson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class R3_ParserJsonTest extends GradedTest {

    private static final String JSON = "["
            + "{\"denumire\":\"Abonament\",\"nota\":\"STB\",\"suma\":70.0,"
            + "\"nrRate\":0,\"categorie\":\"Transport\",\"platita\":true,"
            + "\"dataCheltuiala\":\"2026-11-01\"},"
            + "{\"denumire\":\"FaraNota\",\"nota\":\"\",\"suma\":25.0,"
            + "\"nrRate\":0,\"categorie\":\"Mâncare\",\"platita\":false,"
            + "\"dataCheltuiala\":\"2026-11-02\"},"
            + "{\"denumire\":\"Carti\",\"nota\":\"Sem1\",\"suma\":150.0,"
            + "\"nrRate\":3,\"categorie\":\"Facultate\",\"platita\":false,"
            + "\"dataCheltuiala\":\"2026-10-15\"}"
            + "]";

    private static final String JSON_ALT = "["
            + "{\"denumire\":\"A\",\"nota\":\"\",\"suma\":10.0,"
            + "\"nrRate\":0,\"categorie\":\"Altul\",\"platita\":false,"
            + "\"dataCheltuiala\":\"2026-01-01\"},"
            + "{\"denumire\":\"B\",\"nota\":\"ok\",\"suma\":20.0,"
            + "\"nrRate\":1,\"categorie\":\"Mâncare\",\"platita\":true,"
            + "\"dataCheltuiala\":\"2026-01-02\"},"
            + "{\"denumire\":\"C\",\"nota\":\"\",\"suma\":30.0,"
            + "\"nrRate\":0,\"categorie\":\"Transport\",\"platita\":false,"
            + "\"dataCheltuiala\":\"2026-01-03\"},"
            + "{\"denumire\":\"D\",\"nota\":\"da\",\"suma\":40.0,"
            + "\"nrRate\":2,\"categorie\":\"Facultate\",\"platita\":false,"
            + "\"dataCheltuiala\":\"2026-01-04\"}"
            + "]";

    private static final String JSON_ALL_EMPTY = "["
            + "{\"denumire\":\"X\",\"nota\":\"\",\"suma\":1.0,"
            + "\"nrRate\":0,\"categorie\":\"Mâncare\",\"platita\":false,"
            + "\"dataCheltuiala\":\"2026-01-01\"},"
            + "{\"denumire\":\"Y\",\"nota\":\"\",\"suma\":2.0,"
            + "\"nrRate\":0,\"categorie\":\"Transport\",\"platita\":true,"
            + "\"dataCheltuiala\":\"2026-01-02\"}"
            + "]";

    @Test
    @Punctaj(puncte = 0.75)
    public void r3a_ignoraFaraNota() throws Exception {
        assertEquals(2, ParserJson.dinText(JSON).size());
        // al doilea fixture: 4 cheltuieli, 2 cu nota goala → 2 pastrate (anti-hardcode)
        assertEquals(2, ParserJson.dinText(JSON_ALT).size());
        assertEquals("B", ParserJson.dinText(JSON_ALT).get(0).getDenumire());
        assertEquals("D", ParserJson.dinText(JSON_ALT).get(1).getDenumire());

        assertEquals(0, ParserJson.dinText("[]").size());
        assertEquals(0, ParserJson.dinText(JSON_ALL_EMPTY).size());
    }

    @Test
    @Punctaj(puncte = 0.75)
    public void r3b_mapeazaCampurile() throws Exception {
        List<Cheltuiala> list = ParserJson.dinText(JSON);
        Cheltuiala first = list.get(0);
        assertEquals("Abonament", first.getDenumire());
        assertEquals("STB", first.getNota());
        assertEquals(Categorie.TRANSPORT, first.getCategorie());
        assertTrue(first.isPlatita());
        assertEquals(70.0, first.getSuma(), 0.001);
        assertEquals(0, first.getNrRate());
        assertEquals("2026-11-01", first.getDataCheltuiala().toString());

        Cheltuiala second = list.get(1);
        assertEquals("Carti", second.getDenumire());
        assertEquals("Sem1", second.getNota());
        assertEquals(Categorie.FACULTATE, second.getCategorie());
        assertFalse(second.isPlatita());
        assertEquals(150.0, second.getSuma(), 0.001);
        assertEquals(3, second.getNrRate());
        assertEquals("2026-10-15", second.getDataCheltuiala().toString());

        // mapare etichete UI → enum (fixture alternativ)
        List<Cheltuiala> alt = ParserJson.dinText(JSON_ALT);
        assertEquals(Categorie.MANCARE, alt.get(0).getCategorie());
        assertEquals(Categorie.FACULTATE, alt.get(1).getCategorie());
        assertEquals(20.0, alt.get(0).getSuma(), 0.001);
        assertEquals(40.0, alt.get(1).getSuma(), 0.001);
    }
}
