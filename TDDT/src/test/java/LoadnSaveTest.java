import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.*;
import static org.junit.Assert.*;
import org.testfx.framework.junit.ApplicationTest;

public class LoadnSaveTest extends ApplicationTest {

    TextArea testtxt;
    String filename;
    @Override
    public void start(Stage Teststage) {
        testtxt = new TextArea();
        filename="ForTesting/test";
}
    @Test
    public void test_SaveThenLoadClass(){
        testtxt.setText("abcdefg");
        LoadnSave.save(filename,testtxt.getText(),false);
        testtxt.clear();
        LoadnSave.load(filename,testtxt,false);
        LoadnSave.save(filename,testtxt.getText(),false);
        assertEquals("abcdefg\n",testtxt.getText());
    }

    @Test
    public void test_SaveThenLoadTest(){
        testtxt.setText("abcdefg");
        LoadnSave.save(filename,testtxt.getText(),true);
        testtxt.clear();
        LoadnSave.load(filename,testtxt,true);
        assertEquals("abcdefg\n",testtxt.getText());
    }

    @Test
    public void test_filenotfoundsotextareawontchange(){
        testtxt.setText("abcdefg");
        testtxt.setText("123");
        filename="gibtesgarnicht";
        LoadnSave.load(filename,testtxt,false);
        assertEquals("123",testtxt.getText());
    }
}