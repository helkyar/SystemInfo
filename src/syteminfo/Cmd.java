package syteminfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Javier Palacios
 */
class Cmd {
    //Testing methods for commands
    public static String ran(String comand, int i, int j, String sep){
        String s, msg = "";
        try{
            Process p = Runtime.getRuntime().exec(comand);  //IOEx

            // if(comand.contains("powershell")){p.getOutputStream().close();}

            BufferedReader br = new BufferedReader(
            new InputStreamReader(p.getInputStream()));
            
            for(int z = 0; (s = br.readLine()) != null; z++){
                if(z > i && z < j) {msg += s + " ";}
            }

            if(!sep.equals("") && msg.contains(sep)){
                msg = msg.substring(msg.indexOf(sep)+sep.length());
            }

            p.waitFor(); // InterruptedEx
            p.destroy();  
                        
        } catch(IOException e){LogGen.error(e.getMessage());}
          catch(InterruptedException e){LogGen.error(e.getMessage());} 

        return msg;
    }
    //Default commands processor. numbers specify lines showed and sep the separation
    public static String run(String comand, int i, int j, String sep){
        String s, msg = "";
        try{
            Process p = Runtime.getRuntime().exec(comand);  //IOEx

            // if(comand.contains("powershell")){p.getOutputStream().close();}

            BufferedReader br = new BufferedReader(
            new InputStreamReader(p.getInputStream()));
            
            for(int z = 0; (s = br.readLine()) != null; z++){
                if(z > i && z < j) {msg += s + "<br>";}
            }

            if(!sep.equals("") && msg.contains(sep)){
                msg = msg.substring(msg.indexOf(sep)+sep.length());
            }

            p.waitFor(); // InterruptedEx
            p.destroy();  
                        
        } catch(IOException e){LogGen.error(e.getMessage());}
          catch(InterruptedException e){LogGen.error(e.getMessage());} 

        return msg;
    }
    
    //Execute command arrays (used for Linux commands)
    public static String run(String[] comand, int i, int j, String sep){
        String s, msg = "";
        try{
            Process p = Runtime.getRuntime().exec(comand);  //IOEx
            p.waitFor();
            
            BufferedReader br = new BufferedReader(
            new InputStreamReader(p.getInputStream()));
            
            for(int z = 0; (s = br.readLine()) != null; z++){
                if(z > i && z < j) {msg += s + "<br>";}
            }

            if(!sep.equals("") && msg.contains(sep)){
                msg = msg.substring(msg.indexOf(sep)+sep.length());
            }

            p.waitFor(); // InterruptedEx
            p.destroy();  
                        
        } catch(IOException e){LogGen.error(e.getMessage());}
          catch(InterruptedException e){LogGen.error(e.getMessage());} 

        return msg;
    }
    // Execute on thread
    public static String runThr(String comand, String sep){
        //Redeclare to implement getResult method
        class ParalelReader extends Thread{public String getResult(){return "";}};
        String msg = "";
        
        try {
            ParalelReader reader = new ParalelReader(){
                private String s, msg = "";
                Process p = Runtime.getRuntime().exec(comand);  //IOEx

                @Override
                public void run(){
                    try {
                        BufferedReader br = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                        //IOEx
                        while ((s = br.readLine()) != null){msg += s + "\n";}
                        
                        if(!sep.equals("") && msg.contains(sep)){
                            msg = msg.substring(msg.indexOf(sep)+sep.length());
                        }


                     } catch(IOException e){LogGen.error(e.getMessage());}
                }

                @Override
                public String getResult() {
                    try{
                        p.waitFor(); // InterruptedEx
                        System.out.println ("exit: " + p.exitValue());
                        p.destroy();  
                    } catch(InterruptedException e){LogGen.error(e.getMessage());} 
                    return this.msg;
                }
            };
            
            reader.start();
            msg = reader.getResult();
        } catch(IOException e){LogGen.error(e.getMessage());}
        
        return msg;
    }
}