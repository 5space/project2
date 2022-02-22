package project2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessPanel extends JPanel {

    /** button */
    private JButton[][] board;
    private ChessModel model;

    /** image of the chess piece */
    private ImageIcon wRook;
    private ImageIcon wBishop;
    private ImageIcon wQueen;
    private ImageIcon wKing;
    private ImageIcon wPawn;
    private ImageIcon wKnight;
    private ImageIcon bRook;
    private ImageIcon bBishop;
    private ImageIcon bQueen;
    private ImageIcon bKing;
    private ImageIcon bPawn;
    private ImageIcon bKnight;

    /** coordination on the board */
    private boolean firstTurnFlag;
    private int fromRow;
    private int toRow;
    private int fromCol;
    private int toCol;

    /** when user click a button an action will be executed*/
    private listener listener;


    /** constructor */
    public ChessPanel() {
        model = new ChessModel();
        board = new JButton[model.numRows()][model.numColumns()];
        listener = new listener();
        createIcons();

        JPanel boardPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(model.numRows(), model.numColumns(), 1, 1));

        for (int r = 0; r < model.numRows(); r++) {
            for (int c = 0; c < model.numColumns(); c++) {
                if (model.pieceAt(r, c) == null) {
                    board[r][c] = new JButton("", null);
                    board[r][c].addActionListener(listener);
                } else if (model.pieceAt(r, c).player() == Player.WHITE)
                    placeWhitePieces(r, c);
                else if (model.pieceAt(r, c).player() == Player.BLACK)
                    placeBlackPieces(r, c);

                setBackGroundColor(r, c);
                boardPanel.add(board[r][c]);
            }
        }
        add(boardPanel, BorderLayout.WEST);
        boardPanel.setPreferredSize(new Dimension(600, 600));
        add(buttonPanel);
        firstTurnFlag = true;
    }

    /** paint the board with 2 colors*/
    private void setBackGroundColor(int r, int c) {
        if ((c % 2 == 1 && r % 2 == 0) || (c % 2 == 0 && r % 2 == 1)) {
            board[r][c].setBackground(Color.LIGHT_GRAY);
        } else if ((c % 2 == 0 && r % 2 == 0) || (c % 2 == 1 && r % 2 == 1)) {
            board[r][c].setBackground(Color.WHITE);
        }
    }

    /** create white pieces on the board */
    private void placeWhitePieces(int r, int c) {
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, wPawn);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, wRook);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, wKnight);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, wBishop);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, wQueen);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, wKing);
            board[r][c].addActionListener(listener);
        }
    }

    /** create black pieces on the board */
    private void placeBlackPieces(int r, int c) {
        if (model.pieceAt(r, c).type().equals("Pawn")) {
            board[r][c] = new JButton(null, bPawn);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Rook")) {
            board[r][c] = new JButton(null, bRook);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Knight")) {
            board[r][c] = new JButton(null, bKnight);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Bishop")) {
            board[r][c] = new JButton(null, bBishop);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("Queen")) {
            board[r][c] = new JButton(null, bQueen);
            board[r][c].addActionListener(listener);
        }
        if (model.pieceAt(r, c).type().equals("King")) {
            board[r][c] = new JButton(null, bKing);
            board[r][c].addActionListener(listener);
        }
    }

    /** get the image from file */
    private void createIcons() {
        // Sets the Image for white player pieces
        wRook = new ImageIcon("./icons/wRook.png");
        wBishop = new ImageIcon("./icons/wBishop.png");
        wQueen = new ImageIcon("./icons/wQueen.png");
        wKing = new ImageIcon("./icons/wKing.png");
        wPawn = new ImageIcon("./icons/wPawn.png");
        wKnight = new ImageIcon("./icons/wKnight.png");

        // Sets the Image for black player pieces
        bRook = new ImageIcon("./icons/bRook.png");
        bBishop = new ImageIcon("./icons/bBishop.png");
        bQueen = new ImageIcon("./icons/bQueen.png");
        bKing = new ImageIcon("./icons/bKing.png");
        bPawn = new ImageIcon("./icons/bPawn.png");
        bKnight = new ImageIcon("./icons/bKnight.png");
    }

    /** method that updates the board
     * @param selCol
     * @param selRow
     * @param pieceSelected
     */
    private void displayBoard(boolean pieceSelected, int selRow, int selCol) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                setBackGroundColor(r, c);
                if (pieceSelected) {
                    Move m = new Move(selRow, selCol, r, c);
                    if (model.isValidMove(m)) {
                        board[r][c].setBackground(Color.GRAY);
                    }
                }
                if (model.pieceAt(r, c) == null)
                    board[r][c].setIcon(null);
                else if (model.pieceAt(r, c).player() == Player.WHITE) {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(wPawn);
                    else if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(wRook);
                    else if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(wKnight);
                    else if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(wBishop);
                    else if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(wQueen);
                    else if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(wKing);
                } else if (model.pieceAt(r, c).player() == Player.BLACK) {
                    if (model.pieceAt(r, c).type().equals("Pawn"))
                        board[r][c].setIcon(bPawn);
                    else if (model.pieceAt(r, c).type().equals("Rook"))
                        board[r][c].setIcon(bRook);
                    else if (model.pieceAt(r, c).type().equals("Knight"))
                        board[r][c].setIcon(bKnight);
                    else if (model.pieceAt(r, c).type().equals("Bishop"))
                        board[r][c].setIcon(bBishop);
                    else if (model.pieceAt(r, c).type().equals("Queen"))
                        board[r][c].setIcon(bQueen);
                    else if (model.pieceAt(r, c).type().equals("King"))
                        board[r][c].setIcon(bKing);
                }
            }
        }
        repaint();
    }

    /** inner class that represents action listener for buttons*/
    private class listener implements ActionListener {
        // perform action when click
        public void actionPerformed(ActionEvent event) {
            for (int r = 0; r < model.numRows(); r++) {
                for (int c = 0; c < model.numColumns(); c++) {
                    if (board[r][c] == event.getSource()) {
                        if (firstTurnFlag) {
                            fromRow = r;
                            fromCol = c;
                            firstTurnFlag = false;
                            displayBoard(true, r, c);
                        } else {
                            toRow = r;
                            toCol = c;
                            firstTurnFlag = true;
                            Move m = new Move(fromRow, fromCol, toRow, toCol);
                            if (model.isValidMove(m)) {
                                model.move(m);
                                // En Passant reset
                                for (int row = 0; row < model.numRows(); row++) {
                                    for (int col = 0; col < model.numColumns(); col++) {
                                        if (model.pieceAt(row, col) != null &&
                                            model.pieceAt(row, col).type().equals("Pawn"))
                                        {
                                            ((Pawn) model.pieceAt(row, col)).decEnPassantTimer();
                                        }
                                    }
                                }
                            }
                            // After the 2nd click (move), even if move failed
                            displayBoard(false, 0, 0);
                        }
                    }
                }
            }
        }
    }
}
