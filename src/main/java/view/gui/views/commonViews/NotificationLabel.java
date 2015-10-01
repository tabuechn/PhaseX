package view.gui.views.commonViews;

import view.gui.GUIConstants;
import view.gui.specialViews.CenteredLabel;

import javax.swing.*;
import java.awt.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 28.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class NotificationLabel extends CenteredLabel {

    public NotificationLabel() {
        super("");
        this.setFont(GUIConstants.DEFAULT_FONT);
        this.setVerticalTextPosition(JLabel.CENTER);
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setMinimumSize(GUIConstants.MINIMUM_NOTIFICATION_SIZE);
        this.setPreferredSize(GUIConstants.MAXIMUM_NOTIFICATION_SIZE);
        this.setMaximumSize(GUIConstants.MAXIMUM_NOTIFICATION_SIZE);
        this.setVisible(true);
    }

    public void changeTextSize(float size) {
        this.setFont(getFont().deriveFont(size));
    }

    public void setFontColor(Color color) {
        this.setForeground(color);
    }
}
