/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VisualAssistantFDM.io;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.jdom.Element;
import vrminerlib.utils.MessageBox;

/**
 * This writer convert an XLS file to the lastest version of VRM XML
 * @author Vincent
 */
public class ExcelToXMLWriter extends VisualAssistantXMLWriter {

    private String xlsFileName;
    private Workbook xlsWorkbook;
    private Sheet xlsSheet;
    private int nbColumns;
    private int nbRows;
    private int startingDataRow;

    public ExcelToXMLWriter(String xlsFileName) {
        super(xlsFileName.replace("xls", "xml"));
        this.xlsFileName = xlsFileName;
    }

    @Override
    public void fillStructure(Element xmlStructureElement) {
        String dataString;
        int jColumn = 1;

        try {
            while (jColumn < nbColumns) {

                dataString = xlsSheet.getCell(jColumn, 0).getContents();

                //skip the column if empty
                if (dataString.isEmpty()) {
                    break;
                }

                if (dataString.contains(")") || dataString.contains("(") || dataString.contains("/") || dataString.contains("\\") || dataString.contains(" ") || dataString.contains("-") || dataString.contains("^") || dataString.substring(0, 1).contains("0") || dataString.substring(0, 1).contains("1") || dataString.substring(0, 1).contains("2") || dataString.substring(0, 1).contains("3") || dataString.substring(0, 1).contains("4") || dataString.substring(0, 1).contains("5") || dataString.substring(0, 1).contains("6") || dataString.substring(0, 1).contains("7") || dataString.substring(0, 1).contains("8") || dataString.substring(0, 1).contains("9")) {


                    dataString = dataString.replace("(", "_");
                    dataString = dataString.replace("/", "_");
                    dataString = dataString.replace("\\", "_");
                    dataString = dataString.replace(")", "_");
                    dataString = dataString.replace("-", "_");
                    dataString = dataString.replace("^", "_");
                    dataString = dataString.replace("0", "_");
                    dataString = dataString.replace("1", "_");
                    dataString = dataString.replace("2", "_");
                    dataString = dataString.replace("3", "_");
                    dataString = dataString.replace("4", "_");
                    dataString = dataString.replace("5", "_");
                    dataString = dataString.replace("6", "_");
                    dataString = dataString.replace("7", "_");
                    dataString = dataString.replace("8", "_");
                    dataString = dataString.replace("9", "_");

                    dataString = dataString.replaceAll(" ", "_");
                }
                String attributeName = dataString;

                dataString = xlsSheet.getCell(jColumn, 1).getContents();
                if (dataString.equals("")) {
                    break;
                }

                if (dataString.equalsIgnoreCase("numerique") || dataString.equalsIgnoreCase("numeric")) {
                    dataString = VisualAssistantXMLStructure.NUMERIC_TYPE_NAME;
                } else if (dataString.equalsIgnoreCase("lien") || dataString.equalsIgnoreCase("edge")) {
                    dataString = VisualAssistantXMLStructure.LINK_TYPE_NAME;
                } else if (dataString.equalsIgnoreCase("image")) {
                    dataString = VisualAssistantXMLStructure.IMAGE_TYPE_NAME;
                } else if (dataString.equalsIgnoreCase("texte") || dataString.equalsIgnoreCase("text")) {
                    dataString = VisualAssistantXMLStructure.TEXT_TYPE_NAME;
                } else if (dataString.equalsIgnoreCase("symbolique") || dataString.equalsIgnoreCase("symbolic")) {
                    dataString = VisualAssistantXMLStructure.SYMBOLIC_TYPE_NAME;
                } else if (dataString.equalsIgnoreCase("snd") || dataString.equalsIgnoreCase("sound")) {
                    dataString = VisualAssistantXMLStructure.SOUND_TYPE_NAME;
                } else {
                    dataString = VisualAssistantXMLStructure.TEMPORAL_TYPE_NAME;
                }

                String attributeType = dataString;


                //check if not old format
                if (isImportancePresent()) {
                    dataString = xlsSheet.getCell(jColumn, 2).getContents().toString();
                } else {
                    //default value
                    dataString = "0";
                }
                if (dataString.isEmpty()) {
                    break;
                }

                String attributeImportance = dataString;
                jColumn++;

                Element attribute = createAttribute(attributeName, attributeType, attributeImportance);
                xmlStructureElement.addContent(attribute);
            }

        } catch (Exception e) {
            new MessageBox("Error", "Bad structure definition", MessageBox.ERROR);
        }
    }

    @Override
    public void fillData(Element xmlDataElement) {
        String dataString;
        int iRow = startingDataRow;
        // Parse data
        try {
            while ((iRow < nbRows) && !xlsSheet.getCell(1, iRow).getContents().isEmpty()) {
                Element datum = new Element("datum");
                xmlDataElement.addContent(datum);
                Element id = new Element("id");
                datum.addContent(id);
                id.setText(String.valueOf(iRow - startingDataRow));
                int jColumn = 1;
                while (jColumn < nbColumns) {
                    dataString = xlsSheet.getCell(jColumn, 0).getContents();
                    if (dataString.isEmpty()) {
                        break;
                    }
                    if (dataString.contains(")") || dataString.contains("(") || dataString.contains("/") || dataString.contains("\\") || dataString.contains(" ") || dataString.contains("-") || dataString.contains("^") || dataString.substring(0, 1).contains("0") || dataString.substring(0, 1).contains("1") || dataString.substring(0, 1).contains("2") || dataString.substring(0, 1).contains("3") || dataString.substring(0, 1).contains("4") || dataString.substring(0, 1).contains("5") || dataString.substring(0, 1).contains("6") || dataString.substring(0, 1).contains("7") || dataString.substring(0, 1).contains("8") || dataString.substring(0, 1).contains("9")) {
                        dataString = dataString.replace("(", "_");
                        dataString = dataString.replace("/", "_");
                        dataString = dataString.replace("\\", "_");
                        dataString = dataString.replace(")", "_");
                        dataString = dataString.replace("-", "_");
                        dataString = dataString.replace("^", "_");
                        dataString = dataString.replace("0", "_");
                        dataString = dataString.replace("1", "_");
                        dataString = dataString.replace("2", "_");
                        dataString = dataString.replace("3", "_");
                        dataString = dataString.replace("4", "_");
                        dataString = dataString.replace("5", "_");
                        dataString = dataString.replace("6", "_");
                        dataString = dataString.replace("7", "_");
                        dataString = dataString.replace("8", "_");
                        dataString = dataString.replace("9", "_");

                        dataString = dataString.replaceAll(" ", "_");
                    }

                    Element Attr = new Element(dataString);
                    datum.addContent(Attr);
                    dataString = xlsSheet.getCell(jColumn, iRow).getContents();
                    if (xlsSheet.getCell(jColumn, 1).getContents().equalsIgnoreCase("numerique") || xlsSheet.getCell(jColumn, 1).getContents().equalsIgnoreCase("numeric")) {
                        dataString = dataString.replaceAll(",", ".");
                    }
                    if (xlsSheet.getCell(jColumn, 1).getContents().isEmpty()) {
                        dataString = "0";
                    }
                    Attr.setText(dataString);
                    jColumn++;
                }

                iRow++;

            }
        } catch (Exception e) {
            //TODO traduire
            new MessageBox("Erreur", "Mauvaise definition des donnees, verifiez qu'il n'y ai pas de caracteres non autorises", MessageBox.ERROR);
        }
    }

    @Override
    public void fillVisualisations(Element xmlVisualisationsElement) {
    }

    @Override
    public void fillIGA(Element xmlInterGeneticAlgorithmElement) {
    }

    private boolean isImportancePresent() {
        return xlsSheet.getCell(0, 2).getContents().equalsIgnoreCase("importance");
    }

    @Override
    public void preFilling() {
        try {
            xlsWorkbook = Workbook.getWorkbook(new File(xlsFileName));
        } catch (IOException ex) {
            Logger.getLogger(ExcelToXMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(ExcelToXMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        xlsSheet = xlsWorkbook.getSheet(0);
        nbColumns = xlsSheet.getColumns();
        nbRows = xlsSheet.getRows();

        if (isImportancePresent()) {
            startingDataRow = 4;
        } else {
            startingDataRow = 3;
        }
    }

    @Override
    public void postFilling() {
        xlsWorkbook.close();
    }
}
