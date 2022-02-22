package project2;

public class Queen extends ChessPiece {

	/** constructor utilize super constructor*/
	public Queen(Player player) {
		super(player);
	}

	/** return type of Queen piece*/
	public String type() {
		return "Queen";
	}

	/** check if queen movement is valid
	 * Queen has ability to move like rook and bishop so combine those two check methods*/
	public boolean isValidMove(Move move, IChessPiece[][] board) {
		Bishop move1 = new Bishop(board[move.fromRow][move.fromColumn].player());
		Rook move2 = new Rook(board[move.fromRow][move.fromColumn].player());
		return (move1.isValidMove(move, board) || move2.isValidMove(move, board));
	}
}
