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

    private DrawnCard empty;

    public SinglePhase() {
        this.setPreferredSize(GUIConstants.CARD_DIMENSION);
        empty = new DrawnCard(GUIConstants.BLANK_CARD);
        this.add(empty);
        this.setOpaque(false);
        this.setVisible(true);
    }

    public void addAlreadyPlayedPhase(ICardStack cards) {
        this.phase = cards;
        this.remove(empty);
        panel = new CardPanel();
        panel.addMultipleCards(cards.getList());


    }

    public ICardStack getPhase() {
        return phase;
    }
}
