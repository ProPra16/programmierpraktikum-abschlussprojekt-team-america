
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
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


    //
    public String erstelleJava(boolean isATest, int aufgabennummer) {

        Element WurzelElement = this.dokument.getRootElement();
        List aufgaben = WurzelElement.getChildren("excercise");
        if (isATest){
            Element tmp = (Element) aufgaben.get(aufgabennummer);
            Element tmp2 = tmp.getChild("tests");
            String filename = tmp2.getChild("test").getAttributeValue("name");

            //Abfrage, ob File schon existiert
            try {
                FileInputStream file = new FileInputStream("TestsNClasses/" + filename + ".java");
            } catch (FileNotFoundException e) {
                LoadnSave.save(filename, tmp2.getValue(), false);
            }
            return filename;
        }
        else{
            Element tmp = (Element) aufgaben.get(aufgabennummer);
            Element tmp2 = tmp.getChild("classes");
            String filename = tmp2.getChild("class").getAttributeValue("name");

            //Abfrage, ob File schon existiert
            try {
                FileInputStream file = new FileInputStream("TestsNClasses/" + filename + ".java");
            } catch (FileNotFoundException e) {
                LoadnSave.save(filename, tmp2.getValue(), false);
            }

            //LoadnSave.save(filename, tmp2.getValue(), false);
            return filename;
        }

    }

    public int findeEintragnummer(String titel){
        int nummer = -1;
        String tmp ="";
        ArrayList<String> tmpList = this.getTitles();
        for(int i = 0; i<tmpList.size(); i++){
            tmp = tmpList.get(i);
            if(tmp.equals(titel)) nummer = i;
        }
        return nummer;
    }










/*    public static void main(String[] args) throws JDOMException, IOException {
        XMLKatalog test = new XMLKatalog("Aufgabenkatalog.xml");
        ArrayList<String> names = test.getTitles();
        for(int i=0; i<names.size(); i++){
            System.out.println(names.get(i));
        }
    }*/

}
