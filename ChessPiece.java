package project2;

public abstract class ChessPiece implements IChessPiece {

	private Player owner;

	protected ChessPiece(Player player) {
		this.owner = player;
	}

	public abstract String type();

	public Player player() {
		return owner;
	}

	public boolean isValidMove(Move move, IChessPiece[][] board) {
		return (0 <= move.fromColumn && move.fromColumn < 8 && 0 <= move.fromRow && move.fromRow < 8) &&
				(0 <= move.toColumn && move.toColumn < 8 && 0 <= move.toRow && move.toRow < 8) &&
				(move.fromColumn != move.toColumn || move.fromRow != move.toRow) &&
				//(board[move.fromRow][move.fromColumn] == this) &&
				(board[move.toRow][move.toColumn] == null || board[move.toRow][move.toColumn].player() != player());
	}
}
