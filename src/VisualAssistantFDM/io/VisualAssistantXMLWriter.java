/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualAssistantFDM.io;

import java.io.FileOutputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.*;

/**
 * Write a VRM XML file, writerVersion should be the latest.
 *
 * When passing a Document created from an existing VRM XML file to the constructor, the document must be conform to the lastest version before filling.
 * If no Document is passed (i.e. null) the writer create an empty VRM XML document that will be filled by abstract methods and finaly write the new file
 * @author Abdelheq
 */
public abstract class VisualAssistantXMLWriter {

  
    private VisualAssistantXMLReader reader;
    private String xmlOutputFileName;

    /**
     * Create a Writer based on an empty XML file
     * @param xmlOutputFileName the name of the file to create when calling writeXML method
     */
    public VisualAssistantXMLWriter(String xmlOutputFileName) {
        this(null, xmlOutputFileName);
    }

    /**
     * Create a Writer based on an existing XML file
     * @param xmlBaseDocument can be null to indicate there is no existing XML file
     * @param xmlOutputFileName the name of the file to create when calling writeXML method
     */
    public VisualAssistantXMLWriter(Document xmlBaseDocument, String xmlOutputFileName) {
        this.xmlOutputFileName = xmlOutputFileName;
//        this.xmlOutputDocument = createBaseOutputDocument(xmlBaseDocument);
        reader = new VisualAssistantXMLReader(createBaseOutputDocument(xmlBaseDocument));
    }

    private Document createBaseOutputDocument(Document xmlBaseDocument) {
        Document xmlOutputDocument = xmlBaseDocument;
        if (xmlBaseDocument == null) {
            //the file doesn't exist
            Element xmlRootElement = new Element(VisualAssistantXMLStructure.ROOT_ELEMENT_NAME);
            xmlRootElement.setAttribute("version", VisualAssistantXMLStructure.WRITER_VERSION);
            Element xmlStructureElement = new Element(VisualAssistantXMLStructure.STRUCTURE_ELEMENT_NAME);
            Element xmlDataElement = new Element(VisualAssistantXMLStructure.DATA_ELEMENT_NAME);
            Element xmlVisualisationsElement = new Element(VisualAssistantXMLStructure.VISUALIZATIONS_ELEMENT_NAME);
            Element xmlInterGeneticAlgorithmElement = new Element(VisualAssistantXMLStructure.IGA_ELEMENT_NAME);
            xmlOutputDocument = new Document(xmlRootElement);

            xmlRootElement.addContent(xmlStructureElement);
            xmlRootElement.addContent(xmlDataElement);
            xmlRootElement.addContent(xmlVisualisationsElement);
            xmlRootElement.addContent(xmlInterGeneticAlgorithmElement);
        }
        return xmlOutputDocument;
    }

    public static boolean isVRMXMLLatestVersion(Document documentToTest) {
        if (documentToTest.getRootElement().getAttributeValue("version") != null && documentToTest.getRootElement().getAttributeValue("version").equalsIgnoreCase(VisualAssistantXMLStructure.WRITER_VERSION)) {
            return true;
        }
        return false;
    }

    public abstract void preFilling();

    public abstract void fillStructure(Element xmlStructureElement);

    public abstract void fillData(Element xmlDataElement);

    public abstract void fillVisualisations(Element xmlVisualisationsElement);

    public abstract void fillIGA(Element xmlInterGeneticAlgorithmElement);

    public abstract void postFilling();

    /**
     * Desing pattern Template Method
     * @throws IOException
     */
    public void writeXMLFile() throws IOException {
        fillDocument();
        //build the xml        
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        FileOutputStream fileOutputStream = new FileOutputStream(xmlOutputFileName);
        sortie.output(reader.getXMLDocument(), fileOutputStream);
        fileOutputStream.close();
    }

    public Document writeXMLDocument() {
        fillDocument();
        return reader.getXMLDocument();
    }

    private void fillDocument() {
        //user defined methods
        preFilling();
        fillStructure(
                reader.getXmlStructureElement());
        fillData(
                reader.getXmlDataElement());
        fillVisualisations(
                reader.getXmlVisualisationsElement());
        fillIGA(
                reader.getxmlInterGeneticAlgorithmElement());
        postFilling();
    }

    protected Element createAttribute(String attributeName, String attributeType, String attributeImportance){
        Element attributeElement = new Element(VisualAssistantXMLStructure.ATTRIBUTE_ELEMENT_NAME);
        Element attributeNameElement= new Element(VisualAssistantXMLStructure.ATTRIBUTE_NAME_ELEMENT_NAME);
        Element attributeTypeElement= new Element(VisualAssistantXMLStructure.ATTRIBUTE_TYPE_ELEMENT_NAME);
        Element attributeImportanceElement= new Element(VisualAssistantXMLStructure.ATTRIBUTE_IMPORTANCE_ELEMENT_NAME);

        attributeNameElement.setText(attributeName);
        attributeTypeElement.setText(attributeType);
        attributeImportanceElement.setText(attributeImportance);

        attributeElement.addContent(attributeNameElement);
        attributeElement.addContent(attributeTypeElement);
        attributeElement.addContent(attributeImportanceElement);
        return attributeElement;
    }

}
