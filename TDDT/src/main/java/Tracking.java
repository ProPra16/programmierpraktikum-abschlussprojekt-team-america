import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Tracking {

    public static void schreibeLog(String name, String output, TextArea testtxt) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(new Date());
        FileWriter file = new FileWriter("Log"+name+".txt", true);
        String klasseTest = "";
        if(!testtxt.isDisabled()){
            klasseTest = " (Test geaendert) ";
        }
        else{
            klasseTest = " (Klasse geaendert) ";
        }

        file.append(date+klasseTest+": "+output+"\n");
        file.close();

    }

    public static void zeichneDiagramm(long testZeit, long klasseZeit){

    }
}
