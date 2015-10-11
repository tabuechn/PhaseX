package view.gui;

import model.card.CardColor;
import model.card.impl.Card;

import java.awt.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 26.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public final class GUIConstants {

    public static final int WINDOW_WIDTH = 900;
    public static final int WINDOW_HEIGHT = 1024;
    /**
     * Universal used constants
     */
    public static final Dimension WINDOW_SIZE = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
    public static final Dimension BUTTON_SIZE = new Dimension(300, 150);
    /**
     * Image Paths
     */
    public static final String BACKGROUND_IMAGE_PATH = "src/main/resources/view/gui/Background.png";
    public static final String LOGO_IMAGE_PATH = "src/main/resources/view/gui/Logo.png";
    public static final String BLUE_CARD_IMAGE_PATH = "src/main/resources/view/gui/cards/CardBlue.png";
    public static final String GREEN_CARD_IMAGE_PATH = "src/main/resources/view/gui/cards/CardGreen.png";
    public static final String RED_CARD_IMAGE_PATH = "src/main/resources/view/gui/cards/CardRed.png";
    public static final String YELLOW_CARD_IMAGE_PATH = "src/main/resources/view/gui/cards/CardYellow.png";
    public static final String BACK_CARD_IMAGE_PATH = "src/main/resources/view/gui/cards/CardBack.png";
    public static final String BLANK_CARD_IMAGE_PATH = "src/main/resources/view/gui/cards/NoCard.png";
    public static final String CARD_NUMBER_ONE_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card1.png";
    public static final String CARD_NUMBER_TWO_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card2.png";
    public static final String CARD_NUMBER_THREE_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card3.png";
    public static final String CARD_NUMBER_FOUR_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card4.png";
    public static final String CARD_NUMBER_FIVE_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card5.png";
    public static final String CARD_NUMBER_SIX_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card6.png";
    public static final String CARD_NUMBER_SEVEN_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card7.png";
    public static final String CARD_NUMBER_EIGHT_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card8.png";
    public static final String CARD_NUMBER_NINE_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card9.png";
    public static final String CARD_NUMBER_TEN_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card10.png";
    public static final String CARD_NUMBER_ELEVEN_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card11.png";
    public static final String CARD_NUMBER_TWELVE_IMAGE_PATH = "src/main/resources/view/gui/cards/number/Card12.png";
    public static final String CARD_NO_NUMBER_IMAGE_PATH = "src/main/resources/view/gui/cards/number/noNumber.png";
    public static final Dimension MAXIMUM_NOTIFICATION_SIZE = new Dimension(WINDOW_WIDTH, 50);
    public static final Dimension LOGO_LABEL_SIZE = new Dimension(WINDOW_WIDTH, 250);
    public static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 50);
    public static final Card BLANK_CARD = new Card(0, CardColor.BLANK);
    public static final Card BACK_CARD = new Card(0, CardColor.BACK);
    /**
     * Resized Card Dimensions
     */
    public static final int RESIZED_CARD_WIDTH = 150;
    public static final int CARD_ADJUSTMENT = (int) (GUIConstants.RESIZED_CARD_WIDTH * 0.75 * (-1));
    public static final int CARD_POSITION_LEFT_BORDER = (int) (GUIConstants.RESIZED_CARD_WIDTH * 0.1);
    public static final int RESIZED_CARD_HEIGHT = 200;
    public static final int CARD_POSITION_TOP_BORDER = (int) (GUIConstants.RESIZED_CARD_HEIGHT * 0.1);
    /**
     * Constants needed for the creation of cards
     */
    public static final Dimension CARD_DIMENSION =
            new Dimension(GUIConstants.RESIZED_CARD_WIDTH, GUIConstants.RESIZED_CARD_HEIGHT);
    /**
     * Dimension of the labels
     */
    public static final Dimension PLAYER_CARD_PANEL_SIZE =
            new Dimension(500, GUIConstants.RESIZED_CARD_HEIGHT + CARD_POSITION_TOP_BORDER);
    public static final Dimension HIDDEN_PLAYER_CARD_PANEL_SIZE = new Dimension(400, GUIConstants.RESIZED_CARD_HEIGHT);
    public static final BorderLayout DEFAULT_BORDER_LAYOUT = new BorderLayout(5, 5);

    /**
     * Numbers
     */
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int SIX = 6;
    public static final int SEVEN = 7;
    public static final int EIGHT = 8;
    public static final int NINE = 9;
    public static final int TEN = 10;
    public static final int ELEVEN = 11;
    public static final int TWELVE = 12;
    public static final int ZERO = 0;
    public static final Font FONT_PHASE_DESCRIPTION = new Font("Serif", Font.BOLD, 20);
    public static int MAX_SHOWN_CARDS = 6;

    /**
     * Empty private constructor
     */
    private GUIConstants() {
    }
}
