package view.GUI.views.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.GUI.GUIConstants;
import view.GUI.specialViews.BackgroundPanel;
import view.GUI.views.commonViews.NotificationLabel;
import view.GUI.views.menu.elements.LogoLabel;

import javax.swing.*;
import java.awt.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 26.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class MenuPanel extends BackgroundPanel {

    private static final Logger LOG = LogManager.getLogger(MenuPanel.class);

    private JLabel messages = new NotificationLabel();

    private JLabel logo = new LogoLabel();

    private JButton startButton;

    public MenuPanel(){
        super();
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(getLogoAndMessagePanel());

        createStartButton();
        this.add(getButtonPanel());
        this.add(Box.createVerticalGlue());
        this.setVisible(true);
    }

    public JPanel getLogoAndMessagePanel() {
        BackgroundPanel headerPane = new BackgroundPanel();
        headerPane.setLayout(new BorderLayout());
        headerPane.add(logo, BorderLayout.NORTH);
        headerPane.add(messages, BorderLayout.SOUTH);
        return headerPane;
    }

    private JPanel getButtonPanel() {
        JPanel panel  = new BackgroundPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        createStartButton();
        panel.add(startButton);
        panel.setVisible(true);
        return panel;
    }


    private void createStartButton() {
        startButton = new JButton("Start Game");
        startButton.setMinimumSize(GUIConstants.BUTTON_SIZE);
        startButton.setMaximumSize(GUIConstants.BUTTON_SIZE);
        startButton.setPreferredSize(GUIConstants.BUTTON_SIZE);
        startButton.setFont(GUIConstants.DEFAULT_FONT);
        startButton.addActionListener( e -> LOG.debug("Button startGame clicked"));
        startButton.setVisible(true);

    }

    public void setMessage(String text){
        messages.setText(text);
    }

}
