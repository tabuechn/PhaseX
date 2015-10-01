package view.gui.views.gameField.elements.Piles;

import model.card.ICard;
import view.gui.GUIConstants;
import view.gui.cardDrawer.DrawnCard;

import javax.swing.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 01.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class OpenPile extends JPanel {

    private DrawnCard drawn;

    public OpenPile() {
        drawn = new DrawnCard(GUIConstants.BLANK_CARD);
        this.add(drawn);
        this.setOpaque(false);
        this.setVisible(true);
    }

    public void setOpenCard(ICard card) {
        this.remove(drawn);
        if (card != null) {
            drawn = new DrawnCard(card);
        } else {
            drawn = new DrawnCard(GUIConstants.BLANK_CARD);
        }
        this.add(drawn);
        this.updateUI();
    }

}
