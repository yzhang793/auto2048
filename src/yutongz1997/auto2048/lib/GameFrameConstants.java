package yutongz1997.auto2048.lib;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * The class containing all the constants about menus, labels, messages, etc.
 * of the user interface.
 * @author Yutong Zhang
 */
public final class GameFrameConstants {
    // Make this class not instantiable by using a private constructor.
    private GameFrameConstants() {}

    // All the numbers greater than this should change to the smaller font.
    public static final int NUMBER_CHANGE_FONT = 16384;
    // The constants about single tiles.
    public static final int TILES_X_MULTIPLIER = 120;
    public static final int TILES_Y_MULTIPLIER = 120;
    public static final int TILE_WIDTH = 100;
    public static final int TILE_HEIGHT = 100;
    // The game window's size.
    public static final Rectangle WINDOW_SIZE = new Rectangle(500, 50, 500, 650);

    // The game window's icon.
    private static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static final Image GAME_ICON = toolkit.createImage("res/icon.png");

    // The icons for some menu items.
    public static final ImageIcon ICON_NEW_GAME = new ImageIcon("res/new_game.png");
    public static final ImageIcon ICON_AUTO_PLAY_START = new ImageIcon("res/auto_play_start.png");
    public static final ImageIcon ICON_AUTO_PLAY_PAUSE = new ImageIcon("res/auto_play_pause.png");
    public static final ImageIcon ICON_SETTINGS = new ImageIcon("res/settings.png");

    // The string constants of labels, menus, messages, etc..
    public static final String CAPTION_WINDOW_PREFIX = "Auto 2048 (Player: ";
    public static final String LABEL_BEST_SCORES_PREFIX = "Best: ";
    public static final String LABEL_SCORES_PREFIX = "Scores: ";
    public static final String LABEL_TIP_MESSAGE = "HOW TO PLAY: Use your arrow keys to move the tiles.";
    public static final String CAPTION_ABOUT_DIALOG = "About";
    public static final String MESSAGE_ABOUT_DIALOG = "Auto 2048\nAuthor: Yutong Zhang\n" +
            "Last Update: January 12th, 2018";
    public static final String CAPTION_QUIT_DIALOG = "Confirm Exit";
    public static final String MESSAGE_QUIT_DIALOG = "Are you sure you want to exit Auto 2048?";
    public static final String CAPTION_START_AFTER_GAME_OVER_DIALOG = "Unable to Start";
    public static final String MESSAGE_START_AFTER_GAME_OVER_DIALOG = "The game is already over. "
            + "You cannot start the auto player\nagain unless you create a new game.";
    public static final String CAPTION_GAME_OVER_DIALOG = "Game Over";
    public static final String MESSAGE_GAME_OVER_DIALOG_PREFIX = "GAME OVER, ";
    public static final String LABEL_MAX_TILE_PREFIX = "Maximum Tile: ";
    public static final String LABEL_STEPS_PREFIX = "Steps: ";
    public static final String MENU_GAME = "Game";
    public static final String MENU_ITEM_NEW_GAME = "New Game";
    public static final String MENU_ITEM_AUTO_SOLVE = "Auto Play";
    public static final String MENU_SUBITEM_AUTO_SOLVE_START = "Start";
    public static final String MENU_SUBITEM_AUTO_SOLVE_PAUSE = "Pause";
    public static final String MENU_SUBITEM_AUTO_SOLVE_SETTINGS = "Settings...";
    public static final String MENU_ITEM_EXIT = "Exit";
    public static final String MENU_HELP = "Help";
    public static final String MENU_ITEM_ABOUT = "About";
}
