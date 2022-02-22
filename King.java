package project2;

public class King extends ChessPiece {

	boolean hasMoved = false;

	/** constructor utilizes super constructor*/
	public King(Player player) {
		super(player);
	}

	/** return "King" type */
	public String type() {
		return "King";
	}

	/** return moved status */
	public boolean hasMoved() {
		return hasMoved;
	}

	/** set moved status to true */
	public void setHasMoved() {
		hasMoved = true;
	}

	/** check if king movement is valid
	 *@param move from the move class
	 * @param board board from the cless piece class
	 * @return true if valid move false if invalid chess move
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}
		if (Math.abs(move.toRow - move.fromRow) <= 1 && Math.abs(move.toColumn - move.fromColumn) <= 1) {
			return true;
		}
		else {
			// Checking for castling
			if (player() == Player.WHITE) {
				// Checks the king has not moved and is still in the starting position
				if (move.fromRow == 7 && move.fromColumn == 4 && !hasMoved) {
					// Checking if left castling is clear and rook is still in the starting position and has not moved
					if (move.toRow == 7 && move.toColumn == 2 && board[move.toRow][move.toColumn] == null &&
							board[move.toRow][move.toColumn + 1] == null && board[7][move.toColumn - 1] == null &&
							board[7][0] != null && board[7][0].type().equals("Rook") &&
							board[7][0].player() == Player.WHITE && !((Rook) board[7][0]).hasMoved()) {
						return true;
					}
					// Checking for the right castling
					if (move.toRow == 7 && move.toColumn == 6 && board[move.toRow][move.toColumn] == null &&
							board[move.toRow][move.toColumn - 1] == null && board[7][7] != null &&
							board[7][7].type().equals("Rook") && board[7][7].player() == Player.WHITE &&
							!((Rook) board[7][7]).hasMoved()) {
						return true;
					}
				}
			}
			// Checking for castling of black player
			if (player() == Player.BLACK) {
				if (move.fromRow == 0 && move.fromColumn == 4 && !hasMoved) {
					// Checking for the left castling
					if (move.toRow == 0 && move.toColumn == 2 && board[move.toRow][move.toColumn] == null &&
							board[move.toRow][move.toColumn + 1] == null && board[0][move.toColumn - 1] == null &&
							board[0][0] != null && board[0][0].type().equals("Rook") &&
							board[0][0].player() == Player.BLACK && !((Rook) board[0][0]).hasMoved()) {
						return true;
					}
					// Checking for the right castling
					if (move.toRow == 0 && move.toColumn == 6 && board[move.toRow][move.toColumn] == null &&
							board[move.toRow][move.toColumn - 1] == null && board[0][7] != null &&
							board[0][7].type().equals("Rook") && board[0][7].player() == Player.BLACK &&
							!((Rook) board[0][7]).hasMoved()) {
						return true;
					}
				}

			}
		}
		return false;
	}
}
