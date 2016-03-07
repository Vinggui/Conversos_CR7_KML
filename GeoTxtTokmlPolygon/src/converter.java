import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.io.*;
import java.util.*;

public class converter {
    
    private JFileChooser openFile;
    
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                new converter().createUI();
            }
        };

        EventQueue.invokeLater(r);
    }

    private void createUI() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        JButton ConvertBtn = new JButton("Converter");
        JButton openBtn = new JButton("Abrir");

        ConvertBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                convert();
            }
        });

        openBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                openFile = new JFileChooser();
                int returnVal = openFile.showOpenDialog(null);
                
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                    openFile.getSelectedFile().getName());
                }
            }
        });

        frame.add(new JLabel("Arquivos do Campeiro cr 7 mapa convertidos para kml (formato para o google maps)"), BorderLayout.NORTH);
        frame.add(ConvertBtn, BorderLayout.SOUTH);
        frame.add(openBtn, BorderLayout.CENTER);
        frame.setTitle("Conversor GeoTxt para kml");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private void convert() {
        Scanner fileScanner;
        String line = "";
        
        String lumpLineColor = "CD0000FF";
        String lumpLineWidth = "2";
        String lumpPolygonColor = "9AFF0000";

        String pathLineColor = "FF0000FF";
        String pathLineWidth = "3";
        
        String name = "Name";
        String description = "none";
        
        
        String output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">"
                + "<Document><name>My document</name>"
                + "<description>Content</description>"
                + "<Style id=\"Lump\">"
                + "<LineStyle><color>";
        try {
            if(openFile.getSelectedFile().exists()) {
                //Check if user wants to input more info
                if(false) {
                    //TODO ...
                }
                
                output += lumpLineColor;
                output += "</color><width>";
                output += lumpLineWidth;
                output += "</width></LineStyle><PolyStyle><color>";
                output += lumpPolygonColor;
                output += "</color></PolyStyle>"
                        + "</Style>"
                        + "<Style id=\"Path\">"
                        + "<LineStyle><color>";
                output += pathLineColor;
                output += "</color><width>";
                output += pathLineWidth;
                output += "</width></LineStyle>"
                        + "</Style>"
                        + "<Style id=\"markerstyle\">"
                        + "<IconStyle><Icon><href>"
                        + "http://maps.google.com/intl/en_us/mapfiles/ms/micons/red-dot.png"
                        + "</href></Icon></IconStyle>"
                        + "</Style>"
                        + "<Placemark><name>";
                output += name;
                output += "</name><description>";
                output += description;
                output += "</description>"
                        + "<styleUrl>#Path</styleUrl>"
                        + "<LineString>"
                        + "<tessellate>1</tessellate>"
                        + "<altitudeMode>clampToGround</altitudeMode>"
                        + "<coordinates>";
                
                
                File file = openFile.getSelectedFile();
                //String content = ;
                fileScanner = new Scanner(file);
                
                while(fileScanner.hasNext()) {
                    line = fileScanner.nextLine();
                    String[] tokens = line.split(",");
                    String newLine = tokens[0] + "," + tokens[1] + ",0.0 ";
                    output += newLine;
                    System.out.println(newLine);
                }

                output += "</coordinates>"
                        + "</LineString>"
                        + "</Placemark>"
                        + "</Document>"
                        + "</kml>";

                System.out.println(output);
            }
        } catch(Exception e) {
            System.out.println("Erro de conversão: " + e);
        };
    }
}