package view.gui.views.gameField.elements.Piles;

import view.gui.GUIConstants;
import view.gui.cardDrawer.DrawnCard;

import javax.swing.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 01.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
class HiddenPile extends JPanel {

    public HiddenPile() {
        DrawnCard drawn = new DrawnCard(GUIConstants.BACK_CARD);
        this.add(drawn);
        this.setOpaque(false);
        this.setVisible(true);
    }

}
