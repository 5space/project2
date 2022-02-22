package project2;

public class ChessModel implements IChessModel {
    private IChessPiece[][] board;
	private Player player;


	/** constructor for the chess board */
	public ChessModel() {
		/** chess board size is 8x8 */
		board = new IChessPiece[8][8];

		/** white player go first (USER)*/
		player = Player.WHITE;

        board[7][0] = new Rook(Player.WHITE);
        board[7][1] = new Knight(Player.WHITE);
        board[7][2] = new Bishop(Player.WHITE);
        board[7][3] = new Queen(Player.WHITE);
        board[7][4] = new King(Player.WHITE);
        board[7][5] = new Bishop(Player.WHITE);
        board[7][6] = new Knight(Player.WHITE);
        board[7][7] = new Rook(Player.WHITE);
		for (int i = 0; i < 8; i++) {
			board[6][i] = new Pawn(Player.WHITE);
		}

		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight (Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(Player.BLACK);
		}
	}

	/** check if the game is done*/
	public boolean isComplete() {
		boolean valid = false;
		return valid;
	}

	/** check if movement on this board is valid
	 * @param move from Move class
	 * @return true if move is valid false if invalid
	 */
	public boolean isValidMove(Move move) {
		if (board[move.fromRow][move.fromColumn] != null)
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board))
                return true;
		return false;
	}

	/** store the movement coordination to evaluate and execute */
	public void move(Move move) {
		if (board[move.fromRow][move.fromColumn].type().equals("Pawn")) {
			((Pawn) board[move.fromRow][move.fromColumn]).setHasMoved();
			if (board[move.fromRow][move.fromColumn].player() == Player.WHITE) {
				// Two steps forward
				if (move.fromRow == move.toRow + 2) {
					((Pawn) board[move.fromRow][move.fromColumn]).setEnPassantVulnerable(true);
				}

				// Checks for en passant
				if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
						move.fromRow == move.toRow + 1 && board[move.toRow][move.toColumn] == null) {
					board[move.toRow + 1][move.toColumn] = null;
				}
			} else {  // player is BLACK
				// Two steps forward
				if (move.fromRow == move.toRow - 2) {
					((Pawn) board[move.fromRow][move.fromColumn]).setEnPassantVulnerable(true);
				}

				// Checks for en passant
				if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
						move.fromRow == move.toRow - 1 && board[move.toRow][move.toColumn] == null) {
					board[move.toRow - 1][move.toColumn] = null;
				}
			}
		} else if (board[move.fromRow][move.fromColumn].type().equals("King")) {
			// Checks for castling
            if (!((King) board[move.fromRow][move.fromColumn]).hasMoved()) {
                if (board[move.fromRow][move.fromColumn].player() == Player.WHITE) {
                    // Left castle
                    if (move.toRow == 7 && move.toColumn == 2) {
                        board[7][3] = board[7][0];
                        board[7][0] = null;
                    // Right castle
                    } else if (move.toRow == 7 && move.toColumn == 6) {
                        board[7][5] = board[7][7];
                        board[7][7] = null;
                    }
                } else {  // player is BLACK
                    // Left castle
                    if (move.toRow == 0 && move.toColumn == 2) {
                        board[0][3] = board[0][0];
                        board[0][0] = null;
                    // Right castle
                    } else if (move.toRow == 0 && move.toColumn == 6) {
                        board[0][5] = board[0][7];
                        board[0][7] = null;
                    }
                }
            }
            ((King) board[move.fromRow][move.fromColumn]).setHasMoved();
        } else if (board[move.fromRow][move.fromColumn].type().equals("Rook")) {
			((Rook) board[move.fromRow][move.fromColumn]).setHasMoved();
		}


		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
	}

	/** check if king is checked */
	public boolean inCheck(Player p) {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (board[r][c].isValidMove(new Move(r, c, i, j), board) && board[i][j] != null &&
									board[i][j].type().equals("King")) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/** return whose turn is */
	public Player currentPlayer() {
		return player;
	}

	/** return number of row*/
	public int numRows() {
		return 8;
	}

	/** return number of column */
	public int numColumns() {
		return 8;
	}

	/** return what piece at location
	 * @param row
	 * @param column
	 */
	public IChessPiece pieceAt(int row, int column) {
		return board[row][column];
	}

	/** change turn */
	public void setNextPlayer() {
		player = player.next();
	}

	/** create new piece and put it on the board
	 * @param piece create new piece of chess on demand
	 *
	 * coordination
	 * @param column
	 * @param row
	 */
	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	/** A.I. for black Player side*/
	public void AI() {
		/* Write a simple AI set of rules in the following order.
		 * a. Check to see if you are in check.
		 * 		i. If so, get out of check by moving the king or placing a piece to block the check
		 *
		 * b. Attempt to put opponent into check (or checkmate).
		 * 		i. Attempt to put opponent into check without losing your piece
		 *		ii. Perhaps you have won the game.
		 *
		 * c. Determine if any of your pieces are in danger,
		 *		i. Move them if you can.
		 *		ii. Attempt to protect that piece.
		 *
		 * d. Move a piece (pawns first) forward toward opponent king
		 *		i. check to see if that piece is in danger of being removed, if so, move a different piece.
		 */

		}

	public static void main(String[] args) {
		ChessModel model = new ChessModel();
		model.display();

	}

	public void display() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == null) {
					System.out.print("null\t");
				} else {
					System.out.print(board[i][j].type() + "\t");
				}
			}
			System.out.println();
		}
	}
}
