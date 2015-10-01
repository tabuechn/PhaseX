package view.gui.views.gameField;

import controller.IController;
import view.gui.specialViews.BackgroundPanel;
import view.gui.views.commonViews.NotificationLabel;
import view.gui.views.gameField.elements.Piles.PilePane;
import view.gui.views.gameField.elements.phases.PhaseDescription;
import view.gui.views.gameField.elements.phases.PhasePane;
import view.gui.views.gameField.elements.player.CurrentPlayer;
import view.gui.views.gameField.elements.player.HiddenPlayer;

import java.awt.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class GameField extends BackgroundPanel {

    private PilePane pile;

    private CurrentPlayer currentPlayer;

    private HiddenPlayer hiddenPlayer;

    private IController controller;

    private PhasePane phase;

    private NotificationLabel notification;

    private PhaseDescription phaseDescription;

    public GameField(IController controller) {
        this.controller = controller;
        controller.startGame();

        pile = new PilePane(controller);
        currentPlayer = new CurrentPlayer();
        currentPlayer.addMultipleICards(controller.getCurrentPlayersHand());
        this.setLayout(new BorderLayout());
        this.add(pile, BorderLayout.EAST);
        this.add(currentPlayer, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public void updateGameField() {

    }

    public void activateDrawPhase() {
        pile.setEnabled(true);
        currentPlayer.setEnabled(false);
        phase.setEnabled(false);
    }

    public void activatePlayerTurnFinishedPhase() {
        pile.setEnabled(true);
        currentPlayer.setEnabled(true);
        phase.setEnabled(true);
    }

    public void activatePlayerTurnNotFinished() {
        pile.setEnabled(true);
        currentPlayer.setEnabled(true);
        phase.setEnabled(false);
    }
}
