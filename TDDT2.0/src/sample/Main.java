package sample;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import vk.core.api.*;
import vk.core.internal.InternalCompiler;
import vk.core.internal.InternalResult;

import java.awt.*;
import java.io.PrintStream;
import java.util.*;

public class Main extends Application {
    public static Stage fenster;
    public static Scene rot;
    public static VBox aufbau;
    public static TextArea testtxt;
    public static TextArea classtxt;
    public static ComboBox<String> Katalog;
    public static Button Rot;
    public static  Button Gruen;
    public static Button refactor;
    public static Label state;

    @Override
    public void start(Stage primaryStage) throws Exception{
        fenster=primaryStage;
        fenster.setTitle("Test");
        aufbau=new VBox(20);
        Button compile=new Button("Compi");
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
        testtxt.setPrefColumnCount(30);
        testtxt.setPrefRowCount(30);
            classtxt=new TextArea();
            classtxt.setPrefColumnCount(30);
            classtxt.setPrefRowCount(30);
            classtxt.setDisable(true);
        String a="a";
        String b="b";                       //xml -titel- laden
        Katalog=new ComboBox<String>();
        Katalog.getItems().addAll(a,b);
        VBox menu=new VBox(20);
        HBox h1=new HBox(20);
        HBox h2=new HBox(20);
        h2.getChildren().addAll(Rot,Gruen);
        menu.getChildren().addAll(Katalog,h2,refactor,saveclass,loadclass,savetest,loadtest,state,compile);
        h1.getChildren().addAll(classtxt,menu,testtxt);
        aufbau.getChildren().addAll(h1);
        rot =new Scene(aufbau);
        fenster.setScene(rot);
        fenster.show();


        compile.setOnAction(e->{String klasse=classtxt.getText();String testo=testtxt.getText();compillll(klasse,testo);});
        Rot.setOnAction(e->{zuRot();});
        Gruen.setOnAction(e->{zuGruen();});
        saveclass.setOnAction(e->LoadnSave.save("DNA",classtxt.getText(),false));
        loadclass.setOnAction(e->LoadnSave.load("DNA",classtxt,false));
        savetest.setOnAction(e->LoadnSave.save("DNA",testtxt.getText(),true));
        loadtest.setOnAction(e->LoadnSave.load("DNA",testtxt,true));
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

    public  void compillll(String klasse, String test){
        CompilationUnit b=new CompilationUnit("DNA",klasse,false);
        CompilationUnit c=new CompilationUnit("DNATest",test,true);
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
                if (res.getNumberOfFailedTests() == 1 && classtxt.isDisabled()){
                    Gruen.setDisable(false);
                }
                //refactor.setDisable(false);
                output=output+"\nWelche Tests sind Fehlgeschlagen: ";
               /* if(classtxt.isDisabled()) {
                    Gruen.setDisable(false);
                    Rot.setDisable(true);
                }
                else{
                    Rot.setDisable(false);
                    Gruen.setDisable(true);
                    refactor.setDisable(false);
                }*/

            }

            else{
                refactor.setDisable(false);
                Rot.setDisable(false);
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
