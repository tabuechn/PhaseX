package controller.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import controller.IController;
import controller.UIController;
import controller.states.AbstractState;
import controller.states.impl.DrawPhase;
import controller.states.impl.PlayerTurnFinished;
import controller.states.impl.PlayerTurnNotFinished;
import controller.states.impl.StartPhase;
import model.card.ICard;
import model.card.impl.CardValueComparator;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.DeckNotFitException;
import model.phase.impl.Phase5;
import model.player.IPlayer;
import model.player.impl.Player;
import model.stack.ICardStack;
import util.CardCreator;
import util.Observable;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 */
public class Controller extends Observable implements IController, UIController {
    private static final int PLAYER_STARTING_DECK_SIZE = 10;

    private final int playerCount;

    @JsonDeserialize(as = AbstractState.class)
    private AbstractState roundState;

    private IPlayer[] players;

    @JsonDeserialize(as = IPlayer.class)
    private IPlayer currentPlayer;

    private int currentPlayerIndex;

    @JsonDeserialize(contentAs = ICardStack.class)
    private List<ICardStack> allPhases;

    @JsonDeserialize(as = IDeckOfCards.class)
    private IDeckOfCards drawPile;

    @JsonDeserialize(as = IDeckOfCards.class)
    private IDeckOfCards discardPile;

    private String statusMessage;

    @JsonIgnore
    private IPlayer winner;


    public Controller(int numberOfPlayers) {
        winner = null;
        playerCount = numberOfPlayers;
        drawPile = new DeckOfCards();
        discardPile = new DeckOfCards();
        roundState = new StartPhase();
        statusMessage = "";
    }

    @SuppressWarnings("unused")
    public Controller() {
        this(2);
    }

    @Override
    public void startGame(String firstPlayer) {
        roundState.start(this);
        players[0].setName(firstPlayer);
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
        cards.sort(new CardValueComparator());
        for (ICard card : cards) {
            addToFinishedPhase(card, stack);
        }
    }

    @Override
    @JsonIgnore
    public IDeckOfCards getCurrentPlayersHand() {
        return currentPlayer.getDeckOfCards();
    }

    @Override
    public Map<Integer, Integer> getNumberOfCardsForNextPlayer() {
        Map<Integer, Integer> enemies = new HashMap<>();
        for (IPlayer player : players) {
            if (player != currentPlayer) {
                enemies.put(player.getPlayerNumber(), player.getDeckOfCards().size());
            }
        }
        return enemies;
    }

    @Override
    public void initGame() {
        initPlayers();
    }

    @Override
    public void newRound() {
        drawPile = new DeckOfCards();
        discardPile = new DeckOfCards();
        allPhases = new LinkedList<>();
        addCards();
        discardPile.add(drawPile.removeLast());
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

    @Override
    public List<ICardStack> getAllStacks() {
        return this.allPhases;
    }

    @Override
    public void setAllStacks(List<ICardStack> allStacks) {
        this.allPhases = allStacks;
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

    @Override
    public AbstractState getRoundState() {
        return roundState;
    }

    @Override
    public void setRoundState(String roundState) {
        this.roundState = AbstractState.getStateFromString(roundState);
    }

    @Override
    @JsonProperty("roundState")
    public void setRoundState(AbstractState roundState) {
        this.roundState = roundState;
    }

    @Override
    public boolean currentPlayerHasNoCards() {
        return currentPlayer.getDeckOfCards().isEmpty();
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
    public void addPhase(IDeckOfCards phase) throws DeckNotFitException {
        List<ICardStack> phases = currentPlayer.getPhase().splitAndCheckPhase(phase);
        currentPlayer.setPhaseDone(true);
        removePhaseFromCurrentPlayer(phase);
        putDownStacks(phases);

    }

    @Override
    public IDeckOfCards getDrawPile() {
        return this.drawPile;
    }

    @Override
    public void setDrawPile(IDeckOfCards deck) {
        this.drawPile = deck;
    }

    @Override
    public IDeckOfCards getDiscardPile() {
        return this.discardPile;
    }

    @Override
    public void setDiscardPile(IDeckOfCards deck) {
        this.discardPile = deck;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    @JsonIgnore
    public IPlayer getOpponentPlayer() {
        for(IPlayer player : players) {
            if (!player.equals(currentPlayer)) {
                return player;
            }
        }
        // should never happen
        return currentPlayer;
    }

    @Override
    @SuppressWarnings("squid:S1147")
    public void exitEvent() {
        System.exit(0);
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }

    @Override
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public void setCurrentPlayer(int index) {
        this.currentPlayer = players[index];
    }

    @Override
    @JsonIgnore
    public String getCurrentPhaseDescription() {
        return currentPlayer.getPhase().getDescription();
    }

    @Override
    @JsonIgnore
    public boolean isGameFinished() {
        return currentPlayer.isPhaseDone() && (currentPlayer.getPhase().getPhaseNumber() == Phase5.PHASE_NUMBER);
    }

    @Override
    @JsonIgnore
    public int getPlayerCount() {
        return playerCount;
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
        if (drawPile.isEmpty()) {
            moveDiscardPileToDrawPile();
        }
    }

    private void moveDiscardPileToDrawPile() {
        DeckOfCards newDiscardPile = new DeckOfCards();
        newDiscardPile.add(discardPile.removeLast());
        DeckOfCards newDrawPile = new DeckOfCards(discardPile);
        Collections.shuffle(newDrawPile);
        Collections.shuffle(newDrawPile);
        this.drawPile = newDrawPile;
        this.discardPile = newDiscardPile;
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

    @SuppressWarnings("squid:UnusedPrivateMethod")
    private void calcAndAddPoints(IPlayer player) {
        for (ICard leftOverCard : player.getDeckOfCards()) {
            player.addPoints(leftOverCard.getNumber().ordinal());
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
            players[i] = new Player(i);
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

    private void putDownStacks(List<ICardStack> phases) {
        this.allPhases.addAll(phases.stream().collect(Collectors.toList()));
    }

    private void removePhaseFromCurrentPlayer(IDeckOfCards phase) {
        IDeckOfCards playerDeck = currentPlayer.getDeckOfCards();
        phase.forEach(playerDeck::remove);
    }

    @Override
    public IPlayer getWinner() {
        return winner;
    }

    @Override
    public void setSecondPlayerName(String name) {
        players[1].setName(name);
    }

    @Override
    public IPlayer[] getPlayers() {
        return this.players;
    }

    @Override
    public void setPlayers(@JsonProperty("players") IPlayer[] players) {
        this.players = players;
    }

    @Override
    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    @Override
    public void setCurrentPlayerIndex(int index) {
        this.currentPlayerIndex = index;
    }

    @Override
    public void setPlayer1(IPlayer player1) {
        this.players[0] = player1;
    }

    @Override
    public void setPlayer2(IPlayer player2) {
        this.players[1] = player2;
    }

    @Override
    public void setWinner() {
        this.winner = getCurrentPlayer();
    }
}
