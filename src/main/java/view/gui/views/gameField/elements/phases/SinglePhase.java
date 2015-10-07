package view.gui.views.gameField.elements.phases;

import model.stack.ICardStack;
import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;
import view.gui.cardDrawer.DrawnCard;

import javax.swing.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class SinglePhase extends JPanel {

    private ICardStack phase;

    private CardPanel panel;

    public SinglePhase(ICardStack stack) {
        addAlreadyPlayedPhase(stack);
        this.setOpaque(false);
        this.setVisible(true);
    }

    private void addAlreadyPlayedPhase(ICardStack cards) {
        this.phase = cards;
        if (cards == null) {
            this.add(new DrawnCard(GUIConstants.BLANK_CARD));
        } else {
            panel = new CardPanel();
            panel.setMultipleCards(cards.getList());
            this.add(panel);
        }


    }

    public ICardStack getPhase() {
        return phase;
    }
}
