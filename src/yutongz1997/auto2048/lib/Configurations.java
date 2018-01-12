package yutongz1997.auto2048.lib;

import java.util.Observable;
import java.util.prefs.Preferences;


/**
 * The class that stores all configurations / settings of the game / player.
 * @author Yutong Zhang
 */
public class Configurations extends Observable {
    private static final Preferences preferences = Preferences.userRoot()
            .node(SettingsConstants.PATH_CONFIGURATIONS);

    // The player's name.
    private String playerName;
    // The best scores and the corresponding player's name.
    private String bestPlayerName;
    private int bestScores;
    // The sleep time between each move of the auto player.
    private int sleepTime;
    // The level of the search tree (the larger it is, the better the performance is).
    private int searchTreeLevel;


    /**
     * Constructs a configuration object.
     */
    public Configurations() {
        // Get all the properties from the property file in a specific system-related
        // directory. If this file does not exist, default values will be set.
        playerName = preferences.get(SettingsConstants.KEY_PLAYER_NAME,
                SettingsConstants.DEFAULT_PLAYER_NAME);
        bestPlayerName = preferences.get(SettingsConstants.KEY_BEST_PLAYER_NAME,
                SettingsConstants.DEFAULT_PLAYER_NAME);
        bestScores = preferences.getInt(SettingsConstants.KEY_BEST_SCORES,
                SettingsConstants.DEFAULT_BEST_SCORES);
        sleepTime = preferences.getInt(SettingsConstants.KEY_SLEEP_TIME,
                SettingsConstants.DEFAULT_SLEEP_TIME);
        searchTreeLevel = preferences.getInt(SettingsConstants.KEY_SEARCH_TREE_LEVEL,
                SettingsConstants.SEARCH_TREE_LEVEL_DEFAULT);
    }


    /**
     * Obtains the player's name stored in the configuration file.
     * @return the player's name.
     */
    public String getPlayerName() {
        return playerName;
    }


    /**
     * Changes the current player's name to a given new name, and save this change
     * to the configuration file.
     * @param playerName the player's new name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        preferences.put(SettingsConstants.KEY_PLAYER_NAME, playerName);
        setChanged();
        notifyObservers();
    }


    /**
     * Obtains the player's name who achieves the best scores stored in the
     * configuration file.
     * @return the best player's name
     */
    public String getBestPlayerName() {
        return bestPlayerName;
    }


    /**
     * Obtains the best scores stored in the configuration file.
     * @return the best scores.
     */
    public int getBestScores() {
        return bestScores;
    }


    /**
     * Changes the best player's record, i.e. his / her name and the scores to
     * given name and scores, and save this change to the configuration file.
     * @param bestPlayerName  the new best player's name
     * @param bestScores the new best scores
     */
    public void setBestRecord(String bestPlayerName, int bestScores) {
        this.bestPlayerName = bestPlayerName;
        this.bestScores = bestScores;
        preferences.put(SettingsConstants.KEY_BEST_PLAYER_NAME, bestPlayerName);
        preferences.putInt(SettingsConstants.KEY_BEST_SCORES, bestScores);
        setChanged();
        notifyObservers();
    }


    /**
     * Obtains the sleep time between each move of the auto player stored in
     * the configuration file.
     * @return the sleep time between each move of the auto player
     */
    public int getSleepTime() {
        return sleepTime;
    }


    /**
     * Changes the current auto player's sleep time to a new one, and save this
     * change to the configuration file.
     * @param sleepTime the new sleep time between each move of the auto player
     */
    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        preferences.putInt(SettingsConstants.KEY_SLEEP_TIME, sleepTime);
        setChanged();
        notifyObservers();
    }


    /**
     * Obtains the level of the search tree stored in the configuration file.
     * @return the search tree level
     */
    public int getSearchTreeLevel() {
        return searchTreeLevel;
    }


    /**
     * Changes the level of the search tree to a given new one, and save this
     * change to the configuration file.
     * @param searchTreeLevel the new search tree level
     */
    public void setSearchTreeLevel(int searchTreeLevel) {
        this.searchTreeLevel = searchTreeLevel;
        preferences.putInt(SettingsConstants.KEY_SEARCH_TREE_LEVEL, searchTreeLevel);
        setChanged();
        notifyObservers();
    }


    /**
     * Resets all the properties to their default values.
     */
    public void reset() {
        playerName = SettingsConstants.DEFAULT_PLAYER_NAME;
        preferences.put(SettingsConstants.KEY_PLAYER_NAME, playerName);
        bestPlayerName = SettingsConstants.DEFAULT_PLAYER_NAME;
        preferences.put(SettingsConstants.KEY_BEST_PLAYER_NAME, bestPlayerName);
        bestScores = SettingsConstants.DEFAULT_BEST_SCORES;
        preferences.putInt(SettingsConstants.KEY_BEST_SCORES, bestScores);
        sleepTime = SettingsConstants.DEFAULT_SLEEP_TIME;
        preferences.putInt(SettingsConstants.KEY_SLEEP_TIME, sleepTime);
        searchTreeLevel = SettingsConstants.SEARCH_TREE_LEVEL_DEFAULT;
        preferences.putInt(SettingsConstants.KEY_SEARCH_TREE_LEVEL, searchTreeLevel);
        setChanged();
        notifyObservers();
    }
}
