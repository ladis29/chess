package boardgame;

/*
 * Superclasse piece que será a superclasse de todas as peças do jogo
 * */

public abstract class Piece {

	protected Position position;
	private Board board;

	public Piece(Board board) {
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}

	// cria uma matriz abstrata de possíveis movimentos que deverá se definida na
	// classe de cada peça específica
	public abstract boolean[][] possibleMoves();

	// retorna uma matriz de movimentos possíveis de acordo com a regra específica
	// nas classes de cada peça
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}

	// Verifica se existem movimentos possíveis para a peça selecionada no "source"
	public boolean isThereAnnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				} // fim if
			} // fim for interno
		} // fim for externo
		return false;
	}

}
