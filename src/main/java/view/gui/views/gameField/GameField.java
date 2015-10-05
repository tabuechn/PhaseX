package view.gui.views.gameField;

import controller.IController;
import view.gui.specialViews.BackgroundPanel;
import view.gui.views.commonViews.NotificationLabel;
import view.gui.views.gameField.elements.Piles.PilePane;
import view.gui.views.gameField.elements.phases.PhaseDescription;
import view.gui.views.gameField.elements.phases.PhasePane;
import view.gui.views.gameField.elements.player.CurrentPlayer;
import view.gui.views.gameField.elements.player.HiddenPlayer;

import javax.swing.*;
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
    private PhasePane phases;
    private NotificationLabel notification;
    private PhaseDescription phaseDescription;

    public GameField(IController controller) {
        this.controller = controller;
        controller.startGame();
        this.setLayout(new BorderLayout());

        //Add Piles
        pile = new PilePane(controller);
        this.add(pile, BorderLayout.EAST);

        //Add current player
        currentPlayer = new CurrentPlayer();
        currentPlayer.addMultipleCards(controller.getCurrentPlayersHand());
        this.add(currentPlayer, BorderLayout.SOUTH);

        //Add hiddenPlayer, phaseDescription and notificationLabel
        JPanel upper = new JPanel();
        upper.setLayout(new BorderLayout(5, 5));
        upper.setOpaque(false);
        hiddenPlayer = new HiddenPlayer(controller.getNumberOfCardsForNextPlayer());
        notification = new NotificationLabel();
        phaseDescription = new PhaseDescription();
        upper.add(phaseDescription, BorderLayout.NORTH);
        upper.add(hiddenPlayer, BorderLayout.CENTER);
        upper.add(phaseDescription, BorderLayout.EAST);
        this.add(upper, BorderLayout.NORTH);
        upper.setVisible(true);

        //Add phases Panel
        phases = new PhasePane(4, controller);
        this.add(phases);


        this.setVisible(true);
    }

    public void updateGameField() {

    }

    public void activateDrawPhase() {
        pile.setEnabled(true);
        currentPlayer.setEnabled(false);
        phases.setEnabled(false);
    }

    public void activatePlayerTurnFinishedPhase() {
        pile.setEnabled(true);
        currentPlayer.setEnabled(true);
        phases.setEnabled(true);
    }

    public void activatePlayerTurnNotFinished() {
        pile.setEnabled(true);
        currentPlayer.setEnabled(true);
        phases.setEnabled(false);
    }

    public CurrentPlayer getCurrentPlayer() {
        return currentPlayer;
    }
}
