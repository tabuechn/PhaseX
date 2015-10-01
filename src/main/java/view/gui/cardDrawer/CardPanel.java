package view.gui.cardDrawer;

import model.card.ICard;
import view.gui.GUIConstants;
import view.gui.specialViews.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 30.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CardPanel extends BackgroundPanel {

    private List<DrawnCard> cards;

    private List<ICard> chosenCards;

    private SpringLayout layout;

    public CardPanel(Dimension dimension) {
        cards = new LinkedList<>();
        layout = new SpringLayout();
        this.setLayout(layout);
        this.setMinimumSize(dimension);
        this.setPreferredSize(dimension);
        this.setMaximumSize(dimension);
        this.setVisible(true);
    }

    @Override
    public SpringLayout getLayout() {
        return layout;
    }

    public void addCard(DrawnCard card) {
        cards.add(card);
        this.add(card);
        updateView();
    }


    private void updateView() {
        applyDeferral(GUIConstants.CARD_ADJUSTMENT);
    }

    private void applyDeferral(double deferral) {
        for (JComponent card : cards) {
            setDeferral(card, deferral);
        }
    }

    private void setDeferral(JComponent card, double deferral) {
        if (card == cards.get(0)) {
            layout.putConstraint(SpringLayout.WEST, card, GUIConstants.CARD_POSITION_LEFT_BORDER, SpringLayout.WEST,
                    this);
            layout.putConstraint(SpringLayout.NORTH, card, GUIConstants.CARD_POSITION_TOP_BORDER, SpringLayout.NORTH,
                    this);
        } else {
            @SuppressWarnings("SuspiciousMethodCalls") int pos = cards.indexOf(card);
            layout.putConstraint(SpringLayout.WEST, card, (int) deferral, SpringLayout.EAST, cards.get(pos - 1));
            layout.putConstraint(SpringLayout.NORTH, card, GUIConstants.CARD_POSITION_TOP_BORDER, SpringLayout.NORTH,
                    this);
        }
    }


    public List<ICard> getChosenCards() {
        cards.forEach(this::addChosenCardToList);
        return chosenCards;
    }

    private void addChosenCardToList(DrawnCard card) {
        if (card.isChosen()) {
            chosenCards.add(card.getCard());
        }
    }
}
