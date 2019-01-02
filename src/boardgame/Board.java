package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces;

	// Cria a matriz do tabuleiro do jogo
	public Board(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BoardException("Erro ao criar o tabuleiro, deve haver pelo menos 1 linha e 1 coluna");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	// Substitui o '-' por uma peça quando a posição não for null
	public Piece piece(int row, int column) {
		if (!positionExists(row, column)) {
			throw new BoardException("Esta posição não existe");
		}
		return pieces[row][column];
	}

	// Método para montar o tabuleiro colocando as peças (considerando as linhas e
	// colunas da matriz informada)
	public Piece piece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Esta posição não existe");
		}
		return pieces[position.getRow()][position.getColumn()];
	}

	// Método para colocar as peças (considerando a posição informada)
	public void placePiece(Piece piece, Position position) {
		if (thereIsAPiece(position)) {
			throw new BoardException("Já existe uma peça na posição " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}

	// Método para apagar a peça de uma determinada posição
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Esta Posição não existe");
		}
		if (piece(position) == null) {
			return null;
		}
		Piece aux = piece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}

	// Método booleano que verificará se a posição informada existe(considerando as
	// linhas e colunas da matriz informada)
	public boolean positionExists(int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}

	// Método booleano que verificará se a posição informada existe(considerando a
	// posição informada)
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}

	// Método booleano que verificará se já há uma peça na posição informada para o
	// movimento
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Esta posição não existe");
		}
		return piece(position) != null;
	}

}
