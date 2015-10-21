package view.gui.specialViews;

import javax.swing.*;
import java.awt.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 28.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CenteredLabel extends JLabel {
    protected CenteredLabel() {
        super();
        setCentered();
    }

    protected CenteredLabel(String text) {
        super(text);
        setCentered();
    }

    private void setCentered() {
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
    }

    protected void setFixDimension(Dimension dimension) {
        setAllDimensions(dimension, dimension);
    }

    private void setAllDimensions(Dimension min, Dimension max) {
        this.setMinimumSize(min);
        this.setPreferredSize(max);
        this.setMaximumSize(max);
    }
}
