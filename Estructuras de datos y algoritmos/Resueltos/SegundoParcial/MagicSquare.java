package SegundoParcial;

import utilities.Chronometer;

public class MagicSquare {
	public static void main(String[] args) {
		Chronometer ch = new Chronometer();
		ch.start();
		magicSquare(3);
		ch.stop();
		System.out.println(ch.getFinalSeconds());
	}

	public static void magicSquare(int n) {
		int[][] matrix = new int[n][n];
		int possible = n * n;
		solve(matrix, n, possible, 0, 0);
	}

	private static void solve(int[][] matrix, int n, int possible, int currentRow, int currentCol) {
		if (currentRow >= n) {
			return;
		}
		for (int i = 1; i <= possible; i++) {
			if (isPossible(matrix, i)) {
				matrix[currentRow][currentCol] = i;
				if ((currentCol == n - 1) && (currentRow == n - 1)) {
					if (isSolution(matrix)) {
						print(matrix);
					}
				} else {
					int nextRow = calculateRow(n, currentRow, currentCol);
					int nextCol = calculateCol(n, currentCol, currentRow);
					solve(matrix, n, possible, nextRow, nextCol);
				}
			}
			matrix[currentRow][currentCol] = 0;
		}
	}

	private static boolean isSolution(int[][] matrix) {
		int fil = 0;
		int col = 0;
		int diag = 0;

		// Calculo que las filas tengan el mismo valor
		for (int[] row : matrix) {
			int rowSum = 0;
			for (int i : row) {
				rowSum += i;
			}
			if (fil == 0) {
				fil = rowSum;
			} else {
				if (fil != rowSum) {
					return false;
				}
			}
		}

		// Calculo que las columnas tengan el mismo valor
		for (int i = 0; i < matrix.length; i++) {
			int colSum = 0;
			for (int j = 0; j < matrix.length; j++) {
				colSum += matrix[j][i];
			}
			if (col == 0) {
				col = colSum;
			} else {
				if (col != colSum) {
					return false;
				}
			}
		}

		// Calculo que las diagonales tengan el mismo valor
		int diag1 = 0;
		int diag2 = 0;
		int currentCol = 0;
		for (int i = 0; i < matrix.length; i++) {
			diag1 += matrix[currentCol][currentCol];
			currentCol++;
		}
		currentCol = matrix.length - 1;
		for (int i = 0; i < matrix.length; i++) {
			diag2 += matrix[i][currentCol--];
		}
		if (diag1 != diag2) {
			return false;
		} else {
			diag = diag1;
		}

		// Calculo que todos tengan el mismo valor
		if (fil != col || fil != diag || col != diag) {
			return false;
		}

		return true;
	}

	private static int calculateCol(int n, int currentCol, int currentRow) {
		if ((currentCol + 1) == n) {
			return 0;
		} else {
			return currentCol + 1;
		}
	}

	private static int calculateRow(int n, int currentRow, int currentCol) {
		if ((currentCol + 1) == n) {
			return currentRow + 1;
		} else {
			return currentRow;
		}
	}

	private static boolean isPossible(int[][] matrix, int num) {
		boolean answer = true;
		for (int i = 0; i < matrix.length && answer; i++) {
			for (int j = 0; j < matrix.length && answer; j++) {
				if (matrix[i][j] == num) {
					answer = false;
				}
			}
		}
		return answer;
	}

	private static void print(int[][] matrix) {
		for (int[] row : matrix) {
			for (int i : row) {
				System.out.print(i + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}

}
