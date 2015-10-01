package view.GUI.specialViews;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.GUI.GUIConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class BackgroundPanel extends JPanel {

    private static final Logger LOG = LogManager.getLogger(BackgroundPanel.class);

    private Image background;

    public BackgroundPanel(){
        getBackgroundImage();
        this.setSize(GUIConstants.WINDOW_SIZE);
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(background, 0, 0, this);
    }

    private void getBackgroundImage() {
        try {
            background = ImageIO.read(new File(GUIConstants.BACKGROUND_IMAGE_PATH));
        } catch (IOException e){
            LOG.error("Background image can not be found!", e);
        }
    }

}
