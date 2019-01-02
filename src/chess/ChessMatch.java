package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;

	// Cria Tabuleiro
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.BRANCO;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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
	
	// Metodo que marca as casas para os possíveis movimento de cada peça
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	// Metodo que movimenta as peças
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	// executa a movimentação de fato
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}

	// valida se na posição informada para iniciar o movimento desejado tem uma peça, se a peça é do jogador da rodada e se é possível movê-la
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não há peça na posição informada");
		}
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("A peça escolhida não é sua!!");
		} 
		if (!board.piece(position).isThereAnnyPossibleMove()) {
			throw new ChessException("Não existem movimentos possíveis para a peça escolhida");
		}
	}
	
	//Verifica os possíveis movimentos da peça escolhida e valida se ela pode chegar à posição de destino
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A peça escolhida não pode executar este movimento");
		}
	}
	
	//método que troca o jogador a cada jogada
	private void nextTurn() {
		turn ++;
		currentPlayer = (currentPlayer == Color.BRANCO) ? Color.VERMELHO : Color.BRANCO;
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
		placeNewPiece('a', 1, new Rook(board, Color.BRANCO));
		placeNewPiece('h', 1, new Rook(board, Color.BRANCO));
		placeNewPiece('d', 1, new King(board, Color.BRANCO));

		placeNewPiece('a', 8, new Rook(board, Color.VERMELHO));
		placeNewPiece('h', 8, new Rook(board, Color.VERMELHO));
		placeNewPiece('d', 8, new King(board, Color.VERMELHO));
	}

}
