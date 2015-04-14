package model.fighter;

import model.board.Content;
import model.element.Sword;
import model.fighter.level.Level;

public class Golem implements Fighter {

	private Level level;
	private int health;

	public Golem(Level level) {
		this.level = level;
		this.health = level.getMaxHealth();
	}

	@Override
	public boolean canWalkOver() {
		return false;
	}

	@Override
	public Content interact(Fighter hero) {
		injured(hero.getStrength());
		hero.injured(getStrength());

		FighterHero fg = (FighterHero) hero;
				
		if (!isAlive()) {
			fg.addExperience(level.getValue());
			return new Sword(level.getValue());
		}

		return this;
	}

	@Override
	public boolean isAlive() {
		return (health > 0);
	}

	@Override
	public void injured(int value) {
		if ((health - value) < 0)
			health = 0;
		else
			health -= value;
	}

	@Override
	public void heal(int value) {
		if( (health + value) >= getMaxHealth())
			health = getMaxHealth();
		else
			health += value;
	}

	@Override
	public void healFull() {
		health = getMaxHealth();
	}

	@Override
	public int getStrength() {
		return level.getStrength();
	}

	@Override
	public void stronger(int value) {
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public int getMaxHealth() {
		return level.getMaxHealth();
	}

	@Override
	public Level getLevel() {
		return level;
	}

	@Override
	public boolean hasMaxLevel() {
		return false;
	}

}