package syteminfo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;

/**
 *
 * @author Javier Palacios
 */
public class LogGen {
private static String line = "-----------------------------------------------------------------------------\n";
private static String name;
private static String header;

public static void start(String msg) {
    //Get user name
    String s = Cmd.runThr("whoami", "\\");
    
    try {
        //Date format for the file
        DateFormat logName = new SimpleDateFormat ("dd-MM-yyyy");
        name = "log/" + "log" +"("+ logName.format(new Date()) +")"+ ".log";
            
            File f = new File("log");
            f.mkdir();  //creates folder if none exists
        } catch (Exception ex) {
            ex.printStackTrace();
        }
            writer(line,"\tSession\t\t", msg);
    }

    public static void error(String error){
            writer("", "\tError\t\t", error);
    }

    public static void info(String info){
            writer("","\tInfo\t\t", info);
    }
    
    //Write at specified file
    public static void writer(String s, String type, String msg){
        try {
            DateFormat session = new SimpleDateFormat ("dd/MM/yyyy kk:mm:ss");
            header = s + session.format(new Date()) + type;

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(name,true));

            PrintWriter fileWriter = new PrintWriter(bufferedWriter);
            fileWriter.print(header);
            fileWriter.print(msg + "\n");

            fileWriter.close();
            fileWriter.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}