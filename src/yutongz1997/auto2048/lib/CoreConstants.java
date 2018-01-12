package yutongz1997.auto2048.lib;


/**
 * The class containing all the constants used in the core part of the game.
 * @author Yutong Zhang
 */
public final class CoreConstants {
    // Make this class not instantiable by using a private constructor.
    private CoreConstants() {}

    // The length of every row or column of the board.
    public static final int BOARD_DIMENSION = 4;
    // The length of each tile in the string representation of the board.
    public static final int TILE_LENGTH = 5;
    // The possibility to generate a number 4 on the board.
    public static final double NUMBER_4_POSSIBILITY = 0.1;
    // The weights of all tiles.
    public enum Weights {
        CORNER(50), SIDE(1), OTHER(0), EMPTY(20);

        private int weight;

        Weights(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return weight;
        }
    }
    // The number of possible directions.
    public static final int DIRECTION_NUMBER = 4;
    // The enumeration for all possible directions.
    public enum Directions {
        LEFT(0), RIGHT(1), UP(2), DOWN(3);

        private int directionIndex;

        Directions(int directionCode) {
            this.directionIndex = directionCode;
        }

        public int getDirectionIndex() {
            return directionIndex;
        }
    }
}
