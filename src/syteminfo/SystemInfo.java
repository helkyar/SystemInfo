package syteminfo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.JTree.*;

import java.awt.*; //*
import java.awt.event.*;
import java.awt.BorderLayout;
//-------------------------
import java.awt.Component;

/**
 *
 * @author Javier Palacios
 */

public class SystemInfo extends JFrame {
   
    public static JTree tree; //making it able to be referenced by others classes
    public static JEditorPane text  = new JEditorPane();
    ImageIcon icon;
    //=========================================================================
    // CONSTRUCTOR
    //=========================================================================
    SystemInfo( String os, MouseListener listener, Creator creator) {
        
        JPanel leftPane = new JPanel();
        Box aside = Box.createVerticalBox();
        Box margins = Box.createHorizontalBox();
        Box main = Box.createHorizontalBox();

        if(os.contains("WIN")){icon = new ImageIcon("img/win.png");}
        else {icon = new ImageIcon("img/unx.png");}
        
        JLabel system = new JLabel( os );
        system.setFont(new Font("arial", 1, 24));
        system.setIcon(icon);

        // TREE COMPONENTS -----------------------------------------
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("General System Info");
        tree = new JTree(root);
//        creator.componentCreator("General");
//        creator.componentCreator("Sistema Operativo");
//        creator.componentCreator("Procesador");
//        creator.componentCreator("Almacenamiento");
//        creator.componentCreator("Ram");
//        if(os.contains("WIN")){creator.componentCreator("Graficos");}
//        creator.componentCreator("Pantalla");
//        creator.componentCreator("Placa Base");
//        if(os.contains("WIN")){creator.componentCreator("Conexiones");}
//         creator.componentCreator("Perifericos");
//        if(!os.contains("WIN")){creator.componentCreator("Audio");}
//         creator.componentCreator("Drivers Opticos");
        if(!os.contains("WIN")){creator.componentCreator("PS");}
        if(!os.contains("WIN")){creator.componentCreator("W");}
        if(!os.contains("WIN")){creator.componentCreator("Config");}
        if(!os.contains("WIN")){creator.componentCreator("Ram");}
        if(os.contains("WIN")){creator.componentCreator("Netstat");}
        if(os.contains("WIN")){creator.componentCreator("CMD");}
        
        tree.addMouseListener(listener);

        // SCROLL PANEL ----------------------------------
        text.setContentType("text/html");
        if(os.contains("WIN")){text.setBackground(Color.GRAY);}
        JScrollPane scrollPanel  = new JScrollPane(text);
        
        if(os.contains("WIN")){new WndListener().summary();}
        if(!os.contains("WIN")){new UnxListener().summary();}
        // STRUCTURING THE PANEL ----------------------------------
        leftPane.setLayout(new BorderLayout());
        aside.add(Box.createVerticalStrut(10));
        aside.add(system);
        aside.add(Box.createVerticalStrut(10));
        margins.add(Box.createHorizontalStrut(10));
        margins.add(new JScrollPane(tree));
        margins.add(Box.createHorizontalStrut(10));
        aside.add(margins);
        aside.add(Box.createVerticalStrut(10));
        leftPane.add(aside);

        // STRUCTURING THE FRAME ----------------------------------
        setLayout(new BorderLayout());
        add(leftPane, BorderLayout.WEST);
        add(scrollPanel, BorderLayout.CENTER);        
        
        setTitle("System info");
        setIconImage(new ImageIcon(getClass().getResource("img/info.png")).getImage());
        setSize(500, 500);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // LogGen.info("Session ended");
                System.exit(0);
            }
        });

        LogGen.info("Session started");
    }

    //=========================================================================
    // MAIN
    //=========================================================================
    public static void main(String[] args) {
            
        LogGen.start(". Start attempt");
        //Get OS
        try {
            String OS = System.getProperty("os.name").toLowerCase();
            
            if (OS.contains("win")) {
                try {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("CDE/Motif".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                }
                catch (ClassNotFoundException ex) {LogGen.error(ex.getMessage());}
                catch (InstantiationException ex) {LogGen.error(ex.getMessage());}
                catch (IllegalAccessException ex) {LogGen.error(ex.getMessage());}
                catch (javax.swing.UnsupportedLookAndFeelException ex) {LogGen.error(ex.getMessage());}

                // Crete and launch the Frame. Grab any other possyble error 
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try{
                            // Calls frame with windows event listener class
                            new SystemInfo(OS.toUpperCase(), new WndListener(), new WndCreator());
                        } catch(Throwable e){LogGen.error(e.getMessage());}
                    }
                });     
            }
            
            else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"))
            // Calls frame with unix/linux event listener class
            {new SystemInfo(OS.toUpperCase(), new UnxListener(), new UnxCreator());} 

            else {
                LogGen.error("Operatig system not supported");
                JOptionPane.showMessageDialog(null, "¡Sistema Operativo no soportado!");
            }
        
        } catch (Exception ex) {
            LogGen.error(ex.getMessage());
        } 
    }
}

//=========================================================================
//COMPONENT CREATORS
//=========================================================================
class Creator {

    public void componentCreator(String name){}

    // COMON COMPONENTS (TREE)----------------------------------
    public void componentC(String name, ImageIcon icon, String pref){
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) SystemInfo.tree.getModel().getRoot();
        //Set leaf and root icons
        DefaultTreeCellRenderer render = (DefaultTreeCellRenderer) SystemInfo.tree.getCellRenderer();
        render.setLeafIcon(icon);
        try {
            render.setClosedIcon(new ImageIcon("img/"+pref+"arrow.png"));
            render.setOpenIcon(new ImageIcon("img/"+pref+"arrowd.png"));
        }catch(Exception e){LogGen.error(e.getMessage());}
        
        root.add(new DefaultMutableTreeNode(name));
    }
}
class WndCreator extends Creator{
    public void componentCreator(String name){
        try {
            componentC(name, new ImageIcon("img/gears.png"), "");
        }catch(Exception e){LogGen.error(e.getMessage());}

    }

}
class UnxCreator extends Creator{
    public void componentCreator(String name){
        try {
            componentC(name, new ImageIcon("img/ugears.png"), "u");
        }catch(Exception e){LogGen.error(e.getMessage());}
    }
}

