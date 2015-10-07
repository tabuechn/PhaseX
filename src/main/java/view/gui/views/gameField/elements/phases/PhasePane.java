package view.gui.views.gameField.elements.phases;

import controller.IController;
import model.stack.ICardStack;
import view.gui.views.gameField.elements.player.CurrentPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PhasePane extends JPanel {

    private CurrentPlayer currentPlayer;

    private IController controller;

    public PhasePane(IController controller) {
        this.controller = controller;
        this.setLayout(new GridLayout(2, 2, 5, 5));
        this.setOpaque(false);
        this.setVisible(true);
    }

    private PhasePane() {

    }

    private void addPhases(ICardStack stack) {
        addPhaseWithListener(stack);
    }

    private void addPhaseWithListener(ICardStack stack) {
        SinglePhase phase = new SinglePhase(stack);
        phase.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkPhaseState();
            }

            private void checkPhaseState() {
                if (phase.getPhase() == null) {
                    controller.playPhase(currentPlayer.getChosenCards());
                } else {
                    controller.addMultipleCardsToFinishedPhase(currentPlayer.getChosenCards(), phase.getPhase());
                }
            }
        });
        this.add(phase);
    }

    public void setCurrentPlayer(CurrentPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setAlreadyPlayedPhases(List<ICardStack> phases) {
        this.removeAll();
        List<ICardStack> stacks = new LinkedList<>();
        stacks.addAll(phases);
        stacks = addPossibleEmptyPhases(stacks);
        stacks.forEach(this::addPhases);
    }

    private List<ICardStack> addPossibleEmptyPhases(List<ICardStack> phases) {
        List<ICardStack> stacks = new LinkedList<>();
        int phasesToAdd = 4 - phases.size();
        for (int i = 0; i < phasesToAdd; i++) {
            stacks.add(null);
        }
        return stacks;
    }
}
