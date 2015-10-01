package view.gui.views.gameField.elements.player;

import model.card.ICard;
import view.gui.GUIConstants;
import view.gui.cardDrawer.CardPanel;
import view.gui.cardDrawer.DrawnCard;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class CurrentPlayer extends CardPanel {

    private SpringLayout layout;

    public CurrentPlayer() {
        super(GUIConstants.PLAYER_CARD_PANEL_SIZE);
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
    public void addCard(DrawnCard card) {
        super.addCard(card);
        addPossibilityToShiftCardUp(card);
    }

    public List<ICard> getChosenCards() {
        return super.getChosenCards();
    }

    public void addMultipleICards(List<ICard> cards) {
        for (ICard card : cards) {
            addCard(new DrawnCard(card));
        }
    }
}
