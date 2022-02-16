package project2;

public class Bishop extends ChessPiece {

	public Bishop(Player player) {
		super(player);
	}

	public String type() {
		return "Bishop";
	}

	/**
	 * Check if selected move is valid according to chess rules
	 * @param move
	 * @param board
	 *
	 * @return true/false
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}

		/** Checks that the move is diagonal
		 * As bishop movement as a square diagonal conjecture
		 * Check if row and column has same length -> valid
		 */
		if (Math.abs(move.toRow - move.fromRow) != Math.abs(move.toColumn - move.fromColumn)) {
			return false;
		}

		// Diagonal check up left
		if (move.toRow < move.fromRow && move.toColumn < move.fromColumn) {
			for (int i = 1; i < (move.fromRow - move.toRow); i++) {
				if (board[move.fromRow - i][move.fromColumn - i] != null) {
					return false;
				}
			}
		}
		// Diagonal check up right
		else if (move.toRow < move.fromRow && move.toColumn > move.fromColumn) {
			for (int i = 1; i < (move.fromRow - move.toRow); i++) {
				if (board[move.fromRow - i][move.fromColumn + i] != null) {
					return false;
				}
			}
		}
		// Diagonal check down left
		else if (move.toRow > move.fromRow && move.toColumn < move.fromColumn) {
			for (int i = 1; i < (move.toRow - move.fromRow); i++) {
				if (board[move.fromRow + i][move.fromColumn - i] != null) {
					return false;
				}
			}
		}
		// Diagonal check down right
		else if (move.toRow > move.fromRow && move.toColumn > move.fromColumn) {
			for (int i = 1; i < (move.toRow - move.fromRow); i++) {
				if (board[move.fromRow + i][move.fromColumn + i] != null) {
					return false;
				}
			}
		}

		/**
		 * If there's no piece in the way
		 * If it is not player piece on the (toRow,toColumn) -> take down
		 * If*/
		return true;

	}
}
