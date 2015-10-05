package view.gui.views.gameField.elements.Piles;

import controller.IController;
import model.card.ICard;
import view.gui.views.gameField.elements.player.CurrentPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PilePane extends JPanel {

    private HiddenPile hidden;

    private OpenPile open;

    private IController controller;

    private CurrentPlayer player;

    public PilePane(IController controller) {
        this.controller = controller;
        hidden = new HiddenPile();
        open = new OpenPile();

        this.setOpaque(false);
        this.setLayout(new GridLayout(2, 1, 5, 5));
        this.add(hidden);
        this.add(open);
        this.setVisible(true);

        addListeners();
    }

    private void addListeners() {
        hidden.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.drawHidden();
            }
        });

        open.addMouseListener(new MouseAdapter() {
            private void checkIfOnlyOneCardIsSelected(ICard card) {
                if (card == null) {
                    player.addMultipleCards(controller.getCurrentPlayersHand());
                }
                controller.discardCard(card);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (("DrawPhase").equals(controller.getRoundState().toString())) {
                    controller.drawOpen();
                } else if (("PlayerTurnFinished".equals(controller.getRoundState().toString()) ||
                        "PlayerTurnNotFinished".equals(controller.getRoundState().toString()))) {
                    checkIfOnlyOneCardIsSelected(player.getChosenCard());
                }
            }


        });
    }

    public void setOpenPileCard(ICard card) {
        open.setOpenCard(card);
    }

    public void setOpenEnabled(boolean enabled) {
        open.setEnabled(enabled);
    }

    public void setHiddenEnabled(boolean enabled) {
        hidden.setEnabled(enabled);
    }

    public void setPlayer(CurrentPlayer player) {
        this.player = player;
    }
}
