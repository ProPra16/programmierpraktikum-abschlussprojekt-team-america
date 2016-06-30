package sample;

import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class LoadnSave {

    public static void load(TextArea txtarea){
            File file = new File("test.java");
            BufferedReader in = null;
            int max=0;
            try {
                in = new BufferedReader(new FileReader(file));
               ArrayList<String> input=new ArrayList<String>();
               // StringBuilder input=new StringBuilder()
                String inp=in.readLine();
                while(inp!=null) {
                    input.add(inp+'\n');
                    inp = in.readLine();
                    max++;
                    //System.out.println(inp);
                }
                txtarea.clear();

                for(String s:input) {
                 //   txtarea.setText(input.toString());
                txtarea.appendText(s);
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        }


    public static void save(String text){
        try {
            PrintStream saver = new PrintStream("test.java");
            saver.print(text);
        }catch(Exception e){
            System.out.println(e+"JUNGE!");
        }
    }
}
