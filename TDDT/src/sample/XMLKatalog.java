//hierfür wird jdom2 benötigt (siehe Maven)

package sample;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLKatalog {

    private XMLOutputter katalog;
    private Document dokument;

    //Katalognamen mit ".xml" am Ende eingeben
    public XMLKatalog(String katalogname) throws JDOMException, IOException {
        dokument = null;
        File datei = new File(katalogname);
        SAXBuilder builder = new SAXBuilder();
        this.dokument = builder.build(datei);
        this.katalog = new XMLOutputter();
    }

    public void printWholeDocument() throws IOException {
        this.katalog.output(this.dokument, System.out);
    }

    public ArrayList<String> getTitles(){
        ArrayList<String> names = new ArrayList<String>();
        Element WurzelElement = this.dokument.getRootElement();
        List aufgaben = WurzelElement.getChildren("excercise");
        for(int i = 0; i<aufgaben.size(); i++) {
            Element tmp = (Element) aufgaben.get(i);
            names.add(tmp.getAttributeValue("name"));
            //System.out.println(names.get(i));

        }
        return names;
    }









    public static void main(String[] args) throws JDOMException, IOException {
        XMLKatalog test = new XMLKatalog("Aufgabenkatalog.xml");
        //test.getTitles();
    }

}
