package view.tui;

import com.google.inject.Inject;
import controller.UIController;
import model.card.ICard;
import model.deck.IDeckOfCards;
import model.deck.impl.DeckOfCards;
import model.player.IPlayer;
import model.roundState.StateEnum;
import model.stack.ICardStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.Event;
import util.IObserver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 */
public class TUI implements IObserver {

    private static final Logger LOGGER = LogManager.getLogger(TUI.class);
    private final UIController controller;

    private StringBuilder sb = new StringBuilder();

    @Inject
    public TUI(UIController pController) {
        this.controller = pController;
        controller.addObserver(this);
        printStart();
    }

    @SuppressWarnings("SynchronizeOnNonFinalField")
    public StringBuilder getSb() {
        synchronized (sb) {
            StringBuilder tmp = sb;
            sb = new StringBuilder();
            return tmp;
        }
    }

    public void processInputLine(String input) {
        StateEnum status = controller.getRoundState();
        switch (status) {
            case START_PHASE:
                checkStartPhase(input);
                break;
            case DRAW_PHASE:
                checkDrawPhase(input);
                break;
            case PLAYER_TURN_NOT_FINISHED:
                checkPlayerTurnNotFinished(input);
                break;
            case PLAYER_TURN_FINISHED:
                checkPlayerTurnFinished(input);
                break;
            case END_PHASE:
                checkStartPhase(input);
                break;
            default:
                this.log("Undefined State");
                break;
        }
    }

    private void checkPlayerTurnFinished(String input) {
        String lowerCaseInput = input.toLowerCase();
        if (lowerCaseInput.matches("discard ([0-9]|10)")) {
            discardInput(lowerCaseInput);
        } else if (lowerCaseInput.matches("addtophase [0-3] ([0-9]|10)")) {
            addToPhase(lowerCaseInput);
        } else {
            this.log("Wrong input: unknown command or wrong arguments");
        }
    }

    private void log(String log) {
        LOGGER.info(log);
        sb.append(log).append("\n");
    }

    private void addToPhase(String input) {
        int stackIndex = Integer.parseInt(input.split(" ")[1]);
        int cardIndex = Integer.parseInt(input.split(" ")[2]);
        if (controller.getAllStacks().size() < stackIndex + 1) {
            this.log("Wrong input: there is no phase with the index on the field");
            return;
        }
        if (controller.getCurrentPlayersHand().size() < cardIndex + 1) {
            this.log("Wrong input: the current player doesn't have a card with that index");
            return;
        }
        controller.addToFinishedPhase(controller.getCurrentPlayersHand().get(cardIndex),
                controller.getAllStacks().get(stackIndex));
    }

    private void checkPlayerTurnNotFinished(String input) {
        String lowerCaseInput = input.toLowerCase();
        if (lowerCaseInput.matches("discard ([0-9]|10)")) {
            discardInput(lowerCaseInput);
        } else if (lowerCaseInput.matches("playphase (([0-9]|10)\\s){5,7}([0-9]|10)")) {
            playPhaseInput(lowerCaseInput);
        } else {
            this.log("Wrong input: unknown command or wrong arguments");
        }
    }

    private void playPhaseInput(String input) {
        String[] splitArray = input.split(" ");
        IDeckOfCards phase = new DeckOfCards();
        Set<Integer> numberSet = new HashSet<>();
        for (int i = 1; i < splitArray.length; i++) {
            int index = Integer.parseInt(splitArray[i]);
            if (numberSet.contains(index)) {
                this.log("Wrong input: you cannot pick a card twice");
                return;
            }
            numberSet.add(index);
            phase.add(controller.getCurrentPlayersHand().get(index));
        }
        controller.playPhase(phase);
    }

    @SuppressWarnings("squid:S1166")
    private void discardInput(String input) {
        int number = Integer.parseInt(input.split(" ")[1]);
        try {
            controller.discard(controller.getCurrentPlayersHand().get(number));
        } catch (IndexOutOfBoundsException e) {
            this.log("Wrong input: you don't have a card with that index");
        }
    }

    private void checkDrawPhase(String input) {
        switch (input.toLowerCase()) {
            case "drawhidden":
                controller.drawHidden();
                break;
            case "drawdiscard":
                controller.drawOpen();
                break;
            case "exit":
                controller.exitEvent();
                break;
            default:
                this.log("Wrong input: unknown command");
                break;
        }
    }

    private void checkStartPhase(String input) {
        switch (input.toLowerCase()) {
            case "start":
                controller.startGame("Player1");
                controller.setSecondPlayerName("Player2");
                break;
            case "exit":
                controller.exitEvent();
                break;
            default:
                this.log("Wrong input: unknown command");
                break;
        }
    }


    private void printDraw() {
        this.log(controller.getCurrentPlayer().getPlayerName() + "'s Turn");
        printCurrentPlayersHand();
        printStacks();
        if (controller.getDiscardPile().size() > 0) {
            ICard discardPileCard = controller.getDiscardPile().get(controller.getDiscardPile().size() - 1);
            this.log("The Card on the Discard Pile is: " + discardPileCard.getNumber().toString() +
                    discardPileCard.getColor().toString());
            this.log("Enter DrawHidden to draw from the draw pile");
            this.log("Enter DrawDiscard to draw from the discard pile");
        } else {
            this.log("The Discard Pile is empty");
            this.log("Enter DrawHidden to draw from the draw pile");
        }
    }

    private void printStacks() {
        List<ICardStack> allStacks = controller.getAllStacks();
        if (allStacks.isEmpty()) {
            this.log("There are currently no phases played");
        } else {
            this.log("These Phases are currently played");
            printAllStacks(allStacks);
        }
    }

    private void printAllStacks(List<ICardStack> allStacks) {
        for (ICardStack stack : allStacks) {
            printSingleStack(stack.getList());
        }
    }

    private void printSingleStack(IDeckOfCards list) {
        StringBuilder out = new StringBuilder();
        for (ICard card : list) {
            out.append(card.getNumber().toString());
            out.append(" ");
            out.append(card.getColor().toString());
            out.append(", ");
        }
        this.log(out.toString());
    }

    private void printCurrentPlayersHand() {
        this.log("This is the current players hand:");
        StringBuilder out = new StringBuilder();
        for (ICard card : controller.getCurrentPlayersHand()) {
            out.append(card.getNumber().toString());
            out.append(" ");
            out.append(card.getColor().toString());
            out.append(", ");
        }
        this.log(out.toString());
    }

    private void printStart() {
        this.log("Welcome to PhaseX");
        this.log("Enter start to start a new game and exit to close the application");
    }

    @Override
    public void update(Event e) {
        StateEnum status = controller.getRoundState();
        switch (status) {
            case START_PHASE:
                printStart();
                break;
            case DRAW_PHASE:
                printDraw();
                break;
            case PLAYER_TURN_NOT_FINISHED:
                printPlayerTurnNotFinished();
                break;
            case PLAYER_TURN_FINISHED:
                printPlayerTurnFinished();
                break;
            case END_PHASE:
                printEnd();
                break;
            default:
                LOGGER.warn("Undefined State reached");
                break;
        }
    }

    private void printPlayerTurnFinished() {
        this.log(controller.getCurrentPlayer().getPlayerName() + "'s turn");
        printCurrentPlayersHand();
        printStacks();
        this.log("Enter discard and the index of the card to discard a card");
        this.log("or AddToPhase and the index of the phase and the index of the card");
    }

    private void printPlayerTurnNotFinished() {
        this.log(controller.getCurrentPlayer().getPlayerName() + "'s turn");
        printCurrentPlayersHand();
        printStacks();
        this.log("Enter discard and the index of the card to discard a card");
        this.log("or PlayPhase and the indexes of the cards for the phase to lay down your phase");
    }

    private void printEnd() {
        IPlayer winner = controller.getCurrentPlayer();
        this.log("Congratulation " + winner.getPlayerName() + "! You won the Game!");
        this.log("Enter start to start a new game or exit to close the application");
    }
}
