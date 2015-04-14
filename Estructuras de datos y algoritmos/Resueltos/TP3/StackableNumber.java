package TP3;

public class StackableNumber {

	private int number;
	private char[] characters;
	private int index;
	private int maxIndex;

	public StackableNumber(int number) {
		this.number = number;
		this.index=0;
		if (number == 0) {
			characters = null;
			this.maxIndex = 0;
		}
		else if (number == 1) {
			characters = null;
			this.maxIndex = 0;
		}
		else if (number == 2) {
			characters = "ABC".toCharArray();
			this.maxIndex = 3;
		}
		else if (number == 3) {
			characters = "DEF".toCharArray();
			this.maxIndex = 3;
		}
		else if (number == 4) {
			characters = "GHI".toCharArray();
			this.maxIndex = 3;
		}
		else if (number == 5) {
			characters = "JKL".toCharArray();
			this.maxIndex = 3;
		}
		else if (number == 6) {
			characters = "MNO".toCharArray();
			this.maxIndex = 3;
		}
		else if (number == 7) {
			characters = "PQRS".toCharArray();
			this.maxIndex = 4;
		}
		else if (number == 8) {
			characters = "TUV".toCharArray();
			this.maxIndex = 3;
		}
		else if (number == 9) {
			characters = "WXYZ".toCharArray();
			this.maxIndex = 4;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public int getNumber() {
		return number;
	}

	public char getFirstLetter() {
		if (number != 0 && number != 1)
			return characters[0];
		else
			return 0;
	}

	public char getCharacter() {
			return characters[index];
	}

	public boolean hasNext() {
		return index < maxIndex;
	}
}
