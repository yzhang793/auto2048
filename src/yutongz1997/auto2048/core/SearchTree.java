package yutongz1997.auto2048.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import yutongz1997.auto2048.lib.CoreConstants;
import yutongz1997.auto2048.lib.CoreConstants.Directions;


/**
 * The class of the underlying support for the auto player of this game.
 * @author Yutong Zhang
 */
public class SearchTree {
    // The root node of the search tree.
    private SearchTreeNode root;
    // An array list used to store all nodes in the deepest level.
    private ArrayList<SearchTreeNode> lastLevelNodes;


    /**
     * Constructs a search tree object based on the current game board.
     * @param gameBoard the current game board
     * @param maximumLevel the maximum level / depth of the search tree
     */
    public SearchTree(Board gameBoard, int maximumLevel) {
        root = new SearchTreeNode(gameBoard);
        lastLevelNodes = new ArrayList<>();
        // Constructs the search tree imitating the procedure of the breadth
        // first traversal (using a queue).
        Queue<SearchTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            SearchTreeNode current = queue.peek();
            if (current.depth < maximumLevel) {
                queue.poll();
                current.createChildren();
                for (Directions direction : Directions.values()) {
                    int directionIndex = direction.getDirectionIndex();
                    if (current.children[directionIndex] != null)
                        queue.offer(current.children[directionIndex]);
                }
            } else {
                for (int count = 0; count < lastLevelNodes.size(); count++)
                    lastLevelNodes.add(queue.poll());
                queue.clear();
            }
        }
        // This is the situation when the maximum possible height of the search
        // tree is smaller than the given maximum level. It happens during the
        // very last steps of the game.
        if (lastLevelNodes.isEmpty())
            collectLastLevelNodes(maximumLevel);
    }


    /**
     * Recursively calculates the height of the search tree, i.e. the maximum
     * length of a path from the root to a leaf node.
     * @param root the root node
     * @return the height of the search tree
     */
    private int getHeight(SearchTreeNode root) {
        if (root == null || root.children == null)
            return 0;
        else {
            boolean isLeaf = true;
            for (Directions direction : Directions.values())
                // If all of the children of a node are null, this node is a leaf.
                if (root.children[direction.getDirectionIndex()] != null) {
                    isLeaf = false;
                    break;
                }
            if (!isLeaf) {
                int height = 0;
                for (Directions direction : Directions.values())
                    height = Math.max(height,
                            getHeight(root.children[direction.getDirectionIndex()]));
                return 1 + height;
            } else
                return 0;
        }
    }


    /**
     * Adds all the nodes from the calculated maximum possible level of the search
     * tree (but not exceeds the given maximum level). Again this uses the idea of
     * the breadth first traversal.
     * @param maximumLevel the maximum level of nodes to be collected.
     */
    private void collectLastLevelNodes(int maximumLevel) {
        int level = Math.min(getHeight(root), maximumLevel);
        Queue<SearchTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            SearchTreeNode current = queue.poll();
            if (current.depth == level) {
                lastLevelNodes.add(current);
                continue;
            }
            for (Directions direction : Directions.values()) {
                int directionIndex = direction.getDirectionIndex();
                if (current.children[directionIndex] != null)
                    queue.offer(current.children[directionIndex]);
            }
        }
    }


    /**
     * A helper method that returns the next step's game board by backtracking
     * the parents of a deepest level node level by level.
     * @param lastLevelNode a node in the deepest level of the search tree
     * @return the next step's game board
     */
    private Board nextStep(SearchTreeNode lastLevelNode) {
        SearchTreeNode nextStepNode = lastLevelNode;
        while (nextStepNode.depth != 1)
            nextStepNode = nextStepNode.parent;
        return nextStepNode.gameBoard;
    }


    /**
     * Iterates through the array list of last level nodes, finds the one with the
     * maximum evaluation and returns the next step's game board using the helper
     * method above.
     * @param steps the total steps of the game up to now (which is used to compute
     *              the evaluation value of a board)
     * @return the next step's game board
     */
    public Board nextStep(int steps) {
        double maxEvaluationValue = 0;
        int indexMax = 0;

        for (int index = 0; index < lastLevelNodes.size(); index++) {
            double evaluationValue = lastLevelNodes.get(index).gameBoard.evaluate(steps);
            if (evaluationValue > maxEvaluationValue) {
                maxEvaluationValue = evaluationValue;
                indexMax = index;
            }
        }
        return nextStep(lastLevelNodes.get(indexMax));
    }


    /**
     * The inner class containing the structure of a node of the search tree.
     */
    private class SearchTreeNode {
        private SearchTreeNode parent;
        private SearchTreeNode[] children;
        private int depth;
        private Board gameBoard;


        /**
         * Construct a search tree node object.
         * @param gameBoard the current game board
         */
        SearchTreeNode(Board gameBoard) {
            depth = 0;
            this.gameBoard = gameBoard;
        }


        /**
         * Creates all of the children's nodes of the current node.
         */
        void createChildren() {
            children = new SearchTreeNode[CoreConstants.DIRECTION_NUMBER];
            for (Directions direction : Directions.values()) {
                Board movedBoard = gameBoard.move(direction);
                // If the current board is not movable in a given direction, no
                // child representing this direction will be built, which completes
                // the pruning process during tree construction.
                if (movedBoard != null) {
                    int directionIndex = direction.getDirectionIndex();
                    children[directionIndex] = new SearchTreeNode(movedBoard);
                    children[directionIndex].parent = this;
                    children[directionIndex].depth = depth + 1;
                }
            }
        }
    }
}
