package ro.ase.dam.examen.grading;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.nio.file.Path;

import javax.xml.parsers.DocumentBuilderFactory;

public final class LayoutXmlHelper {

    private LayoutXmlHelper() {}

    public static File moduleDir() {
        String prop = System.getProperty("app.module.dir");
        if(prop != null && !prop.isEmpty())
            return new File(prop);
        // fallback: working dir = app/ cand rulati din Studio pe modul
        File cwd = new File(System.getProperty("user.dir"));
        if(new File(cwd, "src/main/res").isDirectory())
            return cwd;
        File app = new File(cwd, "app");
        if(new File(app, "src/main/res").isDirectory())
            return app;
        throw new IllegalStateException("Nu gasesc app module dir. user.dir=" + cwd);
    }

    public static Document parseResXml(String relativeUnderRes) throws Exception {
        Path path = moduleDir().toPath().resolve("src/main/res").resolve(relativeUnderRes);
        File f = path.toFile();
        if(!f.isFile())
            throw new IllegalStateException("Lipseste: " + f.getAbsolutePath());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        return dbf.newDocumentBuilder().parse(f);
    }

    public static Element findByAndroidId(Document doc, String idLocalName) {
        String want = "@+id/" + idLocalName;
        NodeList all = doc.getElementsByTagName("*");
        for(int i = 0; i < all.getLength(); i++)
        {
            if(!(all.item(i) instanceof Element))
                continue;
            Element el = (Element) all.item(i);
            String id = el.getAttributeNS("http://schemas.android.com/apk/res/android", "id");
            if(id.isEmpty())
                id = el.getAttribute("android:id");
            if(want.equals(id))
                return el;
        }
        return null;
    }

    public static String androidAttr(Element el, String name) {
        String v = el.getAttributeNS("http://schemas.android.com/apk/res/android", name);
        if(v == null || v.isEmpty())
            v = el.getAttribute("android:" + name);
        return v;
    }

    public static String appAttr(Element el, String name) {
        String v = el.getAttributeNS("http://schemas.android.com/apk/res-auto", name);
        if(v == null || v.isEmpty())
            v = el.getAttribute("app:" + name);
        return v;
    }
}
