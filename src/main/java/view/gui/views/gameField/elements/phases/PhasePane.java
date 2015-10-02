package view.gui.views.gameField.elements.phases;

import controller.IController;
import view.gui.views.gameField.elements.player.CurrentPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * If everything works right this class was
 * created by Konraifen88 on 29.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class PhasePane extends JPanel {

    private CurrentPlayer currentPlayer;

    private IController controller;

    private PhasePane phases;

    public PhasePane(int sumOfSinglePhasesToCreate, IController controller) {
        this.controller = controller;
        this.setLayout(new GridLayout(2, 2, 5, 5));
        addPhases(sumOfSinglePhasesToCreate);
    }

    private void addPhases(int sum) {
        for (int i = 0; i < sum; i++) {
            addPhaseWithListener();
        }
    }

    private void addPhaseWithListener() {
        SinglePhase phase = new SinglePhase();
        phase.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkPhaseState();
            }

            private void checkPhaseState() {
                if (phase.getPhase().getList().size() == 1) {
                    controller.playPhase(currentPlayer.getChosenCards());
                } else {
                    controller.addMultipleCardsToFinishedPhase(currentPlayer.getChosenCards(), phase.getPhase());
                }
            }

        });
        this.add(phase);
    }

    public void setRoundDependedValues(CurrentPlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
