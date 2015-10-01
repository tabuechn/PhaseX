package view.gui.views.gameField.elements.phases;

import javax.swing.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 01.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PhaseDescription extends JPanel {

    private JTextPane textPane;

    public PhaseDescription() {
        textPane = new JTextPane();
        this.add(textPane);
        this.setOpaque(false);
        this.setVisible(true);
    }

    public void setText(String text) {
        textPane.setText(text);
    }
}
