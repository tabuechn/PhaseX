package view.gui.views.menu.elements;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.gui.GUIConstants;
import view.gui.specialViews.CenteredLabel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * If everything works right this class was
 * created by Konraifen88 on 26.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class LogoLabel extends CenteredLabel {

    private static final Logger LOG = LogManager.getLogger(LogoLabel.class);

    public LogoLabel() {
        super();
        this.setFixDimension(GUIConstants.LOGO_LABEL_SIZE);
        addLogoImage();
        this.setVisible(true);
    }


    private void addLogoImage() {
        this.setIcon(new ImageIcon(getClass().getResource(GUIConstants.LOGO_IMAGE_PATH)));
    }
}
