
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import vk.core.api.*;
import vk.core.internal.InternalCompiler;
import vk.core.internal.InternalResult;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main extends Application {
    public static Stage fenster;
    public static Scene rot;
    public static VBox aufbau;
    public static TextArea testtxt;
    public static TextArea classtxt;
    public static ComboBox<String> Katalog;
    public static XMLKatalog aufgaben;
    public static String klasseDateiname = "";
    public static Button Rot;
    public static Button Gruen;
    public static Button refactor;
    public static Label state;
    public static long testZeit;
    public static long klasseZeit;
    public static long refactorZeit;
    public static long stopper;
    public static long differenz;
    @Override
    public void start(Stage primaryStage) throws Exception{
        fenster=primaryStage;
        fenster.setTitle("Test");
        aufbau=new VBox(20);
        Button compile=new Button("Compi");
        Button loadaufgabe=new Button("Aufgabe laden");
        loadaufgabe.setDisable(true);
        Rot=new Button("Rot");
        Rot.setDisable(true);
        Gruen=new Button("Grün");
        Gruen.setDisable(true);
        Button saveclass=new Button("Klasse Speichern");
        Button loadclass=new Button("Klasse Laden");
        Button savetest=new Button("Test Speichern");
        Button loadtest=new Button("Laden");
        refactor=new Button("Refactor");
        refactor.setDisable(true);
        state=new Label();
        state.setText("Test");
        state.setTextFill(Color.RED);
        testtxt=new TextArea();
        testtxt.setPrefColumnCount(50);
        testtxt.setPrefRowCount(30);
        testtxt.setDisable(true);
            classtxt=new TextArea();
            classtxt.setPrefColumnCount(50);
            classtxt.setPrefRowCount(30);
            classtxt.setDisable(true);

        //lade den Katalog
        this.aufgaben = new XMLKatalog("Aufgabenkatalog.xml");

        //xml -titel- laden
        ArrayList<String> aufgabenTitel = aufgaben.getTitles();
        Katalog=new ComboBox<String>();
        Katalog.setPromptText("Aufgaben");
        Katalog.getItems().addAll(aufgabenTitel);

      /*  Katalog.setOnAction(e ->{
            int aktuelleAufgabe = aufgaben.findeEintragnummer(Katalog.getValue());
            //System.out.println(aktuelleAufgabe);
            String testDateiname = aufgaben.erstelleJava(true, aktuelleAufgabe);
            klasseDateiname = aufgaben.erstelleJava(false, aktuelleAufgabe);
            LoadnSave.load(testDateiname, testtxt, false);
            LoadnSave.load(klasseDateiname, classtxt, false);
            //System.out.println(aktuelleAufgabe);

        });*/
        Katalog.setOnAction(e->loadaufgabe.setDisable(false));

        VBox menu=new VBox(20);
        HBox h1=new HBox(20);
        HBox h2=new HBox(20);
        h2.getChildren().addAll(Rot,Gruen);
        menu.getChildren().addAll(Katalog,loadaufgabe,h2,refactor,saveclass,loadclass,savetest,loadtest,state,compile);
        h1.getChildren().addAll(classtxt,menu,testtxt);
        aufbau.getChildren().addAll(h1);
        rot =new Scene(aufbau);
        fenster.setScene(rot);
        fenster.show();

        loadaufgabe.setOnAction(e->{
            klasseZeit = 0;
            testZeit = 0;
            refactorZeit = 0;
            stopper = System.currentTimeMillis();

            int aktuelleAufgabe = aufgaben.findeEintragnummer(Katalog.getValue());
            String testDateiname = aufgaben.erstelleJava(true, aktuelleAufgabe);
            klasseDateiname = aufgaben.erstelleJava(false, aktuelleAufgabe);
            LoadnSave.load(testDateiname, testtxt, false);
            LoadnSave.load(klasseDateiname, classtxt, false);
            zuRot();
            Gruen.setDisable(true);
            loadaufgabe.setDisable(true);

        });
        compile.setOnAction(e->{String klasse=classtxt.getText();String testo=testtxt.getText();
            try {
                compillll(klasse,testo);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        saveclass.setOnAction(e->LoadnSave.save(klasseDateiname,classtxt.getText(),false));
        loadclass.setOnAction(e->{System.out.println(klasseDateiname);LoadnSave.load(klasseDateiname,classtxt,false);});
        savetest.setOnAction(e->LoadnSave.save(klasseDateiname,testtxt.getText(),true));
        loadtest.setOnAction(e->LoadnSave.load(klasseDateiname,testtxt,true));
        Rot.setOnAction(e->{zuRot();});
        Gruen.setOnAction(e->{zuGruen();});
        refactor.setOnAction(e->{zuRefactor();});
    }



    public static void zuRot(){
        testtxt.setDisable(false);
        classtxt.setDisable(true);
        Rot.setDisable(true);
        refactor.setDisable(true);
        state.setText("Test");
        state.setTextFill(Color.RED);

    }

    public void zuGruen(){
        classtxt.setDisable(false);
        testtxt.setDisable(true);
        Gruen.setDisable(true);
        state.setText("Klasse");
        state.setTextFill(Color.GREEN);
    }

    public void zuRefactor(){
        refactor.setDisable(true);
        Rot.setDisable(true);
        state.setText("Refactor");
        state.setTextFill(Color.GREY);
    }




    public  void compillll(String klasse, String test) throws IOException {
        CompilationUnit b=new CompilationUnit(klasseDateiname,klasse,false);
        CompilationUnit c=new CompilationUnit(klasseDateiname+"Test",test,true);
        String output="";
        CompilationUnit[] cs={b,c};
        InternalCompiler compiii= new InternalCompiler(cs);
        compiii.compileAndRunTests();
        CompilerResult comres=compiii.getCompilerResult();
        if(!comres.hasCompileErrors()) {
            TestResult res = compiii.getTestResult();
            output=output+"\nAnzahl Erfolgreiche Tests: " + res.getNumberOfSuccessfulTests();
            output=output+"\nAnzahl Fehlgeschlagen Tests: " + res.getNumberOfFailedTests();
            //System.out.println("Anzahl Erfolgreiche Tests: " + res.getNumberOfSuccessfulTests());
            //System.out.println("Anzahl Fehlgeschlong Tests: " + res.getNumberOfFailedTests());
            if (res.getNumberOfFailedTests() != 0) {
                if (res.getNumberOfFailedTests() == 1 && state.getText().equals("Test")){
                    Gruen.setDisable(false);
                }
                output=output+"\nWelche Tests sind Fehlgeschlagen: ";
            }

            else{
                if(state.getText().equals("Klasse")) {
                    refactor.setDisable(false);
                    Rot.setDisable(false);
                }
            }
            for (TestFailure fail : res.getTestFailures()) {
                output = output+ fail.getMethodName();
            }
            AlertBox.display("TestFehler", output);
            System.out.println();

        }
        else{

            AlertBox.display("CompilerFehler", refactorerror(cs, comres));
        }

        Tracking.schreibeLog(klasseDateiname, output+refactorerror(cs, comres), testtxt);
        zeitUpdate(state);
        Tracking.zeichneDiagramm(klasseDateiname,testZeit, klasseZeit, refactorZeit);
    }

    public static void zeitUpdate(Label state){
        if(state.getText().equals("Klasse")){
            differenz = System.currentTimeMillis()-stopper;
            stopper = System.currentTimeMillis();
            klasseZeit = klasseZeit+differenz;
            //System.out.println("KLasse");
        }
        else if (state.getText().equals("Test")){
            differenz = System.currentTimeMillis()-stopper;
            stopper = System.currentTimeMillis();
            testZeit = testZeit+differenz;
            //System.out.println("Test");
        }else if (state.getText().equals("Refactor")){
            differenz = System.currentTimeMillis()-stopper;
            stopper = System.currentTimeMillis();
            refactorZeit = refactorZeit+differenz;
            //System.out.println("refactor");
        }
    }

    public  String refactorerror(CompilationUnit[] cs,CompilerResult comres){
        String a="";
        for (CompilationUnit cu : cs){
            for(CompileError ce:comres.getCompilerErrorsForCompilationUnit(cu)){
                a=a+'\n'+ce.getMessage();

            }

        }
        System.out.println(a);
        return a;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
