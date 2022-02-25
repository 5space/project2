package project2;

import java.util.ArrayList;

public class ChessModel implements IChessModel {
    private IChessPiece[][] board;
	private Player player;
	ArrayList<Object> moveHistory;

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

		moveHistory = new ArrayList <Object>();
	}

	/** check if the game is done*/
	public boolean isComplete() {
		boolean valid = false;
		return valid;
	}

	/** check if movement on this board is valid
	 * @param move from Move class
	 * @return if move is valid
	 */
	public boolean isValidMove(Move move) {
		if (board[move.fromRow][move.fromColumn] != null && board[move.fromRow][move.fromColumn].player() == player)
			if (board[move.fromRow][move.fromColumn].isValidMove(move, board))
                return true;
		return false;
	}

	/** store the movement coordination to evaluate and execute */
	public void move(Move move) {
		ArrayList<Object> tempMoveHistory = new ArrayList <Object>();

		ArrayList<Object> extraPieces = new ArrayList <Object>();

		ArrayList<String> enPassantChanges = new ArrayList <String>();

		// En Passant reset
		for (int row = 0; row < numRows(); row++) {
			for (int col = 0; col < numColumns(); col++) {
				if (pieceAt(row, col) != null &&
						pieceAt(row, col).type().equals("Pawn") &&
						((Pawn) pieceAt(row, col)).isEnPassantVulnerable()) {
					((Pawn) pieceAt(row, col)).setEnPassantVulnerable(false);
					enPassantChanges.add(row + "," + col + "," + false);
				}
			}
		}


		boolean hasMoved = false;
		if (board[move.fromRow][move.fromColumn].type().equals("Pawn")) {
			if (!((Pawn) board[move.fromRow][move.fromColumn]).hasMoved()) {
				((Pawn) board[move.fromRow][move.fromColumn]).setHasMoved(true);
				hasMoved = true;
			}
			if (board[move.fromRow][move.fromColumn].player() == Player.WHITE) {
				// Two steps forward
				if (move.fromRow == move.toRow + 2) {
					((Pawn) board[move.fromRow][move.fromColumn]).setEnPassantVulnerable(true);
					enPassantChanges.add(move.fromRow + "," + move.fromColumn + "," + true);
				}

				// Checks for en passant
				if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
						move.fromRow == move.toRow + 1 && board[move.toRow][move.toColumn] == null) {
					extraPieces.add(board[move.toRow + 1][move.toColumn]);
					extraPieces.add("" + (move.toRow + 1) + move.toColumn);
					enPassantChanges.add((move.toRow + 1) + "," + move.toColumn + "," + false);
					board[move.toRow + 1][move.toColumn] = null;
				}

				// Checks for promotion
				if (move.toRow == 0) {
					extraPieces.add(board[move.fromRow][move.fromColumn]);
					extraPieces.add("" + move.fromRow + move.fromColumn);
					board[move.fromRow][move.fromColumn] = new Queen(Player.WHITE);
				}
			} else {  // player is BLACK
				// Two steps forward
				if (move.fromRow == move.toRow - 2) {
					((Pawn) board[move.fromRow][move.fromColumn]).setEnPassantVulnerable(true);
					enPassantChanges.add(move.fromRow + "," + move.fromColumn + "," + true);
				}

				// Checks for en passant
				if ((move.fromColumn == move.toColumn + 1 || move.fromColumn == move.toColumn - 1) &&
						move.fromRow == move.toRow - 1 && board[move.toRow][move.toColumn] == null) {
					extraPieces.add(board[move.toRow - 1][move.toColumn]);
					extraPieces.add("" + (move.toRow - 1) + move.toColumn);
					enPassantChanges.add((move.toRow - 1) + "," + move.toColumn + "," + false);
					board[move.toRow - 1][move.toColumn] = null;
				}

				// Checks for promotion
				if (move.toRow == 7) {
					extraPieces.add(board[move.fromRow][move.fromColumn]);
					extraPieces.add("" + move.fromRow + move.fromColumn);
					board[move.fromRow][move.fromColumn] = new Queen(Player.BLACK);
				}
			}
		} else if (board[move.fromRow][move.fromColumn].type().equals("King")) {
			// Checks for castling
            if (!((King) board[move.fromRow][move.fromColumn]).hasMoved()) {
                if (board[move.fromRow][move.fromColumn].player() == Player.WHITE) {
                    // Left castle
                    if (move.toRow == 7 && move.toColumn == 2) {
						extraPieces.add(board[7][0]);
						extraPieces.add("" + 7 + 0);
						extraPieces.add(null);
						extraPieces.add("" + 7 + 3);
                        board[7][3] = board[7][0];
                        board[7][0] = null;
                    // Right castle
                    } else if (move.toRow == 7 && move.toColumn == 6) {
						extraPieces.add(board[7][7]);
						extraPieces.add("" + 7 + 7);
						extraPieces.add(null);
						extraPieces.add("" + 7 + 5);
                        board[7][5] = board[7][7];
                        board[7][7] = null;
                    }
                } else {  // player is BLACK
                    // Left castle
                    if (move.toRow == 0 && move.toColumn == 2) {
						extraPieces.add(board[0][0]);
						extraPieces.add("" + 0 + 0);
						extraPieces.add(null);
						extraPieces.add("" + 0 + 3);
                        board[0][3] = board[0][0];
                        board[0][0] = null;
                    // Right castle
                    } else if (move.toRow == 0 && move.toColumn == 6) {
						extraPieces.add(board[0][7]);
						extraPieces.add("" + 0 + 7);
						extraPieces.add(null);
						extraPieces.add("" + 0 + 5);
                        board[0][5] = board[0][7];
                        board[0][7] = null;
                    }
                }
            }
            ((King) board[move.fromRow][move.fromColumn]).setHasMoved(true);
			hasMoved = true;
        } else if (board[move.fromRow][move.fromColumn].type().equals("Rook")) {
			((Rook) board[move.fromRow][move.fromColumn]).setHasMoved(true);
			hasMoved = true;
		}

		// 0. Add direct move to tempMoveHistory
		tempMoveHistory.add(move);

		// 1. Adds direct piece captured to tempMoveHistory (null if no piece captured)
		if (board[move.toRow][move.toColumn] != null) {
			tempMoveHistory.add(board[move.toRow][move.toColumn]);
		} else {
			tempMoveHistory.add(null);
		}

		// 2. Add extra piece movement to tempMoveHistory (null if no extra piece movement)
		tempMoveHistory.add(extraPieces);

		// 3. Add hasMoved to tempMoveHistory
		tempMoveHistory.add(hasMoved);

		// 4. Add enPassantChanges to tempMoveHistory
		tempMoveHistory.add(enPassantChanges);

		// Finally, add the tempMoveHistory to the moveHistory
		moveHistory.add(tempMoveHistory);

		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;

		setNextPlayer();
	}

	/** check if king is checked */
	public boolean inCheck(Player p) {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null && !board[r][c].player().equals(p)) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (board[i][j] != null && board[i][j].type().equals("King") &&
									board[r][c].isValidMove(new Move(r, c, i, j), board)) {
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

	public void undo() {
		// Get the last moveHistory from the move history
		ArrayList<Object> lastMoveFull = (ArrayList<Object>) moveHistory.get(moveHistory.size() - 1);
		moveHistory.remove(moveHistory.size() - 1);

		// 0. Get the last move from the last move history
		Move lastMove = (Move) lastMoveFull.get(0);
		// Returns the direct moved piece back to its old spot
		setPiece(lastMove.fromRow, lastMove.fromColumn, board[lastMove.toRow][lastMove.toColumn]);

		// 1. If a piece was directly captured, return it back
		setPiece(lastMove.toRow, lastMove.toColumn, (IChessPiece) lastMoveFull.get(1));

		// 2. Handling extra pieces moved (castle, en passant)
		ArrayList<Object> extraPieces = (ArrayList<Object>) lastMoveFull.get(2);
		if (extraPieces.size() >= 2) {
			setPiece(extraPieces.get(1).toString().charAt(0) - '0',
					extraPieces.get(1).toString().charAt(1) - '0',
					(IChessPiece) extraPieces.get(0));
		}
		if (extraPieces.size() == 4) {
			setPiece(extraPieces.get(3).toString().charAt(0) - '0',
					extraPieces.get(3).toString().charAt(1) - '0',
					null);
		}

		// 3. Set hasMoved to false if piece moved
		if ((boolean) lastMoveFull.get(3)) {
			if (board[lastMove.fromRow][lastMove.fromColumn].type().equals("Pawn")) {
				((Pawn) board[lastMove.fromRow][lastMove.fromColumn]).setHasMoved(false);
			} else if (board[lastMove.fromRow][lastMove.fromColumn].type().equals("King")) {
				((King) board[lastMove.fromRow][lastMove.fromColumn]).setHasMoved(false);
			} else if (board[lastMove.fromRow][lastMove.fromColumn].type().equals("Rook")) {
				((Rook) board[lastMove.fromRow][lastMove.fromColumn]).setHasMoved(false);
			}
		}

		// 4. Set enPassantChanges for each piece that changed status
		ArrayList<String> enPassantChanges = (ArrayList<String>) lastMoveFull.get(4);
		for (String change : enPassantChanges) {
			String[] temp = change.split(",");
			((Pawn) board[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])]).setEnPassantVulnerable(!Boolean.parseBoolean(temp[2]));
		}

		setNextPlayer();
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
