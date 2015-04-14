package model.element;

import model.board.Content;
import model.fighter.Fighter;

public class AttackBonus extends Bonus implements Content{

	private int value;
	
	public AttackBonus(int value){
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public boolean canWalkOver() {
		return false;
	}

	@Override
	public Content interact(Fighter hero) {
		hero.stronger(value);
		return null;
	}

}
