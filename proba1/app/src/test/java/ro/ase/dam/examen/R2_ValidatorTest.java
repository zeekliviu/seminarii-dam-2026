package ro.ase.dam.examen;

import org.junit.Test;

import java.time.LocalDate;

import ro.ase.dam.examen.domeniu.TipSala;
import ro.ase.dam.examen.domeniu.Rezervare;
import ro.ase.dam.examen.domeniu.Validator;
import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class R2_ValidatorTest extends GradedTest {

    private Rezervare ok() {
        return new Rezervare("Sala X", "obs", 50.0, 1, TipSala.ALTUL, false, LocalDate.now());
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r2a_acceptaRezervareCorecta() {
        assertTrue(Validator.esteValid(ok()));
        // pret = 0 e valid (pret >= 0)
        assertTrue(Validator.esteValid(
                new Rezervare("Sala Y", "ceva", 0.0, 1, TipSala.LABORATOR, true, LocalDate.now())));
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r2b_respingeCazuriInvalide() {
        assertFalse(Validator.esteValid(null));
        assertFalse(Validator.esteValid(
                new Rezervare(null, "obs", 10, 1, TipSala.ALTUL, false, LocalDate.now())));
        assertFalse(Validator.esteValid(
                new Rezervare("", "obs", 10, 1, TipSala.ALTUL, false, LocalDate.now())));
        // pret < 0 → invalid
        assertFalse(Validator.esteValid(
                new Rezervare("X", "obs", -1, 1, TipSala.ALTUL, false, LocalDate.now())));
        // nrOre = 0 → invalid (nrOre > 0)
        assertFalse(Validator.esteValid(
                new Rezervare("X", "obs", 10, 0, TipSala.ALTUL, false, LocalDate.now())));
        // nrOre < 0 → invalid
        assertFalse(Validator.esteValid(
                new Rezervare("X", "obs", 10, -1, TipSala.ALTUL, false, LocalDate.now())));
    }
}
