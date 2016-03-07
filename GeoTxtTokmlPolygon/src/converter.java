import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class converter {
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
                JFileChooser saveFile = new JFileChooser();
                saveFile.showSaveDialog(null);
            }
        });

        openBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser openFile = new JFileChooser();
                openFile.showOpenDialog(null);
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
}