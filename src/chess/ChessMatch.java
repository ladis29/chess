package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;

	// Cria Tabuleiro
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}

	// Coloca aas peças informadas nas suas posições no tabuleiro
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {

				mat[i][j] = (ChessPiece) board.piece(i, j);

			} // fim do for que percorre as colunas
		} // fim do for que percorre as linhas
		return mat;
	}

	// Metodo que movimenta as peças
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece) capturedPiece;
	}

	// executa a movimentação de fato
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}

	// valida se na posição informada para iniciar o movimento desejado tem uma peça
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não há peça na posição informada");
		}
		if (!board.piece(position).isThereAnnyPossibleMove()) {
			throw new ChessException("Não existem movimentos possíveis para a peça escolhida");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A peça escolhida não pode executar este movimento");
		}
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
