package project2;

/** the basic of chess piece */
public abstract class ChessPiece implements IChessPiece {

	/** white or black side*/
	private Player owner;

	/** constructor */
	protected ChessPiece(Player player) {
		this.owner = player;
	}

	/** return type of the piece */
	public abstract String type();

	/** get side of the piece */
	public Player player() {
		return owner;
	}

	/** check if the move is valid
	 * @param move the movement plan to execute
	 * @param board the current board
	 */
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		return (
				0 <= move.fromColumn && move.fromColumn < 8 &&
				0 <= move.fromRow && move.fromRow < 8) &&
				(0 <= move.toColumn && move.toColumn < 8 && 0 <= move.toRow && move.toRow < 8) &&
				(move.fromColumn != move.toColumn || move.fromRow != move.toRow) &&
				//(board[move.fromRow][move.fromColumn] == this) &&
				(board[move.toRow][move.toColumn] == null || board[move.toRow][move.toColumn].player() != player());
	}
}
