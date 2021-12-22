package syteminfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.print.DocFlavor.STRING;

import java.awt.event.MouseAdapter;
import java.awt.*; //*
 
/**
 *
 * @author Javier Palacios
 */
class WndListener extends MouseAdapter{
    //Variables accesible to avoid refresh and the consequent time delay
    private String imgPath = "file:img/"; //path for windows
    //set tabs values  with ascii code for html text type
    private WExe[] exe = WExe.values();
    private String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    private String tab2 = tab + "&nbsp;&nbsp;&nbsp;";

    private String genInfo = "";
    private String sodisplay = "";
    private String procdisplay ="";
    private String diskdisplay=""; 
    private String ramdisplay=""; 
    private String gpudisplay=""; 
    private String screendisplay=""; 
    private String storagedisplay=""; 
    private String motherboarddisplay=""; 
    private String connectiondislay = "";
    private String audiodisplay=""; 

    private String pc = "";
    private String soType = "";
    private String ram = "Not found";
    private String ramInd = "";
    private String speaker = "";    
    private String conection = "";
    private String conectionInd = "";
    private String storage = "";
    private String storageInd = "";
    private String monitor ="";
    private String monitorInd;
    private String motherboardInd = "";
    private String motherboard = "";
    private String email ="";
    private String sisop="";

    public void mouseClicked(MouseEvent evt) {
        LogGen.info("Tree clicket: " + SystemInfo.tree.getSelectionPath().getLastPathComponent());
        try{
            int leaf = SystemInfo.tree.getSelectionModel().getSelectionRows()[0];
            //set cursor to wait while processing the request
            SystemInfo.tree.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            //set listeners for each leaf based on position
            if (leaf == 1){netstatInfoDisplay();}
            else if (leaf == 2){Cmd.run("start cmd", 0, 1, "");}
//            else{summary();}
//            if (leaf == 2){netstatInfoDisplay();}
//            if (leaf == 3){netstatInfoDisplay();}
//             if (leaf == 1){summary();}
//            else if (leaf == 2){soInfoDisplay();} 
//            else if (leaf == 3){procInfoDisplay();}
//            else if (leaf == 4){storageInfoDisplay();}
//            else if (leaf == 5){ramInfoDisplay();}
//            else if (leaf == 6){gpuInfodisplay();}
//            else if (leaf == 6){screenInfoDisplay();}
//            else if (leaf == 7){motherboardInfoDisplay();}
//            else if (leaf == 8){connectionInfoDisplay();} 
//            else if (leaf == 9){netstatInfoDisplay();}
//            else if (leaf == 10){audioInfoDisplay();}
//            else {SystemInfo.text.setText("<br>"+tab+"SELECCIONA UNA OPCION");}

            SystemInfo.tree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }catch (Exception e){LogGen.error(e.getMessage());}
    }
    
    private void netstatInfoDisplay(){
        String [] netstat = Cmd.ran("netstat -ano", 3, 1000, "").split("\\s+");
        String finalnet="<table><tr><td>"+tab+"</td><td><strong>Proto</strong></td>"
                + "<td><strong>D.Local</strong></td><td><strong>D.Remota</strong>"
                + "</td><td><strong>Estado</strong></td><td><strong>PID</strong></td></tr>";

        for(int j=1; j<netstat.length;){
            try{
                if(netstat[j].equals("UDP")){
                    finalnet += "<tr><td></td><td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+
                    "<td>"+netstat[j++]+"</td>"+"<td>-</td>"+"<td>"+netstat[j++]+"</td></tr>";
                } else {
                    finalnet += "<tr><td></td><td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+
                    "<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td></tr>";
                }
                
            }catch(Exception a){}
        }
        
        finalnet += "</table>";
        SystemInfo.text.setText("<br>"+finalnet);

    }
    
    private void soInfoDisplay(){
        if( sodisplay.length()<1){
            if(sisop.length()<1){summaryInfo();}
            
            sodisplay= "<br><br>"+tab+"<img src=\""+imgPath+"so.png"+"\" /> Sistema Operativo<br>"
                +sisop;
        }
        SystemInfo.text.setText(sodisplay); 
    }

    private void procInfoDisplay(){
        if(procdisplay.length()<1){
            summaryInfo();
           procdisplay = "<br><br>"+tab+"<img src=\""+imgPath+"cpu.png"+"\" /> Procesador<br>"       
                +tab2+Cmd.run(exe[2].path,1,3,"REG_SZ")+"<br>";
        }
        SystemInfo.text.setText(procdisplay); 
    }

    private void storageInfoDisplay(){
        if(storagedisplay.length()<1){
            storageInfo();
            storagedisplay = "<br><br>"+tab+"<img src=\""+imgPath+"hdd.png"+"\" /> Disco<br>"           
                +storageInd+"<br>";
        }
        SystemInfo.text.setText(storagedisplay); 
    }

    private void ramInfoDisplay(){
        if(ramdisplay.length()<1){
            summaryInfo();
            String[] ramArr = ramInd.split("<br>");
            ramdisplay = "<br><br>"+tab+"<img src=\""+imgPath+"ram.png"+"\" /> Ram<br>"             
                +tab2+"Cantidad total de memoria fisica: "+ramArr[0].substring(ramArr[0].indexOf(":")+1)+"<br>"
                +tab2+"Memoria fisica disponible: "+ramArr[1].substring(ramArr[1].indexOf(":")+1)+"<br>"
                +tab2+"Volumen maximo de memoria virtual: "+ramArr[2].substring(ramArr[2].lastIndexOf(":")+1)+"<br>"
                +tab2+"Memoria virtual disponible: "+ramArr[3].substring(ramArr[3].lastIndexOf(":")+1)+"<br>"
                +tab2+"Memoria vrtual en uso:"+ramArr[4].substring(ramArr[4].lastIndexOf(":")+1)+"<br>";
        }
        SystemInfo.text.setText(ramdisplay); 
    }
    
    private void gpuInfodisplay(){
        if(gpudisplay.length()<1){
            summaryInfo();
            gpudisplay = "<br><br>"+tab+"<img src=\""+imgPath+"gpu.png"+"\" /> Graficos<br>"        
                +tab2+Cmd.run(exe[10].path,8,10,"REG_SZ")+"<br>";
        }
        SystemInfo.text.setText(gpudisplay); 
    }

    private void screenInfoDisplay(){
        if(screendisplay.length()<1){
        screenInfo();
            screendisplay = "<br><br>"+tab+"<img src=\""+imgPath+"sc.png"+"\" /> Pantalla<br>"
                +monitorInd+"<br>";
        }
        SystemInfo.text.setText(screendisplay); 
    }

    private void motherboardInfoDisplay(){
        if(motherboarddisplay.length()<1){
            summaryInfo();
            motherboarddisplay = "<br><br>"+tab+"<img src=\""+imgPath+"mb.png"+"\" /> Placa Base<br>"
                +motherboardInd;
        }
        SystemInfo.text.setText(motherboarddisplay); 
    }

    private void connectionInfoDisplay(){
        if(connectiondislay.length()<1){
            summaryInfo();
            connectiondislay = "<br><br>"+tab+"<img src=\""+imgPath+"cn.png"+"\" /> Conexiones<br>"
                +tab2+conectionInd+"<br>";
        }
        SystemInfo.text.setText(connectiondislay); 
    }

    private void audioInfoDisplay(){
        if(audiodisplay.length()<1){
            audioInfo();
            audiodisplay = "<br><br>"+tab+"<img src=\""+imgPath+"au.png"+"\" /> Audio<br>"
                +speaker;
        }
        SystemInfo.text.setText(audiodisplay); 
    }

    public void summary(){
        /*String model = Files.general[i]s(Paths.get("/proc/cpuinfo"))
            .filter(general[i] -> general[i].startsWith("model name"))
            .map(general[i] -> general[i].replaceAll(".*: ", ""))
            .findFirst().orElse("")*/ 

        if(genInfo.length()<1){

            storageInfo();
            screenInfo();
            audioInfo();
            // SO(type), Motherboard, Ram, 
            summaryInfo();
            
            genInfo = "<br><br>"+tab+"<img style=\"margin:30px\" src=\""+imgPath+"pc.png"+"\" /> General<br>"
                +tab2+"Dispositivo: "+pc+Cmd.run(exe[4].path,1,3,"REG_SZ")
                +tab2+"Usuario: "+email+Cmd.run(exe[0].path,-1,1,"\\")+"<br>"+
                tab+"<img src=\""+imgPath+"so.png"+"\" /> Sistema Operativo<br>"
                +sisop+
                tab+"<img src=\""+imgPath+"cpu.png"+"\" /> Procesador<br>"       
                +tab2+Cmd.run(exe[2].path,1,3,"REG_SZ")+"<br>"+
                tab+"<img src=\""+imgPath+"hdd.png"+"\" /> Disco<br>"           
                +storage+"<br>"+
                tab+"<img src=\""+imgPath+"ram.png"+"\" /> Ram<br>"             
                +tab2+ram+"<br>"+
//                tab+"<img src=\""+imgPath+"gpu.png"+"\" /> Graficos<br>"        
//                +tab2+Cmd.run(exe[10].path,8,10,"REG_SZ")+"<br>"+
                tab+"<img src=\""+imgPath+"sc.png"+"\" /> Pantalla<br>"
                +monitor+"<br>"+
                tab+"<img src=\""+imgPath+"mb.png"+"\" /> Placa Base<br>"
                +motherboard+
                tab+"<img src=\""+imgPath+"cn.png"+"\" /> Conexiones<br>"
                +tab2+conection+"<br>";
//                tab+"<img src=\""+imgPath+"au.png"+"\" /> Audio<br>"
//                +speaker;
            }
        SystemInfo.text.setText(genInfo);        
    }
    
    private void storageInfo(){
        String volume = Cmd.run("powershell gdr -PSProvider 'FileSystem'",2,10,""); 
        String[] volumeArr = volume.split("<br>");
        String disk = Cmd.run("powershell.exe get-PhysicalDisk | select friendlyname",2,10,""); 
        String[] size = Cmd.run("powershell.exe get-PhysicalDisk | select size",2,10,"").split("<br>"); 
        String[] type = Cmd.run("powershell.exe get-PhysicalDisk | select mediatype",2,10,"").split("<br>"); 
        String[] diskArr = disk.split("<br>");
        storage = "";
        for(int i = 0; i< diskArr.length; i++){
            Long s = Long.parseLong(size[i])/1024/1024/1024;
            storage += tab2+diskArr[i]+"<br>";
            storageInd += tab2+diskArr[i]+" ("+type[i]+") "+s+" GB"+"<br>";
        }

        if (storage.length()<1) {speaker = "There was a problem";}
    }

    private void screenInfo(){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screen = env.getScreenDevices(); 

        monitor = "";
        monitorInd ="";
        for (int i =0; i<screen.length; i++){
            int j = i+1;
            monitor += tab2+"Pantalla " + j + ": " + screen[i].getDisplayMode().getWidth()
                + " x " + screen[i].getDisplayMode().getHeight() + " px" + "<br>";
            monitorInd += monitor +tab2+ "Frecuencia de refresco: " + screen[i].getDisplayMode().getRefreshRate() + " hz" +
                "<br>" +tab2+ "Profundidad de bit: " + screen[i].getDisplayMode().getBitDepth() + "<br>";
        }
    }

    private void audioInfo(){
        String audio = Cmd.run(exe[14].path,-1,100,"");
        String[] audioArr = audio.split("<br>");
        speaker = "";
        for(String line : audioArr){
            if(line.contains("HKEY_CURRENT_USER")){
                line = "reg query \"HKCU"+line.substring(line.indexOf("USER")+4)+"\"";
                speaker += tab2+Cmd.run(line,1,3,"REG_SZ");
            }
        }
        if (speaker.length()<1) {speaker = tab2+"Not found";}

    }

    private void summaryInfo(){         
        pc = Cmd.run(exe[5].path,1,3,"REG_SZ");
        pc = pc.substring(0, pc.indexOf("<"));
        motherboard = tab2+"Fabricante: "+pc+"<br>"+tab2+"Modelo: "+Cmd.run(exe[6].path,1,3,"REG_SZ")+"<br>";

        String sisinfo = Cmd.run("systeminfo",-1,100,"");
        String[] general = sisinfo.split("<br>");

        for(int i = 0; i< general.length; i++){
            if(general[i].contains("emoria")){
                ramInd += tab2+general[i]+"<br>";  
                if(general[i].contains("sica:")){
                    ram="Cantidad total: "+general[i].substring(general[i].indexOf("sica:")+5)+ "<br>";
                }
            }
            if(general[i].contains("Tipo de sistema:")){
                soType = general[i].substring(general[i].indexOf("sistema:")+9, general[i].indexOf("-"));
            }
            if(general[i].contains("BIOS:")){
                motherboardInd = tab2+"Version BIOS: " + general[i].substring(general[i].indexOf("BIOS:")+5) + "<br>";
            }
            if(general[i].contains("Propiedad de:")){
                email = general[i].substring(general[i].indexOf("de:")+3)+"\\";
            }
            if(general[i].contains("Tarjeta(s) de red:")){
                conection = general[i].substring(general[i].indexOf("red:")+4)+"<br>";
                conectionInd = general[i].substring(general[i].indexOf("red:")+4)+"<br>";

                for(int j = 0; !general[i+j].contains("Requisitos Hyper-V:"); j++){
                    String s = tab2+"&nbsp;&nbsp;"+general[i+j].substring(general[i+j].indexOf("]:")+2)+"<br>";

                    if(general[i+j].contains("]:") && (!general[i+j].contains("8.") && !general[i+j].contains("::"))){
                        conection += s;
                        conectionInd += tab2+general[i+j]+"<br>";
                    }else{conectionInd += tab2+"&nbsp;&nbsp;&nbsp;&nbsp;"+general[i+j]+"<br>";}
                    
                }
            }
        }
        motherboardInd += motherboard;
        sisop = tab2+"("+soType+") "+Cmd.run(exe[1].path,1,3,"REG_SZ")+"<br>";
    }
}