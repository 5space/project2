package project2;

public class Knight extends ChessPiece {

	/** Constructor*/
	public Knight(Player player) {
		super(player);
	}

	/** return Knight type as a string */
	public String type() {
		return "Knight";
	}

	/** check the knight move is valid or not */
	public boolean isValidMove(Move move, IChessPiece[][] board){
		if (!super.isValidMove(move, board)) {
			return false;
		}
		return (
				Math.abs(move.fromRow - move.toRow) == 2 &&
				Math.abs(move.fromColumn - move.toColumn) == 1) ||
				(Math.abs(move.fromRow - move.toRow) == 1 && Math.abs(move.fromColumn - move.toColumn) == 2);
	}
}
