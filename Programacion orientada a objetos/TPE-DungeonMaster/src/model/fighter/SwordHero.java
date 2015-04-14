package model.fighter;

import model.element.Sword;

public class SwordHero extends Hero {

	private Sword sword;

	public SwordHero(FighterHero fighter, Sword sword) {
		super(fighter);
		this.sword = sword;
	}

	@Override
	public int getStrength() {
		return getFighter().getStrength() + sword.getValue();
	}
	
}
