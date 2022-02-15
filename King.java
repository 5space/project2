package project2;

public class King extends ChessPiece {

	boolean hasMoved = false;

	public King(Player player) {
		super(player);
	}

	public String type() {
		return "King";
	}

	public boolean hasMoved() {
		return hasMoved;
	}
	
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}

		// Checks if the move is at most one square away
		//			if (player() == Player.WHITE) {
		//				if (move.toRow == 0 && move.toColumn == 4) {
		//					if (board[move.toRow][move.toColumn] == null) {
		//
		//					}
		//				}
		//			}
		if (Math.abs(move.toRow - move.fromRow) <= 1 && Math.abs(move.toColumn - move.fromColumn) <= 1) {
			return true;
		} else {
			// Checking for castling
			if (player() == Player.WHITE) {
				// Checks the king has not moved and is still in the starting position
				if (move.fromRow == 7 && move.fromColumn == 4 && !hasMoved) {
					// Checking if the left castling is clear and the rook is still in the starting position and has not moved
					if (move.toRow == 7 && move.toColumn == 2 && board[move.toRow][move.toColumn] == null &&
							board[move.toRow][move.toColumn + 1] == null && board[7][move.toColumn - 1] == null &&
							board[7][0] != null && board[7][0].type().equals("Rook") &&
							board[7][0].player() == Player.WHITE && !((Rook) board[7][0]).hasMoved()) {
						board[7][3] = board[7][0];
						board[7][0] = null;
						return true;
					}
					// Checking for the right castling
					if (move.toRow == 7 && move.toColumn == 6 && board[move.toRow][move.toColumn] == null &&
							board[move.toRow][move.toColumn - 1] == null && board[7][7] != null &&
							board[7][7].type().equals("Rook") && board[7][7].player() == Player.WHITE &&
							!((Rook) board[7][7]).hasMoved()) {
						board[7][5] = board[7][7];
						board[7][7] = null;
						return true;
					}
				}
			}
			if (player() == Player.BLACK) {
				if (move.fromRow == 0 && move.fromColumn == 4 && !hasMoved) {
					// Checking for the left castling
					if (move.toRow == 0 && move.toColumn == 2 && board[move.toRow][move.toColumn] == null &&
							board[move.toRow][move.toColumn + 1] == null && board[0][move.toColumn - 1] == null &&
							board[0][0] != null && board[0][0].type().equals("Rook") &&
							board[0][0].player() == Player.BLACK && !((Rook) board[0][0]).hasMoved()) {
						board[0][3] = board[0][0];
						board[0][0] = null;
						return true;
					}
					// Checking for the right castling
					if (move.toRow == 0 && move.toColumn == 6 && board[move.toRow][move.toColumn] == null &&
							board[move.toRow][move.toColumn - 1] == null && board[0][7] != null &&
							board[0][7].type().equals("Rook") && board[0][7].player() == Player.BLACK &&
							!((Rook) board[0][7]).hasMoved()) {
						board[0][5] = board[0][7];
						board[0][7] = null;
						return true;
					}
				}

			}
		}
		return false;
	}
}
