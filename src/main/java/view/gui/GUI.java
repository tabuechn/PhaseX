package view.gui;

import com.google.inject.Inject;
import controller.UIController;
import util.Event;
import util.IObserver;
import view.gui.mainWindow.MainWindow;
import view.gui.views.gameField.GameField;
import view.gui.views.menu.MenuPanel;

/**
 * If everything works right this class was
 * created by Konraifen88 on 25.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class GUI implements IObserver {

    private UIController controller;

    private MainWindow window = new MainWindow();

    private GameField gameField;

    private MenuPanel menu;

    @Inject
    public GUI(UIController controller) {
        this.controller = controller;
        gameField = new GameField(controller);
        menu = new MenuPanel(controller);
        controller.addObserver(this);
        window.setContentPane(menu);
        window.pack();
        window.setVisible(true);
    }

    public void start() {
        controller.startGame();
    }

    @Override
    public void update(Event e) {
        String state = controller.getRoundState().toString();
        switch (state) {
            case "StartPhase":
                startPhase();
                break;
            case "EndPhase":
                endPhase();
                break;
            case "DrawPhase":
                drawPhase();
                break;
            case "PlayerTurnNotFinished":
                playerTurnNotFinished();
                break;
            case "PlayerTurnFinished":
                playerTurnFinished();
                break;
            default:
                throw new IllegalStateException();
        }
        gameField.updateGameField();
    }

    private void playerTurnFinished() {
        window.setContentPane(gameField);
        gameField.activatePlayerTurnFinishedPhase();
        window.repaint();
    }

    private void playerTurnNotFinished() {
        window.setContentPane(gameField);
        gameField.activatePlayerTurnNotFinished();
        window.repaint();
    }

    private void drawPhase() {
        window.setContentPane(gameField);
        gameField.activateDrawPhase();
        window.repaint();
    }

    private void endPhase() {
        menu.setMessage(controller.getCurrentPlayer().getPlayerName() + " has won!");
        window.setContentPane(menu);
        window.repaint();
    }

    private void startPhase() {
        window.setContentPane(menu);
        window.repaint();
    }

}
