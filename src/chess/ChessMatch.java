package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckmate() {
		return checkMate;
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
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}

	// Metodo que controla o andamento do jogo
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("Você não pode se colocar em cheque");
		}

		check = (testCheck(opponent(currentPlayer))) ? true : false;

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;

		} else {
			nextTurn();
		}

		return (ChessPiece) capturedPiece;
	}

	// executa a movimentação de fato
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		return capturedPiece;
	}

	// Desfaz o movimento em caso de o próprio jogador se colocar em cheque
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}

	// valida se na posição informada para iniciar o movimento desejado tem uma
	// peça, se a peça é do jogador da rodada e se é possível movê-la
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("Não há peça na posição informada");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("A peça escolhida não é sua!!");
		}
		if (!board.piece(position).isThereAnnyPossibleMove()) {
			throw new ChessException("Não existem movimentos possíveis para a peça escolhida");
		}
	}

	// Verifica os possíveis movimentos da peça escolhida e valida se ela pode
	// chegar à posição de destino
	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A peça escolhida não pode executar este movimento");
		}
	}

	// método que troca o jogador a cada jogada
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.BRANCO) ? Color.VERMELHO : Color.BRANCO;
	}

	// Esse método é só para ter o controle de quem é o oponente do jogador do
	// momento. Isso facilita a lógica do cheque.
	private Color opponent(Color color) {
		return (color == Color.BRANCO) ? Color.VERMELHO : Color.BRANCO;
	}

	// Verifica qual das peças é o REI de cada cor para auxiliar na detecção da
	// situação de cheque
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			} // fim if
		} // fim for
		throw new IllegalStateException("Não existe o REI da cor " + color + " no tabuleiro!!");
	}

	// Verifica se algum rei está em situação de cheque
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			} // fim if
		} // fim for
		return false;
	}

	private boolean testCheckMate(Color color) {
		// Se não estiver em cheque desconsidera o resto e retorna false
		if (!testCheck(color)) {
			return false;
		}
		// Cria uma lista de movimentos possíveis para todas as peças da cor que está em
		// cheque
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		// para cada peça
		for (Piece p : list) {
			// cria uma matriz de movimentos possíveis
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					// testa cada posição do tabuleiro
					if (mat[i][j]) {
						// pega a posição inicial da peça que está sendo testada
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						// cria uma posição final para a peça dentro das possibilidades de movimento
						Position target = new Position(i, j);
						// move a peça para a posição criada
						Piece capturedPiece = makeMove(source, target);
						// testa se o rei continua em cheque após o movimento simulado
						boolean testCheck = testCheck(color);
						// desfaz o movimento
						undoMove(source, target, capturedPiece);
						// se o rei siu do cheque
						if (!testCheck) {
							return false;
						} // fim if testcheck
					} // fim if mat[i][j]
				} // finm for interno
			} // fim for externo
		} // fim foreach

		return true;
	}

	/*
	 * O método a seguir coloca as peças no tabuleiro passando as posições nas
	 * coordenadas do jogo e não da matriz
	 */
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
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
