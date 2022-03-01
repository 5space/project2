package project2;

import java.util.ArrayList;
import java.util.Collections;

/*****************************************************************
 * Main for the chess mode. Creates a chess board and pieces
 * and enables you to play a game against AI.
 *
 * @author Logan Nommensen (Cybersecurity),
 * Christopher Lamus (Computer Engineering), Don Nguyen (Computer Science)
 *
 * @version Winter 2022
 *****************************************************************/

public class ChessModel implements IChessModel {
	/** The board of interface chess pieces */
    final private IChessPiece[][] board;

	/** The player object, holding whose turn it is */
	private Player player;

	/** List of previous moves for the undo function */
	private final ArrayList<Object> moveHistory;

	/******************************************************************
	* Constructor for the chess model
	*/
	public ChessModel() {
		// chess board size is 8x8 */
		board = new IChessPiece[8][8];

		// white player go first (USER)*/
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

		moveHistory = new ArrayList<>();
	}

	/******************************************************************
	 * Method to check if the game has completed (checkmate)
	 *
	 * @return if game is complete
	 */
	public boolean isComplete() {
		boolean canMove = false;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (isValidMove(new Move(r, c, i, j))) {
								canMove = true;
								move(new Move(r, c, i, j));
								if (!inCheck(Player.BLACK)
										&& !inCheck(Player.WHITE)) {
									undo();
									return false;
								}
								undo();
							}
						}
					}
				}
			}
		}
		return canMove;
	}

	/******************************************************************
	 * Check if movement on this board is valid
	 *
	 * @param move from Move class
	 *
	 * @return if move is valid
	 */
	public boolean isValidMove(Move move) {
		if (board[move.fromRow][move.fromColumn] != null &&
				board[move.fromRow]
						[move.fromColumn].player() == player &&
				board[move.fromRow]
						[move.fromColumn].isValidMove(move, board)) {
			Player tempPlayer = player;
			move(move);
			if (!inCheck(tempPlayer)) {
				undo();
				return true;
			} else {
				undo();
				return false;
			}
		}
		return false;
	}

	/******************************************************************
	 * Method to execute a move and the resulting changes to the board
	 * (en passant, castling, etc.), then add the move to the move history
	 *
	 * @param move from the Move class
	 */
	public void move(Move move) {
		ArrayList<Object> tempMoveHistory = new ArrayList <>();

		ArrayList<Object> extraPieces = new ArrayList <>();

		ArrayList<String> enPassantChanges = new ArrayList <>();

		// En Passant reset
		for (int row = 0; row < numRows(); row++) {
			for (int col = 0; col < numColumns(); col++) {
				if (pieceAt(row, col) != null &&
						pieceAt(row, col).type().equals("Pawn") &&
						((Pawn) pieceAt(row, col))
								.isEnPassantVulnerable()) {
					((Pawn) pieceAt(row, col))
							.setEnPassantVulnerable(false);
					enPassantChanges.add(row + "," + col + "," + false);
				}
			}
		}


		boolean hasMoved = false;
		if (board[move.fromRow]
				[move.fromColumn].type().equals("Pawn")) {
			if (!((Pawn) board[move.fromRow]
					[move.fromColumn]).hasMoved()) {
				((Pawn) board[move.fromRow]
						[move.fromColumn]).setHasMoved(true);
				hasMoved = true;
			}
			if (board[move.fromRow][move.fromColumn].player()
					== Player.WHITE) {
				// Two steps forward
				if (move.fromRow == move.toRow + 2) {
					((Pawn) board[move.fromRow]
							[move.fromColumn]).setEnPassantVulnerable(true);
					enPassantChanges.add
							(move.fromRow + "," + move.fromColumn + "," + true);
				}

				// Checks for en passant
				if ((move.fromColumn == move.toColumn + 1
						|| move.fromColumn == move.toColumn - 1) &&
						move.fromRow == move.toRow + 1
						&& board[move.toRow][move.toColumn] == null) {
					extraPieces.add
							(board[move.toRow + 1][move.toColumn]);
					extraPieces.add
							("" + (move.toRow + 1) + move.toColumn);
					enPassantChanges.add
							((move.toRow + 1) + ","
									+ move.toColumn + "," + false);
					board[move.toRow + 1][move.toColumn] = null;
				}

				// Checks for promotion
				if (move.toRow == 0) {
					extraPieces.add(board[move.fromRow][move.fromColumn]);
					extraPieces.add("" + move.fromRow + move.fromColumn);
					board[move.fromRow]
							[move.fromColumn] = new Queen(Player.WHITE);
				}
			} else {  // player is BLACK
				// Two steps forward
				if (move.fromRow == move.toRow - 2) {
					((Pawn) board[move.fromRow][move.fromColumn])
							.setEnPassantVulnerable(true);
					enPassantChanges.add
							(move.fromRow + ","
									+ move.fromColumn + "," + true);
				}

				// Checks for en passant
				if ((move.fromColumn == move.toColumn + 1
						|| move.fromColumn == move.toColumn - 1) &&
						move.fromRow == move.toRow - 1
						&& board[move.toRow][move.toColumn] == null) {
					extraPieces.add(board[move.toRow - 1][move.toColumn]);
					extraPieces.add(""
							+ (move.toRow - 1) + move.toColumn);
					enPassantChanges.add((move.toRow - 1) + ","
							+ move.toColumn + "," + false);
					board[move.toRow - 1][move.toColumn] = null;
				}

				// Checks for promotion
				if (move.toRow == 7) {
					extraPieces.add
							(board[move.fromRow][move.fromColumn]);
					extraPieces.add
							("" + move.fromRow + move.fromColumn);
					board[move.fromRow]
							[move.fromColumn] = new Queen(Player.BLACK);
				}
			}
		} else if (board[move.fromRow]
				[move.fromColumn].type().equals("King")) {
			// Checks for castling
            if (!((King) board[move.fromRow]
					[move.fromColumn]).hasMoved()) {
                if (board[move.fromRow]
						[move.fromColumn].player() == Player.WHITE) {
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
				((King) board[move.fromRow]
						[move.fromColumn]).setHasMoved(true);
				hasMoved = true;
			}
        } else if (board[move.fromRow]
				[move.fromColumn].type().equals("Rook")) {
			((Rook) board[move.fromRow]
					[move.fromColumn]).setHasMoved(true);
			hasMoved = true;
		}

		// 0. Add direct move to tempMoveHistory
		tempMoveHistory.add(move);

		// 1. Adds direct piece captured to tempMoveHistory
		// (null if no piece captured)
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

		board[move.toRow][move.toColumn]
				= board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;

		setNextPlayer();
	}

	/******************************************************************
	 * Check if a player's king is in check
	 *
	 * @param p Player's king to check for check
	 *
	 * @return if the player's king is in check
	 */
	public boolean inCheck(Player p) {
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null && !board[r][c].player().equals(p)) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (board[i][j] != null
									&& board[i][j].player().equals(p)
									&& board[i][j]
									.type().equals("King") &&
									board[r][c].isValidMove
											(new Move(r, c, i, j), board)) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/******************************************************************
	 * Check current player
	 *
	 * @return whose turn it is
	 */
	public Player currentPlayer() {
		return player;
	}

	/******************************************************************
	 *  Method for checking the number of rows in the board
	 *
	 *  @return number of rows
	 */
	public int numRows() {
		return 8;
	}

	/******************************************************************
	 *  Method for checking the number of columns in the board
	 *
	 *  @return number of columns
	 */
	public int numColumns() {
		return 8;
	}

	/******************************************************************
	 * Return what piece is at a location
	 *
	 * @param row Row of the location
	 * @param column Column of the location
	 *
	 * @return piece at location
	 *
	 * @throws IllegalArgumentException if the location is invalid
	 * (row or column out of bounds)
	 */
	public IChessPiece pieceAt(int row, int column) {
		if (row < 0 || row > 7 || column < 0 || column > 7) {
			throw new IllegalArgumentException("Invalid row or column");
		}
		return board[row][column];
	}

	/******************************************************************
	 * Method to change turn to the next player
	 */
	public void setNextPlayer() {
		player = player.next();
	}

	/******************************************************************
	 * Put a piece on the board
	 *
	 * @param row row of the location
	 * @param column column of the location
	 * @param piece the chess piece to be placed
	 */
	public void setPiece(int row, int column, IChessPiece piece) {
		board[row][column] = piece;
	}

	/******************************************************************
	 * Undoes the last move in the move history.
	 */
	public void undo() {
		if (moveHistory.size() == 0) {
			return;
		}
		// Get the last moveHistory from the move history
		ArrayList<Object> lastMoveFull
				= (ArrayList<Object>) moveHistory.get(moveHistory.size() - 1);
		moveHistory.remove(moveHistory.size() - 1);

		// 0. Get the last move from the last move history
		Move lastMove = (Move) lastMoveFull.get(0);
		// Returns the direct moved piece back to its old spot
		setPiece(lastMove.fromRow,
				lastMove.fromColumn, board[lastMove.toRow][lastMove.toColumn]);

		// 1. If a piece was directly captured, return it back
		setPiece(lastMove.toRow,
				lastMove.toColumn, (IChessPiece) lastMoveFull.get(1));

		// 2. Handling extra pieces moved (castle, en passant)
		ArrayList<Object> extraPieces
				= (ArrayList<Object>) lastMoveFull.get(2);
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
			if (board[lastMove.fromRow]
					[lastMove.fromColumn].type().equals("Pawn")) {
				((Pawn) board[lastMove.fromRow]
						[lastMove.fromColumn]).setHasMoved(false);
			} else if (board[lastMove.fromRow]
					[lastMove.fromColumn].type().equals("King")) {
				((King) board[lastMove.fromRow]
						[lastMove.fromColumn]).setHasMoved(false);
			} else if (board[lastMove.fromRow]
					[lastMove.fromColumn].type().equals("Rook")) {
				((Rook) board[lastMove.fromRow]
						[lastMove.fromColumn]).setHasMoved(false);
			}
		}

		// 4. Set enPassantChanges for each piece that changed status
		ArrayList<String> enPassantChanges
				= (ArrayList<String>) lastMoveFull.get(4);
		for (String change : enPassantChanges) {
			String[] temp = change.split(",");
			((Pawn) board[Integer.parseInt(temp[0])]
					[Integer.parseInt(temp[1])]).setEnPassantVulnerable
					(!Boolean.parseBoolean(temp[2]));
		}

		setNextPlayer();
	}

	/******************************************************************
	 * Helper method for getting a piece's value for AI purposes
	 *
	 * @return value of the piece
	 */
	private int getPieceValue(IChessPiece piece) {
		if (piece == null) {
			return 0;
		}
		switch (piece.type()) {
			case "Pawn":
				return 1;
			case "Rook":
				return 5;
			case "Knight":
				return 3;
			case "Bishop":
				return 3;
			case "Queen":
				return 9;
			case "King":
				return 100;
			default:
				return 0;
		}
	}

	/******************************************************************
	 * Helper method for the AI to find the value of the board
	 *
	 * @return the value of the current board
	 */
	private int evaluateBoard() {
		int score = 0;
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null) {
					if (board[r][c].player().equals(Player.BLACK)) {
						score += getPieceValue(board[r][c]);
					} else {
						score -= getPieceValue(board[r][c]);
					}
				}
			}
		}
		return score;
	}

	/******************************************************************
	 * Helper method for the AI to find the value of a move.
	 * The method recursively checks how good/bad a move is for black
	 * if white makes their best possible move.
	 *
	 * @param depth the current depth of the search
	 * @param maxing if the algorithm should be maximizing (playing as black)
	 *
	 * @return the value of the move
	 */
	private int minimax(int depth, boolean maxing) {
		// If depth reaches 0, return the value of the board
		if (depth == 0) {
			return evaluateBoard();
		}
		// Make a list for the values of all possible moves
		ArrayList<Integer> moveValues = new ArrayList<>();
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				// If the found piece is the correct player's piece
				if (board[r][c] != null && (board[r][c].player().equals(currentPlayer()))) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							Move testMove = new Move(r, c, i, j);
							if (isValidMove(testMove)) {
								move(testMove);

								// Call itself recursively to find a final value
								moveValues.add(minimax(depth - 1, !maxing));

								undo();
							}
						}
					}
				}
			}
		}

		// After finding all values for a set of moves, find best for who's maxing
		if (moveValues.size() == 0) {
			return -99999999;
		} else {
			int bestValue;
			// If maxing, find the highest value (best for black)
			if (maxing) {
				bestValue = -1000;
				for (int value : moveValues) {
					if (value > bestValue) {
						bestValue = value;
					}
				}
			} else { // Minimizing, so find the lowest value (best for white)
				bestValue = 1000;
					for (int value : moveValues) {
						if (value < bestValue) {
							bestValue = value;
						}
					}
				}
			return bestValue;
		}
	}

	/******************************************************************
	 * Method for generating a "good" move to play for a player
	 */
	public void AI() {
		// Make a list for all the possible moves
		ArrayList<ArrayList<Object>> weightedMoves = new ArrayList<>();

		// Go through every black piece and try every move
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (board[r][c] != null && board[r][c].player().equals(Player.BLACK)) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							Move testMove = new Move(r, c, i, j);
							if (isValidMove(testMove)) {
								// Helper list for a move and its value
								ArrayList<Object> tempMove = new ArrayList<>();

								// Actual try the move
								move(testMove);

								// Run a minimax algorithm to check how good that
								// move is, then add the move to the helper array
								tempMove.add(minimax(2, true));

								// Undo the move so we can loop again safely
								undo();

								// Add the test move's value, then add the helper
								tempMove.add(testMove);
								weightedMoves.add(tempMove);
							}
						}
					}
				}
			}
		}

		// After all moves have been assigned a value, if there's no possible
		// moves the game is over.
		if (weightedMoves.size() == 0) {
			System.out.println("No possible moves for AI. Good game.");
		} else {
			// Finds the best (least bad?) move the AI can make
			int bestMoveScore = -999;
			// Makes a list for the best moves with the same weight, so
			// it doesn't get stuck in an infinite loop
			ArrayList<Move> bestMoves = new ArrayList<>();
			for (ArrayList<Object> move : weightedMoves) {
				if ((int) move.get(0) > bestMoveScore) {
					bestMoves.clear();
					bestMoves.add((Move) move.get(1));
					bestMoveScore = (int) move.get(0);
				} else if ((int) move.get(0) == bestMoveScore) {
					bestMoves.add((Move) move.get(1));
				}
 			}
			// Shuffles the list of best moves
			Collections.shuffle(bestMoves);

			// Finally, make the best move!
			move(bestMoves.get(0));
		}
	}

	public static void main(String[] args) {
		ChessModel model = new ChessModel();
		model.display();

	}
	/******************************************************************
	 * Creates a text base display for the chess game used for
	 * testing purposes
	 */
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
