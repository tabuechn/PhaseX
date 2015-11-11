package view.gui.cardDrawer;

import model.card.CardColor;
import model.card.CardValue;
import model.card.ICard;
import org.apache.commons.lang3.StringUtils;
import view.gui.GUIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * If everything works right this class was
 * created by Konraifen88 on 25.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DrawnCard extends JLayeredPane implements ActionListener {

    private final ICard card;
    private boolean chosen;

    public DrawnCard(ICard card) {
        this.card = card;
        setMinimumSize(GUIConstants.CARD_DIMENSION);
        setPreferredSize(GUIConstants.CARD_DIMENSION);
        setMaximumSize(GUIConstants.CARD_DIMENSION);
        add(getBackgroundLabel(card.getColor()), -GUIConstants.ONE);
        add(getNumberLabel(card.getNumber()), 0);

        setVisible(true);
    }


    private JLabel getBackgroundLabel(CardColor color) {
        String path = getCardColorPath(color);
        JLabel label = new JLabel();
        label.setSize(this.getPreferredSize());
        if (StringUtils.isBlank(path)) {
            label.setText(color.name());
        } else {
            ImageIcon ii = new ImageIcon(getClass().getResource(path));
            ii.setImage(ii.getImage()
                    .getScaledInstance(GUIConstants.RESIZED_CARD_WIDTH, GUIConstants.RESIZED_CARD_HEIGHT,
                            Image.SCALE_DEFAULT));
            label.setIcon(ii);
        }
        label.setVisible(true);
        return label;
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private String getCardColorPath(CardColor color) {
        String path;
        switch (color) {
            case BLUE:
                path = GUIConstants.BLUE_CARD_IMAGE_PATH;
                break;
            case GREEN:
                path = GUIConstants.GREEN_CARD_IMAGE_PATH;
                break;
            case RED:
                path = GUIConstants.RED_CARD_IMAGE_PATH;
                break;
            case YELLOW:
                path = GUIConstants.YELLOW_CARD_IMAGE_PATH;
                break;
            case BLANK:
                path = GUIConstants.BLANK_CARD_IMAGE_PATH;
                break;
            default:
                path = GUIConstants.BACK_CARD_IMAGE_PATH;
        }
        return path;
    }

    private JLabel getNumberLabel(CardValue number) {
        String path = getCardNumberImagePath(number);
        JLabel label = new JLabel();
        label.setSize(this.getPreferredSize());
        if (StringUtils.isBlank(path)) {
            label.setFont(GUIConstants.DEFAULT_FONT);
            label.setText(number.toString());
        } else {
            System.out.println(path);
            System.out.println(getClass().getResource(path));
            ImageIcon ii = new ImageIcon(getClass().getResource(path));
            ii.setImage(ii.getImage()
                    .getScaledInstance(GUIConstants.RESIZED_CARD_WIDTH, GUIConstants.RESIZED_CARD_HEIGHT,
                            Image.SCALE_DEFAULT));
            label.setIcon(ii);
        }
        label.setVisible(true);
        return label;
    }

    private String getCardNumberImagePath(CardValue number) {
        return GUIConstants.CARD_NUMBER_IMAGE.replace("{0}", Integer.toString(number.ordinal()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public ICard getCard() {
        return card;
    }
}
