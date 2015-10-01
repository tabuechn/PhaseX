package view.GUI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * If everything works right this class was
 * created by Konraifen88 on 01.10.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class MainWindow extends JFrame{

    private static final Logger LOG = LogManager.getLogger(MainWindow.class);

    public MainWindow(){
        super();
        setDefaultLookAndFeelDecorated(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                LOG.error("window closed");
                System.exit(0);
                super.windowClosed(e);
            }
        });
    }
}
