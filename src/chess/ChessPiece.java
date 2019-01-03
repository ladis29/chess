package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;

	// Determina a cor das peças(brancas ou pretas)
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	

	/*
	 * Este método foi criado para informar a posição das peças à classe ChessMatch
	 * para que esta pudesse verificar a ocasião de uma situação de cheque ou cheque
	 * mate. Isso foi necessário porque devido ao encapsulamento(protected) não é
	 * possível para a classe ChessMatch acessar o posicionamento de uma peça
	 * consultando a classe Piece.
	 */
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p.getColor() != color;
	}

}
