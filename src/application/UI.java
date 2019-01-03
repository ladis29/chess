package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	/*
	 * Os códigos a seguir foram enconttrados em:
	 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-
	 * using-system-out-println servem para imprimir cor nas peças no console
	 */
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	/*
	 * O código a seguir serve para limpara a tela após cada jogada e foi encontrado
	 * no seguinte endereço:
	 * https://stackoverflow.com/questions/2979383/java-clear-the-console
	 */
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	// Método que recebe a posição informada pelo usuário para mover a peça
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine().toLowerCase();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);

		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo a posição informada, valores válidos são de 'a1' a 'h8'");
		}

	}
	
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Jogada : " + chessMatch.getTurn());
		System.out.println("Esperando Jogador : " + chessMatch.getCurrentPlayer());
	}

	// Formato como o tabuleiro será exibido no console
	public static void printBoard(ChessPiece[][] pieces) {
		System.out.println("  a b c d e f g h");
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.print((8 - i) + " ");
			System.out.println();
		}
		System.out.println("  a b c d e f g h");

	}

	// PrintBoard para imprimir os possíveis movimentos
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		System.out.println("  a b c d e f g h");
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.print((8 - i) + " ");
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	// impressão das peças no console considerando a cor de cada peça e que o fundo
	// será escuro
	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}

		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (piece.getColor() == Color.BRANCO) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_RED + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	//Imprime as peças capturadas na tela
	public static void printCapturedPieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.BRANCO).collect(Collectors.toList());
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.VERMELHO).collect(Collectors.toList());
		System.out.println("Peças capturadas");
		System.out.print("Brancas: ");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.println(ANSI_RESET);
		System.out.print("Vermelhas: ");
		System.out.print(ANSI_RED);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.println(ANSI_RESET);
		
	}

}
