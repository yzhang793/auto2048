package yutongz1997.auto2048.lib;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.Dimension;


/**
 * The class containing all general stuffs of the GUI, for instances, colors of
 * all the tiles, fonts, and so on.
 * @author Yutong Zhang
 */
public final class GeneralGUIConstants {
    // Make this class not instantiable by using a private constructor.
    private GeneralGUIConstants() {}

    // The colors of all the tiles.
    private static final Color COLOR_BLANK, COLOR_2, COLOR_4, COLOR_8, COLOR_16,
            COLOR_32, COLOR_64, COLOR_128, COLOR_256, COLOR_512, COLOR_1024,
            COLOR_2048, COLOR_4096, COLOR_BIG;
    public static final HashMap<String, Color> COLORS;
    static {
        COLOR_BLANK = new Color(205, 191, 182);
        COLOR_2 = new Color(239, 229, 219);
        COLOR_4 = new Color(238, 223, 200);
        COLOR_8 = new Color(245, 176, 121);
        COLOR_16 = new Color(246, 148, 99);
        COLOR_32 = new Color(245, 124, 95);
        COLOR_64 = new Color(247, 94, 60);
        COLOR_128 = new Color(237, 206, 113);
        COLOR_256 = new Color(236, 203, 96);
        COLOR_512 = new Color(237, 198, 79);
        COLOR_1024 = new Color(236, 196, 64);
        COLOR_2048 = new Color(238, 193, 48);
        COLOR_4096 = new Color(255, 61, 61);
        COLOR_BIG = new Color(255, 29, 30);

        COLORS = new HashMap<>();
        COLORS.put("", COLOR_BLANK);
        COLORS.put("2", COLOR_2);
        COLORS.put("4", COLOR_4);
        COLORS.put("8", COLOR_8);
        COLORS.put("16", COLOR_16);
        COLORS.put("32", COLOR_32);
        COLORS.put("64", COLOR_64);
        COLORS.put("128", COLOR_128);
        COLORS.put("256", COLOR_256);
        COLORS.put("512", COLOR_512);
        COLORS.put("1024", COLOR_1024);
        COLORS.put("2048", COLOR_2048);
        COLORS.put("4096", COLOR_4096);
        COLORS.put("8192", COLOR_BIG);
        COLORS.put("16384", COLOR_BIG);
        COLORS.put("32768", COLOR_BIG);
        COLORS.put("65536", COLOR_BIG);
    }
    // The fonts displayed in the GUI.
    public static Font FONT_DEFAULT;
    public static Font FONT_TILE_REGULAR;
    public static Font FONT_TILE_BIG;
    static {
        try {
            FONT_DEFAULT = Font.createFont(Font.TRUETYPE_FONT, new File("res/ClearSans-Regular.ttf"))
                    .deriveFont(15.0f);
            FONT_TILE_REGULAR = Font.createFont(Font.TRUETYPE_FONT, new File("res/ClearSans-Bold.ttf"))
                    .deriveFont(36.0f);
            FONT_TILE_BIG = Font.createFont(Font.TRUETYPE_FONT, new File("res/ClearSans-Bold.ttf"))
                    .deriveFont(30.0f);
            GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            graphicsEnvironment.registerFont(FONT_DEFAULT);
            graphicsEnvironment.registerFont(FONT_TILE_REGULAR);
            graphicsEnvironment.registerFont(FONT_TILE_BIG);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    // All the constants about bounds / borders / spaces of some components.
    public static final Rectangle RECTANGLE_PANEL_SCORES_BOUNDS
            = new Rectangle(18, 20, 460, 40);
    public static final Rectangle RECTANGLE_LABEL_BEST_SCORES_BOUNDS
            = new Rectangle(10, 5, 50, 30);
    public static final Rectangle RECTANGLE_FIELD_BEST_SCORES_BOUNDS
            = new Rectangle(55, 5, 150, 30);
    public static final Rectangle RECTANGLE_LABEL_SCORES_BOUNDS
            = new Rectangle(240, 5, 70, 30);
    public static final Rectangle RECTANGLE_FIELD_SCORES_BOUNDS
            = new Rectangle(300, 5, 150, 30);
    public static final Rectangle RECTANGLE_PANEL_MAIN_BOUNDS
            = new Rectangle(18, 80, 460, 500);
    public static final Border BORDER_SCORES
            = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.lightGray);
    public static final Border BORDER_TILES
            = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.gray);
    public static final Border BORDER_SETTINGS
            = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    public static final Dimension DIM_HORIZONTAL_MEDIUM
            = new Dimension(10, 0);
    public static final Dimension DIM_VERTICAL_SMALL
            = new Dimension(0, 5);
}
