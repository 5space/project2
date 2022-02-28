package project2;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

public class ChessTest {
    /** chess model for testing */
    ChessModel demo = new ChessModel();

    /** Test if the chess pieces are in correct position */
    @Test
    public void testRookPosition(){
        assertEquals(demo.pieceAt(0,0).type(),"Rook");
        assertEquals(demo.pieceAt(0,7).type(),"Rook");
        assertEquals(demo.pieceAt(7,7).type(),"Rook");
        assertEquals(demo.pieceAt(7,0).type(),"Rook");
    }

    @Test
    public void testBishopPosition(){
        assertEquals(demo.pieceAt(7,5).type(),"Bishop");
        assertEquals(demo.pieceAt(7,2).type(),"Bishop");
        assertEquals(demo.pieceAt(0,2).type(),"Bishop");
        assertEquals(demo.pieceAt(0,5).type(),"Bishop");
    }

    @Test
    public void testKnightPosition(){
        assertEquals(demo.pieceAt(7,1).type(),"Knight");
        assertEquals(demo.pieceAt(7,6).type(),"Knight");
        assertEquals(demo.pieceAt(0,1).type(),"Knight");
        assertEquals(demo.pieceAt(0,6).type(),"Knight");
    }

    @Test
    public void testQueenPosition(){
        assertEquals(demo.pieceAt(7,3).type(),"Queen");
        assertEquals(demo.pieceAt(0,3).type(),"Queen");
    }

    @Test
    public void testKingPosition(){
        assertEquals(demo.pieceAt(7,4).type(),"King");
        assertEquals(demo.pieceAt(0,4).type(),"King");
    }

    @Test
    public void testPawn(){
        for (int i = 0; i < 8; i++) {
            assertEquals(demo.pieceAt(6,i).type(),"Pawn");
        }
        for (int i = 0; i < 8; i++) {
            assertEquals(demo.pieceAt(1,i).type(),"Pawn" );
        }
    }

    @Test
    public void testPawnMovement(){
        Move firstStrike = new Move(6,0,4,0);
        assertTrue(demo.isValidMove(firstStrike));
        Move alternateFirstStrike = new Move(6,0,5,0);
        assertTrue(demo.isValidMove(alternateFirstStrike));
    }

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
        cleanBoard.setPiece(5,5, demoRookWhite );
        cleanBoard.setPiece(0,0, demoRookBlack );
        // valid move
        assertTrue(cleanBoard.isValidMove(new Move(5,5,0,5)));
        // invalid move
        assertFalse(cleanBoard.isValidMove(new Move(0,0,7,7)));
    }
}
