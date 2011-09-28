/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualAssistantFDM.io;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import vrminerlib.io.converter.XMLDocumentConverterUtility;
import vrminerlib.utils.MessageBox;

/**
 *
 * @author Abdelheq
 */


public class VisualAssistantXMLReader {

    private Element xmlStructureElement;
    private Element xmlDataElement;
    private Element xmlVisualisationsElement;
    private Element xmlInterGeneticAlgorithmElement;
    private Document xmlDocument;

    /**
     * Create a Reader based on the file name
     * @param xmlInputFileName the name of the file to read
     */
    public VisualAssistantXMLReader(String xmlInputFileName) {
        SAXBuilder sxb = new SAXBuilder();
        try {
            xmlDocument = sxb.build(new File(xmlInputFileName));
        } catch (Exception ex) {
            Logger.getLogger(VisualAssistantXMLReader.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        //conversion test (with file overwrite)
        if (!isVRMXMLLatestVersion(xmlDocument)) {
            new MessageBox("XML Conversion required", "The file " + getXMLDocument().getBaseURI() + " need to be converted to the latest version of VRMiner XML file format.\n Please, ensure that the file is on a writable device and make a backup now if you want; the file will be overwritten.", MessageBox.INFO);
            xmlDocument = XMLDocumentConverterUtility.convertToLastVersion(xmlDocument);
            try {
                new VisualAssistantXMLWriterAdapter(getXMLDocument(), xmlInputFileName).writeXMLFile();
            } catch (Exception ex) {
                Logger.getLogger(VisualAssistantXMLReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        mapElements();
    }

    /**
     * Create a Reader based on an existing XML Document
     * Test if the document is in the latest VRMiner XML format
     * @param xmlBaseDocument can be null to indicate there is no existing XML file
     * @param xmlOutputFileName the name of the file to create when calling writeXML method
     */
    public VisualAssistantXMLReader(Document xmlBaseDocument) {
        this.xmlDocument = xmlBaseDocument;

        //conversion test, auto convert, no file overwrite
        if (!isVRMXMLLatestVersion(xmlDocument)) {
            Logger.getAnonymousLogger().info("Internal XML Conversion required, "+xmlBaseDocument+" need to be converted to the latest version of VRMiner XML file format.");
            xmlDocument = XMLDocumentConverterUtility.convertToLastVersion(xmlDocument);
            Logger.getAnonymousLogger().info("Internal XML Conversion done.");
        }

        mapElements();        
    }

    private void mapElements() {
        //map the main elements
        xmlStructureElement = xmlDocument.getRootElement().getChild(VisualAssistantXMLStructure.STRUCTURE_ELEMENT_NAME);
        xmlDataElement = xmlDocument.getRootElement().getChild(VisualAssistantXMLStructure.DATA_ELEMENT_NAME);
        xmlVisualisationsElement = xmlDocument.getRootElement().getChild(VisualAssistantXMLStructure.VISUALIZATIONS_ELEMENT_NAME);
        xmlInterGeneticAlgorithmElement = xmlDocument.getRootElement().getChild(VisualAssistantXMLStructure.IGA_ELEMENT_NAME);
    }

    public static boolean isVRMXMLLatestVersion(Document documentToTest) {
        if (documentToTest.getRootElement().getAttributeValue("version") != null && documentToTest.getRootElement().getAttributeValue("version").equalsIgnoreCase(VisualAssistantXMLStructure.WRITER_VERSION)) {
            return true;
        }
        return false;
    }

    /**
     * @return the xmlStructureElement
     */
    public final Element getXmlStructureElement() {
        return xmlStructureElement;
    }

    /**
     * @return the xmlDataElement
     */
    public final Element getXmlDataElement() {
        return xmlDataElement;
    }

    /**
     * @return the xmlVisualisationsElement
     */
    public final Element getXmlVisualisationsElement() {
        return xmlVisualisationsElement;
    }

    /**
     * @return the xmlVisualisationsElement
     */
    public final Element getxmlInterGeneticAlgorithmElement() {
        return xmlInterGeneticAlgorithmElement;
    }

    /**
     * @return the xmlDocument
     */
    public final Document getXMLDocument() {
        return xmlDocument;
    }

}
