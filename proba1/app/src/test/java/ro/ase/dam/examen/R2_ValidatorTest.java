package ro.ase.dam.examen;

import org.junit.Test;

import java.time.LocalDate;

import ro.ase.dam.examen.domeniu.Categorie;
import ro.ase.dam.examen.domeniu.Cheltuiala;
import ro.ase.dam.examen.domeniu.Validator;
import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class R2_ValidatorTest extends GradedTest {

    private Cheltuiala ok() {
        return new Cheltuiala("X", "y", 10.0, 1, Categorie.ALTUL, false, LocalDate.now());
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r2a_acceptaCheltuialaCorecta() {
        assertTrue(Validator.esteValid(ok()));
        // nrRate = 0 e valid (nrRate >= 0)
        assertTrue(Validator.esteValid(
                new Cheltuiala("Y", "ceva", 5.0, 0, Categorie.MANCARE, true, LocalDate.now())));
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r2b_respingeCazuriInvalide() {
        assertFalse(Validator.esteValid(null));
        assertFalse(Validator.esteValid(
                new Cheltuiala(null, "y", 10, 1, Categorie.ALTUL, false, LocalDate.now())));
        assertFalse(Validator.esteValid(
                new Cheltuiala("", "y", 10, 1, Categorie.ALTUL, false, LocalDate.now())));
        // suma = 0 → invalid (suma > 0)
        assertFalse(Validator.esteValid(
                new Cheltuiala("X", "y", 0, 1, Categorie.ALTUL, false, LocalDate.now())));
        // suma < 0 → invalid
        assertFalse(Validator.esteValid(
                new Cheltuiala("X", "y", -5, 1, Categorie.ALTUL, false, LocalDate.now())));
        // nrRate < 0 → invalid
        assertFalse(Validator.esteValid(
                new Cheltuiala("X", "y", 10, -1, Categorie.ALTUL, false, LocalDate.now())));
    }
}
