package view.gui.views.gameField.elements.player;

import model.card.CardColor;
import model.card.impl.Card;
import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;
import view.gui.cardDrawer.DrawnCard;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class HiddenPlayer extends CardPanel {

    public HiddenPlayer(int numberOfCards) {
        super(GUIConstants.PLAYER_CARD_PANEL_SIZE);
        for (int i = 0; i < numberOfCards; i++) {
            addCard(new DrawnCard(new Card(0, CardColor.BACK)));
        }
    }
}
