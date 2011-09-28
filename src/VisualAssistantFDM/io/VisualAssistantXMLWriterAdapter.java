/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualAssistantFDM.io;

import org.jdom.Document;
import org.jdom.Element;

/**
 *
 * @author Abdelheq
 */
public class VisualAssistantXMLWriterAdapter extends VisualAssistantXMLWriter{
    
    public VisualAssistantXMLWriterAdapter(String xmlOutputFileName) {
        super(xmlOutputFileName);
    }

    public VisualAssistantXMLWriterAdapter(Document xmlBaseDocument, String xmlOutputFileName) {
        super(xmlBaseDocument, xmlOutputFileName);
    }

    @Override
    public void preFilling() {        
    }

    @Override
    public void fillStructure(Element xmlStructureElement) {        
    }

    @Override
    public void fillData(Element xmlDataElement) {        
    }

    @Override
    public void fillVisualisations(Element xmlVisualisationsElement) {        
    }

    @Override
    public void fillIGA(Element xmlInterGeneticAlgorithmElement) {
    }

    @Override
    public void postFilling() {        
    }
    
}
