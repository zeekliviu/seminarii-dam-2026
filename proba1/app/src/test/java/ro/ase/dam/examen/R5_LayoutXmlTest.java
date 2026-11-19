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

    private Element tvDenumire() throws Exception {
        Document doc = LayoutXmlHelper.parseResXml("layout/elem_listview.xml");
        Element tv = LayoutXmlHelper.findByAndroidId(doc, "tvDenumire");
        assertNotNull(tv);
        return tv;
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r5a_denumireaPeToataLatimea() throws Exception {
        Element tv = tvDenumire();
        String width = LayoutXmlHelper.androidAttr(tv, "layout_width");
        assertTrue("0dp".equals(width) || "match_parent".equals(width));
        assertEquals("parent", LayoutXmlHelper.appAttr(tv, "layout_constraintStart_toStartOf"));
        assertEquals("parent", LayoutXmlHelper.appAttr(tv, "layout_constraintEnd_toEndOf"));
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r5b_boldSi20sp() throws Exception {
        Element tv = tvDenumire();
        String style = LayoutXmlHelper.androidAttr(tv, "textStyle");
        assertTrue("textStyle trebuie sa contina bold", style != null && style.contains("bold"));
        assertEquals("20sp", LayoutXmlHelper.androidAttr(tv, "textSize"));
    }

    @Test
    @Punctaj(puncte = 0.5)
    public void r5c_culoareDupaPlatita() throws Exception {
        String src = SourceHelper.readMainJava("ro/ase/dam/examen/adapters/CustomAdapter.java");
        String getView = SourceHelper.methodBody(src, "getView");
        assertTrue("getView trebuie implementat", getView.length() > 20);
        assertTrue(getView.contains("setTextColor"));
        assertTrue(getView.contains("isPlatita") || getView.contains("getPlatita"));
        assertTrue(SourceHelper.containsCompact(getView, "Color.CYAN")
                || SourceHelper.containsCompact(getView, "Color.cyan"));
        assertTrue(SourceHelper.containsCompact(getView, "Color.RED")
                || SourceHelper.containsCompact(getView, "Color.red"));
        assertTrue(getView.contains("tvDenumire")
                || SourceHelper.containsCode(getView, "R.id.tvDenumire"));
        assertTrue(SourceHelper.appearsBefore(getView, "isPlatita", "Color.CYAN")
                || SourceHelper.appearsBefore(getView, "isPlatita", "Color.RED")
                || SourceHelper.appearsBefore(getView, "isPlatita", "Color.cyan")
                || SourceHelper.appearsBefore(getView, "isPlatita", "Color.red"));
    }
}
