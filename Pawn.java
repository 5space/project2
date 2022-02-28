package project2;
/**
 * Creates a chess piece called a pawn
 * with certain moves being valid
 * based on the chess rules
 * @author Logan, Chirs, Don
 * @version 2/28/2022
 */
public class Pawn extends ChessPiece {
	boolean hasMoved;
	/** how many step the pawn can move */
	boolean enPassantVulnerable = false;

	/** constructor
	 * @param player is the pawn a player pawn
	 */
	public Pawn(Player player) {
		super(player);
	}

	/** return the pawn type (black or white)*/
	public String type() {
		return "Pawn";
	}

	/** check if the piece has moved */
	public boolean hasMoved() {
		return hasMoved;
	}

	/** set moved status */
	public void setHasMoved(boolean bool) {
		hasMoved = bool;
	}

	/** set the enPassant move for chess */
	public boolean isEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	/** set en passant at the beginning */
	public void setEnPassantVulnerable(boolean bool) {
		enPassantVulnerable = bool;
	}

	@Override
	/**********************************************************************
	 *  determines if the move is valid for a pawn piece
	 * @param move from the move class
	 * @param board a chess board
	 * @return true if the selected pawn move is valid based on chess rules
	 * */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		if (!super.isValidMove(move, board)) {
			return false;
		}

		if (player() == Player.WHITE) {
			// Normal 1 move forward
			if (move.fromColumn == move.toColumn
					&& move.fromRow == move.toRow + 1
					&& board[move.toRow][move.toColumn] == null) {
				return true;
			}
			// If the pawn hasn't moved yet, it can move 2 spaces forward
			if (!hasMoved && move.fromColumn == move.toColumn
					&& move.fromRow == move.toRow + 2 &&
					board[move.toRow + 1][move.toColumn] == null
					&& board[move.toRow][move.toColumn] == null) {
				return true;
			}
			// Moving diagonally (normal capture or en passant)
			if ((move.fromColumn == move.toColumn + 1
					|| move.fromColumn == move.toColumn - 1) &&
					move.fromRow == move.toRow + 1) {
				// Normal piece capture
				if (board[move.toRow][move.toColumn] != null &&
						board[move.toRow][move.toColumn].player()
								== Player.BLACK) {
					return true;
				}
				// En passant
				else return board[move.toRow + 1][move.toColumn]
						!= null &&
						board[move.toRow + 1]
								[move.toColumn].type().equals("Pawn") &&
						board[move.toRow + 1]
								[move.toColumn].player() == Player.BLACK &&
						((Pawn) board[move.toRow + 1]
								[move.toColumn]).isEnPassantVulnerable();
			}
		} else {  // player() == Player.BLACK
			// Normal 1 move forward
			if (move.fromColumn == move.toColumn && move.fromRow
					== move.toRow - 1
					&& board[move.toRow][move.toColumn] == null) {
				return true;
			}
			// If the pawn hasn't moved yet, it can move 2 spaces forward
			if (!hasMoved && move.fromColumn
					== move.toColumn && move.fromRow == move.toRow - 2 &&
					board[move.toRow - 1][move.toColumn] == null &&
					board[move.toRow][move.toColumn] == null) {
				return true;
			}
			// Moving diagonally (normal capture or en passant)
			if ((move.fromColumn == move.toColumn + 1
					|| move.fromColumn == move.toColumn - 1) &&
					move.fromRow == move.toRow - 1) {
				// Normal piece capture
				if (board[move.toRow][move.toColumn] != null &&
						board[move.toRow][move.toColumn].player()
								== Player.WHITE) {
					return true;
				}
				// En passant
				else return board[move.toRow - 1][move.toColumn]
						!= null &&
						board[move.toRow - 1]
								[move.toColumn].type().equals("Pawn") &&
						board[move.toRow - 1]
								[move.toColumn].player() == Player.WHITE &&
						((Pawn) board[move.toRow - 1]
								[move.toColumn]).isEnPassantVulnerable();
			}
		}
		return false;
	}
}
