//package sample;

import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class LoadnSave {

    public static void load(String FileName,TextArea txtarea,boolean isTest){
            File file;
            if(isTest) file = new File("TestsNClasses/"+FileName+"Test.java");
            else file = new File("TestsNClasses/"+FileName+".java");
            BufferedReader in = null;
            //int max=0;
            try {
                in = new BufferedReader(new FileReader(file));
               ArrayList<String> input=new ArrayList<String>();
                String inp=in.readLine();
                while(inp!=null) {
                    input.add(inp+'\n');
                    inp = in.readLine();
                   // max++;
                }
                txtarea.clear();

                for(String s:input) {
                txtarea.appendText(s);
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        }


    public static void save(String FileName,String text,boolean isTest){
        try {
            PrintStream saver;
            if(isTest) saver = new PrintStream("TestsNClasses/"+FileName+"Test.java");
            else saver = new PrintStream("TestsNClasses/"+FileName+".java");
            saver.print(text);
        }catch(Exception e){
            System.out.println(e+"JUNGE!");
        }
    }
}
