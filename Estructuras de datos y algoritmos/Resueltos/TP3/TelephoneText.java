package TP3;

import java.util.LinkedList;
import java.util.Queue;

public class TelephoneText {

	private int[] number;
	private char[] text;
	private int textIndex;
	Queue<StackableNumber> stack;

	public TelephoneText(int[] number) {
		this.number = number;
		this.stack = new LinkedList<StackableNumber>();
		this.text = new char[number.length];
	}

	public void printText() {
		for (int i : number) {
			StackableNumber aux = new StackableNumber(i);
			stack.add(aux);
			text[textIndex++] = aux.getFirstLetter();
		}
		System.out.println(text);
		text[textIndex] = stack.peek().getCharacter();
		System.out.println(text);
	}
}
