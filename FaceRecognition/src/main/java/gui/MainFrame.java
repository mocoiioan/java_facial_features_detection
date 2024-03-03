package gui;

import algorithm.FaceDetection;
import constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serial;

public class MainFrame extends JFrame{
    @Serial
    private static final long serialVersionUID = 1L;
    // image panel
    private ImagePanel imagePanel;
    private JFileChooser jFileChooser;
    private FaceDetection faceDetection;
    private File file;

    // webcam

    public MainFrame(){
        super(Constants.APPLICATION_NAME);

        setJMenuBar(createMenuBar());

        this.imagePanel = new ImagePanel();
        this.jFileChooser = new JFileChooser();
        this.faceDetection = new FaceDetection();

        add(imagePanel, BorderLayout.CENTER);

        setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(this);
    }

    public JMenuBar createMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem loadMenuItem = new JMenuItem("Load image");
        JMenuItem detectMenuItem = new JMenuItem("Detect faces");
        JMenuItem exitMenuItem = new JMenuItem("Exit App");
        fileMenu.add(loadMenuItem);
        fileMenu.add(detectMenuItem);
        fileMenu.add(exitMenuItem);

        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jFileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION){
                    MainFrame.this.file = jFileChooser.getSelectedFile();
                    MainFrame.this.imagePanel.loadImage(MainFrame.this.file);
                }
            }
        });

        detectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.faceDetection.detectFaces(MainFrame.this.file, MainFrame.this.imagePanel);
            }
        });

        jMenuBar.add(fileMenu);

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(MainFrame.this, Constants.EXIT_WARNING);

                if(action == JOptionPane.OK_OPTION){
                    System.gc();
                    System.exit(0);
                }
            }
        });

        return jMenuBar;
    }
}
