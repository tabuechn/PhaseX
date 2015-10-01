package controller.impl;

import controller.IController;
import controller.states.AbstractState;
import controller.states.impl.DrawPhase;
import controller.states.impl.PlayerTurnFinished;
import controller.states.impl.PlayerTurnNotFinished;
import controller.states.impl.StartPhase;
import model.card.ICard;
import model.card.impl.CardComparator;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.phase.impl.Phase5;
import model.player.IPlayer;
import model.player.impl.Player;
import model.stack.ICardStack;
import util.CardCreator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 */
public class Controller extends util.Observable implements IController {
    public static final int PLAYER_STARTING_DECK_SIZE = 10;

    private final int playerCount;


    private AbstractState roundState;

    private IPlayer[] players;
    private IPlayer currentPlayer;

    private int currentPlayerIndex;

    private List<ICardStack> allPhases;

    private IDeckOfCards drawPile;
    private IDeckOfCards discardPile;

    private String statusMessage;

    public Controller(int numberOfPlayers) {
        playerCount = numberOfPlayers;
        drawPile = new DeckOfCards();
        discardPile = new DeckOfCards();
        roundState = new StartPhase();
        statusMessage = "";
    }

    @Override
    public IDeckOfCards getDrawPile() {
        return this.drawPile;
    }

    @Override
    public IDeckOfCards getDiscardPile() {
        return this.discardPile;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public void exitEvent() {
        System.exit(0);
    }

    @Override
    public void startGame() {
        roundState.start(this);
        notifyObservers();
    }

    @Override
    public void drawOpen() {
        roundState.drawOpen(this);
        notifyObservers();
    }

    @Override
    public void drawHidden() {
        roundState.drawHidden(this);
        notifyObservers();
    }

    @Override
    public void discard(ICard card) {
        roundState.discard(this, card);
        notifyObservers();
    }

    @Override
    public void playPhase(IDeckOfCards phase) {
        roundState.playPhase(this, phase);
        notifyObservers();
    }

    @Override
    public void addToFinishedPhase(ICard card, ICardStack stack) {
        roundState.addToFinishedPhase(this, card, stack);
        notifyObservers();
    }

    @Override
    public void addMultipleCardsToFinishedPhase(List<ICard> cards, ICardStack stack) {
        cards.sort(new CardComparator());
        for (ICard card : cards) {
            addToFinishedPhase(card, stack);
        }
    }


    @Override
    public IDeckOfCards getCurrentPlayersHand() {
        return currentPlayer.getDeckOfCards();
    }

    @Override
    public Map<String, Integer> getNumberOfCardsForAllPlayers() {
        Map<String, Integer> playerMap = new HashMap<>();
        for (IPlayer player : players) {
            if (player != currentPlayer) {
                playerMap.put(player.getPlayerName(), player.getDeckOfCards().size());
            }
        }
        return playerMap;
    }


    @Override
    public List<ICardStack> getAllStacks() {
        return this.allPhases;
    }

    @Override
    public void drawOpenCard() {
        IDeckOfCards currentDeck = currentPlayer.getDeckOfCards();
        currentDeck.add(discardPile.removeLast());
        currentPlayer.setDeckOfCards(currentDeck);
        afterDraw();
    }

    @Override
    public void drawHiddenCard() {
        IDeckOfCards currentDeck = currentPlayer.getDeckOfCards();
        currentDeck.add(drawPile.removeLast());
        currentPlayer.setDeckOfCards(currentDeck);
        afterDraw();
    }

    @Override
    public void discardCard(ICard card) {
        currentPlayer.getDeckOfCards().remove(card);
        discardPile.add(card);
    }

    private void afterDraw() {
        if (currentPlayer.isPhaseDone()) {
            roundState = new PlayerTurnFinished();
        } else {
            roundState = new PlayerTurnNotFinished();
        }
        checkIfDrawPileEmpty();
    }

    private void checkIfDrawPileEmpty() {
        if (drawPile.size() == 0) {
            moveDiscardPileToDrawPile();
        }
    }

    private void moveDiscardPileToDrawPile() {
        DeckOfCards newDiscardPile = new DeckOfCards();
        newDiscardPile.add(discardPile.removeLast());
        DeckOfCards newDrawPile = new DeckOfCards(discardPile);
        Collections.shuffle(newDrawPile);
        this.drawPile = newDrawPile;
        this.discardPile = newDiscardPile;
    }

    public void initGame() {
        initPlayers();
    }


    public void newRound() {
        drawPile = new DeckOfCards();
        discardPile = new DeckOfCards();
        allPhases = new LinkedList<>();
        addCards();
        drawForPlayers();
        currentPlayerIndex = 0;
        currentPlayer = players[currentPlayerIndex];
        roundState = new DrawPhase();
    }

    @Override
    public void endRound() {
        getAllLosers().forEach(this::calcAndAddPoints);
        incrementPhases();
    }

    private void incrementPhases() {
        for (IPlayer player : players) {
            checkAndIncrementPhases(player);
        }
    }

    private void checkAndIncrementPhases(IPlayer player) {
        if (player.isPhaseDone()) {

            player.nextPhase();

            player.setPhaseDone(false);
        }
    }


    private void calcAndAddPoints(IPlayer player) {
        for (ICard leftOverCard : player.getDeckOfCards()) {
            player.addPoints(leftOverCard.getNumber());
        }
    }

    private List<IPlayer> getAllLosers() {
        List<IPlayer> losers = new LinkedList<>();
        for (IPlayer player : players) {
            if (player.getPlayerNumber() != currentPlayer.getPlayerNumber()) {
                losers.add(player);
            }
        }
        return losers;
    }

    private void drawForPlayers() {
        for (IPlayer player : players) {
            player.setDeckOfCards(draw10Cards());
        }
    }

    private void initPlayers() {
        players = new IPlayer[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new Player("Player" + (i + 1), i);
        }
    }

    private DeckOfCards draw10Cards() {
        DeckOfCards deck = new DeckOfCards();
        for (int i = 0; i < PLAYER_STARTING_DECK_SIZE; i++) {
            deck.add(drawPile.removeLast());
        }
        return deck;
    }

    private void addCards() {
        drawPile = CardCreator.giveDeckOfCards();
        Collections.shuffle(drawPile);
    }


    public AbstractState getRoundState() {
        return roundState;
    }

    public void setRoundState(AbstractState roundState) {
        this.roundState = roundState;
    }

    @Override
    public boolean currentPlayerHasNoCards() {
        return currentPlayer.getDeckOfCards().size() == 0;
    }

    @Override
    public void nextPlayer() {
        if (currentPlayerIndex == playerCount - 1) {
            currentPlayerIndex = 0;
            currentPlayer = players[currentPlayerIndex];
        } else {
            currentPlayerIndex++;
            currentPlayer = players[currentPlayerIndex];
        }
    }

    @Override
    public boolean deckMatchesCurrentPlayersPhase(IDeckOfCards phase) {
        return currentPlayer.getPhase().checkIfDeckFitsToPhase(phase);
    }


    @Override
    public void addPhase(IDeckOfCards phase) {
        currentPlayer.setPhaseDone(true);
        removePhaseFromCurrentPlayer(phase);
        List<ICardStack> phases = currentPlayer.getPhase().splitPhaseIntoStacks(phase);
        putDownStacks(phases);
    }

    private void putDownStacks(List<ICardStack> phases) {
        this.allPhases.addAll(phases.stream().collect(Collectors.toList()));
    }


    private void removePhaseFromCurrentPlayer(IDeckOfCards phase) {
        IDeckOfCards playerDeck = currentPlayer.getDeckOfCards();
        phase.forEach(playerDeck::remove);
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String getCurrentPhaseDescription() {
        return currentPlayer.getPhase().getDescription();
    }

    @Override
    public boolean isGameFinished() {
        return currentPlayer.isPhaseDone() && (currentPlayer.getPhase().getPhaseNumber() == Phase5.PHASE_NUMBER);
        //return gameFinished;
    }
}
