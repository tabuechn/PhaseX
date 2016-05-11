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
@SuppressWarnings("squid:CommentedOutCodeLine")
public class Init {

    private static Init instance;

    private TUI tui;
    private Injector in;

    private Init() {
        in = Guice.createInjector(new PhaseXModule());

        tui = in.getInstance(TUI.class);

//        gui = in.getInstance(GUI.class);
//        gui.start();
    }

    public static Init getInstance() {
        if (instance == null) {
            instance = new Init();
        }
        return instance;
    }

    public Injector getIn() {
        return in;
    }

    public TUI getTui() {
        return tui;
    }

    @SuppressFBWarnings("DM_DEFAULT_ENCODING")
    public static void main(String[] argv) {


        Init in = new Init();

        Scanner scanner = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            in.tui.processInputLine(scanner.nextLine());
        }

    }
}
