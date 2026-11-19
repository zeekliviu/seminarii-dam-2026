package ro.ase.dam.examen;

import org.junit.Test;

import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.Punctaj;
import ro.ase.dam.examen.grading.SourceHelper;

import static org.junit.Assert.assertTrue;

public class R7_LongPressTest extends GradedTest {

    @Test
    @Punctaj(puncte = 0.75)
    public void r7a_longClickPeLista() throws Exception {
        String src = SourceHelper.readMainJava("ro/ase/dam/examen/MainActivity.java");
        assertTrue(SourceHelper.containsCode(src, "setOnItemLongClickListener"));
        String window = SourceHelper.windowAfter(src, "setOnItemLongClickListener", 900);
        assertTrue(window.contains("AlertDialog") || window.contains("Builder"));
        assertTrue("Long-click ar trebui sa returneze true",
                window.contains("return true") || SourceHelper.containsCompact(window, "return true;"));
    }

    @Test
    @Punctaj(puncte = 0.75)
    public void r7b_confirmareCuDialogSiStergere() throws Exception {
        String src = SourceHelper.readMainJava("ro/ase/dam/examen/MainActivity.java");
        assertTrue(SourceHelper.containsCode(src, "AlertDialog"));
        assertTrue(SourceHelper.containsCode(src, "setPositiveButton"));
        assertTrue(SourceHelper.containsCode(src, "setNegativeButton"));
        assertTrue(SourceHelper.containsCode(src, ".show()")
                || SourceHelper.containsCompact(src, ".show();"));

        String afterPositive = SourceHelper.windowAfter(src, "setPositiveButton", 400);
        assertTrue(afterPositive.contains("remove"));
        assertTrue(afterPositive.contains("notifyDataSetChanged"));
        assertTrue(SourceHelper.appearsBefore(afterPositive, "remove", "notifyDataSetChanged")
                || SourceHelper.near(afterPositive, "remove", "notifyDataSetChanged", 120));
    }
}
