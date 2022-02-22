package project2;

public class Rook extends ChessPiece {
	/** check if the rook has moved for castling */
	boolean hasMoved = false;

	/** constructor
	 * @param player
	 * @return super class
	 */
	public Rook(Player player) {
		super(player);
	}

	/** return type as a string */
	public String type() {
		return "Rook";
	}

	/** return move status */
	public boolean hasMoved() {
		return hasMoved;
	}

	/** set moving status */
	public void setHasMoved() {
		hasMoved = true;
	}

	/** check if the movement is valid for a rook piece */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (!super.isValidMove(move, board)) {
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

		// If there's no piece in the way
		return true;
	}
}
