package yutongz1997.auto2048.gui;

import java.util.Observable;
import java.util.Observer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import yutongz1997.auto2048.core.Board;
import yutongz1997.auto2048.lib.*;
import yutongz1997.auto2048.core.SearchTree;


/**
 * The class of the game's main window.
 * @author Yutong Zhang
 */
public class GameFrame extends JFrame implements Observer {
    // Some (shared between methods) GUI Components, e.g. panels, menus, etc..
    private JMenuBar menuBar;
    private JLabel fieldBestScores;
    private JTextField fieldScores;
    private JLabel[][] tiles;
    private JMenuItem itemNewGame;
    private JMenuItem subItemAutoPlayStart;
    private JMenuItem subItemAutoPlayPause;
    private JMenuItem itemSettings;
    // The game board.
    private Board gameBoard;
    // The configurations of the game.
    private Configurations config;
    // The steps taken up to now.
    private int steps;
    // The current status of the auto player (true for on, false for off).
    private boolean autoPlayStatus;


    /**
     * Constructs the game window.
     */
    public GameFrame() {
        // Set the icon of the game.
        setIconImage(GameFrameConstants.GAME_ICON);
        // Make the GUI style of this application identical to that of current
        // operating system.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.getDefaults().put("Button.showMnemonics", Boolean.TRUE);
            UIManager.getLookAndFeelDefaults().put("MenuItem.acceleratorForeground", Color.gray);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        gameBoard = new Board();
        config = new Configurations();
        config.addObserver(this);
        steps = 0;
        autoPlayStatus = false;

        menuBar = new JMenuBar();
        buildGameMenu();
        buildHelpMenu();
        initializeGame();
    }


    /**
     * Builds the menu "Game".
     */
    private void buildGameMenu() {
        final JMenu menuGame = new JMenu(GameFrameConstants.MENU_GAME);
        menuGame.setMnemonic(KeyEvent.VK_G);

        // All the menu items in the menu "Game".
        // Item "New Game".
        itemNewGame = new JMenuItem(GameFrameConstants.MENU_ITEM_NEW_GAME,
                GameFrameConstants.ICON_NEW_GAME);
        itemNewGame.setMnemonic(KeyEvent.VK_N);
        itemNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        itemNewGame.addActionListener((ActionEvent event) -> newGame());
        menuGame.add(itemNewGame);
        // Items "Start" and "Pause" in the submenu "Auto Play".
        subItemAutoPlayStart = new JMenuItem(GameFrameConstants.MENU_SUBITEM_AUTO_SOLVE_START,
                GameFrameConstants.ICON_AUTO_PLAY_START);
        subItemAutoPlayStart.setMnemonic(KeyEvent.VK_S);
        subItemAutoPlayStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        subItemAutoPlayPause = new JMenuItem(GameFrameConstants.MENU_SUBITEM_AUTO_SOLVE_PAUSE,
                GameFrameConstants.ICON_AUTO_PLAY_PAUSE);
        subItemAutoPlayPause.setMnemonic(KeyEvent.VK_P);
        subItemAutoPlayPause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        subItemAutoPlayPause.setEnabled(false);
        subItemAutoPlayStart.addActionListener((ActionEvent event) -> autoPlay());
        subItemAutoPlayPause.addActionListener((ActionEvent event) -> changeAutoPlayStatus());
        // Submenu "Auto Play".
        final JMenu subMenuAutoPlay = new JMenu(GameFrameConstants.MENU_ITEM_AUTO_SOLVE);
        subMenuAutoPlay.add(subItemAutoPlayStart);
        subMenuAutoPlay.add(subItemAutoPlayPause);
        menuGame.add(subMenuAutoPlay);
        menuGame.addSeparator();
        // Item "Settings"
        itemSettings = new JMenuItem(GameFrameConstants.MENU_SUBITEM_AUTO_SOLVE_SETTINGS,
                GameFrameConstants.ICON_SETTINGS);
        itemSettings.setMnemonic(KeyEvent.VK_T);
        itemSettings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                KeyEvent.CTRL_MASK | KeyEvent.ALT_MASK));
        itemSettings.addActionListener((ActionEvent event) -> {
            SettingsDialog settingsDialog = new SettingsDialog(config);
            settingsDialog.setVisible(true);
        });
        menuGame.add(itemSettings);
        menuGame.addSeparator();
        // Item "Exit".
        final JMenuItem itemExit = new JMenuItem(GameFrameConstants.MENU_ITEM_EXIT);
        itemExit.setMnemonic(KeyEvent.VK_X);
        itemExit.addActionListener((ActionEvent event) -> exit());
        menuGame.add(itemExit);

        menuBar.add(menuGame);
    }


    /**
     * Builds the menu "Help".
     */
    private void buildHelpMenu() {
        final JMenu menuHelp = new JMenu(GameFrameConstants.MENU_HELP);
        menuHelp.setMnemonic(KeyEvent.VK_H);

        // All the menu items in the menu "Help".
        // Item "About".
        final JMenuItem itemAbout = new JMenuItem(GameFrameConstants.MENU_ITEM_ABOUT);
        itemAbout.setMnemonic(KeyEvent.VK_A);
        itemAbout.addActionListener((ActionEvent event) -> JOptionPane.showMessageDialog(null,
                GameFrameConstants.MESSAGE_ABOUT_DIALOG, GameFrameConstants.CAPTION_ABOUT_DIALOG,
                JOptionPane.INFORMATION_MESSAGE));
        menuHelp.add(itemAbout);

        menuBar.add(menuHelp);
    }


    /**
     * Builds other components and initialize the game.
     */
    private void initializeGame() {
        // The panel containing all the information about scores.
        final JPanel panelScores = new JPanel();
        panelScores.setBackground(Color.white);
        panelScores.setBounds(GeneralGUIConstants.RECTANGLE_PANEL_SCORES_BOUNDS);
        panelScores.setBorder(GeneralGUIConstants.BORDER_SCORES);
        getContentPane().add(panelScores);
        panelScores.setLayout(null);

        final JLabel labelBestScores = new JLabel(GameFrameConstants.LABEL_BEST_SCORES_PREFIX);
        labelBestScores.setFont(GeneralGUIConstants.FONT_DEFAULT);
        labelBestScores.setBounds(GeneralGUIConstants.RECTANGLE_LABEL_BEST_SCORES_BOUNDS);
        panelScores.add(labelBestScores);

        fieldBestScores = new JLabel("0");
        fieldBestScores.setFont(GeneralGUIConstants.FONT_DEFAULT);
        fieldBestScores.setBounds(GeneralGUIConstants.RECTANGLE_FIELD_BEST_SCORES_BOUNDS);
        fieldBestScores.setText(String.valueOf(config.getBestScores())
                + " (" + config.getBestPlayerName() + ")");
        panelScores.add(fieldBestScores);

        final JLabel labelScores = new JLabel(GameFrameConstants.LABEL_SCORES_PREFIX);
        labelScores.setFont(GeneralGUIConstants.FONT_DEFAULT);
        labelScores.setBounds(GeneralGUIConstants.RECTANGLE_LABEL_SCORES_BOUNDS);
        panelScores.add(labelScores);

        fieldScores = new JTextField("0");
        fieldScores.setFont(GeneralGUIConstants.FONT_DEFAULT);
        fieldScores.setBounds(GeneralGUIConstants.RECTANGLE_FIELD_SCORES_BOUNDS);
        fieldScores.setEditable(false);
        fieldScores.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent event) {
                if (!autoPlayStatus)
                    doMoveKeyPressed(event);
            }
        });
        panelScores.add(fieldScores);

        // The panel containing all the tiles.
        final JPanel panelBoard = new JPanel();
        panelBoard.setBounds(GeneralGUIConstants.RECTANGLE_PANEL_MAIN_BOUNDS);
        getContentPane().add(panelBoard);
        panelBoard.setLayout(null);

        tiles = new JLabel[CoreConstants.BOARD_DIMENSION][CoreConstants.BOARD_DIMENSION];
        int[][] board = gameBoard.getBoard();
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++) {
            for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++) {
                tiles[row][column] = new JLabel();
                if (board[row][column] >= GameFrameConstants.NUMBER_CHANGE_FONT)
                    tiles[row][column].setFont(GeneralGUIConstants.FONT_TILE_BIG);
                else
                    tiles[row][column].setFont(GeneralGUIConstants.FONT_TILE_REGULAR);
                tiles[row][column].setHorizontalAlignment(SwingConstants.CENTER);
                String tile = Utility.tileToString(board[row][column]);
                tiles[row][column].setText(tile);
                tiles[row][column].setBounds(GameFrameConstants.TILES_X_MULTIPLIER * column,
                        GameFrameConstants.TILES_Y_MULTIPLIER * row,
                        GameFrameConstants.TILE_WIDTH, GameFrameConstants.TILE_HEIGHT);
                tiles[row][column].setForeground(Color.darkGray);
                tiles[row][column].setBackground(GeneralGUIConstants.COLORS.get(tile));
                tiles[row][column].setOpaque(true);
                tiles[row][column].setBorder(GeneralGUIConstants.BORDER_TILES);
                panelBoard.add(tiles[row][column]);
            }
        }

        final JLabel labelTips = new JLabel(GameFrameConstants.LABEL_TIP_MESSAGE);
        labelTips.setFont(GeneralGUIConstants.FONT_DEFAULT);
        labelTips.setBounds(0, 480, 500, 20);
        panelBoard.add(labelTips);

        // The settings about the game window.
        setResizable(false);
        getContentPane().setLayout(null);
        setBounds(GameFrameConstants.WINDOW_SIZE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setWindowTitle(config.getPlayerName());
        setJMenuBar(menuBar);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }


    /**
     * Creates a new game and resets some status to default.
     */
    private void newGame() {
        gameBoard = new Board();
        updateTiles(gameBoard);
        steps = 0;
        fieldScores.setEnabled(true);
        fieldScores.requestFocus();
    }


    /**
     * Updates the current window's title by a new given player's name.
     * @param playerName the new player's name.
     */
    private void setWindowTitle(String playerName) {
        setTitle(GameFrameConstants.CAPTION_WINDOW_PREFIX + playerName + ")");
    }


    /**
     * Changes the status of related stuffs (menus, etc.) of the auto play.
     */
    private void changeAutoPlayStatus() {
        autoPlayStatus = !autoPlayStatus;
        itemNewGame.setEnabled(!autoPlayStatus);
        subItemAutoPlayPause.setEnabled(autoPlayStatus);
        subItemAutoPlayStart.setEnabled(!autoPlayStatus);
        itemSettings.setEnabled(!autoPlayStatus);
    }


    /**
     * Runs the underlying AI to play the game automatically.
     */
    private void autoPlay() {
        if (!gameBoard.isGameOver()) {
            Thread autoPlayThread = new Thread(() -> {
                try {
                    int searchTreeLevel = config.getSearchTreeLevel();
                    int sleepTime = config.getSleepTime();
                    while (autoPlayStatus) {
                        SearchTree searchTree = new SearchTree(gameBoard, searchTreeLevel);
                        Board temp = searchTree.nextStep(steps);
                        if (temp != null)
                            updateTiles(temp);
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            synchronized (this) {
                changeAutoPlayStatus();
                autoPlayThread.start();
            }
        } else
            JOptionPane.showMessageDialog(null,
                    GameFrameConstants.MESSAGE_START_AFTER_GAME_OVER_DIALOG,
                    GameFrameConstants.CAPTION_START_AFTER_GAME_OVER_DIALOG,
                    JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Exits the game with a notification.
     */
    private void exit() {
        int option = JOptionPane.showConfirmDialog(null,
                GameFrameConstants.MESSAGE_QUIT_DIALOG,
                GameFrameConstants.CAPTION_QUIT_DIALOG,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (option == JOptionPane.OK_OPTION)
            System.exit(0);
    }


    /**
     * Moves all the tiles when certain keys are pressed.
     * @param event the key event
     */
    private void doMoveKeyPressed(final KeyEvent event) {
        Board temp = null;
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                temp = gameBoard.move(CoreConstants.Directions.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                temp = gameBoard.move(CoreConstants.Directions.RIGHT);
                break;
            case KeyEvent.VK_UP:
                temp = gameBoard.move(CoreConstants.Directions.UP);
                break;
            case KeyEvent.VK_DOWN:
                temp = gameBoard.move(CoreConstants.Directions.DOWN);
                break;
            default:
                break;
        }
        if (temp != null)
            updateTiles(temp);
    }


    /**
     * Updates all the tiles and information with a given new game board.
     * @param newBoard the new board
     */
    private void updateTiles(Board newBoard) {
        gameBoard.replaceBoardWith(newBoard);
        if (gameBoard.getScores() > config.getBestScores())
            config.setBestRecord(config.getPlayerName(), gameBoard.getScores());
        int[][] board = gameBoard.getBoard();
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++) {
            for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++) {
                String tile = Utility.tileToString(board[row][column]);
                tiles[row][column].setText(tile);
                tiles[row][column].setBackground(GeneralGUIConstants.COLORS.get(tile));
            }
        }
        fieldScores.setText(String.valueOf(gameBoard.getScores()));
        steps++;
        if (gameBoard.isGameOver()) {
            changeAutoPlayStatus();
            String message = GameFrameConstants.MESSAGE_GAME_OVER_DIALOG_PREFIX
                    + config.getPlayerName() + "!\n"
                    + GameFrameConstants.LABEL_SCORES_PREFIX + fieldScores.getText() + "\n"
                    + GameFrameConstants.LABEL_MAX_TILE_PREFIX + gameBoard.getMaxTile() + "\n"
                    + GameFrameConstants.LABEL_STEPS_PREFIX + steps;
            JOptionPane.showMessageDialog(null, message,
                    GameFrameConstants.CAPTION_GAME_OVER_DIALOG,
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    /**
     * Updates all of the settings once the configurations change.
     * @param o <strong>must</strong> be the Configurations object
     * @param arg <em>not used here</em>
     */
    @Override
    public void update(Observable o, Object arg) {
        // Make sure that the update can be executed successfully.
        if (o instanceof Configurations) {
            Configurations newConfig = (Configurations) o;
            setWindowTitle(newConfig.getPlayerName());
            fieldBestScores.setText(String.valueOf(newConfig.getBestScores())
                    + " (" + newConfig.getBestPlayerName() + ")");
        }
    }
}
