package project2;

public class Move {
	/**
	 * To navigate on the board
	 * To evaluate if the movement on the board is valid
	 */
	public int fromRow, fromColumn, toRow, toColumn;

	/** Default constructor*/
	public Move() {
	}

	/**
	 * Constructor of the move with destination
	 * @param fromRow initial row location
	 * @param fromColumn initial column location
	 * @param toRow destine row
	 * @param toColumn destine column
	 */
	public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
	}

	@Override
	public String toString() {
		return "Move [fromRow=" + fromRow + ", fromColumn=" + fromColumn + ", toRow=" + toRow + ", toColumn=" + toColumn
				+ "]";
	}
	
	
}
