package view.gui;

import java.awt.*;

/**
 * If everything works right this class was
 * created by Konraifen88 on 26.09.2015.
 * If it doesn't work I don't know who the hell wrote it.
 */
public final class GUIConstants {

    public static final int CARD_ADJUSTMENT = -150;
    public static final int CARD_POSITION_LEFT_BORDER = 10;
    public static final int CARD_POSITION_TOP_BORDER = 100;
    public static final int WINDOW_WIDTH = 1280;
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
    public static final String CARD_NO_NUMBER_IMAGE_PATH = "src/main/resources/view/gui/cards/number/noNumber.png";
    /**
     * Constants needed for the creation of cards
     */
    public static final int AMOUNT_OF_TEXT_LABELS = 4;
    public static final Dimension CARD_DIMENSION = new Dimension(200, 300);
    /**
     * Dimension of the labels
     */
    public static final Dimension PLAYER_CARD_PANEL_SIZE = new Dimension(750, CARD_DIMENSION.height + 100);
    public static final Dimension HIDDEN_PLAYER_CARD_PANEL_SIZE = new Dimension(700, CARD_DIMENSION.height);
    public static final Dimension MAXIMUM_NOTIFICATION_SIZE = new Dimension(WINDOW_WIDTH, 100);
    public static final Dimension MINIMUM_NOTIFICATION_SIZE = new Dimension(WINDOW_WIDTH, 100);
    public static final Dimension LOGO_LABEL_SIZE = new Dimension(WINDOW_WIDTH, 250);
    public static final Dimension BUTTON_LABEL_MIN_SIZE = new Dimension(GUIConstants.WINDOW_WIDTH, 600);
    public static final Dimension BUTTON_LABEL_MAX_SIZE = new Dimension(GUIConstants.WINDOW_WIDTH, 775);
    public static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 50);

    private GUIConstants() {
    }
}
