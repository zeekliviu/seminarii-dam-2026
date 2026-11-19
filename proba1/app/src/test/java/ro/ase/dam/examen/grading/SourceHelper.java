package ro.ase.dam.examen.grading;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SourceHelper {

    private SourceHelper() {}

    public static String readMainJava(String relativeUnderMainJava) throws Exception {
        File f = new File(LayoutXmlHelper.moduleDir(),
                "src/main/java/" + relativeUnderMainJava);
        if(!f.isFile())
            throw new IllegalStateException("Lipseste sursa: " + f.getAbsolutePath());
        return Files.readString(f.toPath(), StandardCharsets.UTF_8);
    }

    public static String stripComments(String src) {
        String faraBloc = src.replaceAll("/\\*[\\s\\S]*?\\*/", " ");
        return faraBloc.replaceAll("//.*", " ");
    }

    public static String compact(String src) {
        return stripComments(src).replaceAll("\\s+", " ");
    }

    public static boolean containsCode(String src, String snippet) {
        return stripComments(src).contains(snippet);
    }

    public static boolean containsCompact(String src, String snippetCompact) {
        return compact(src).contains(snippetCompact);
    }

    /** true daca a apare inainte de b in sursa fara comentarii */
    public static boolean appearsBefore(String src, String a, String b) {
        String s = stripComments(src);
        int ia = s.indexOf(a);
        int ib = s.indexOf(b);
        return ia >= 0 && ib >= 0 && ia < ib;
    }

    public static boolean near(String src, String a, String b, int maxDistance) {
        String s = compact(src);
        int ia = s.indexOf(a);
        int ib = s.indexOf(b);
        return ia >= 0 && ib >= 0 && Math.abs(ia - ib) <= maxDistance;
    }

    /**
     * Extrage corpul metodei care contine semnatura (ex. "getView"), pana la inchiderea
     * acoladelor corespunzatoare. Gol daca nu gaseste.
     */
    public static String methodBody(String src, String methodNameHint) {
        String s = stripComments(src);
        Pattern p = Pattern.compile(
                "\\b" + Pattern.quote(methodNameHint) + "\\s*\\([^;{]*\\)\\s*(?:throws\\s+[^{]+)?\\{");
        Matcher m = p.matcher(s);
        if(!m.find())
            return "";
        int open = m.end() - 1;
        return extractBalanced(s, open);
    }

    /** Corpul aproximativ dupa primul apel marker (ex. setPositiveButton), pana la ');' al builder-ului e greu - luam fereastra. */
    public static String windowAfter(String src, String marker, int length) {
        String s = stripComments(src);
        int i = s.indexOf(marker);
        if(i < 0)
            return "";
        int end = Math.min(s.length(), i + marker.length() + length);
        return s.substring(i, end);
    }

    public static String extractBalanced(String s, int openBraceIndex) {
        if(openBraceIndex < 0 || openBraceIndex >= s.length() || s.charAt(openBraceIndex) != '{')
            return "";
        int depth = 0;
        for(int i = openBraceIndex; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if(c == '{')
                depth++;
            else if(c == '}')
            {
                depth--;
                if(depth == 0)
                    return s.substring(openBraceIndex, i + 1);
            }
        }
        return s.substring(openBraceIndex);
    }

    public static List<String> menuIdsBesides(String excludeLocalId) throws Exception {
        org.w3c.dom.Document doc = LayoutXmlHelper.parseResXml("menu/meniu_principal.xml");
        org.w3c.dom.NodeList items = doc.getElementsByTagName("item");
        List<String> ids = new ArrayList<>();
        for(int i = 0; i < items.getLength(); i++)
        {
            org.w3c.dom.Element el = (org.w3c.dom.Element) items.item(i);
            String id = LayoutXmlHelper.androidAttr(el, "id");
            if(id == null || id.isEmpty())
                continue;
            String local = id.replace("@+id/", "").replace("@id/", "");
            if(!local.equals(excludeLocalId))
                ids.add(local);
        }
        return ids;
    }

    public static boolean menuItemHasTitle(String menuLocalId) throws Exception {
        org.w3c.dom.Document doc = LayoutXmlHelper.parseResXml("menu/meniu_principal.xml");
        org.w3c.dom.NodeList items = doc.getElementsByTagName("item");
        for(int i = 0; i < items.getLength(); i++)
        {
            org.w3c.dom.Element el = (org.w3c.dom.Element) items.item(i);
            String id = LayoutXmlHelper.androidAttr(el, "id");
            if(id == null)
                continue;
            String local = id.replace("@+id/", "").replace("@id/", "");
            if(!local.equals(menuLocalId))
                continue;
            String title = LayoutXmlHelper.androidAttr(el, "title");
            return title != null && !title.isEmpty();
        }
        return false;
    }

    public static boolean handlerReferencesMenuId(String mainActivitySrc, String menuLocalId) {
        String s = stripComments(mainActivitySrc);
        return s.contains("R.id." + menuLocalId);
    }

    /** @deprecated prefer {@link #methodBody} */
    public static String methodBodyContaining(String src, String methodSignatureHint) {
        String body = methodBody(src, methodSignatureHint);
        if(!body.isEmpty())
            return body;
        String s = stripComments(src);
        int start = s.indexOf(methodSignatureHint);
        if(start < 0)
            return "";
        return s.substring(start);
    }

    public static boolean matches(String src, String regex) {
        return Pattern.compile(regex, Pattern.DOTALL).matcher(stripComments(src)).find();
    }
}
