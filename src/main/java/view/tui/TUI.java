package view.tui;

import com.google.inject.Inject;
import controller.IController;
import controller.UIController;
import model.card.ICard;
import model.deckOfCards.IDeckOfCards;
import model.deckOfCards.impl.DeckOfCards;
import model.player.IPlayer;
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
    private UIController controller;

    @Inject
    public TUI(UIController pController) {
        this.controller = pController;
        controller.addObserver(this);
        printStart();
    }

    public void processInputLine(String input) {
        String status = controller.getRoundState().toString();
        switch (status) {
            case "StartPhase":
                checkStartPhase(input);
                break;
            case "DrawPhase":
                checkDrawPhase(input);
                break;
            case "PlayerTurnNotFinished":
                checkPlayerTurnNotFinished(input);
                break;
            case "PlayerTurnFinished":
                checkPlayerTurnFinished(input);
                break;
            case "EndPhase":
                checkStartPhase(input);
                break;
            default:
                LOGGER.info("Undefined State");
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
            LOGGER.info("Wrong input: unknown command or wrong arguments");
        }
    }

    private void addToPhase(String input) {
        int stackIndex = Integer.parseInt(input.split(" ")[1]);
        int cardIndex = Integer.parseInt(input.split(" ")[2]);
        if (controller.getAllStacks().size() < stackIndex + 1) {
            LOGGER.info("Wrong input: there is no phase with the index on the field");
            return;
        }
        if (controller.getCurrentPlayersHand().size() < cardIndex + 1) {
            LOGGER.info("Wrong input: the current player doesn't have a card with that index");
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
            LOGGER.info("Wrong input: unknown command or wrong arguments");
        }
    }

    private void playPhaseInput(String input) {
        String[] splitArray = input.split(" ");
        IDeckOfCards phase = new DeckOfCards();
        Set<Integer> numberSet = new HashSet<>();
        for (int i = 1; i < splitArray.length; i++) {
            int index = Integer.parseInt(splitArray[i]);
            if (numberSet.contains(index)) {
                LOGGER.info("Wrong input: you cannot pick a card twice");
                return;
            }
            numberSet.add(index);
            phase.add(controller.getCurrentPlayersHand().get(index));
        }
        controller.playPhase(phase);
    }

    private void discardInput(String input) {
        int number = Integer.parseInt(input.split(" ")[1]);
        try {
            controller.discard(controller.getCurrentPlayersHand().get(number));
        } catch (IndexOutOfBoundsException e) {
            LOGGER.info("Wrong input: you don't have a card with that index");
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
                LOGGER.info("Wrong input: unknown command");
                break;
        }
    }

    private void checkStartPhase(String input) {
        switch (input.toLowerCase()) {
            case "start":
                controller.startGame();
                break;
            case "exit":
                controller.exitEvent();
                break;
            default:
                LOGGER.info("Wrong input: unknown command");
                break;
        }
    }


    private void printDraw() {
        LOGGER.info(controller.getCurrentPlayer().getPlayerName() + "'s Turn");
        printCurrentPlayersHand();
        printStacks();
        if (controller.getDiscardPile().size() > 0) {
            ICard discardPileCard = controller.getDiscardPile().get(controller.getDiscardPile().size() - 1);
            LOGGER.info("The Card on the Discard Pile is: " + Integer.toString(discardPileCard.getNumber()) +
                    discardPileCard.getColor().toString());
            LOGGER.info("Enter DrawHidden to draw from the draw pile");
            LOGGER.info("Enter DrawDiscard to draw from the discard pile");
        } else {
            LOGGER.info("The Discard Pile is empty");
            LOGGER.info("Enter DrawHidden to draw from the draw pile");
        }
    }

    private void printStacks() {
        List<ICardStack> allStacks = controller.getAllStacks();
        if (allStacks.isEmpty()) {
            LOGGER.info("There are currently no phases played");
        } else {
            LOGGER.info("These Phases are currently played");
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
            out.append(Integer.toString(card.getNumber()));
            out.append(" ");
            out.append(card.getColor().toString());
            out.append(", ");
        }
        LOGGER.info(out.toString());
    }

    private void printCurrentPlayersHand() {
        LOGGER.info("This is the current players hand:");
        StringBuilder out = new StringBuilder();
        for (ICard card : controller.getCurrentPlayersHand()) {
            out.append(Integer.toString(card.getNumber()));
            out.append(" ");
            out.append(card.getColor().toString());
            out.append(", ");
        }
        LOGGER.info(out);
    }

    private void printStart() {
        LOGGER.info("Welcome to PhaseX");
        LOGGER.info("Enter start to start a new game and exit to close the application");
    }

    @Override
    public void update(Event e) {
        String status = controller.getRoundState().toString();
        switch (status) {
            case "StartPhase":
                printStart();
                break;
            case "DrawPhase":
                printDraw();
                break;
            case "PlayerTurnNotFinished":
                printPlayerTurnNotFinished();
                break;
            case "PlayerTurnFinished":
                printPlayerTurnFinished();
                break;
            case "EndPhase":
                printEnd();
                break;
            default:
                LOGGER.warn("Undefined State reached");
                break;
        }
    }

    private void printPlayerTurnFinished() {
        LOGGER.info(controller.getCurrentPlayer().getPlayerName() + "'s turn");
        printCurrentPlayersHand();
        printStacks();
        LOGGER.info("Enter discard and the index of the card to discard a card");
        LOGGER.info("or AddToPhase and the index of the phase and the index of the card");
    }

    private void printPlayerTurnNotFinished() {
        LOGGER.info(controller.getCurrentPlayer().getPlayerName() + "'s turn");
        printCurrentPlayersHand();
        printStacks();
        LOGGER.info("Enter discard and the index of the card to discard a card");
        LOGGER.info("or PlayPhase and the indexes of the cards for the phase to lay down your phase");
    }

    private void printEnd() {
        IPlayer winner = controller.getCurrentPlayer();
        LOGGER.info("Congratulation " + winner.getPlayerName() + "! You won the Game!");
        LOGGER.info("Enter start to start a new game or exit to close the application");
    }
}
