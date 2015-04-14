package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidParameterException;

public class GameData {

	String filePath;
	String[] fileData;
	long timeCap = 0;
	int depth = 0;
	boolean forDepth = false;
	boolean forTime = false;
	boolean visual = false;
	boolean console = false;
	boolean prune = false;
	boolean tree = false;
	int rows = 0;
	int cols = 0;
	int player1InitialScore = 0;
	int player2InitialScore = 0;

	/*
	 * This class holds all the important information of the game. It also
	 * validates all the parameters.
	 */
	public GameData(String[] args) throws NumberFormatException, IOException {
		if (args == null) {
			throw new InvalidParameterException("No parameter.");
		}
		if (args.length > 7) {
			throw new InvalidParameterException("Too many parameters.");
		}
		String file = args[0];
		this.filePath = args[1];
		String timeOrDepth = args[2];
		String timeOrDepthValue = args[3];
		String visualOrConsole = args[4];
		String prune = null;
		String tree = null;
		if (args.length == 6) {
			prune = args[5];
		}
		if (args.length == 7) {
			prune = args[5];
			tree = args[6];
		}

		validateAndAssign(file, timeOrDepth, timeOrDepthValue, visualOrConsole,
				prune, tree);

	}

	public String[] getBoardData() {
		return fileData;
	}

	public boolean isForTime() {
		return forTime;
	}

	public boolean isForDepth() {
		return forDepth;
	}

	public boolean isVisual() {
		return visual;
	}

	public boolean isConsole() {
		return console;
	}

	public boolean isPruneValidated() {
		return prune;
	}

	public boolean isTreeValidated() {
		return tree;
	}

	public long getTimeCap() {
		return timeCap;
	}

	public int getDepth() {
		return depth;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getPlayer1InitialScore() {
		return player1InitialScore;
	}

	public int getPlayer2InitialScore() {
		return player2InitialScore;
	}

	@SuppressWarnings("resource")
	private void validateAndAssign(String file, String timeOrDepth,
			String timeOrDepthValue, String visualOrConsole, String prune,
			String tree) throws NumberFormatException, IOException {

		/*
		 * Validate data in file
		 */
		FileReader fr = new FileReader(filePath);
		BufferedReader textReader = new BufferedReader(fr);
		this.rows = Integer.parseInt(textReader.readLine());
		this.cols = Integer.parseInt(textReader.readLine());
		this.player1InitialScore = Integer.parseInt(textReader.readLine());
		this.player2InitialScore = Integer.parseInt(textReader.readLine());
		String[] data = new String[rows];
		if (data.length != rows) {
			throw new InvalidDataInFileException(
					"Error en los datos del archivo. El tamaño de las filas no coinciden.");
		}
		for (int i = 0; i < rows; i++) {
			data[i] = textReader.readLine();
		}
		if (textReader.readLine() != null) {
			throw new InvalidDataInFileException(
					"Error en los datos del archivo. El tamaño de las filas no coinciden.");
		}
		textReader.close();
		this.fileData = data;
		if (this.rows < 1 || this.cols < 1 || this.player1InitialScore < 0
				|| this.player2InitialScore < 0) {
			throw new InvalidDataInFileException(
					"Error en los datos del archivo.");
		}
		for (String row : data) {
			if (row.length() != cols) {
				throw new InvalidDataInFileException(
						"Error en los datos del archivo. El tamaño de las columnas no coindiden.");
			}
			char[] aux = row.toCharArray();
			for (int i = 0; i < aux.length; i++) {
				if (!isNumeric(aux[i])) {
					throw new InvalidDataInFileException(
							"Error en los datos del tablero. Deben ser caracteres numericos.");
				}
			}
		}

		/*
		 * Validate parameters
		 */
		if (file.compareTo("-file") != 0) {
			throw new InvalidParameterException("Error en parametro.");
		}
		if (timeOrDepth.compareTo("-depth") != 0
				&& timeOrDepth.compareTo("-maxtime") != 0) {
			throw new InvalidParameterException("Error en parametro.");
		}
		if (timeOrDepth.compareTo("-depth") == 0) {
			this.forDepth = true;
			this.depth = Integer.parseInt(timeOrDepthValue);
			if (this.depth < 1) {
				throw new InvalidParameterException(
						"La profundidad debe ser un entero mayor o igual a 1");
			}
		}
		if (timeOrDepth.compareTo("-maxtime") == 0) {
			this.forTime = true;
			this.timeCap = Long.parseLong(timeOrDepthValue);
			if (this.timeCap <= 0) {
				throw new InvalidParameterException(
						"El tiempo debe ser mayor a 0.");
			}
			this.timeCap *= 1000;
		}
		if (visualOrConsole.compareTo("-visual") != 0
				&& visualOrConsole.compareTo("-console") != 0) {
			throw new InvalidParameterException("Error en parametro.");
		}
		if (visualOrConsole.compareTo("-visual") == 0) {
			this.visual = true;
		}
		if (visualOrConsole.compareTo("-console") == 0) {
			this.console = true;
		}
		if (prune != null) {
			if (prune.compareTo("-prune") == 0) {
				this.prune = true;
			} else if (prune.compareTo("-tree") == 0) {
				this.tree = true;
			} else {
				throw new InvalidParameterException("Error en parametro.");
			}
		}
		if (tree != null) {
			if (tree.compareTo("-tree") == 0) {
				this.tree = true;
			} else {
				throw new InvalidParameterException("Error en parametro.");
			}
		}
	}

	/*
	 * Checks if the parameter is a number, using the ASCII code of the numbers.
	 * 
	 * @param The char to check.
	 */
	private boolean isNumeric(char c) {
		boolean aux = false;
		if (c == 48 || c == 49 || c == 50 || c == 51 || c == 52 || c == 53
				|| c == 54 || c == 55 || c == 56 || c == 57) {
			aux = true;
		}
		return aux;
	}

}
