package view.gui.views.gameField.elements.phases;

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
            setMultipleCards(cards.getList());
        }
    }

    public ICardStack getPhase() {
        return phase;
    }
}
