package chess;

import boardgame.BoardException;

public class ChessException extends BoardException{
	private static final long serialVersionUID = 1L;
	
	//Tratamento de excessões do jogo
	public ChessException(String msg) {
		super(msg);
	}
	

}
