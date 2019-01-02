package boardgame;

public class BoardException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	// Tratamento de excessões do tabuleiro
	public BoardException(String msg) {
		super(msg);
	}

}
