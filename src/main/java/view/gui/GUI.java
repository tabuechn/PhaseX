package view.gui;

import controller.IController;
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

    private IController controller;

    private MainWindow window = new MainWindow();

    private GameField gameField;

    private MenuPanel menu;

    public GUI(IController controller) {
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
    }

    private void playerTurnFinished() {
        gameField.updateGameField();
        window.setContentPane(gameField);
        gameField.activatePlayerTurnFinishedPhase();
    }

    private void playerTurnNotFinished() {
        gameField.updateGameField();
        window.setContentPane(gameField);
        gameField.activatePlayerTurnNotFinished();
    }

    private void drawPhase() {
        gameField.updateGameField();
        window.setContentPane(gameField);
        gameField.activateDrawPhase();
    }

    private void endPhase() {
        menu.setMessage(controller.getCurrentPlayer().getPlayerName() + " has won!");
        window.setContentPane(menu);
    }

    private void startPhase() {
        window.setContentPane(menu);
    }

}
