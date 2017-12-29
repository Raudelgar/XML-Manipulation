package com.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLStructure {
    private Logger log = LoggerFactory.getLogger(XMLStructure.class);

    private String path;
    private List<String> values = new ArrayList<String>();
    private File input = null;
    private NodeList nodeList = null;
    private Document doc;

    public XMLStructure(String path) {
        this.path = path;
    }

    /**
     * Initialized xml file read
     */

    public void init() {
        try {
            input = new File(String.valueOf(path));
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setIgnoringComments(true);
            docFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.parse(input);
            doc.getDocumentElement().normalize();

            nodeList = doc.getDocumentElement().getChildNodes();
            log.info("The xml file is upload successfully");

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            log.error("Error on the Parse Configuration");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException");
        } catch (SAXException e) {
            e.printStackTrace();
            log.error("SAXException");
        }
    }

    /**
     * Read the entire xml file, through all nodes, elements and sub-elements
     * searching for the string key parameter.
     * @param key
     * @return
     */

    public String getXMLElement(String key) {

        init();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                //To avoid #element empty when reading xml file
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    if (key.equalsIgnoreCase(node.getNodeName())) {
                        values.add(node.getTextContent());
                    }

                    NodeList subNodeList = node.getChildNodes();
                    for (int j = 0; j < subNodeList.getLength(); j++) {
                        Node subNode = subNodeList.item(j);
                        if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                            if (key.equalsIgnoreCase(subNode.getNodeName())) {
                                values.add(subNode.getTextContent());
                            }

                            NodeList elementList = subNode.getChildNodes();
                            for (int k = 0; k < elementList.getLength(); k++) {
                                Node element = elementList.item(k);
                                if (element.getNodeType() == Node.ELEMENT_NODE) {
                                    if (key.equalsIgnoreCase(element.getNodeName())) {
                                        values.add(element.getTextContent());
                                    }

                                    NodeList subElementList = element.getChildNodes();
                                    for (int l = 0; l < subElementList.getLength(); l++) {
                                        Node subElement = subElementList.item(l);
                                        if (subElement.getNodeType() == Node.ELEMENT_NODE) {
                                            if (key.equalsIgnoreCase(subElement.getNodeName())) {
                                                values.add(subElement.getTextContent());
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

            }

        log.info(toStringValues());
        return toStringValues();
    }

    public void updateXmlValue(String tag,String subTag,Object value) {
        init();
        Node node = doc.getElementsByTagName(tag).item(0);
        nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength() ; i++) {
            Node subNode = nodeList.item(i);
            if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eNode = (Element) subNode;
                if (subTag.equalsIgnoreCase(eNode.getNodeName())) {
                    eNode.setTextContent(String.valueOf(value));
                }
            }
        }
        //Write back into xml file
        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(String.valueOf(input)));
            transformer.transform(source,result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            log.error("Error Transformer Configuration");
        } catch (TransformerException e) {
            e.printStackTrace();
            log.error("Transformer Exception");
        }

        log.info("Update Xml Value Successful");
    }

    public void createXmlFile() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();
            Element rootElement = document.createElement();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String toStringValues() {
        String result = "Output: \n";
        for (int i = 0; i < values.size(); i++) {
            result += " "+values.get(i);
        }
        return result+"\n";
    }

}
