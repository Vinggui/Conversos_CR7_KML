import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
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
                File currentPath = null;
                try {
                    currentPath = new File(converter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                } catch(Exception e) {
                    
                }
                openFile = new JFileChooser(currentPath);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("GeoTxt files", "txt");
                openFile.setFileFilter(filter);
                openFile.setMultiSelectionEnabled(true);
                openFile.showOpenDialog(null);
                
                //if(returnVal == JFileChooser.APPROVE_OPTION) {
                    
                //}
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
        
        String name = "";
        String description = "none";
        
        
        try {
            if(openFile.getSelectedFiles().length > 0) {
                for(int fileCounter=0; fileCounter < openFile.getSelectedFiles().length; fileCounter++) {
                    File[] files = openFile.getSelectedFiles();
                    File file = files[fileCounter];
                    String fileName = file.getName();
                    int dotIndex = fileName.lastIndexOf('.');
                    String extension = "";
                    if (dotIndex > 0) {
                        extension = fileName.substring(dotIndex+1);
                    }
                    
                    if(extension.equalsIgnoreCase(".txt")) {
                        //Ignore files with wrong extension then .txt
                        continue;
                    }
                    

                    String output = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                            + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">"
                            + "<Document><name>My document</name>"
                            + "<description>Content</description>"
                            + "<Style id=\"Lump\">"
                            + "<LineStyle><color>";
                    
                    //Check if user wants to input more info
                    //if(false) {
                        //TODO ...
                    //} else {
                        name = fileName.substring(0,dotIndex-2);//Até o "_C.txt"
                    //}
                    
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
                            + "<styleUrl>#Lump</styleUrl>"
                            + "<Polygon>"
                            + "<tessellate>1</tessellate>"
                            + "<altitudeMode>clampToGround</altitudeMode>"
                            + "<outerBoundaryIs><LinearRing><coordinates>";
                    
                    //String content = ;
                    fileScanner = new Scanner(file);
                    
                    boolean first = true;
                    String firstPoint = "";
                    while(fileScanner.hasNext()) {
                        line = fileScanner.nextLine();
                        String[] tokens = line.split(",");
                        String newLine = tokens[1] + "," + tokens[0] + ",0.0 ";
                        output += newLine;
                        if(first) {
                            firstPoint = newLine;
                            first = false;
                        }
                    }
                    output += firstPoint;
    
                    output += "</coordinates></LinearRing></outerBoundaryIs>"
                            + "</Polygon>"
                            + "</Placemark>"
                            + "</Document>"
                            + "</kml>";

                    String outputFilePath = file.getParent() + "/" + file.getName().replace(".txt", ".kml");
                    PrintWriter writer = new PrintWriter(outputFilePath, "UTF-8");
                    writer.print(output);
                    writer.close();
                    
                    System.out.println("File "+fileName+" compiled."+ outputFilePath);
                }
            }
        } catch(Exception e) {
            System.out.println("Erro de conversão: " + e);
        };
    }
}