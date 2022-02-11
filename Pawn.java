package project2;

public class Pawn extends ChessPiece {
	boolean hasMoved;

	public Pawn(Player player) {
		super(player);
	}

	public String type() {
		return "Pawn";
	}

	@Override
	// determines if the move is valid for a pawn piece
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}


		if (player() == Player.WHITE) {
			// Normal 1 move forward
			if (move.fromColumn == move.toColumn && move.fromRow == move.toRow + 1 && board[move.toRow][move.toColumn] == null) {
				hasMoved = true;
				return true;
			}
			// If the pawn hasn't moved yet, it can move 2 spaces forward
			if (!hasMoved && move.fromColumn == move.toColumn && move.fromRow == move.toRow + 2 && board[move.toRow][move.toColumn] == null) {
				hasMoved = true;
				return true;
			}
			// Can move diagonally forward if there is an enemy piece there
			if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
					(move.fromRow == move.toRow + 1) && board[move.toRow][move.toColumn].player() == Player.BLACK) {
				hasMoved = true;
				return true;
			}
		} else {
			// Normal 1 move forward
			if (move.fromColumn == move.toColumn && move.fromRow == move.toRow - 1 && board[move.toRow][move.toColumn] == null) {
				hasMoved = true;
				return true;
			}
			// If the pawn hasn't moved yet, it can move 2 spaces forward
			if (!hasMoved && move.fromColumn == move.toColumn && move.fromRow == move.toRow - 2 && board[move.toRow][move.toColumn] == null) {
				hasMoved = true;
				return true;
			}
			// Can move diagonally forward if there is an enemy piece there
			if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
					(move.fromRow == move.toRow - 1) && board[move.toRow][move.toColumn].player() == Player.WHITE) {
				hasMoved = true;
				return true;
			}
		}
		return false;
	}
}
