package model.fighter;

import model.element.Shield;

public class ShieldHero extends Hero {

	private Shield shield;

	public ShieldHero(FighterHero fighter, Shield shield) {
		super(fighter);
		this.shield = shield;
	}

	@Override
	public void injured(int value) {
		int aux;
		aux = ( (value * 10) * shield.getValue() ) / 100;
		getFighter().injured(aux);
	}

}
