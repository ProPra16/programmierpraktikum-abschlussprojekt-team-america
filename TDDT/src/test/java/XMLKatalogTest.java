import org.jdom2.JDOMException;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class XMLKatalogTest {

    XMLKatalog test = new XMLKatalog("TestKatalog.xml");

    public XMLKatalogTest() throws JDOMException, IOException {
    }
    //test = new XMLKatalog("TestKatalog.xml");

    @Test
    public void test_getTitles(){
        String ersterTitel = "Roemische Zahlen";
        String zweiterTitel = "Zweite Aufgabe!";
        assertEquals(ersterTitel, test.getTitles().get(0));
        assertEquals(zweiterTitel, test.getTitles().get(1));

    }

    @Test
    public void testFnF() throws JDOMException, IOException {
        try{
            XMLKatalog test2 = new XMLKatalog("gibtesnicht.xml");
            fail();
        }
        catch(FileNotFoundException e){

        }
    }

    @Test
    public void test_findeEintragsnummer(){
        String titel = "Zweite Aufgabe!";
        assertEquals(1, test.findeEintragnummer(titel));

    }
}
