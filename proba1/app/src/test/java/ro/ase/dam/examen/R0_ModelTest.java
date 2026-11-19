package ro.ase.dam.examen;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ro.ase.dam.examen.domeniu.TipSala;
import ro.ase.dam.examen.domeniu.Rezervare;
import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class R0_ModelTest extends GradedTest {

    @Test
    @Punctaj(puncte = 0.5)
    public void r0a_constructorSiGetters() {
        Rezervare r = new Rezervare("Sala A101", "Grup 1086", 100.0, 2,
                TipSala.AMFITEATRU, true, LocalDate.of(2026, 11, 1));
        assertEquals("Sala A101", r.getTitlu());
        assertEquals("Grup 1086", r.getObservatii());
        assertEquals(100.0, r.getPret(), 0.001);
        assertEquals(2, r.getNrOre());
        assertEquals(TipSala.AMFITEATRU, r.getTipSala());
        assertEquals(true, r.isConfirmata());
        assertEquals(LocalDate.of(2026, 11, 1), r.getDataRezervare());

        // al doilea obiect - anti-singleton hardcodat
        Rezervare r2 = new Rezervare("Lab B203", "Seminar", 50.0, 3,
                TipSala.LABORATOR, false, LocalDate.of(2026, 10, 15));
        assertEquals("Lab B203", r2.getTitlu());
        assertEquals("Seminar", r2.getObservatii());
        assertEquals(50.0, r2.getPret(), 0.001);
        assertEquals(3, r2.getNrOre());
        assertEquals(false, r2.isConfirmata());
        assertEquals(TipSala.LABORATOR, r2.getTipSala());
        assertEquals(LocalDate.of(2026, 10, 15), r2.getDataRezervare());

        // setters trebuie sa actualizeze starea
        r2.setTitlu("Lab C101");
        r2.setConfirmata(true);
        r2.setPret(75.0);
        assertEquals("Lab C101", r2.getTitlu());
        assertEquals(true, r2.isConfirmata());
        assertEquals(75.0, r2.getPret(), 0.001);
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r0b_toStringSiCampuriPrivate() {
        Rezervare r = new Rezervare("Sala examen", "DAM", 200.0, 2,
                TipSala.SALA_CURS, false, LocalDate.of(2026, 3, 20));
        String s = r.toString();
        assertTrue(s.contains("Sala examen"));
        assertTrue(s.contains("200") || s.contains("200.0"));
        assertTrue(s.contains("DAM") || s.contains("SALA_CURS") || s.contains("Sală curs"));

        Set<String> names = new HashSet<>();
        int privateFields = 0;
        for (Field f : Rezervare.class.getDeclaredFields()) {
            if (f.isSynthetic()) continue;
            if (Modifier.isPrivate(f.getModifiers())) {
                privateFields++;
                names.add(f.getName());
            }
        }
        assertTrue("Campurile trebuie sa fie private", privateFields >= 7);
        assertTrue(names.containsAll(Arrays.asList(
                "titlu", "observatii", "pret", "nrOre", "tipSala", "confirmata", "dataRezervare")));
    }
}
