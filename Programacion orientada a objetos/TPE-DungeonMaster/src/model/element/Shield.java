package model.element;

import model.board.Content;
import model.fighter.Fighter;
import model.fighter.FighterHero;
import model.fighter.ShieldHero;

public class Shield extends Bonus implements Content{

private int value;
	
	public Shield(int value){
		this.value = value;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public boolean canWalkOver() {
		return true;
	}
	
	@Override
	public Content interact(Fighter hero) {
		FighterHero fg = (FighterHero) hero;
		return new ShieldHero(fg, this);
	}

}
