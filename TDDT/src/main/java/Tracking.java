import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Tracking extends Application{
    private long test;
    private long klasse;
    private long refactor;
    private String name;
    public Tracking(String name, long test, long klasse, long refactor){
        this.klasse = klasse;
        this.test = test;
        this.name = name;
        this.refactor = refactor;
    }
    public static void zeichneDiagramm(String name, long test,long klasse, long refactor){
        Tracking track = new Tracking(name,test,klasse,refactor);
        track.start(new Stage());
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage){
        stage.setTitle("Tracking");
        GridPane grid = new GridPane();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Dauer für die Bearbeitung von Klasse und Test");
        //xAxis.setLabel("Test Klasse");
        yAxis.setLabel("Zeit in ms");

        XYChart.Series series = new XYChart.Series();
        series.setName("Dauer für die Bearbeitung von Klasse und Test in ms");
        bc.setStyle("-fx-bar-fill: navy;");
        series.getData().add(new XYChart.Data("Test", test));
        series.getData().add(new XYChart.Data("Klasse", klasse));
        series.getData().add(new XYChart.Data("Refactor", refactor));

        //Buuton Close
        Button close = new Button("Schliessen");
        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });
        GridPane.setConstraints(close, 1, 1);
        grid.getChildren().add(close);

        //Button speichern
        Button safe = new Button("Speichern");
        safe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WritableImage image = bc.snapshot(new SnapshotParameters(), null);
                File file = new File("Tracking/"+"Chart"+name+".png");

                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                } catch (IOException e) {
             
                }
            }
        });
        GridPane.setConstraints(safe, 1, 2);
        grid.getChildren().add(safe);



        GridPane.setConstraints(bc, 0, 1);
        grid.getChildren().add(bc);
        Scene scene  = new Scene(grid,800,600);
        bc.getData().addAll(series);
        bc.setCategoryGap(20);
        stage.setScene(scene);
        stage.show();
    }


    

    public static void schreibeLog(String name, String output, TextArea testtxt) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String date = sdf.format(new Date());
        FileWriter file = new FileWriter("Tracking/"+"Log"+name+".txt", true);
        String klasseTest = "";
        if(!testtxt.isDisabled()){
            klasseTest = " (Test geaendert) ";
        }
        else{
            klasseTest = " (Klasse geaendert) ";
        }

        file.append(date+klasseTest+": "+output+"\n"+"\n");
        file.close();

    }

}
