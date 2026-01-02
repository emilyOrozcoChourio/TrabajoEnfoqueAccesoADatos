package com.emily.contratos.io;


import com.emily.contratos.model.Contract;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.List;

public class XmlWriter {

    public void writeWithoutTipoContrato(List<Contract> contracts, File outFile) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        // Raíz
        Element root = doc.createElement("contratos");
        doc.appendChild(root);

        for (Contract c : contracts) {
            Element eContrato = doc.createElement("contrato");

            append(doc, eContrato, "numero_expediente", c.getNumeroExpediente());
            append(doc, eContrato, "adjudicatario", c.getAdjudicatario());
            append(doc, eContrato, "importe", c.getImporte() != null ? c.getImporte().toString() : null);
            append(doc, eContrato, "fecha_adjudicacion", c.getFechaAdjudicacion() != null ? c.getFechaAdjudicacion().toString() : null);
            append(doc, eContrato, "organo_contratacion", c.getOrganoContratacion());

            // IMPORTANTE: No escribir "tipo_contrato" en el XML de salida
            // append(doc, eContrato, "tipo_contrato", c.getTipoContrato()); // EXCLUIDO

            append(doc, eContrato, "objeto", c.getObjeto());
            append(doc, eContrato, "procedimiento", c.getProcedimiento());
            append(doc, eContrato, "observaciones", c.getObservaciones());

            root.appendChild(eContrato);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // Ajuste de indentación para que sea legible
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(new DOMSource(doc), new StreamResult(outFile));
    }

    private void append(Document doc, Element parent, String tag, String value) {
        Element child = doc.createElement(tag);
        child.appendChild(doc.createTextNode(value != null ? value : ""));
        parent.appendChild(child);
    }
}
