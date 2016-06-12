package controller.impl;

import actors.actor.ActorMaster;
import actors.message.*;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import controller.UIController;
import controller.container.IPlayerContainer;
import controller.container.impl.PlayerContainer;
import controller.message.IStatusMessage;
import controller.message.impl.StatusMessage;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.phase.impl.Phase5;
import model.player.IPlayer;
import model.stack.ICardStack;
import model.state.IRoundState;
import model.state.StateEnum;
import model.state.impl.RoundState;
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

    @JsonDeserialize(as = PlayerContainer.class)
    private IPlayerContainer players;

    @JsonDeserialize(as = RoundState.class)
    private IRoundState state;

    @JsonDeserialize(as = DeckOfCards.class)
    private IDeckOfCards drawPile;

    @JsonDeserialize(as = DeckOfCards.class)
    private IDeckOfCards discardPile;

    @JsonDeserialize(contentAs = ICardStack.class)
    private List<ICardStack> cardStacks;

    @JsonIgnore
    private ActorSystem phaseXActorSystem;

    @JsonIgnore
    private ActorRef master;

    @JsonIgnore
    private IStatusMessage statusMessage;

    @JsonIgnore
    private IPlayer winner;

    @JsonCreator
    public ActorController(){
        players = new PlayerContainer();
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
        drawCard(fut);
        drawPileCheck();
        notifyObservers();
    }

    @Override
    public void drawOpen() {
        DrawOpenMessage dom = new DrawOpenMessage(discardPile, players.getCurrentPlayer().getDeckOfCards(), players.getCurrentPlayer(), state);
        Future<Object> fut = Patterns.ask(master,dom,TIMEOUT);
        drawCard(fut);
        notifyObservers();
    }

    private void drawCard(Future<Object> fut) {
        try {
            Object result = Await.result(fut, TIMEOUT.duration());
            if (result instanceof DrawOpenMessage) {
                DrawOpenMessage message = (DrawOpenMessage) result;
                this.setCurrentPlayer(message.getCurrentPlayer());
                this.setDiscardPile(message.getPile());
                afterDraw();
            } else if (result instanceof DrawHiddenMessage) {
                DrawHiddenMessage message = (DrawHiddenMessage) result;
                this.setCurrentPlayer(message.getCurrentPlayer());
                this.setDrawPile(message.getPile());
                afterDraw();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void discard(ICard card) {
        DiscardMessage dm = new DiscardMessage(state, discardPile, card, players.getCurrentPlayer());
        Future<Object> fut = Patterns.ask(master, dm, TIMEOUT);
        try {
            Object result = Await.result(fut, TIMEOUT.duration());
            if (result instanceof DiscardMessage) {
                dm = (DiscardMessage) result;
                this.setCurrentPlayer(dm.getCurrentPlayer());
                this.setDiscardPile(dm.getDiscardPile());
                afterDiscard();
            }
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
        players.nextPlayer();
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
        PlayPhaseMessage ppm = new PlayPhaseMessage(state, phase, players.getCurrentPlayer(), cardStacks);
        Future<Object> fut = Patterns.ask(master, ppm, TIMEOUT);
        try {
            Object result = Await.result(fut, TIMEOUT.duration());
            if (result instanceof PlayPhaseMessage) {
                ppm = (PlayPhaseMessage) result;
                this.setCurrentPlayer(ppm.getCurrentPlayer());
                this.setAllStacks(ppm.getAllStacks());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyObservers();
    }

    @Override
    public void addToFinishedPhase(ICard card, int stackNumber) {
        addMultipleCardsToFinishedPhase(new DeckOfCards(Collections.singletonList(card)), stackNumber);
    }

    @Override
    public void addMultipleCardsToFinishedPhase(List<ICard> cards, int stackNumber) {
        AddToPhaseMessage dtpm = new AddToPhaseMessage(state, cards, cardStacks.get(stackNumber), players.getCurrentPlayer());
        Future<Object> fut = Patterns.ask(master, dtpm, TIMEOUT);
        try {
            Object result = Await.result(fut, TIMEOUT.duration());
            if (result instanceof AddToPhaseMessage) {
                dtpm = (AddToPhaseMessage) result;
                this.setCurrentPlayer(dtpm.getCurrentPlayer());
                this.cardStacks.set(stackNumber, dtpm.getStack());
                //TODO: It is not correct to call end of turn  at this point, or did I do something wrong in test?
                //endOfTurn();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyObservers();
    }

    @Override
    @JsonIgnore
    public IDeckOfCards getCurrentPlayersHand() {
        return players.getCurrentPlayer().getDeckOfCards();
    }

    @Override
    @JsonIgnore
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
    public void setRoundState(StateEnum roundState) {
        state.setState(roundState);
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
    @JsonIgnore
    public IPlayer getCurrentPlayer() {
        return players.getCurrentPlayer();
    }

    @Override
    @JsonIgnore
    public void setCurrentPlayer(int index) {
        players.setCurrentPlayerIndex(index);
    }

    @Override
    @JsonIgnore
    public void setCurrentPlayer(IPlayer player) {
        players.setCurrentPlayer(player);
    }

    @Override
    @JsonIgnore
    public IPlayer getOpponentPlayer() {
        return players.getOtherPlayer();
    }

    @Override
    public void exitEvent() {
        System.out.println("Exiting now");
    }

    @Override
    @JsonProperty("statusMessage")
    public String getStatusMessage() {
        return statusMessage.getStatusMessage();
    }

    @Override
    @JsonProperty("statusMessage")
    public void setStatusMessage(String statusMessage) {
        this.statusMessage.setStatusMessage(statusMessage);
    }

    @Override
    @JsonIgnore
    public String getCurrentPhaseDescription() {
        return players.getCurrentPlayer().getPhase().getDescription();
    }

    @Override
    @JsonIgnore
    public IPlayer getWinner() {
        return winner;
    }

    @Override
    @JsonIgnore
    public void setSecondPlayerName(String name) {
        players.getOtherPlayer().setName(name);
    }

    @Override
    @JsonIgnore
    public IPlayer[] getPlayersArray() {
        return players.getPlayers();
    }

    @Override
    @JsonIgnore
    public void setPlayersArray(IPlayer[] players) {
        this.players.setPlayers(players);
    }

    @Override
    @JsonIgnore
    public int getCurrentPlayerIndex() {
        return players.getCurrentPlayerIndex();
    }

    @Override
    @JsonIgnore
    public void setCurrentPlayerIndex(int index) {
        players.setCurrentPlayerIndex(index);
    }

    @Override
    @JsonIgnore
    public void setPlayer1(IPlayer player1) {
        players.getPlayers()[0] = player1;
    }

    @Override
    @JsonIgnore
    public void setPlayer2(IPlayer player2) {
        players.getPlayers()[1] = player2;
    }

    @JsonProperty("players")
    public IPlayerContainer getPlayers() {
        return this.players;
    }

    @JsonProperty("players")
    public void setPlayers(PlayerContainer players) {
        this.players = players;
    }

}
