package sample;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import vk.core.api.*;
import vk.core.internal.InternalCompiler;
import vk.core.internal.InternalResult;

import java.util.*;

public class Main extends Application {
    public static Stage fenster;
    public static Scene rot;
    public static VBox aufbau;
    public static TextArea testtxt;
    public static TextArea classtxt;
    public static ComboBox<String> Katalog;
    public static XMLKatalog aufgaben;

    @Override
    public void start(Stage primaryStage) throws Exception{
        fenster=primaryStage;
        fenster.setTitle("Test");
        aufbau=new VBox(20);
        Button compile=new Button("Compi");
            Button saveclass=new Button("Klasse Speichern");
            Button loadclass=new Button("Klasse Laden");
                Button savetest=new Button("Test Speichern");
                Button loadtest=new Button("Laden");
        testtxt=new TextArea();
        testtxt.setPrefColumnCount(75);
        testtxt.setPrefRowCount(30);
            classtxt=new TextArea();
            classtxt.setPrefColumnCount(75);
            classtxt.setPrefRowCount(30);

        //lade den Katalog
        this.aufgaben = new XMLKatalog("Aufgabenkatalog.xml");

        //xml -titel- laden
        ArrayList<String> aufgabenTitel = aufgaben.getTitles();
        Katalog=new ComboBox<String>();
        Katalog.getItems().addAll(aufgabenTitel);

        Katalog.setOnAction(e ->{
            int aktuelleAufgabe = aufgaben.findeEintragnummer(Katalog.getValue());
            String testDateiname = aufgaben.erstelleJava(true, aktuelleAufgabe);
            String klasseDateiname = aufgaben.erstelleJava(false, aktuelleAufgabe);
            LoadnSave.load(testDateiname, testtxt, false);
            LoadnSave.load(klasseDateiname, classtxt, false);
            //System.out.println(aktuelleAufgabe);

        });

        HBox h1=new HBox(20);
        HBox h2=new HBox(20);
        h1.getChildren().addAll(classtxt,Katalog,testtxt);
        h2.getChildren().addAll(saveclass,loadclass,compile,savetest,loadtest);
        aufbau.getChildren().addAll(h1,h2);
        rot =new Scene(aufbau);
        fenster.setScene(rot);
        fenster.show();


        compile.setOnAction(e->{String klasse=classtxt.getText();String testo=testtxt.getText();compillll(klasse,testo);});
        saveclass.setOnAction(e->LoadnSave.save("DNA",classtxt.getText(),false));
        loadclass.setOnAction(e->LoadnSave.load("DNA",classtxt,false));
        savetest.setOnAction(e->LoadnSave.save("DNA",testtxt.getText(),true));
        loadtest.setOnAction(e->LoadnSave.load("DNA",testtxt,true));
    }


    public void compillll(String klasse, String test){
        CompilationUnit b=new CompilationUnit("DNA",klasse,false);
        CompilationUnit c=new CompilationUnit("DNATest",test,true);
        CompilationUnit[] cs={b,c};
        InternalCompiler compiii= new InternalCompiler(cs);
        compiii.compileAndRunTests();
        TestResult res=compiii.getTestResult();
        System.out.println("Anzahl Erfolgreiche Tests: "+res.getNumberOfSuccessfulTests());
        System.out.println("Anzahl Fehlgeschlong Tests: "+res.getNumberOfFailedTests());
        if (res.getNumberOfFailedTests()!=0){
            System.out.print("Welche Test sind Fehlgeschlong:");
        }
        for (TestFailure fail : res.getTestFailures()) {
            System.out.print(" "+fail.getMethodName());
        }
        System.out.println();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
