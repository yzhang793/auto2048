package yutongz1997.auto2048;

import yutongz1997.auto2048.gui.GameFrame;

import java.awt.EventQueue;


/**
 * The main class.
 * @author Yutong Zhang
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                GameFrame gameWindow = new GameFrame();
                gameWindow.setVisible(true);
        });
    }
}
