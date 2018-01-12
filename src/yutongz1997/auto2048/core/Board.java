package yutongz1997.auto2048.core;

import java.util.Arrays;
import java.util.Random;

import yutongz1997.auto2048.lib.CoreConstants;
import yutongz1997.auto2048.lib.CoreConstants.Directions;
import yutongz1997.auto2048.lib.CoreConstants.Weights;
import yutongz1997.auto2048.lib.Utility;


/**
 * The class of game engine, which provides fundamental but the most important
 * elements (the game board, the moves, the scores, etc.) of the game.
 * @author Yutong Zhang
 */
public class Board implements Cloneable {
    private static final Random randNumGenerator = new Random();
    private int[][] board;
    private int scores;


    /**
     * Constructs a game board object.
     */
    public Board() {
        scores = 0;
        board = new int[CoreConstants.BOARD_DIMENSION][CoreConstants.BOARD_DIMENSION];
        // Place two numbers (2 or 4) randomly on the board.
        addNumber();
        addNumber();
    }


    /**
     * Deeply copies everything in the current game board, including all numbers
     * and the scores of the player.
     * @return a copied board of the current one
     */
    public Board clone() {
        Board boardCopy = null;
        try {
            super.clone();
            boardCopy = new Board();
            boardCopy.scores = scores;
            for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++)
                System.arraycopy(board[row], 0, boardCopy.board[row], 0, board.length);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return boardCopy;
    }


    /**
     * Returns the current game board as a two-dimension array.
     * @return the game board
     */
    public int[][] getBoard() {
        return board;
    }


    /**
     * Replaces the current game board with a board given by the user.
     * @param anotherBoard the given game board
     */
    public void replaceBoardWith(Board anotherBoard) {
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++)
            System.arraycopy(anotherBoard.board[row], 0, board[row], 0,
                    anotherBoard.board.length);
        scores = anotherBoard.scores;
    }


    /**
     * Returns the scores the player gets based on all tiles' information.
     * @return the scores
     */
    public int getScores() {
        return scores;
    }


    /**
     * Counts the number of empty tiles currently presented on the board.
     * @return the number of empty tiles
     */
    private int getEmptyTileNumber() {
        int count = 0;
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++)
            for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++)
                if (board[row][column] == 0)
                    count++;
        return count;
    }


    /**
     * Determines the maximum number contained in all tiles currently presented
     * on the board.
     * @return the maximum number
     */
    public int getMaxTile() {
        int maxTile = board[0][0];
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++)
            for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++)
                if (board[row][column] > maxTile)
                    maxTile = board[row][column];
        return maxTile;
    }


    /**
     * Adds a number (2 or 4, which is randomly generated) in a random empty tile.
     */
    private void addNumber() {
        if (getEmptyTileNumber() > 0) {
            while (true) {
                int row = randNumGenerator.nextInt(CoreConstants.BOARD_DIMENSION);
                int column = randNumGenerator.nextInt(CoreConstants.BOARD_DIMENSION);
                if (board[row][column] == 0) {
                    board[row][column] = Math.random() > CoreConstants.NUMBER_4_POSSIBILITY ? 2 : 4;
                    break;
                }
            }
        }
    }


    /**
     * Determines whether the game is over based on the current tile distribution
     * of the board. The game is NOT over if one of the five following conditions
     * is satisfied:
     *     <p>(a) There exists an empty tile.</p>
     *     <p>(b) The current tile and the one on its left contain the same number.
     *         (Since they can be combined and produce a larger one)</p>
     *     <p>(c) The current tile and the one on its right contain the same number.</p>
     *     <p>(d) The current tile and the one above it contain the same number.</p>
     *     <p>(e) The current tile and the one below it contain the same number.</p>
     * @return true if the game is over; false otherwise
     */
    public boolean isGameOver() {
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++)
            for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++)
                if (board[row][column] == 0 || (row > 0 && board[row][column] == board[row - 1][column])
                        || (row < CoreConstants.BOARD_DIMENSION - 1 && board[row][column] == board[row + 1][column])
                        || (column > 0 && board[row][column] == board[row][column - 1])
                        || (column < CoreConstants.BOARD_DIMENSION - 1 && board[row][column] == board[row][column + 1]))
                    return false;
        return true;
    }


    /**
     * Deeply copies the current board at first, then makes a left move on the
     * cloned board and adds a new number.
     * @return the final status of the board after moving left
     */
    private Board moveLeft() {
        Board newBoard = clone();
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++) {
            // First pass: combine as many tiles as possible.
            for (int column = 0; column < CoreConstants.BOARD_DIMENSION - 1; column++) {
                int currentNum = newBoard.board[row][column];
                int tempIndex = column + 1;
                // If two tiles (with zero or more empty tiles between them) contain
                // same numbers, they can combine.
                while (currentNum != 0 && tempIndex <= CoreConstants.BOARD_DIMENSION - 1) {
                    if (newBoard.board[row][tempIndex] != 0
                            && newBoard.board[row][tempIndex] != currentNum)
                        break;
                    else if (newBoard.board[row][tempIndex] == currentNum) {
                        newBoard.board[row][column] *= 2;
                        newBoard.board[row][tempIndex] = 0;
                        newBoard.scores += (currentNum * 2);
                        break;
                    } else
                        tempIndex++;
                }
            }
            // Second pass: shift all the tiles to as left as possible.
            for (int column = 1; column < CoreConstants.BOARD_DIMENSION; column++) {
                if (newBoard.board[row][column] != 0) {
                    int tempIndex = column;
                    while (tempIndex > 0 && newBoard.board[row][tempIndex - 1] == 0) {
                        Utility.swap(newBoard.board[row], tempIndex - 1, tempIndex);
                        tempIndex--;
                    }
                }
            }
        }
        // Having moved validly, place a new number (2 or 4) randomly on the board.
        if (isValidMove(newBoard))
            newBoard.addNumber();

        return newBoard;
    }


    /**
     * Deeply copies the current board at first, then makes a right move on the
     * cloned board and adds a new number.
     * @return the final status of the board after moving right
     */
    private Board moveRight() {
        Board newBoard = clone();
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++) {
            // First pass: combine as many tiles as possible.
            for (int column = CoreConstants.BOARD_DIMENSION - 1; column > 0; column--) {
                int currentNum = newBoard.board[row][column];
                int tempIndex = column - 1;
                // If two tiles (with zero or more empty tiles between them) contain
                // same numbers, they can combine.
                while (currentNum != 0 && tempIndex >= 0) {
                    if (newBoard.board[row][tempIndex] != 0
                            && newBoard.board[row][tempIndex] != currentNum)
                        break;
                    else if (newBoard.board[row][tempIndex] == currentNum) {
                        newBoard.board[row][column] *= 2;
                        newBoard.board[row][tempIndex] = 0;
                        newBoard.scores += (currentNum * 2);
                        break;
                    } else
                        tempIndex--;
                }
            }
            // Second pass: shift all the tiles to as right as possible.
            for (int column = CoreConstants.BOARD_DIMENSION - 2; column >= 0; column--) {
                if (newBoard.board[row][column] != 0) {
                    int tempIndex = column;
                    while (tempIndex < CoreConstants.BOARD_DIMENSION - 1
                            && newBoard.board[row][tempIndex + 1] == 0) {
                        Utility.swap(newBoard.board[row], tempIndex, tempIndex + 1);
                        tempIndex++;
                    }
                }
            }
        }
        // Having moved validly, place a new number (2 or 4) randomly on the board.
        if (isValidMove(newBoard))
            newBoard.addNumber();

        return newBoard;
    }


    /**
     * Deeply copies the current board at first, then makes an up move on the
     * cloned board and adds a new number.
     * @return the final status of the board after moving up
     */
    private Board moveUp() {
        Board newBoard = clone();
        for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++) {
            // First pass: combine as many tiles as possible.
            for (int row = 0; row < CoreConstants.BOARD_DIMENSION - 1; row++) {
                int currentNum = newBoard.board[row][column];
                int tempIndex = row + 1;
                // If two tiles (with zero or more empty tiles between them) contain
                // same numbers, they can combine.
                while (currentNum != 0 && tempIndex <= CoreConstants.BOARD_DIMENSION - 1) {
                    if (newBoard.board[tempIndex][column] != 0
                            && newBoard.board[tempIndex][column] != currentNum)
                        break;
                    else if (newBoard.board[tempIndex][column] == currentNum) {
                        newBoard.board[row][column] *= 2;
                        newBoard.board[tempIndex][column] = 0;
                        newBoard.scores += (currentNum * 2);
                        break;
                    } else
                        tempIndex++;
                }
            }
            // Second pass: shift all the tiles to as top as possible.
            for (int row = 1; row < CoreConstants.BOARD_DIMENSION; row++) {
                if (newBoard.board[row][column] != 0) {
                    int tempIndex = row;
                    while (tempIndex > 0 && newBoard.board[tempIndex - 1][column] == 0) {
                        Utility.swap(newBoard.board, column, tempIndex - 1, tempIndex);
                        tempIndex--;
                    }
                }
            }
        }
        // Having moved validly, place a new number (2 or 4) randomly on the board.
        if (isValidMove(newBoard))
            newBoard.addNumber();

        return newBoard;
    }


    /**
     * Deeply copies the current board at first, then makes a down move on the
     * cloned board and adds a new number.
     * @return the final status of the board after moving down
     */
    private Board moveDown() {
        Board newBoard = clone();
        for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++) {
            // First pass: combine as many tiles as possible.
            for (int row = CoreConstants.BOARD_DIMENSION - 1; row > 0; row--) {
                int currentNum = newBoard.board[row][column];
                int tempIndex = row - 1;
                // If two tiles (with zero or more empty tiles between them) contain
                // same numbers, they can combine.
                while (currentNum != 0 && tempIndex >= 0) {
                    if (newBoard.board[tempIndex][column] != 0
                            && newBoard.board[tempIndex][column] != currentNum)
                        break;
                    else if (newBoard.board[tempIndex][column] == currentNum) {
                        newBoard.board[row][column] *= 2;
                        newBoard.board[tempIndex][column] = 0;
                        newBoard.scores += (currentNum * 2);
                        break;
                    } else
                        tempIndex--;
                }
            }
            // Second pass: shift all the tiles to as bottom as possible.
            for (int row = CoreConstants.BOARD_DIMENSION - 2; row >= 0; row--) {
                if (newBoard.board[row][column] != 0) {
                    int tempIndex = row;
                    while (tempIndex < CoreConstants.BOARD_DIMENSION - 1
                            && newBoard.board[tempIndex + 1][column] == 0) {
                        Utility.swap(newBoard.board, column, tempIndex, tempIndex + 1);
                        tempIndex++;
                    }
                }
            }
        }
        // Having moved validly, place a new number (2 or 4) randomly on the board.
        if (isValidMove(newBoard))
            newBoard.addNumber();

        return newBoard;
    }


    /**
     * Determines if the game board actually moved after a move action. No matter
     * what happens, first try moving it to get a new board, then compare it to
     * the original one. If all elements of them are identical, the move is invalid
     * (no actual move happened); valid otherwise.
     * @param movedBoard the new board obtained by applying a move action to the
     *                   original one
     * @return true if the move is valid; false otherwise
     */
    private boolean isValidMove(Board movedBoard) {
        return movedBoard != null && !Arrays.deepEquals(board, movedBoard.board);
    }


    /**
     * Obtains a new board by moving the current game board to the given direction.
     * @param direction the given direction
     * @return the new board if the move is valid; null otherwise
     */
    public Board move(Directions direction) {
        Board movedBoard;
        switch (direction) {
            case LEFT:
                movedBoard = moveLeft();
                break;
            case RIGHT:
                movedBoard = moveRight();
                break;
            case UP:
                movedBoard = moveUp();
                break;
            case DOWN:
                movedBoard = moveDown();
                break;
            default:
                movedBoard = null;
        }
        return (isValidMove(movedBoard)) ? movedBoard : null;
    }


    /**
     * Determines the weight of a tile at the given location. The weight is
     * calculated as follows:
     *     <p>(a) Corner tiles: a constant value 'WEIGHT_CORNER' times the number
     *     contained in the tile.</p>
     *     <p>(b) Side tiles (except corner ones): a constant value 'WEIGHT_SIDE'
     *     times the number contained in
     *         the tile.</p>
     *     <p>(c) Other tiles: a constant value 'WEIGHT_OTHER' times the number
     *     contained in the tile.</p>
     * @param row the row index of the tile
     * @param column the column index of the tile
     * @return the weight of the given tile
     */
    private long getTileWeight(int row, int column) {
        if (row < 0 || row >= CoreConstants.BOARD_DIMENSION
                || column < 0 || column >= CoreConstants.BOARD_DIMENSION)
            return 0;

        int weight = 0;
        if ((row == 0 && column == 0) || (row == 0 && column == CoreConstants.BOARD_DIMENSION - 1)
                || (row == CoreConstants.BOARD_DIMENSION - 1 && column == 0)
                || (row == CoreConstants.BOARD_DIMENSION - 1 && column == CoreConstants.BOARD_DIMENSION - 1))
            weight += (board[row][column] * Weights.CORNER.getWeight());
        else if ((row == 0 && column > 0 && column < CoreConstants.BOARD_DIMENSION - 1)
                || (row == CoreConstants.BOARD_DIMENSION - 1 && column > 0 && column < CoreConstants.BOARD_DIMENSION - 1)
                || (row > 0 && row < CoreConstants.BOARD_DIMENSION - 1 && column == 0)
                || (row > 0 && row < CoreConstants.BOARD_DIMENSION - 1 && column == CoreConstants.BOARD_DIMENSION - 1))
            weight += (board[row][column] * Weights.SIDE.getWeight());
        else
            weight += (board[row][column] * Weights.OTHER.getWeight());
        return weight;
    }


    /**
     * Calculates the evaluation value, defined as the sum of following values,
     * of the current game board:
     *     <p>(a) the square of the scores of the board;</p>
     *     <p>(b) the square of the maximum number presented on the board;</p>
     *     <p>(c) a constant value 'WEIGHT_EMPTY' times the number of empty tiles;</p>
     *     <p>(d) the weights of all tiles.</p>
     * @param steps the steps taken up to now
     * @return the evaluation value of the board
     */
    public double evaluate(int steps) {
        double evaluationValue = 0;
        evaluationValue += (Math.pow(getScores(), 2) + Math.pow(getMaxTile(), 2)
                + getEmptyTileNumber() * Weights.EMPTY.getWeight());
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++)
            for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++)
                evaluationValue += getTileWeight(row, column);
        evaluationValue = (steps != 0) ? (evaluationValue / steps) : 0;
        return evaluationValue;
    }


    /**
     * Returns a formatted string which contains all the information of the board
     * and tiles, and it can be used later to output to the screen, a file, etc..
     * @return a string representation of the board information
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // The scores the player gets.
        builder.append("Scores: ");
        builder.append(scores);
        builder.append('\n');
        // A character array representing the top and bottom border of the board.
        // Its length should be equal to the board length times the tile length
        // plus the number of vertical bars.
        char[] horizontalBorder = new char[CoreConstants.BOARD_DIMENSION * (CoreConstants.TILE_LENGTH + 1) + 1];
        Arrays.fill(horizontalBorder, '-');
        // The top border.
        builder.append(horizontalBorder);
        builder.append('\n');
        // The board and all tiles.
        for (int row = 0; row < CoreConstants.BOARD_DIMENSION; row++) {
            builder.append('|');
            for (int column = 0; column < CoreConstants.BOARD_DIMENSION; column++) {
                String number = "";
                if (board[row][column] != 0) {
                    number += board[row][column];
                    builder.append(number);
                }
                // Fill the empty space with whitespace characters.
                for (int x = 0; x < CoreConstants.TILE_LENGTH - number.length(); x++)
                    builder.append(' ');
                builder.append('|');
            }
            builder.append('\n');
            // The horizontal border between each row and the bottom border.
            builder.append(horizontalBorder);
            builder.append('\n');
        }

        return builder.toString();
    }
}
