package view.gui.cardDrawer;

import model.card.ICard;
import model.deck.IDeckOfCards;
import view.gui.GUIConstants;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 30.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CardPanel extends JPanel {

    private final SpringLayout layout;
    private List<DrawnCard> allDrawnCards;

    protected CardPanel() {
        allDrawnCards = new LinkedList<>();
        layout = new SpringLayout();
        this.setLayout(layout);
        this.setOpaque(false);
        this.setVisible(true);
    }

    @Override
    public SpringLayout getLayout() {
        return layout;
    }

    protected void addCard(ICard card) {
        DrawnCard drawnCard = new DrawnCard(card);
        allDrawnCards.add(drawnCard);
        this.add(drawnCard);
        allDrawnCards.forEach(this::setDeferral);
    }

    protected List<DrawnCard> setMultipleCards(IDeckOfCards cards) {
        allDrawnCards = new LinkedList<>();
        this.removeAll();
        cards.forEach(this::addCard);
        return allDrawnCards;
    }

    private void setDeferral(JComponent card) {
        if (card == allDrawnCards.get(0)) {
            layout.putConstraint(SpringLayout.WEST, card, GUIConstants.CARD_POSITION_LEFT_BORDER, SpringLayout.WEST,
                    this);
            layout.putConstraint(SpringLayout.NORTH, card, GUIConstants.CARD_POSITION_TOP_BORDER, SpringLayout.NORTH,
                    this);
        } else {
            @SuppressWarnings("SuspiciousMethodCalls") int pos = allDrawnCards.indexOf(card);
            layout.putConstraint(SpringLayout.WEST, card, GUIConstants.CARD_ADJUSTMENT, SpringLayout.EAST,
                    allDrawnCards.get(pos - 1));
            layout.putConstraint(SpringLayout.NORTH, card, GUIConstants.CARD_POSITION_TOP_BORDER, SpringLayout.NORTH,
                    this);
        }
    }

    protected List<DrawnCard> getAllDrawnCards() {
        return allDrawnCards;
    }
}
