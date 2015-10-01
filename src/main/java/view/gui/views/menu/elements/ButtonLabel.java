package view.GUI.views.menu.elements;

import view.GUI.GUIConstants;
import view.GUI.specialViews.CenteredLabel;

import javax.swing.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 28.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ButtonLabel extends CenteredLabel {

    public ButtonLabel() {
        JButton startGame = new JButton("Start Game");
        setButtonSize(startGame);
        startGame.setVisible(true);
        this.add(startGame);
        this.setVisible(true);
        this.repaint();
    }

    private void setButtonSize(JButton startGame) {
        startGame.setMinimumSize(GUIConstants.BUTTON_SIZE);
        startGame.setPreferredSize(GUIConstants.BUTTON_SIZE);
        startGame.setMaximumSize(GUIConstants.BUTTON_SIZE);
    }
}
