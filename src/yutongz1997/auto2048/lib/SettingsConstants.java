package yutongz1997.auto2048.lib;


/**
 * The class containing all the constants used in the setting dialog.
 * @author Yutong Zhang
 */
public class SettingsConstants {
    // Make this class not instantiable by using a private constructor.
    private SettingsConstants() {}

    // The path of the configuration file.
    public static final String PATH_CONFIGURATIONS = "yutongz1997.auto2048";
    // The keys of all the properties.
    public static final String KEY_PLAYER_NAME = "PLAYER_NAME";
    public static final String KEY_BEST_PLAYER_NAME = "BEST_PLAYER_NAME";
    public static final String KEY_BEST_SCORES = "BEST_SCORES";
    public static final String KEY_SLEEP_TIME = "SLEEP_TIME";
    public static final String KEY_SEARCH_TREE_LEVEL = "SEARCH_TREE_LEVEL";
    // The default values of all the properties.
    public static final String DEFAULT_PLAYER_NAME = "Anonymous";
    public static final int DEFAULT_BEST_SCORES = 0;
    public static final int DEFAULT_SLEEP_TIME = 500;
    // The minimum, maximum and default level of the search tree.
    public static final int SEARCH_TREE_LEVEL_MIN = 1;
    public static final int SEARCH_TREE_LEVEL_MAX = 12;
    public static final int SEARCH_TREE_LEVEL_DEFAULT = 6;

    // The constants of the slider.
    public static final int SLIDER_VALUE_MIN = 0;
    public static final int SLIDER_VALUE_MAX = 1000;
    public static final int SLIDER_VALUE_MID = 500;
    public static final int SLIDER_MAJOR_TICK_SPACING = 100;
    public static final int SLIDER_MINOR_TICK_SPACING = 50;
    public static final String SLIDER_LABEL_MIN = "0.0s";
    public static final String SLIDER_LABEL_MAX = "1.0s";
    public static final String SLIDER_LABEL_MID = "0.5s";

    // The string constants of labels, buttons, messages, etc..
    public static final String CAPTION_SETTINGS = "Settings";
    public static final String TITLE_GENERAL = "General";
    public static final String TITLE_AUTO_PLAY = "Auto Play";
    public static final String LABEL_PLAYER_NAME = "The player's name:";
    public static final String LABEL_SLEEP_TIME = "The sleep time between each move:";
    public static final String LABEL_SEARCH_LEVEL = "Search level:";
    public static final String BUTTON_RESET = "Reset";
    public static final String BUTTON_OK = "OK";
    public static final String BUTTON_CANCEL = "Cancel";
    public static final String CAPTION_RESET_DIALOG = "Confirm Reset";
    public static final String MESSAGE_RESET_DIALOG = "This operation will eliminate all the player's data"
            + " and reset to\n default settings. Are you sure you want to proceed?";
}
