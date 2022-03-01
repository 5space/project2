package project2;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

public class ChessTest {
    /** chess model for testing */
    ChessModel demo = new ChessModel();

    /** Test if the Rooks are in correct position */
    @Test
    public void testRookPosition(){
        assertEquals(demo.pieceAt(0,0).type(),"Rook");
        assertEquals(demo.pieceAt(0,7).type(),"Rook");
        assertEquals(demo.pieceAt(7,7).type(),"Rook");
        assertEquals(demo.pieceAt(7,0).type(),"Rook");
    }

    /** Test if the Bishops are in correct position */
    @Test
    public void testBishopPosition(){
        assertEquals(demo.pieceAt(7,5).type(),"Bishop");
        assertEquals(demo.pieceAt(7,2).type(),"Bishop");
        assertEquals(demo.pieceAt(0,2).type(),"Bishop");
        assertEquals(demo.pieceAt(0,5).type(),"Bishop");
    }

    /** Test if the Knights are in correct position */
    @Test
    public void testKnightPosition(){
        assertEquals(demo.pieceAt(7,1).type(),"Knight");
        assertEquals(demo.pieceAt(7,6).type(),"Knight");
        assertEquals(demo.pieceAt(0,1).type(),"Knight");
        assertEquals(demo.pieceAt(0,6).type(),"Knight");
    }

    /** Test if the Queens are in correct position */
    @Test
    public void testQueenPosition(){
        assertEquals(demo.pieceAt(7,3).type(),"Queen");
        assertEquals(demo.pieceAt(0,3).type(),"Queen");
    }

    /** Test if the Kings are in correct position */
    @Test
    public void testKingPosition(){
        assertEquals(demo.pieceAt(7,4).type(),"King");
        assertEquals(demo.pieceAt(0,4).type(),"King");
    }

    /** Test if the Pawns are in correct position */
    @Test
    public void testPawnPosition(){
        for (int i = 0; i < 8; i++) {
            assertEquals(demo.pieceAt(6,i).type(),"Pawn");
        }
        for (int i = 0; i < 8; i++) {
            assertEquals(demo.pieceAt(1,i).type(),"Pawn" );
        }
    }

    /** Test Pawns can move one or two spaces on first move */
    @Test
    public void testPawnMovement(){
        Move firstStrike = new Move(6,0,4,0);
        assertTrue(demo.isValidMove(firstStrike));
        Move alternateFirstStrike = new Move(6,0,5,0);
        assertTrue(demo.isValidMove(alternateFirstStrike));
    }

    /** Tests Rook movement */
    @Test
    public void testRookMovement(){
        ChessModel cleanBoard = new ChessModel();
        // set up a clear board for rook to move freely
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                cleanBoard.setPiece(r, c, null);
            }
        }
        Rook demoRookWhite = new Rook(Player.WHITE);
        Rook demoRookBlack = new Rook(Player.BLACK);
        cleanBoard.setPiece(5,5, demoRookWhite);
        cleanBoard.setPiece(0,0, demoRookBlack);
        // valid move
        assertTrue(cleanBoard.isValidMove(new Move(5,5,0,5)));
        // invalid move
        assertFalse(cleanBoard.isValidMove(new Move(0,0,7,7)));
        Pawn demoPawnWhite = new Pawn(Player.WHITE);
        cleanBoard.setPiece(5,6, demoPawnWhite);
        assertFalse(cleanBoard.isValidMove(new Move(5,5,5,7)));
    }

    /** Tests King movement */
    @Test
    public void testKingMovement(){
        ChessModel cleanBoard = new ChessModel();
        // set up a clear board for king to move freely
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                cleanBoard.setPiece(r, c, null);
            }
        }
        King demoKingWhite = new King(Player.WHITE);
        cleanBoard.setPiece(5,5, demoKingWhite);
        // valid moves
        assertTrue(cleanBoard.isValidMove(new Move(5,5,6,5)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,4,5)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,6,6)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,4,6)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,5,6)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,5,4)));

        // invalid moves
        assertFalse(cleanBoard.isValidMove(new Move(0,0,7,7)));
        assertFalse(cleanBoard.isValidMove(new Move(5,5,5,7)));
        assertFalse(cleanBoard.isValidMove(new Move(5,5,5,3)));
    }

    /** Tests Queen movement */
    @Test
    public void testQueenMovement() {
        ChessModel cleanBoard = new ChessModel();
        // set up a clear board for queen to move freely
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                cleanBoard.setPiece(r, c, null);
            }
        }
        Queen demoQueenWhite = new Queen(Player.WHITE);
        cleanBoard.setPiece(5,5, demoQueenWhite);
        // valid moves
        assertTrue(cleanBoard.isValidMove(new Move(5,5,0,5)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,5,0)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,0,0)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,7,7)));

        // invalid moves
        Pawn demoPawnWhite = new Pawn(Player.WHITE);
        cleanBoard.setPiece(5,6, demoPawnWhite);
        assertFalse(cleanBoard.isValidMove(new Move(5,5,5,7)));
    }

    /** Tests Knight movement */
    @Test
    public void testKnightMovement() {
        ChessModel cleanBoard = new ChessModel();
        // set up a clear board for knight to move freely
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                cleanBoard.setPiece(r, c, null);
            }
        }
        Knight demoKingWhite = new Knight(Player.WHITE);
        cleanBoard.setPiece(5,5, demoKingWhite);
        // valid moves
        assertTrue(cleanBoard.isValidMove(new Move(5,5,6,7)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,7,6)));

        // invalid moves
        assertFalse(cleanBoard.isValidMove(new Move(5,5,0,0)));
        assertFalse(cleanBoard.isValidMove(new Move(5,5,5,5)));
    }

    /** Tests Bishop movement */
    @Test
    public void testBishopMovement() {
        ChessModel cleanBoard = new ChessModel();
        // set up a clear board for bishop to move freely
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                cleanBoard.setPiece(r, c, null);
            }
        }
        Bishop demoBishopWhite = new Bishop(Player.WHITE);
        cleanBoard.setPiece(5,5, demoBishopWhite);
        // valid moves
        assertTrue(cleanBoard.isValidMove(new Move(5,5,0,0)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,7,7)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,6,6)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,6,4)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,4,6)));
        assertTrue(cleanBoard.isValidMove(new Move(5,5,4,4)));

        // invalid moves
        assertFalse(cleanBoard.isValidMove(new Move(5,5,0,7)));
        assertFalse(cleanBoard.isValidMove(new Move(5,5,5,5)));
        Pawn demoPawnWhite = new Pawn(Player.WHITE);
        cleanBoard.setPiece(6,6, demoPawnWhite);
        assertFalse(cleanBoard.isValidMove(new Move(5,5,7,7)));
    }
}
