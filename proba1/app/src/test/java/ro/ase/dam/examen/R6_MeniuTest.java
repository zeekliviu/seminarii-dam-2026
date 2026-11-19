package ro.ase.dam.examen;

import org.junit.Test;

import java.util.List;

import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;
import ro.ase.dam.examen.grading.SourceHelper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class R6_MeniuTest extends GradedTest {

    @Test
    @Punctaj(puncte = 0.5)
    public void r6a_existaOptiuneInMeniuPeLangaAdauga() throws Exception {
        List<String> extra = SourceHelper.menuIdsBesides("action_adauga");
        assertFalse("Meniu: adaugati o optiune in plus fata de Adauga", extra.isEmpty());
        for (String id : extra)
            assertTrue("Item-ul " + id + " trebuie sa aiba title", SourceHelper.menuItemHasTitle(id));
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r6b_handlerLeagaIdMeniuDeSumaPreturi() throws Exception {
        String src = SourceHelper.readMainJava("ro/ase/dam/examen/MainActivity.java");
        List<String> extra = SourceHelper.menuIdsBesides("action_adauga");
        assertFalse(extra.isEmpty());

        assertTrue(SourceHelper.containsCode(src, "onOptionsItemSelected"));
        assertTrue(SourceHelper.containsCode(src, "sumaPreturi"));
        assertTrue(SourceHelper.containsCode(src, "Toast")
                || SourceHelper.containsCode(src, "Snackbar"));

        boolean legat = false;
        for (String id : extra) {
            if (!SourceHelper.handlerReferencesMenuId(src, id)) continue;
            String window = SourceHelper.windowAfter(src, "R.id." + id, 350);
            if (window.contains("sumaPreturi")
                    && (window.contains("Toast") || window.contains("Snackbar"))) {
                legat = true;
                break;
            }
            if (SourceHelper.near(src, "R.id." + id, "sumaPreturi", 400)) {
                legat = true;
                break;
            }
        }
        assertTrue("Optiunea noua din meniu trebuie tratata cu sumaPreturi (+afisare)", legat);
    }
}
