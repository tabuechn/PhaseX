package view.gui.views.gameField;

import controller.UIController;
import model.card.ICard;
import model.card.impl.CardValueComparator;
import model.deckOfCards.IDeckOfCards;
import view.gui.GUIConstants;
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
    private UIController controller;
    private PhasePane phases;
    private NotificationLabel notification;
    private PhaseDescription phaseDescription;

    public GameField(UIController controller) {
        this.controller = controller;
        controller.startGame();
        this.setLayout(new BorderLayout());

        //Add current player
        currentPlayer = new CurrentPlayer();
        currentPlayer.setMultipleCards(controller.getCurrentPlayersHand());
        this.add(currentPlayer, BorderLayout.SOUTH);

        //Add Piles
        pile = new PilePane(controller, currentPlayer);
        this.add(pile, BorderLayout.EAST);

        //Add hiddenPlayer, phaseDescription and notificationLabel
        JPanel upper = new JPanel();
        upper.setLayout(GUIConstants.DEFAULT_BORDER_LAYOUT);
        upper.setOpaque(false);
        hiddenPlayer = new HiddenPlayer(controller.getNumberOfCardsForNextPlayer());
        notification = new NotificationLabel();
        phaseDescription = new PhaseDescription();
        phaseDescription.setFont(GUIConstants.FONT_PHASE_DESCRIPTION);
        upper.add(phaseDescription, BorderLayout.NORTH);
        upper.add(hiddenPlayer, BorderLayout.CENTER);
        upper.add(phaseDescription, BorderLayout.EAST);
        this.add(upper, BorderLayout.NORTH);
        upper.setVisible(true);
        //test
        //Add phases Panel
        phases = new PhasePane(controller);
        this.add(phases);


        this.setVisible(true);
    }

    public void updateGameField() {
        setSortedPlayerCards();
        phases.setCurrentPlayer(currentPlayer);
        phases.setAlreadyPlayedPhases(controller.getAllStacks());
        pile.setOpenPileCard(getOpenPileOrBlankCard());
        hiddenPlayer.setNewNumberOfCards(controller.getNumberOfCardsForNextPlayer());
        notification.setText(controller.getStatusMessage());
        phaseDescription.setText(getDescriptionText());
        this.updateUI();
    }

    private String getDescriptionText() {
        return "<html>Player: " + controller.getCurrentPlayer().getPlayerName() + "<br><br>Phase: " +
                controller.getCurrentPhaseDescription() + "</html>";
    }

    private void setSortedPlayerCards() {
        IDeckOfCards cards = controller.getCurrentPlayersHand();
        cards.sort(new CardValueComparator());
        currentPlayer.setMultipleCards(cards);
    }

    public void activateDrawPhase() {
        pile.setHiddenEnabled(true);
        currentPlayer.setEnabled(false);
        phases.setEnabled(false);
    }

    public void activatePlayerTurnFinishedPhase() {
        pile.setHiddenEnabled(false);
        currentPlayer.setEnabled(true);
        phases.setEnabled(true);
    }

    public void activatePlayerTurnNotFinished() {
        pile.setHiddenEnabled(false);
        currentPlayer.setEnabled(true);
        phases.setEnabled(false);
    }

    private ICard getOpenPileOrBlankCard() {
        IDeckOfCards tmp = controller.getDiscardPile();
        if (tmp.size() > GUIConstants.ZERO) {
            return tmp.get(tmp.size() - GUIConstants.ONE);
        } else {
            return GUIConstants.BLANK_CARD;
        }
    }
}
