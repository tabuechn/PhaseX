package view.gui.views.gameField.elements.Piles;

import model.card.CardColor;
import model.card.ICard;
import model.card.impl.Card;
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
        drawn = new DrawnCard(new Card(0, CardColor.BLANK));
        this.add(drawn);
        this.setOpaque(false);
        this.setVisible(true);
    }

    public void setOpenCard(ICard card) {
        this.remove(drawn);
        drawn = new DrawnCard(card);
        this.add(drawn);
        this.updateUI();
    }

}
