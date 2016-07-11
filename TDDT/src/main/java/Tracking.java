import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Tracking extends Application{
    private long test;
    private long klasse;
    public Tracking(long test, long klasse){
        this.klasse = klasse;
        this.test = test;
    }
    public static void zeichneDiagramm(long test,long klasse){
        Tracking track = new Tracking(test,klasse);
        track.start(new Stage());
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage){
        stage.setTitle("Tracking");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Dauer für die Bearbeitung von Klasse und Test");
        //xAxis.setLabel("Test Klasse");
        yAxis.setLabel("Zeit in ms");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Dauer für die Bearbeitung von Klasse und Test in ms");
        series1.getData().add(new XYChart.Data("Test", test));
        series1.getData().add(new XYChart.Data("Klasse", klasse));

        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }


    

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

}
