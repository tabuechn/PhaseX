package view.gui.views.gameField.elements.phases;

import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.stack.ICardStack;
import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class SinglePhase extends CardPanel {

    private ICardStack phase;

    public SinglePhase(ICardStack stack) {
        addAlreadyPlayedPhase(stack);
        this.setOpaque(false);
        this.setVisible(true);
    }

    private void addAlreadyPlayedPhase(ICardStack cards) {
        this.phase = cards;
        if (cards == null) {
            this.addCard(GUIConstants.BLANK_CARD);
        } else {
            setMultipleCards(removeSomeCardsIfGreaterThanSix(cards));
        }
    }

    private IDeckOfCards removeSomeCardsIfGreaterThanSix(ICardStack cards) {
        IDeckOfCards min = new DeckOfCards();
        if (cards.getList().size() > GUIConstants.MAX_SHOWN_CARDS) {
            min.addAll(cards.getList().subList(0, 3));
            min.addAll(cards.getList().subList(cards.getList().size() - 3, cards.getList().size()));
        } else {
            min.addAll(cards.getList());
        }
        return min;
    }

    public ICardStack getPhase() {
        return phase;
    }
}
