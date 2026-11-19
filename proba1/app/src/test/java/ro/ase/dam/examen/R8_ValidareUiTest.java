package ro.ase.dam.examen;

import org.junit.Test;

import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;
import ro.ase.dam.examen.grading.SourceHelper;

import static org.junit.Assert.assertTrue;

public class R8_ValidareUiTest extends GradedTest {

    @Test
    @Punctaj(puncte = 0.75)
    public void r8a_setErrorPeDenumireLaInvalid() throws Exception {
        String src = SourceHelper.readMainJava("ro/ase/dam/examen/AddActivity.java");
        assertTrue(SourceHelper.containsCode(src, "setError"));
        assertTrue(SourceHelper.containsCode(src, "etDenumire"));
        assertTrue(SourceHelper.near(src, "etDenumire", "setError", 80)
                || SourceHelper.containsCompact(src, "etDenumire.setError")
                || SourceHelper.matches(src, "etDenumire\\s*\\.\\s*setError"));
        assertTrue(SourceHelper.containsCode(src, "esteValid")
                || SourceHelper.containsCode(src, "Validator"));
        assertTrue(SourceHelper.appearsBefore(src, "esteValid", "setError")
                || SourceHelper.appearsBefore(src, "Validator", "setError"));
    }

    @Test
    @Punctaj(puncte = 0.75)
    public void r8b_trimiteObiectulInapoiSiFinish() throws Exception {
        String add = SourceHelper.readMainJava("ro/ase/dam/examen/AddActivity.java");
        assertTrue(SourceHelper.containsCode(add, "esteValid")
                || SourceHelper.containsCode(add, "Validator"));
        assertTrue(SourceHelper.containsCode(add, "setResult"));
        assertTrue(SourceHelper.containsCode(add, "RESULT_OK"));
        assertTrue(SourceHelper.containsCode(add, "putExtra"));
        assertTrue(SourceHelper.containsCode(add, "finish"));
        assertTrue(SourceHelper.appearsBefore(add, "esteValid", "setResult")
                || SourceHelper.appearsBefore(add, "Validator", "setResult"));
        assertTrue(SourceHelper.appearsBefore(add, "setResult", "finish")
                || SourceHelper.near(add, "setResult", "finish", 120));
        assertTrue(SourceHelper.near(add, "putExtra", "setResult", 200)
                || SourceHelper.appearsBefore(add, "putExtra", "setResult")
                || SourceHelper.appearsBefore(add, "putExtra", "finish"));

        String main = SourceHelper.readMainJava("ro/ase/dam/examen/MainActivity.java");
        assertTrue(SourceHelper.containsCode(main, "registerForActivityResult")
                || SourceHelper.containsCode(main, "ActivityResultContracts"));
        assertTrue(SourceHelper.containsCode(main, "getSerializableExtra")
                || SourceHelper.containsCode(main, "IntentCompat"));
        assertTrue(SourceHelper.containsCode(main, "cheltuieli.add")
                || SourceHelper.containsCompact(main, "cheltuieli.add("));
        assertTrue(SourceHelper.containsCode(main, "notifyDataSetChanged"));
    }
}
