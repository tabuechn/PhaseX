package view.gui.views.gameField.elements.player;

import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;
import view.gui.cardDrawer.DrawnCard;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CurrentPlayer extends CardPanel {

    private final SpringLayout layout;
    private List<DrawnCard> drawnCards;
    private IDeckOfCards chosenCards;

    public CurrentPlayer() {
        super();
        this.setPreferredSize(GUIConstants.PLAYER_CARD_PANEL_SIZE);
        this.setOpaque(false);
        layout = getLayout();

        this.setVisible(true);
    }

    private void addPossibilityToShiftCardUp(DrawnCard card) {
        drawnCards.add(card);
        card.getComponentsInLayer(0)[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (card.isChosen()) {
                    shiftCardUp(card, GUIConstants.CARD_POSITION_TOP_BORDER);
                    card.setChosen(false);
                    chosenCards.remove(card.getCard());
                } else {
                    shiftCardUp(card, 0);
                    card.setChosen(true);
                    chosenCards.add(card.getCard());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!card.isChosen()) {
                    shiftCardUp(card, 0);
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
    public List<DrawnCard> setMultipleCards(IDeckOfCards cards) {
        this.chosenCards = new DeckOfCards();
        this.drawnCards = new LinkedList<>();
        super.setMultipleCards(cards).forEach(this::addPossibilityToShiftCardUp);
        this.updateUI();
        this.repaint();
        return this.drawnCards;
    }

    public IDeckOfCards getChosenCards() {
        return chosenCards;
    }

}
