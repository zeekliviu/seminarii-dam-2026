package ro.ase.dam.examen;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ro.ase.dam.examen.grading.GradedTest;
import ro.ase.dam.examen.grading.LayoutXmlHelper;
import ro.ase.dam.examen.grading.Punctaj;
import ro.ase.dam.examen.grading.SourceHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class R5_LayoutXmlTest extends GradedTest {

    private Element tvTitlu() throws Exception {
        Document doc = LayoutXmlHelper.parseResXml("layout/elem_listview.xml");
        Element tv = LayoutXmlHelper.findByAndroidId(doc, "tvTitlu");
        assertNotNull(tv);
        return tv;
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r5a_titluPeToataLatimea() throws Exception {
        Element tv = tvTitlu();
        String width = LayoutXmlHelper.androidAttr(tv, "layout_width");
        assertTrue("0dp".equals(width) || "match_parent".equals(width));
        assertEquals("parent", LayoutXmlHelper.appAttr(tv, "layout_constraintStart_toStartOf"));
        assertEquals("parent", LayoutXmlHelper.appAttr(tv, "layout_constraintEnd_toEndOf"));
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r5b_italicSi16sp() throws Exception {
        Element tv = tvTitlu();
        String style = LayoutXmlHelper.androidAttr(tv, "textStyle");
        assertTrue("textStyle trebuie sa contina italic", style != null && style.contains("italic"));
        assertEquals("16sp", LayoutXmlHelper.androidAttr(tv, "textSize"));
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r5c_culoareDupaConfirmata() throws Exception {
        String src = SourceHelper.readMainJava("ro/ase/dam/examen/adapters/CustomAdapter.java");
        String getView = SourceHelper.methodBody(src, "getView");
        assertTrue("getView trebuie implementat", getView.length() > 20);
        assertTrue(getView.contains("setTextColor"));
        assertTrue(getView.contains("isConfirmata") || getView.contains("getConfirmata"));
        assertTrue(SourceHelper.containsCompact(getView, "Color.BLUE")
                || SourceHelper.containsCompact(getView, "Color.blue"));
        assertTrue(SourceHelper.containsCompact(getView, "Color.MAGENTA")
                || SourceHelper.containsCompact(getView, "Color.magenta"));
        assertTrue(getView.contains("tvTitlu")
                || SourceHelper.containsCode(getView, "R.id.tvTitlu"));
        assertTrue(SourceHelper.appearsBefore(getView, "isConfirmata", "Color.BLUE")
                || SourceHelper.appearsBefore(getView, "isConfirmata", "Color.MAGENTA")
                || SourceHelper.appearsBefore(getView, "isConfirmata", "Color.blue")
                || SourceHelper.appearsBefore(getView, "isConfirmata", "Color.magenta"));
    }
}
