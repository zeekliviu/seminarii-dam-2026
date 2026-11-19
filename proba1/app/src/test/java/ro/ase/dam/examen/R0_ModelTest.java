package ro.ase.dam.examen;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ro.ase.dam.examen.domeniu.Categorie;
import ro.ase.dam.examen.domeniu.Cheltuiala;
import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class R0_ModelTest extends GradedTest {

    @Test
    @Punctaj(puncte = 0.5)
    public void r0a_constructorSiGetters() {
        Cheltuiala c = new Cheltuiala("Abonament", "STB", 70.0, 0,
                Categorie.TRANSPORT, true, LocalDate.of(2026, 11, 1));
        assertEquals("Abonament", c.getDenumire());
        assertEquals("STB", c.getNota());
        assertEquals(70.0, c.getSuma(), 0.001);
        assertEquals(0, c.getNrRate());
        assertEquals(Categorie.TRANSPORT, c.getCategorie());
        assertEquals(true, c.isPlatita());
        assertEquals(LocalDate.of(2026, 11, 1), c.getDataCheltuiala());

        // al doilea obiect - anti-singleton hardcodat
        Cheltuiala c2 = new Cheltuiala("Carti", "Sem1", 150.0, 3,
                Categorie.FACULTATE, false, LocalDate.of(2026, 10, 15));
        assertEquals("Carti", c2.getDenumire());
        assertEquals("Sem1", c2.getNota());
        assertEquals(150.0, c2.getSuma(), 0.001);
        assertEquals(3, c2.getNrRate());
        assertEquals(false, c2.isPlatita());
        assertEquals(Categorie.FACULTATE, c2.getCategorie());
        assertEquals(LocalDate.of(2026, 10, 15), c2.getDataCheltuiala());

        // setters trebuie sa actualizeze starea
        c2.setDenumire("Carti2");
        c2.setPlatita(true);
        c2.setSuma(200.0);
        assertEquals("Carti2", c2.getDenumire());
        assertEquals(true, c2.isPlatita());
        assertEquals(200.0, c2.getSuma(), 0.001);
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r0b_toStringSiCampuriPrivate() {
        Cheltuiala c = new Cheltuiala("Chirie", "Nov", 800.0, 1,
                Categorie.ALTUL, false, LocalDate.of(2026, 3, 20));
        String s = c.toString();
        assertTrue(s.contains("Chirie"));
        assertTrue(s.contains("800") || s.contains("800.0"));
        assertTrue(s.contains("Nov") || s.contains("ALTUL") || s.contains("Altul"));

        Set<String> names = new HashSet<>();
        int privateFields = 0;
        for (Field f : Cheltuiala.class.getDeclaredFields()) {
            if (f.isSynthetic()) continue;
            if (Modifier.isPrivate(f.getModifiers())) {
                privateFields++;
                names.add(f.getName());
            }
        }
        assertTrue("Campurile trebuie sa fie private", privateFields >= 7);
        assertTrue(names.containsAll(Arrays.asList(
                "denumire", "nota", "suma", "nrRate", "categorie", "platita", "dataCheltuiala")));
    }
}
