package view.gui.cardDrawer;

import model.card.CardColor;
import model.card.ICard;
import org.apache.commons.lang3.StringUtils;
import view.gui.GUIConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * If everything works right this class was
 * created by Konraifen88 on 25.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public class DrawnCard extends JLayeredPane implements ActionListener {

    private boolean chosen;

    private ICard card;

    public DrawnCard() {
        //Do nothing
    }

    public DrawnCard(ICard card) {
        this.card = card;
        setMinimumSize(GUIConstants.CARD_DIMENSION);
        setPreferredSize(GUIConstants.CARD_DIMENSION);
        setMaximumSize(GUIConstants.CARD_DIMENSION);
        add(getBackgroundLabel(card.getColor()), -1);
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
            label.setIcon(new ImageIcon(path));
        }
        label.setVisible(true);
        return label;
    }

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

    private JLabel getNumberLabel(int number) {
        String path = getCardNumberImagePath(number);
        JLabel label = new JLabel();
        label.setSize(this.getPreferredSize());
        if (StringUtils.isBlank(path)) {
            label.setFont(GUIConstants.DEFAULT_FONT);
            label.setText(Integer.toString(number));
        } else {
            label.setIcon(new ImageIcon(path));
        }
        label.setVisible(true);
        return label;
    }

    private String getCardNumberImagePath(int number) {
        String path;
        switch (number) {
            case 1:
                path = GUIConstants.CARD_NUMBER_ONE_IMAGE_PATH;
                break;
            case 2:
                path = GUIConstants.CARD_NUMBER_TWO_IMAGE_PATH;
                break;
            case 3:
                path = GUIConstants.CARD_NUMBER_THREE_IMAGE_PATH;
                break;
            case 4:
                path = GUIConstants.CARD_NUMBER_FOUR_IMAGE_PATH;
                break;
            case 5:
                path = GUIConstants.CARD_NUMBER_FIVE_IMAGE_PATH;
                break;
            case 6:
                path = GUIConstants.CARD_NUMBER_SIX_IMAGE_PATH;
                break;
            case 7:
                path = GUIConstants.CARD_NUMBER_SEVEN_IMAGE_PATH;
                break;
            case 8:
                path = GUIConstants.CARD_NUMBER_EIGHT_IMAGE_PATH;
                break;
            case 9:
                path = GUIConstants.CARD_NUMBER_NINE_IMAGE_PATH;
                break;
            default:
                path = GUIConstants.CARD_NO_NUMBER_IMAGE_PATH;
        }
        return path;
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
