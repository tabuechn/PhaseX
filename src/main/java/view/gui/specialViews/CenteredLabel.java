package view.GUI.specialViews;

import javax.swing.*;
import java.awt.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 28.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CenteredLabel extends JLabel {
    public CenteredLabel(){
        super();
        setCentered();
    }

    public CenteredLabel(ImageIcon image){
        super(image);
        setCentered();
    }

    public CenteredLabel(String text){
        super(text);
        setCentered();
    }

    private void setCentered() {
        this.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
    }

    public void setFixDimension(Dimension dimension){
        setAllDimensions(dimension, dimension);
    }

    public void setAllDimensions(Dimension min, Dimension max) {
        this.setMinimumSize(min);
        this.setPreferredSize(max);
        this.setMaximumSize(max);
    }
}
