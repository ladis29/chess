package boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	protected int getRows() {
		return rows;
	}

	protected int getColumns() {
		return columns;
	}

	protected void setRows(int rows) {
		this.rows = rows;
	}

	protected void setColumns(int columns) {
		this.columns = columns;
	}
	
	
	
	

}
