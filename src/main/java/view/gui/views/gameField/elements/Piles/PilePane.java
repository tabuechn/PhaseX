package view.GUI.views.gameField.elements.Piles;

import controller.IController;
import model.card.ICard;
import view.GUI.specialViews.BackgroundPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PilePane extends BackgroundPanel{

    private HiddenPile hidden;

    private OpenPile open;

    private IController controller;

    public PilePane(IController controller) {
        this.controller = controller;
        hidden = new HiddenPile();
        open = new OpenPile();

        this.setLayout(new GridLayout(2,1, 5, 5));
        this.add(hidden);
        this.add(open);
        this.setVisible(true);

        addListeners();
    }

    public void addListeners() {
        hidden.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.drawHidden();
            }
        });

        open.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (("DrawPhase").equals(controller.getRoundState().toString())) {
                    controller.drawOpen();
                } else if (("PlayerTurnFinished".equals(controller.getRoundState().toString()) ||
                        "PlayerTurnNotFinished".equals(controller.getRoundState().toString()))) {
                    //TODO: What to do when discard phase
                }
            }
        });
    }

    public void setOpenPileCard(ICard card) {
        open.setOpenCard(card);
    }

}
