package view.gui.views.gameField.elements.player;

import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;
import view.gui.cardDrawer.DrawnCard;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CurrentPlayer extends CardPanel {

    private SpringLayout layout;

    private IDeckOfCards cards;

    public CurrentPlayer() {
        super();
        layout = getLayout();
    }


    private void addPossibilityToShiftCardUp(DrawnCard card) {
        card.getComponentsInLayer(0)[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (card.isChosen()) {
                    shiftCardUp(card, GUIConstants.CARD_POSITION_TOP_BORDER);
                    card.setChosen(false);
                } else {
                    shiftCardUp(card, 0);
                    card.setChosen(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!card.isChosen()) {
                    shiftCardUp(card, GUIConstants.CARD_POSITION_TOP_BORDER - 25);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!card.isChosen()) {
                    shiftCardUp(card, GUIConstants.CARD_POSITION_TOP_BORDER);
                }
            }
        });
    }

    private void shiftCardUp(DrawnCard card, int pos) {
        layout.putConstraint(SpringLayout.NORTH, card, pos, SpringLayout.NORTH, this);
        this.updateUI();
    }

    @Override
    public void addCard(ICard card) {
        super.addCard(card);
        addPossibilityToShiftCardUp(new DrawnCard(card));
    }

    public IDeckOfCards getChosenCards() {
        return super.getChosenCards();
    }

    public void addMultipleICards(IDeckOfCards cards) {
        this.cards = cards;
        cards.forEach(this::addCard);
    }

    public IDeckOfCards getCards() {
        return cards;
    }
}
