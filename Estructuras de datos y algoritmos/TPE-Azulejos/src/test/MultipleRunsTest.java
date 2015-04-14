package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

import utilities.Chronometer;
import utilities.RandomGameFileGenerator;
import backend.Board;
import backend.GameData;
import backend.GameState;
import backend.InvalidDataInFileException;
import backend.MinState;
import frontend.AzulejosFrame;
import frontend.AzulejosGame;

public class MultipleRunsTest {
	public static void main(String[] args) throws NumberFormatException,
			IOException {
		try {
			args[3] = Integer.toString(5);
			int i = 0;
			int total = 10;
			int acum = 0;
			double time1 = 0;
			double time2 = 0;
			Chronometer ch3 = new Chronometer(0);
			ch3.start();
			while (i < total) {
				args[5] = null;
				RandomGameFileGenerator.createFile(8, 8, 1, 3);
				GameData gameData = new GameData(args);
				Board board = new Board(gameData);
				board.gravity();
				GameState state = new MinState(board,
						gameData.getPlayer1InitialScore(),
						gameData.getPlayer2InitialScore());
				if (gameData.isVisual()) {
					AzulejosGame game = new AzulejosGame(state);
					AzulejosFrame frame = new AzulejosFrame(game);
					frame.setVisible(true);
				} else {
					Chronometer ch1 = new Chronometer(0);
					Chronometer ch2 = new Chronometer(0);
					ch1.start();
					GameState aux1 = state.minimax();
					ch1.stop();
					time1 = time1 + ch1.getFinalSeconds();
					args[5] = "-prune";
					gameData = new GameData(args);
					ch2.start();
					GameState aux2 = state.minimax();
					ch2.stop();
					time2 = time2 + ch2.getFinalSeconds();
					if (aux1.getX() == aux2.getX()
							&& aux1.getY() == aux2.getY()) {
						acum++;
					}
				}
				i++;
			}
			ch3.stop();
			System.out.println("Profundidad calculada      : " + args[3]);
			System.out.println("Total de corridas          : " + total);
			System.out.println("Total de resultados iguales: " + acum);
			System.out.println("Tiempo sin poda            : " + time1);
			System.out.println("Tiempo con poda            : " + time2);
			System.out.println("Tiempo total               : "
					+ ch3.getFinalSeconds());
		} catch (InvalidDataInFileException e) {
			System.err.println(e.getMessage());
		} catch (InvalidParameterException i) {
			System.err.println(i.getMessage());
		} catch (NumberFormatException n) {
			System.err
					.println("Las filas, columnas, puntajes, profundidad y tiempo deben ser numeros naturales.");
		} catch (FileNotFoundException f) {
			System.err.println("Archivo o directorio inexistente.");
		}
		return;
	}

	public static void printState(GameState state) throws IOException {
		Chronometer ch = new Chronometer(0);
		ch.start();
		GameState aux = state.minimax();
		ch.stop();
		System.out
				.println("The best possible movement for the parameters given is: ("
						+ Integer.toString(aux.getX())
						+ ","
						+ Integer.toString(aux.getY()) + ")");
		if (!state.isForTime()) {
			System.out.println("Calculated in: " + ch.getFinalSeconds()
					+ " seconds.");
		}
	}

}
