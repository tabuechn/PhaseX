package controller.impl;

import actors.actor.ActorMaster;
import actors.message.DrawHiddenMessage;
import actors.message.DrawOpenMessage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.LineNumbers;
import akka.util.Timeout;
import com.sun.org.apache.xpath.internal.operations.Bool;
import controller.UIController;
import controller.playerContainer.impl.PlayerContainer;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.roundState.IRoundState;
import model.roundState.StateEnum;
import model.roundState.impl.RoundState;
import model.stack.ICardStack;
import scala.concurrent.Await;
import util.CardCreator;
import util.Observable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import scala.concurrent.Future;
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

    public ActorController(){
        state = new RoundState();
        drawPile = new DeckOfCards();
        discardPile = new DeckOfCards();
        cardStacks = new LinkedList<>();
        phaseXActorSystem = ActorSystem.create("PhaseXActorSystem");
        master = phaseXActorSystem.actorOf(Props.create(ActorMaster.class), "game");
    }

    @Override
    public void startGame(String firstPlayerName) {
        if (this.state.getState().equals(StateEnum.START_PHASE)){
            initGame(firstPlayerName);
        }
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
    public void drawOpen() {
        System.out.println("Sending draw Open");
        DrawOpenMessage dom = new DrawOpenMessage(discardPile,players.getCurrentPlayer().getDeckOfCards());
        Future<Object> fut = Patterns.ask(master,dom,TIMEOUT);
        boolean result = false;
        try {
            result = (boolean) Await.result(fut,TIMEOUT.duration());
            System.out.println("Got response");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToMasterActor(Object o) {


    }

    @Override
    public void drawHidden() {
        System.out.println("Sending draw Hidden");
        DrawHiddenMessage dhm = new DrawHiddenMessage(drawPile,players.getCurrentPlayer().getDeckOfCards());
    }

    @Override
    public void discard(ICard card) {

    }

    @Override
    public void playPhase(IDeckOfCards phase) {

    }

    @Override
    public void addToFinishedPhase(ICard card, ICardStack stack) {

    }

    @Override
    public void addMultipleCardsToFinishedPhase(List<ICard> cards, ICardStack stack) {

    }

    @Override
    public IDeckOfCards getCurrentPlayersHand() {
        return players.getCurrentPlayer().getDeckOfCards();
    }

    @Override
    public Map<Integer, Integer> getNumberOfCardsForNextPlayer() {
        return null;
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
        return null;
    }

    @Override
    public void setCurrentPlayer(IPlayer player) {

    }

    @Override
    public void setCurrentPlayer(int index) {

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
        return null;
    }

    @Override
    public void setStatusMessage(String statusMessage) {

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

    }

    @Override
    public IPlayer[] getPlayers() {
        return new IPlayer[0];
    }

    @Override
    public void setPlayers(IPlayer[] players) {

    }

    @Override
    public int getCurrentPlayerIndex() {
        return 0;
    }

    @Override
    public void setCurrentPlayerIndex(int index) {

    }

    @Override
    public void setPlayer1(IPlayer player1) {

    }

    @Override
    public void setPlayer2(IPlayer player2) {

    }
}
