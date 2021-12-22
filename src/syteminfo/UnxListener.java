/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syteminfo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*; //*

/**
 *
 * @author Javier Palacios
 */
public class UnxListener extends MouseAdapter{
    private String imgPath = "file:img/u"; //path for linux
    //set tabs values  with ascii code for html text type
    private String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    private String tab2 = tab + "&nbsp;&nbsp;&nbsp;";
    
    String[] cmd = { "/bin/bash", "-c", "cat /proc/asound/cards | head -1  | awk '{print $4,$5,$6,$7,$8}'" };
    String[] cmd2 = { "/bin/bash", "-c", "lsb_release -d | cut -b 14-" };
    String[] cmd3 = { "/bin/bash", "-c", "hostname" };
    String[] cmd4 = { "/bin/bash", "-c", "cat /sys/class/dmi/id/bios_version" };
    String[] cmd5 = { "/bin/bash", "-c", "cat /sys/class/dmi/id/product_name" };
    String[] cmd6 = { "/bin/bash", "-c", "lscpu | grep Nombre | awk '{print $4,$5,$6,$7,$8,$9}'" };
    String[] cmd7 = { "/bin/bash", "-c", "grep 'cpu cores' /proc/cpuinfo | uniq | awk '{print $4}'" };
    String[] cmd8 = { "/bin/bash", "-c", "grep -c processor /proc/cpuinfo" };
    String[] cmd9 = { "/bin/bash", "-c", "free -h | grep Mem | awk '{print $2}'" };
    String[] cmd10 = { "/bin/bash", "-c", "free -h | grep Mem | awk '{print $3}'" };
    String[] cmd11 = { "/bin/bash", "-c", "free -h | grep Mem | awk '{print $4}'" };
    String[] cmd12 = { "/bin/bash", "-c", "cat /sys/class/dmi/id/bios_vendor" };
    String[] cmd13 = { "/bin/bash", "-c", "lsblk --nodeps -o model | tail -n +2" };
    String[] cmd14 = { "/bin/bash", "-c", "xrandr | tail -n 1 | awk '{print $1}'" };
    String[] cmd15 = { "/bin/bash", "-c", "xrandr | tail -n 1 | awk '{print $2}'" };
    
    public void mouseClicked(MouseEvent evt) {
        LogGen.info("Tree clicket: " + SystemInfo.tree.getSelectionPath().getLastPathComponent());
        
        try{
            int leaf = SystemInfo.tree.getSelectionModel().getSelectionRows()[0];
            //set cursor to wait while processing the request
            SystemInfo.tree.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            //set listeners for each leaf based on position
            if (leaf == 1){psauxInfoDisplay();}
            else if (leaf == 2){whoInfoDisplay();}
            else if (leaf == 3){configInfoDisplay();}
            else if (leaf == 4){ramInfoDisplay();}
            else{summary();}
//            if (leaf == 1){summary();}
//            else if (leaf == 2){soInfoDisplay();} 
//            else if (leaf == 3){procInfoDisplay();}
//            else if (leaf == 4){storageInfoDisplay();}
//            else if (leaf == 5){ramInfoDisplay();}
//            else if (leaf == 6){screenInfoDisplay();}
//            else if (leaf == 7){motherboardInfoDisplay();} 
//            else if (leaf == 8){audioInfoDisplay();}
//            else {SystemInfo.text.setText("<br>"+tab+"SELECCIONA UNA OPCION");}

            SystemInfo.tree.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }catch (Exception e){LogGen.error(e.getMessage());}
    }
    
    private void psauxInfoDisplay(){
        String [] netstat = Cmd.ran("ps aux", 3, 1000, "").split("\\s+");
        String finalnet="<table><tr><td>"+tab+"</td><td><strong>User</strong></td>"
                +"<td><strong>PID</strong></td><td><strong>CPU</strong></td>"
                +"<td><strong>MEM</strong></td><td><strong>VSC</strong></td>"
                +"<td><strong>RSS</strong></td><td><strong>TTY</strong></td>"
                + "<td><strong>STAT</strong></td><td><strong>START</strong>"
                + "</td><td><strong>TIME</strong></td><td><strong>CMD</strong></td></tr>";

        for(int j=1; j<netstat.length;){
            try{
                    finalnet += "<tr><td></td><td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+
                    "<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+
                    "<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+
                    "<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td></tr>";                
            }catch(Exception a){}
        }
        
        finalnet += "</table>";
        SystemInfo.text.setText("<br>"+finalnet);

    }
    
    private void whoInfoDisplay(){
        String [] netstat = Cmd.ran("who", -1, 1000, "").split("\\s+");
        String finalnet="<table><tr><td>"+tab+"</td><td><strong>User</strong></td>"
                +"<td><strong>TErminal</strong></td><td><strong>Date</strong></td>"
                +"<td><strong>Hour</strong></td><td>IP</td></tr>";

        for(int j=0; j<netstat.length;){
            try{
                if(j==0){
                finalnet += "<tr><td></td><td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+
                "<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+"<td></td></tr>";  
                }else{
                finalnet += "<tr><td></td><td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+
                "<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td>"+"<td>"+netstat[j++]+"</td></tr>";  
                }
                              
            }catch(Exception a){}
        }
        
        finalnet += "</table>";
        SystemInfo.text.setText("<br>"+finalnet);

    }
        
    private void configInfoDisplay(){

        SystemInfo.text.setText("<br>"+Cmd.ran("ifconfig", -1, 1000, ""));

    }
        public void summary() {

        SystemInfo.text.setText(
            "<br><br>"+tab+"<img style=\"margin:30px\" src=\""+imgPath+"pc.png"+"\" /> General<br>"
            +tab+"<img src=\""+imgPath+"so.png"+"\" /> Sistema Operativo<br>"
            +tab2+Cmd.run(cmd2,  -1, 3, "")
            +tab2+"Eq Info: "+Cmd.run(cmd3,  -1, 3, "")+"<br>"
            +tab+"<img src=\""+imgPath+"cpu.png"+"\" /> Procesador<br>" 
            +tab2+"CPU Info: "+Cmd.run(cmd6,  -1, 3, "")
            +tab2+"CPU cores: "+Cmd.run(cmd7,  -1, 3, "")
            +tab2+"CPU hilos: "+Cmd.run(cmd8,  -1, 3, "")+"<br>"
            +tab+"<img src=\""+imgPath+"hdd.png"+"\" /> Disco<br>" 
            +tab2+"Disco duro: "+Cmd.run(cmd13,  -1, 3, "")+"<br>"
            +tab+"<img src=\""+imgPath+"ram.png"+"\" /> Ram<br>"   
            +tab2+"Mem Info: "+Cmd.run(cmd9,  -1, 3, "")
            +tab2+"Mem use: "+Cmd.run(cmd10,  -1, 3, "")
            +tab2+"Mem free: "+Cmd.run(cmd11,  -1, 3, "")+"<br>"
            +tab+"<img src=\""+imgPath+"sc.png"+"\" /> Pantalla<br>"
            +tab2+"Pantalla refr(hz): "+Cmd.run(cmd15, -1, 3, "")+"<br>"
            +tab+"<img src=\""+imgPath+"mb.png"+"\" /> Placa Base<br>"
            +tab2+"Bios v: "+Cmd.run(cmd4, -1, 3, "")
            +tab2+"Bios name: "+Cmd.run(cmd5,  -1, 3, "")
            +tab2+"Bios manuf: "+Cmd.run(cmd12,  -1, 3, "")+"<br>"
            +tab+"<img src=\""+imgPath+"au.png"+"\" /> Audio<br>"
            +tab2+Cmd.run(cmd, -1, 3, "")
        );
        
    }
     
    private void soInfoDisplay() {
        SystemInfo.text.setText("<br><br>"
            +tab+"<img src=\""+imgPath+"so.png"+"\" /> Sistema Operativo<br>"
            +tab2+Cmd.run(cmd2,  -1, 3, "")
            +tab2+"Eq Info: "+Cmd.run(cmd3,  -1, 3, "")
        );
                
    }

    private void procInfoDisplay() {
        SystemInfo.text.setText("<br><br>"
            +tab+"<img src=\""+imgPath+"cpu.png"+"\" /> Procesador<br>" 
            +tab2+"CPU Info: "+Cmd.run(cmd6,  -1, 3, "")
            +tab2+"CPU cores: "+Cmd.run(cmd7,  -1, 3, "")
            +tab2+"CPU hilos: "+Cmd.run(cmd8,  -1, 3, "")
        );
            }

    private void storageInfoDisplay() {
        SystemInfo.text.setText("<br><br>"
            +tab+"<img src=\""+imgPath+"hdd.png"+"\" /> Disco<br>" 
            +tab2+"Disco duro: "+Cmd.run(cmd13,  -1, 3, "")
        );
             }

    private void ramInfoDisplay() {
             SystemInfo.text.setText("<br><br>"
            +tab+"<img src=\""+imgPath+"ram.png"+"\" /> Ram<br>"   
            +tab2+"Mem Info: "+Cmd.run(cmd9,  -1, 3, "")
            +tab2+"Mem use: "+Cmd.run(cmd10,  -1, 3, "")
            +tab2+"Mem free: "+Cmd.run(cmd11,  -1, 3, "")  
        );
            }

    private void screenInfoDisplay() {
             SystemInfo.text.setText("<br><br>"
            +tab+"<img src=\""+imgPath+"sc.png"+"\" /> Pantalla<br>"
            +tab2+"Pantalla refr(hz): "+Cmd.run(cmd15, -1, 3, "")
        );
            }

    private void motherboardInfoDisplay() {
             SystemInfo.text.setText("<br><br>"
            +tab+"<img src=\""+imgPath+"mb.png"+"\" /> Placa Base<br>"
            +tab2+"Bios v: "+Cmd.run(cmd4, -1, 3, "")
            +tab2+"Bios name: "+Cmd.run(cmd5,  -1, 3, "")
            +tab2+"Bios manuf: "+Cmd.run(cmd12,  -1, 3, "")

        );
            }


    private void audioInfoDisplay() {
             SystemInfo.text.setText(
            "<br><br>"+tab+"<img src=\""+imgPath+"au.png"+"\" /> Audio<br>"
            +tab2+Cmd.run(cmd, -1, 3, "")
        );
             }

}
