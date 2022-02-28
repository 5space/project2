package project2;

/**
 * A pawn chess piece, which can check if a move is valid based on
 * the rules of the game.
 *
 * @author Logan Nommensen (Cybersecurity),
 * Christopher Lamus (Computer Engineering), Don Nguyen (Computer Science)
 *
 * @version Winter 2022
 */

public class Pawn extends ChessPiece {
	/** If the Pawn has moved yet */
	boolean hasMoved;

	/** If the Pawn is currently vulnerable to an en passant */
	boolean enPassantVulnerable = false;

	/******************************************************************
	 * Constructor for the pawn
	 * @param player the player that the pawn belongs to
	 */
	public Pawn(Player player) {
		super(player);
	}

	/******************************************************************
	 * Returns the type of the piece
	 *
	 * @return The piece type ("Pawn")
	 */
	public String type() {
		return "Pawn";
	}

	/******************************************************************
	 * Check if the piece has moved
	 *
	 * @return if the piece has moved
	 */
	public boolean hasMoved() {
		return hasMoved;
	}

	/******************************************************************
	 * Set if the pawn has moved
	 *
	 * @param bool if the pawn has moved
	 */
	public void setHasMoved(boolean bool) {
		hasMoved = bool;
	}

	/******************************************************************
	 * Returns if the piece is vulnerable to an en passant
	 *
	 * @return if the piece is vulnerable to an en passant
	 */
	public boolean isEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	/******************************************************************
	 * Set en passant vulnerability
	 *
	 * @param bool if the piece is vulnerable to an en passant
	 */
	public void setEnPassantVulnerable(boolean bool) {
		enPassantVulnerable = bool;
	}

	/**********************************************************************
	 * Determines if the move is valid for a Pawn
	 *
	 * @param move from the move class
	 * @param board the chess board the piece is on
	 *
	 * @return if the selected pawn move is valid based on chess rules
	 */
	@Override
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
