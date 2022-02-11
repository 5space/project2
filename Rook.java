package project2;

public class Rook extends ChessPiece {

	public Rook(Player player) {
		super(player);
	}

	public String type() {
		return "Rook";
	}

	// determines if the move is valid for a rook piece
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		// If the move is the same spot, it is not valid
		if (move.fromColumn == move.toColumn && move.fromRow == move.toRow) {
			return false;
		}

		// If the move is in a different column AND row, it is not valid
		if (move.fromColumn != move.toColumn && move.fromRow != move.toRow) {
			return false;
		}

		// Moving vertically
		if (move.fromColumn == move.toColumn) {
			// Moving down
			if (move.fromRow < move.toRow) {
				// Checks all spots between from and to (exclusive)
				for (int i = move.fromRow + 1; i < move.toRow; i++) {
					// If spot is occupied
					if (board[i][move.fromColumn] != null) {
						return false;
					}
				}
			}
			// Moving up
			else {
				// Checks all spots between from and to (exclusive)
				for (int i = move.fromRow - 1; i > move.toRow; i--) {
					// If spot is occupied
					if (board[i][move.fromColumn] != null) {
						return false;
					}
				}
			}
		}
		// Moving horizontally
		else {
			// Moving right
			if (move.fromColumn < move.toColumn) {
				// Checks all spots between from and to (exclusive)
				for (int i = move.fromColumn + 1; i < move.toColumn; i++) {
					// If spot is occupied
					if (board[move.fromRow][i] != null) {
						return false;
					}
				}
			}
			// Moving left
			else {
				// Checks all spots between from and to (exclusive)
				for (int i = move.fromColumn - 1; i > move.toColumn; i--) {
					// If spot is occupied
					if (board[move.fromRow][i] != null) {
						return false;
					}
				}
			}
		}
		// If there's no piece in the way, and the destination isn't the same player's
		return board[move.toRow][move.toColumn] == null || board[move.toRow][move.toColumn].player() != player();
	}
}
