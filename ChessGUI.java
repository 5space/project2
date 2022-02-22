package project2;
/** Main for the chess Gui
 * creates a chess board and pieces
 * and enables you to play a game against AI
 * @author Logan, Chirs, Don
 * @version 2/22/2022
 */
import java.awt.Dimension;

import javax.swing.JFrame;

public class ChessGUI {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessPanel panel = new ChessPanel();
        frame.getContentPane().add(panel);

        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(800, 660));
        frame.pack();
        frame.setVisible(true);
    }
}
