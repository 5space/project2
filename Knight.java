package project2;

public class Knight extends ChessPiece {

	/** Constructor*/
	public Knight(Player player) {
		super(player);
	}

	public String type() {
		return "Knight";
	}

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
