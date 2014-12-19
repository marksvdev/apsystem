package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by marksv on 12/19/2014.
 */
public class XMLManager {
    public static Document generateNewDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("state");
            doc.appendChild(root);
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Document loadXMLFromString(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            return builder.parse(is);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getValue(Document doc, String name) {
        try {
            return getRoot(doc).getElementsByTagName(name).item(0).getTextContent();
        } catch (Exception e) {
            // name does not exist
            return null;
        }
    }

    public static Document insert(Document doc, String name, String value) {
        if (elementExists(doc, name)) {
            // if name does not exist: create new element and insert into document with name and value
            Element pump_sn = doc.createElement(name);
            pump_sn.appendChild(doc.createTextNode(value));
            getRoot(doc).appendChild(pump_sn);
            return doc;
        }

        // if name does exist: update existing name with value
        return update(doc, name, value);
    }

    public static String DocumentToString(Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = null;
            transformer = tf.newTransformer();
//            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString().replaceAll("\n|\r", "");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean elementExists(Document doc, String name) {
        return getRoot(doc).getElementsByTagName(name).getLength() == 0;
    }

    private static Document update(Document doc, String name, String value) {
        Node node = getRoot(doc).getElementsByTagName(name).item(0);
        node.setTextContent(value);
        return doc;
    }

    private static Element getRoot(Document doc) {
        Node state = doc.getElementsByTagName("state").item(0);
        return (Element) state;
    }
}
