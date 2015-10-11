package controller;

import controller.states.AbstractState;
import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.player.IPlayer;
import model.stack.ICardStack;
import util.IObservable;

import java.util.List;

/**
 * Created by tabuechn on 11.10.2015. Be grateful for this code! ^(°.°)^
 */
public interface UIController extends IObservable {
    /**
     * Event to start the Game for the UIs
     */
    void startGame();

    /**
     * Event to draw a card from the discard pile
     */
    void drawOpen();

    /**
     * Event to draw a card from the hidden pile
     */
    void drawHidden();

    /**
     * Event to Discard a Card for the UIs
     *
     * @param card the card to be discarded
     */
    void discard(ICard card);

    /**
     * Event to lay down a Phase for the UIs
     *
     * @param phase the phase to be check and played
     */
    void playPhase(IDeckOfCards phase);

    /**
     * Event for the UIs to add a card to finished phase
     *
     * @param card  the card to add to the stack
     * @param stack the stack the card should be added to
     */
    void addToFinishedPhase(ICard card, ICardStack stack);

    void addMultipleCardsToFinishedPhase(List<ICard> cards, ICardStack stack);

    /**
     * Returns the cards which the current Player has in his hand
     *
     * @return the cards of the current Player
     */
    IDeckOfCards getCurrentPlayersHand();

    /**
     * Returns the number of cards for the next player
     *
     * @return number of cards for the next player
     */
    int getNumberOfCardsForNextPlayer();

    /**
     * Getter for the UIs to get all dtacks which are played in the current Round
     *
     * @return List of all stacks
     */
    List<ICardStack> getAllStacks();

    /**
     * Getter for the UIs to let them know, in which state the game is right now
     *
     * @return the current State
     */
    AbstractState getRoundState();

    /**
     * Getter for the draw pile
     *
     * @return the draw pile
     */
    IDeckOfCards getDrawPile();

    /**
     * Getter for the discard pile
     *
     * @return the discard pile
     */
    IDeckOfCards getDiscardPile();

    /**
     * Getter for the current player
     *
     * @return the current player
     */
    IPlayer getCurrentPlayer();

    /**
     * Stops the application
     */
    void exitEvent();


    /**
     * Getter for the status message
     * @return the current status message
     */
    String getStatusMessage();

    /**
     * Getter for the phase description
     *
     * @return description of the current phase
     */
    String getCurrentPhaseDescription();
}
