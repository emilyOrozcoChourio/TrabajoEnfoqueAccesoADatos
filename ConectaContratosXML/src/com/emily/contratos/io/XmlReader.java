package com.emily.contratos.io;


import com.emily.contratos.model.Contract;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XmlReader {

    // Ajusta estos nombres a las etiquetas reales del XML
    public static class TAGS {
        public static final String ROOT = "contratos";            // raíz del documento
        public static final String ITEM = "contrato";             // elemento por contrato
        public static final String NUM_EXP = "numero_expediente";
        public static final String ADJUDICATARIO = "adjudicatario";
        public static final String IMPORTE = "importe";
        public static final String FECHA = "fecha_adjudicacion";
        public static final String ORGANO = "organo_contratacion";
        public static final String TIPO = "tipo_contrato";        // se excluirá del XML de salida
        public static final String OBJETO = "objeto";
        public static final String PROCEDIMIENTO = "procedimiento";
        public static final String OBS = "observaciones";
    }

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<Contract> readFromUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        try (InputStream in = url.openStream()) {
            return parse(in);
        }
    }

    public List<Contract> parse(InputStream in) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(in);
        doc.getDocumentElement().normalize();

        List<Contract> list = new ArrayList<>();
        NodeList items = doc.getElementsByTagName(TAGS.ITEM);

        for (int i = 0; i < items.getLength(); i++) {
            Node node = items.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) node;
                Contract c = new Contract();
                c.setNumeroExpediente(getText(el, TAGS.NUM_EXP));
                c.setAdjudicatario(getText(el, TAGS.ADJUDICATARIO));
                c.setImporte(parseBigDecimal(getText(el, TAGS.IMPORTE)));
                c.setFechaAdjudicacion(parseDate(getText(el, TAGS.FECHA)));
                c.setOrganoContratacion(getText(el, TAGS.ORGANO));
                c.setTipoContrato(getText(el, TAGS.TIPO)); // guardamos en BD pero excluimos en XML de salida
                c.setObjeto(getText(el, TAGS.OBJETO));
                c.setProcedimiento(getText(el, TAGS.PROCEDIMIENTO));
                c.setObservaciones(getText(el, TAGS.OBS));
                list.add(c);
            }
        }
        return list;
    }

    private String getText(Element el, String tag) {
        NodeList nl = el.getElementsByTagName(tag);
        if (nl == null || nl.getLength() == 0) return null;
        Node n = nl.item(0);
        return n != null ? n.getTextContent().trim() : null;
    }

    private BigDecimal parseBigDecimal(String s) {
        if (s == null || s.isEmpty()) return null;
        // Limpia separadores como "1.234,56" -> "1234.56"
        String normalized = s.replace(".", "").replace(",", ".");
        try { return new BigDecimal(normalized); } catch (Exception e) { return null; }
    }

    private LocalDate parseDate(String s) {
        if (s == null || s.isEmpty()) return null;
        try { return LocalDate.parse(s, DATE_FMT); } catch (Exception e) { return null; }
    }
}
