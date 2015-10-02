package view.gui.views.gameField.elements.player;

import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class HiddenPlayer extends CardPanel {

    public HiddenPlayer(int numberOfCards) {
        super();
        for (int i = 0; i < numberOfCards; i++) {
            addCard(GUIConstants.BACK_CARD);
        }
    }
}
