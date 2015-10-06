package view.gui.views.gameField.elements.player;

import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;
import view.gui.cardDrawer.DrawnCard;

import javax.swing.*;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class HiddenPlayer extends CardPanel {

    private SpringLayout layout;

    public HiddenPlayer(int numberOfCards) {
        super();
        this.layout = super.getLayout();
        this.setPreferredSize(GUIConstants.HIDDEN_PLAYER_CARD_PANEL_SIZE);
        IDeckOfCards hiddenCards = new DeckOfCards();
        for (int i = 0; i < numberOfCards; i++) {
            hiddenCards.add(GUIConstants.BACK_CARD);
        }
        setMultipleCards(hiddenCards);
        shiftCardsUp();
    }

    private void shiftCardsUp() {
        List<DrawnCard> tmp = getAllDrawnCards();
        for (DrawnCard drawnCard : tmp) {
            layout.putConstraint(SpringLayout.NORTH, drawnCard, 0, SpringLayout.NORTH, this);
        }
    }
}
