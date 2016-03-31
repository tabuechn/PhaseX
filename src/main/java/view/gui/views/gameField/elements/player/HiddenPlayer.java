package view.gui.views.gameField.elements.player;

import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;
import view.gui.cardDrawer.DrawnCard;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class HiddenPlayer extends CardPanel {

    private final SpringLayout layout;

    public HiddenPlayer(Map<Integer, Integer> numberOfCards) {
        super();
        this.layout = super.getLayout();
        this.setPreferredSize(GUIConstants.HIDDEN_PLAYER_CARD_PANEL_SIZE);
        addHiddenCards(numberOfCards);
    }

    private void addHiddenCards(Map<Integer, Integer> numberOfCards) {
        this.removeAll();
        for (Integer player : numberOfCards.values()) {
            IDeckOfCards hiddenCards = getHiddenCards(player);
            setMultipleCards(hiddenCards);
            shiftCardsUp();
        }
    }

    private IDeckOfCards getHiddenCards(Integer sum) {
        IDeckOfCards hiddenCards = new DeckOfCards();
        for (int i = 0; i < sum; i++) {
            hiddenCards.add(GUIConstants.BACK_CARD);
        }
        return hiddenCards;
    }

    private void shiftCardsUp() {
        List<DrawnCard> tmp = getAllDrawnCards();
        for (DrawnCard drawnCard : tmp) {
            layout.putConstraint(SpringLayout.NORTH, drawnCard, 0, SpringLayout.NORTH, this);
        }
    }

    public void setNewNumberOfCards(Map<Integer, Integer> num) {
        addHiddenCards(num);
    }
}
