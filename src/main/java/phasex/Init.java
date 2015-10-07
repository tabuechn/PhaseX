package phasex;

import com.google.inject.Guice;
import com.google.inject.Injector;
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

    @SuppressFBWarnings("DM_DEFAULT_ENCODING")
    public static void main(String[] argv) {

        scanner = new Scanner(System.in);

        Injector in = Guice.createInjector(new PhaseXModule());

        TUI tui = in.getInstance(TUI.class);

        @SuppressWarnings("unused")
        GUI gui = in.getInstance(GUI.class);

        //noinspection InfiniteLoopStatement
        while (true) {
            tui.processInputLine(scanner.nextLine());
        }

    }
}
