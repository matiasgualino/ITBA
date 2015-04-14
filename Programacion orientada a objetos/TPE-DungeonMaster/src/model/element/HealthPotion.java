package model.element;

import model.board.Content;
import model.fighter.Fighter;

public class HealthPotion implements Content {

	@Override
	public boolean canWalkOver() {
		return false;
	}

	@Override
	public Content interact(Fighter hero) {
		hero.healFull();
		return null;
	}

}
