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
            assertEquals(demo.pieceAt(1,i).type(),"Pawn"gitq );
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
    public void testRook(){
        ChessModel demoMove = new ChessModel();
        demoMove.setPiece(6, 2, new Rook(Player.BLACK));
        Move rookMove = new Move(6,2,2,2);
        assertTrue(demoMove.isValidMove(rookMove));
    }
}
