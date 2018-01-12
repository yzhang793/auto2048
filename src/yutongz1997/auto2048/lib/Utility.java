package yutongz1997.auto2048.lib;


/**
 * The utility class that provides some useful helper methods.
 * @author Yutong Zhang
 */
public final class Utility {
    // Make this class not instantiable by using a private constructor.
    private Utility() {}


    /**
     * Swaps the elements at the i-th position and j-th position of an array.
     * @param array the array for swapping
     * @param i the index of the first element
     * @param j the index of the second element
     */
    public static void swap(int[] array, int i, int j) {
        // Invalid parameters.
        if (array == null || i < 0 || i >= array.length || j < 0 || j >= array.length)
            return;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    /**
     * Swaps the elements at the i-th row and j-th row of a specified column of
     * a two dimensional array.
     * @param array the two dimensional array for swapping
     * @param column the specified column
     * @param i the row index of the first element
     * @param j the row index of the second element
     */
    public static void swap(int[][] array, int column, int i, int j) {
        // Invalid parameters.
        if (array == null || i < 0 || i >= array[column].length
                || j < 0 || j >= array[column].length)
            return;
        int temp = array[i][column];
        array[i][column] = array[j][column];
        array[j][column] = temp;
    }


    /**
     * Converts the number contained in a tile to the string representation of
     * the tile.
     * @param number the number needed to convert
     * @return the string representation of the tile
     */
    public static String tileToString(int number) {
        if (number == 0)
            return "";
        return String.valueOf(number);
    }
}
