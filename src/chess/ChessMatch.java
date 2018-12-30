package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;

	// Cria Tabuleiro
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}

	//Coloca aas peças informadas nas suas posições no tabuleiro
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {

				mat[i][j] = (ChessPiece) board.piece(i, j);

			} // fim do for que percorre as colunas
		} // fim do for que percorre as linhas
		return mat;
	}

	/*
	 * O método a seguir coloca as peças no tabuleiro passando as posições nas
	 * coordenadas do jogo e não da matriz
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	// Colocando as peças no tabuleiro
	private void initialSetup() {
		placeNewPiece('a', 8, new Rook(board, Color.WHITE));
		placeNewPiece('h', 8, new Rook(board, Color.WHITE));
		placeNewPiece('d', 8, new King(board, Color.WHITE));

		placeNewPiece('a', 1, new Rook(board, Color.BLACK));
		placeNewPiece('h', 1, new Rook(board, Color.BLACK));
		placeNewPiece('d', 1, new King(board, Color.BLACK));
	}

}
