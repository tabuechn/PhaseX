package view.gui.views.gameField.elements.phases;

import javax.swing.*;
import java.awt.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PhasePane extends JPanel {
    public PhasePane() {
        this.setLayout(new GridLayout(2, 2, 5, 5));
        this.add(new SinglePhase());
        this.add(new SinglePhase());
        this.add(new SinglePhase());
        this.add(new SinglePhase());
    }
}
