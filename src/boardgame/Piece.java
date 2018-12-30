package boardgame;

/*
 * Superclasse piece que será a superclasse de todas as peças do jogo
 * */

public class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
	}

	protected Board getBoard() {
		return board;
	}
	

}
