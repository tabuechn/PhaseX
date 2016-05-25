package controller.impl;

import actors.actor.ActorMaster;
import actors.message.DiscardMessage;
import actors.message.DrawHiddenMessage;
import actors.message.DrawOpenMessage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import controller.UIController;
import controller.playerContainer.impl.PlayerContainer;
import controller.statusMessage.IStatusMessage;
import controller.statusMessage.impl.StatusMessage;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.impl.Phase5;
import model.player.IPlayer;
import model.roundState.IRoundState;
import model.roundState.StateEnum;
import model.roundState.impl.RoundState;
import model.stack.ICardStack;
import scala.concurrent.Await;
import scala.concurrent.Future;
import util.CardCreator;
import util.Observable;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * If everything works right this class was
 * created by konraifen88 on 10.05.2016.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class ActorController extends Observable implements UIController {

    private static final Timeout TIMEOUT = new Timeout(60, TimeUnit.SECONDS);

    private static final int PLAYER_STARTING_DECK_SIZE = 10;
    private PlayerContainer players;
    private IRoundState state;
    private IDeckOfCards drawPile;
    private IDeckOfCards discardPile;
    private List<ICardStack> cardStacks;
    private ActorSystem phaseXActorSystem;
    private ActorRef master;
    private IStatusMessage statusMessage;
    private IPlayer winner;

    public ActorController(){
        state = new RoundState();
        drawPile = new DeckOfCards();
        discardPile = new DeckOfCards();
        cardStacks = new LinkedList<>();
        phaseXActorSystem = ActorSystem.create("PhaseXActorSystem");
        master = phaseXActorSystem.actorOf(Props.create(ActorMaster.class), "game");
        statusMessage = new StatusMessage();
    }

    @Override
    public void startGame(String firstPlayerName) {
        if (this.state.getState().equals(StateEnum.START_PHASE)){
            initGame(firstPlayerName);
        }
        notifyObservers();
    }

    private void initGame(String firstPlayerName) {
        players = new PlayerContainer(firstPlayerName);
        newRound();
    }

    private void newRound() {
        drawPile = new DeckOfCards();
        discardPile = new DeckOfCards();
        cardStacks = new LinkedList<>();
        initCards();
        state.setState(StateEnum.DRAW_PHASE);
    }

    private void initCards() {
        drawPile = CardCreator.giveDeckOfCards();
        Collections.shuffle(drawPile);
        discardPile.add(drawPile.removeLast());
        players.getCurrentPlayer().setDeckOfCards(draw10Cards());
        players.getOtherPlayer().setDeckOfCards(draw10Cards());
    }

    private DeckOfCards draw10Cards() {
        DeckOfCards deck = new DeckOfCards();
        for (int i = 0; i < PLAYER_STARTING_DECK_SIZE; i++) {
            deck.add(drawPile.removeLast());
        }
        return deck;
    }

    @Override
    public void drawHidden() {
        DrawHiddenMessage dhm = new DrawHiddenMessage(drawPile, players.getCurrentPlayer().getDeckOfCards(), players.getCurrentPlayer(), state);
        Future<Object> fut = Patterns.ask(master, dhm, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
            afterDraw();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyObservers();
    }

    @Override
    public void drawOpen() {
        DrawOpenMessage dom = new DrawOpenMessage(discardPile, players.getCurrentPlayer().getDeckOfCards(), players.getCurrentPlayer(), state);
        Future<Object> fut = Patterns.ask(master,dom,TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut,TIMEOUT.duration());
            afterDraw();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyObservers();
    }

    @Override
    public void discard(ICard card) {
        DiscardMessage dm = new DiscardMessage(state, discardPile, card, players.getCurrentPlayer());
        Future<Object> fut = Patterns.ask(master, dm, TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut, TIMEOUT.duration());
            afterDiscard();
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyObservers();
    }

    private void afterDiscard() {
        if (players.getCurrentPlayer().getDeckOfCards().isEmpty()) {
            endOfTurn();
        } else {
            nextPlayer();
            state.setState(StateEnum.DRAW_PHASE);
        }
    }

    private void nextPlayer() {
        if (players.getCurrentPlayerIndex() == 1) {
            players.setCurrentPlayerIndex(0);
            players.setCurrentPlayer(players.getPlayers()[0]);
        } else {
            players.setCurrentPlayerIndex(1);
            players.setCurrentPlayer(players.getPlayers()[1]);
        }
    }

    private void endOfTurn() {
        if (isGameFinished()) {
            state.setState(StateEnum.END_PHASE);
            setWinner();
            statusMessage.setStatusMessage("Player " + players.getCurrentPlayer().getPlayerName() + " has won");
        } else {
            endRound();
            newRound();
        }
    }

    private void endRound() {
        getAllLosers().forEach(this::calcAndAddPoints);
        incrementPhases();
    }

    private void incrementPhases() {
        for (IPlayer player : players.getPlayers()) {
            checkAndIncrementPhases(player);
        }
    }

    private void checkAndIncrementPhases(IPlayer player) {
        if (player.isPhaseDone()) {
            player.nextPhase();
            player.setPhaseDone(false);
        }
    }

    private List<IPlayer> getAllLosers() {
        List<IPlayer> losers = new LinkedList<>();
        losers.add(players.getOtherPlayer());
        return losers;
    }

    private void calcAndAddPoints(IPlayer player) {
        for (ICard leftOverCard : player.getDeckOfCards()) {
            player.addPoints(leftOverCard.getNumber().ordinal());
        }
    }

    private void setWinner() {
        this.winner = players.getCurrentPlayer();
    }

    private boolean isGameFinished() {
        IPlayer currentPlayer = players.getCurrentPlayer();
        return currentPlayer.isPhaseDone() && (currentPlayer.getPhase().getPhaseNumber() == Phase5.PHASE_NUMBER);
    }

    private void afterDraw() {
        if (players.getCurrentPlayer().isPhaseDone()) {
            state.setState(StateEnum.PLAYER_TURN_FINISHED);
        } else {
            state.setState(StateEnum.PLAYER_TURN_NOT_FINISHED);
        }
        drawPileCheck();
    }

    private void drawPileCheck() {
        if (this.drawPile.isEmpty()) {
            DeckOfCards newDiscardPile = new DeckOfCards();
            newDiscardPile.add(discardPile.removeLast());
            DeckOfCards newDrawPile = new DeckOfCards(discardPile);
            Collections.shuffle(newDrawPile);
            Collections.shuffle(newDrawPile);
            this.drawPile = newDrawPile;
            this.discardPile = newDiscardPile;
        }
    }

    @Override
    public void playPhase(IDeckOfCards phase) {
        //TODO implement play phase
        notifyObservers();
    }

    @Override
    public void addToFinishedPhase(ICard card, ICardStack stack) {
        //TODO implement add to finishedPhase
        notifyObservers();
    }

    @Override
    public void addMultipleCardsToFinishedPhase(List<ICard> cards, ICardStack stack) {
        for (ICard card : cards) {
            addToFinishedPhase(card, stack);
        }
        notifyObservers();
    }

    @Override
    public IDeckOfCards getCurrentPlayersHand() {
        return players.getCurrentPlayer().getDeckOfCards();
    }

    @Override
    public Map<Integer, Integer> getNumberOfCardsForNextPlayer() {
        Map<Integer, Integer> returnMap = new TreeMap<>();
        returnMap.put(0, players.getOtherPlayer().getDeckOfCards().size());
        return returnMap;
    }

    @Override
    public List<ICardStack> getAllStacks() {
        return cardStacks;
    }

    @Override
    public void setAllStacks(List<ICardStack> allStacks) {
        this.cardStacks = allStacks;
    }

    @Override
    public StateEnum getRoundState() {
        return state.getState();
    }

    @Override
    public void setRoundState(String roundState) {
        state.setState(StateEnum.getRoundNameByString(roundState));
    }

    @Override
    public IDeckOfCards getDrawPile() {
        return drawPile;
    }

    @Override
    public void setDrawPile(IDeckOfCards deck) {
        this.drawPile = deck;
    }

    @Override
    public IDeckOfCards getDiscardPile() {
        return discardPile;
    }

    @Override
    public void setDiscardPile(IDeckOfCards deck) {
        this.discardPile = deck;
    }

    @Override
    public IPlayer getCurrentPlayer() {
        return players.getCurrentPlayer();
    }

    @Override
    public void setCurrentPlayer(IPlayer player) {
        players.setCurrentPlayer(player);
    }

    @Override
    public void setCurrentPlayer(int index) {
        players.setCurrentPlayerIndex(index);
    }

    @Override
    public IPlayer getOpponentPlayer() {
        return players.getOtherPlayer();
    }

    @Override
    public void exitEvent() {
        System.out.println("Exiting now");
    }

    @Override
    public String getStatusMessage() {
        return "TODO: create status messages";
    }

    @Override
    public void setStatusMessage(String statusMessage) {
        //TODO: implement status message
    }

    @Override
    public String getCurrentPhaseDescription() {
        return null;
    }

    @Override
    public IPlayer getWinner() {
        return null;
    }

    @Override
    public void setSecondPlayerName(String name) {
        players.getOtherPlayer().setName(name);
    }

    @Override
    public IPlayer[] getPlayers() {
        return players.getPlayers();
    }

    @Override
    public void setPlayers(IPlayer[] players) {
        this.players.setPlayers(players);
    }

    @Override
    public int getCurrentPlayerIndex() {
        return players.getCurrentPlayerIndex();
    }

    @Override
    public void setCurrentPlayerIndex(int index) {
        players.setCurrentPlayerIndex(index);
    }

    @Override
    public void setPlayer1(IPlayer player1) {
        players.getPlayers()[0] = player1;
    }

    @Override
    public void setPlayer2(IPlayer player2) {
        players.getPlayers()[1] = player2;
    }
}
