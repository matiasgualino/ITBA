package frontend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;

import utilities.Chronometer;
import backend.Board;
import backend.GameData;
import backend.GameState;
import backend.InvalidDataInFileException;
import backend.MinState;

public class Main {

	public static void main(String[] args) throws NumberFormatException,
			IOException {
		try {
			GameData gameData = new GameData(args);
			Board board = new Board(gameData);
			board.gravity();
			GameState state = new MinState(board, gameData.getPlayer1InitialScore(), gameData.getPlayer2InitialScore());
			if (gameData.isVisual()) {
				AzulejosGame game = new AzulejosGame(state);
				AzulejosFrame frame = new AzulejosFrame(game);
				frame.setVisible(true);
			} else {
				printState(state);
			}
		} catch (InvalidDataInFileException e) {
			System.err.println(e.getMessage());
		} catch (InvalidParameterException i) {
			System.err.println(i.getMessage());
		}catch (NumberFormatException n){
			System.err.println("Las filas, columnas, puntajes, profundidad y tiempo deben ser numeros naturales.");
		}catch (FileNotFoundException f){
			System.err.println("Archivo o directorio inexistente.");
		}
		return;
	}
	
	public static void printState(GameState state) throws IOException{
		Chronometer ch = new Chronometer(0);
		ch.start();
		GameState aux = state.minimax();
		ch.stop();
		System.out.println("The best possible movement for the parameters given is: ("+ Integer.toString(aux.getX())+ ","+ Integer.toString(aux.getY()) + ")");
		if(!state.isForTime()){
			System.out.println("Calculated in: " + ch.getFinalSeconds() + " seconds.");
		}
	}

}