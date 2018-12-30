package chess;

public class ChessException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	//Tratamento de excessões do jogo
	public ChessException(String msg) {
		super(msg);
	}
	

}
