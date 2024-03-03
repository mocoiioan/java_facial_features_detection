package gui;

import constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.Serial;

public class ImagePanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;
    private JLabel imageLabel;
    private ImageIcon transformedImageIcon;

    public ImagePanel(){
        this.imageLabel = new JLabel();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(Constants.IMAGE_LABEL_BORDER, Constants.IMAGE_LABEL_BORDER,
                Constants.IMAGE_LABEL_BORDER, Constants.IMAGE_LABEL_BORDER));

        add(imageLabel, BorderLayout.CENTER);
    }

    public void updateImage(final Image image){
        imageLabel.setIcon(new ImageIcon(scaleImage(image)));
    }

    public Image scaleImage(final Image image){
        return image.getScaledInstance(800, 800, Image.SCALE_SMOOTH);
    }

    public void loadImage(File file){
        this.transformedImageIcon = new ImageIcon(file.getAbsolutePath());
        Image image = transformedImageIcon.getImage();
        updateImage(image);
    }
}
