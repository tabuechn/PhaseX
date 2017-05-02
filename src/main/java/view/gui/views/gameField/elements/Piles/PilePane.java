package view.gui.views.gameField.elements.Piles;

import controller.UIController;
import model.card.ICard;
import view.gui.views.gameField.elements.player.CurrentPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PilePane extends JPanel {

    private static final GridLayout PILE_PANE_LAYOUT = new GridLayout(2, 1, 5, 5);

    private final HiddenPile hidden;

    private final OpenPile open;

    private final UIController controller;

    private final CurrentPlayer player;

    public PilePane(UIController controller, CurrentPlayer player) {
        this.controller = controller;
        this.player = player;
        hidden = new HiddenPile();
        open = new OpenPile();

        this.setOpaque(false);
        this.setLayout(PILE_PANE_LAYOUT);
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
            private void checkIfOnlyOneCardIsSelected(List<ICard> cards) {
                if (cards.size() != 1) {
                    player.setMultipleCards(controller.getCurrentPlayersHand());
                } else {
                    controller.discard(cards.get(0));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (("DRAW_PHASE").equals(controller.getRoundState().toString())) {
                    controller.drawOpen();
                } else if (("PLAYER_TURN_NOT_FINISHED".equals(controller.getRoundState().toString()) ||
                        "PLAYER_TURN_FINISHED".equals(controller.getRoundState().toString()))) {
                    checkIfOnlyOneCardIsSelected(player.getChosenCards());
                }
            }
        });
    }

    public void setOpenPileCard(ICard card) {
        open.setOpenCard(card);
    }

    public void setHiddenEnabled(boolean enabled) {
        hidden.setEnabled(enabled);
    }

}
