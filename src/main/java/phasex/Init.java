package phasex;

import controller.IController;
import controller.impl.Controller;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import view.gui.GUI;
import view.tui.TUI;

import java.util.Scanner;

/**
 * Created by Tarek on 24.09.2015. Be grateful for this superior Code!
 */
@SuppressFBWarnings("DLS_DEAD_LOCAL_STORE")
public final class Init {

    private static Scanner scanner;

    private Init() {
    }

    public static void main(String[] argv) {

        scanner = new Scanner(System.in);

        IController controller = new Controller(2);

        TUI tui = new TUI(controller);

        @SuppressWarnings("unused")
        GUI gui = new GUI(controller);

        //noinspection InfiniteLoopStatement
        while (true) {
            tui.processInputLine(scanner.nextLine());
        }

    }
}
