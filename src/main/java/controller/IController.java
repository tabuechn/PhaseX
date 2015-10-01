package controller;

import controller.states.AbstractState;
import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.player.IPlayer;
import model.stack.ICardStack;
import util.IObservable;

import java.util.List;
import java.util.Map;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 */
public interface IController extends IObservable {


    void notifyObservers();

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
     * Returns a Map with the players names and their number of cards
     *
     * @return map with the players names and their number of cards
     */
    Map<String, Integer> getNumberOfCardsForAllPlayers();

    /**
     * Initialises the Game
     */
    void initGame();

    /**
     * Initialises a new round by shuffling the cards and giving 10 cards to each Player
     */
    void newRound();

    /**
     * End the Round by adding Points for the Players with remaining cards and incrementing their phasecounter
     */
    void endRound();

    /**
     * Getter for the UIs to get all dtacks which are played in the current Round
     *
     * @return List of all stacks
     */
    List<ICardStack> getAllStacks();

    /**
     * Draws a card from the discard pile with the current Player
     */
    void drawOpenCard();

    /**
     * Draws a card from the hidden pile with the current Player
     */
    void drawHiddenCard();

    /**
     * Discards the card form the current player's hand
     *
     * @param card the card form the current player to discard
     */
    void discardCard(ICard card);

    /**
     * Getter for the UIs to let them know, in which state the game is right now
     *
     * @return the current State
     */
    AbstractState getRoundState();

    /**
     * Setter for the state pattern to set the current state
     *
     * @param roundState the next state
     */
    void setRoundState(AbstractState roundState);

    /**
     * Checker for the states to check if the round has ended
     *
     * @return true if the current player has no more cards in his hand or else its false
     */
    boolean currentPlayerHasNoCards();

    /**
     * Changes to the next player
     */
    void nextPlayer();

    /**
     * Checks if the given IDeckOfCards matches the current phase of the current Player
     *
     * @param phase phase
     * @return tur if the cards match the phase or else false
     */
    boolean deckMatchesCurrentPlayersPhase(IDeckOfCards phase);

    /**
     * Removes the Card form the players hand and lays the phase down
     *
     * @param phase the phase to lay down
     */
    void addPhase(IDeckOfCards phase);

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

    String getStatusMessage();

    void setStatusMessage(String statusMessage);

    String getCurrentPhaseDescription();

    boolean isGameFinished();
}
