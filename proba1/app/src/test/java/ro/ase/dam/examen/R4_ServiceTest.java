package ro.ase.dam.examen;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ro.ase.dam.examen.domeniu.TipSala;
import ro.ase.dam.examen.domeniu.Rezervare;
import ro.ase.dam.examen.domeniu.RezervariService;
import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class R4_ServiceTest extends GradedTest {

    private Rezervare r(String titlu, double pret, boolean confirmata) {
        return new Rezervare(titlu, "obs", pret, 1, TipSala.ALTUL, confirmata, LocalDate.now());
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r4a_filtreazaConfirmate() {
        List<Rezervare> sursa = new ArrayList<>(Arrays.asList(
                r("A", 100, true), r("B", 200, false), r("C", 300, true)));
        List<Rezervare> confirmate = RezervariService.filtreazaConfirmate(sursa);
        assertEquals(2, confirmate.size());
        assertTrue(confirmate.stream().allMatch(Rezervare::isConfirmata));
        assertEquals(Arrays.asList("A", "C"),
                confirmate.stream().map(Rezervare::getTitlu).collect(Collectors.toList()));
        assertEquals(0, RezervariService.filtreazaConfirmate(Collections.emptyList()).size());
        assertEquals(0, RezervariService.filtreazaConfirmate(
                Arrays.asList(r("X", 1, false), r("Y", 2, false))).size());

        List<Rezervare> dinNull = RezervariService.filtreazaConfirmate(null);
        assertNotNull(dinNull);
        assertEquals(0, dinNull.size());

        // nu modifica lista originala
        assertEquals(3, sursa.size());
        sursa.remove(0);
        assertEquals(2, confirmate.size());
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r4b_sumaPreturi() {
        assertEquals(30.5, RezervariService.sumaPreturi(Arrays.asList(r("A", 10.5, true), r("B", 20, false))), 0.001);
        assertEquals(0.0, RezervariService.sumaPreturi(Collections.emptyList()), 0.001);
        assertEquals(0.0, RezervariService.sumaPreturi(null), 0.001);
        assertEquals(7.0, RezervariService.sumaPreturi(Collections.singletonList(r("Z", 7, false))), 0.001);
        assertEquals(0.0, RezervariService.sumaPreturi(Arrays.asList(r("Z", 0, true))), 0.001);
    }
}
