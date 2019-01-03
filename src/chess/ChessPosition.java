package chess;

import boardgame.Position;

public class ChessPosition {

	private char column;
	private int row;

	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) {
			throw new ChessException("A posição informada não existe, as posições válidas são entre 'a1' e 'h8'.");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	/*
	 * O método a seguir converte a posição informada com caracteres e inteiros (a1,
	 * b6, g4...) para a posição da matriz do jogo neste caso a regra é a seguinte:
	 * o valor inteiro das colunas é calculado sob a seguinte regra: coluna = coluna
	 * - o valor do caractere a na tabela asci o valor da linha é calculado sob a
	 * seguinte regra: linha = 8 - a linha informada
	 */
	protected Position toPosition() {
		return new Position(8 - row, column - 'a');
	}

	/*
	 * O método abaixo faz a operação inversa do método anterior, convertendo uma
	 * posição na matriz do jogo para uma posição com caracteres e inteiros
	 * possibilitando assim a interação entre o jogo e o jogador;
	 */
	protected static ChessPosition fromPosition(Position position) {
		return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
	}

	@Override
	public String toString() {
		return "" + column + row;
	}

}
