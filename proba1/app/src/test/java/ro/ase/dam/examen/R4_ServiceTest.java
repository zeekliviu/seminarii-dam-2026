package ro.ase.dam.examen;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ro.ase.dam.examen.domeniu.Categorie;
import ro.ase.dam.examen.domeniu.Cheltuiala;
import ro.ase.dam.examen.domeniu.CheltuieliService;
import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class R4_ServiceTest extends GradedTest {

    private Cheltuiala c(String denumire, double suma, boolean platita) {
        return new Cheltuiala(denumire, "nota", suma, 0, Categorie.ALTUL, platita, LocalDate.now());
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r4a_filtreazaPlatite() {
        List<Cheltuiala> sursa = new ArrayList<>(Arrays.asList(
                c("A", 100, true), c("B", 200, false), c("C", 300, true)));
        List<Cheltuiala> platite = CheltuieliService.filtreazaPlatite(sursa);
        assertEquals(2, platite.size());
        assertTrue(platite.stream().allMatch(Cheltuiala::isPlatita));
        assertEquals(Arrays.asList("A", "C"),
                platite.stream().map(Cheltuiala::getDenumire).collect(Collectors.toList()));
        assertEquals(0, CheltuieliService.filtreazaPlatite(Collections.emptyList()).size());
        assertEquals(0, CheltuieliService.filtreazaPlatite(
                Arrays.asList(c("X", 1, false), c("Y", 2, false))).size());

        List<Cheltuiala> dinNull = CheltuieliService.filtreazaPlatite(null);
        assertNotNull(dinNull);
        assertEquals(0, dinNull.size());

        // nu modifica lista originala
        assertEquals(3, sursa.size());
        sursa.remove(0);
        assertEquals(2, platite.size());
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r4b_sumaTotala() {
        assertEquals(30.5, CheltuieliService.sumaTotala(Arrays.asList(c("A", 10.5, true), c("B", 20, false))), 0.001);
        assertEquals(0.0, CheltuieliService.sumaTotala(Collections.emptyList()), 0.001);
        assertEquals(0.0, CheltuieliService.sumaTotala(null), 0.001);
        assertEquals(7.0, CheltuieliService.sumaTotala(Collections.singletonList(c("Z", 7, false))), 0.001);
        assertEquals(0.0, CheltuieliService.sumaTotala(Arrays.asList(c("Z", 0, true))), 0.001);
    }
}
