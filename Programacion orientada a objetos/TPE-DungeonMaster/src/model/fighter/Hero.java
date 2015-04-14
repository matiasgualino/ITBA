package model.fighter;

import model.board.Content;
import model.fighter.level.Level;

public class Hero implements FighterHero {

	private FighterHero fighter;

	public Hero(FighterHero fighter) {
		this.fighter = fighter;
	}

	public FighterHero getFighter() {
		return fighter;
	}

	@Override
	public void addExperience(int value) {
		fighter.addExperience(value);
	}

	@Override
	public boolean canWalkOver() {
		return fighter.canWalkOver();
	}

	@Override
	public int getExperience() {
		return fighter.getExperience();
	}

	@Override
	public int getHealth() {
		return fighter.getHealth();
	}

	@Override
	public Level getLevel() {
		return fighter.getLevel();
	}

	@Override
	public int getMaxExperience() {
		return fighter.getMaxExperience();
	}

	@Override
	public int getMaxHealth() {
		return fighter.getMaxHealth();
	}

	@Override
	public int getStrength() {
		return fighter.getStrength();
	}

	@Override
	public boolean hasMaxLevel() {
		return fighter.hasMaxLevel();
	}

	@Override
	public void heal(int value) {
		fighter.heal(value);
	}

	@Override
	public void healFull() {
		fighter.healFull();
	}

	@Override
	public void injured(int value) {
		fighter.injured(value);
	}

	@Override
	public Content interact(Fighter hero) {
		return this;
	}

	@Override
	public boolean isAlive() {
		return fighter.isAlive();
	}

	@Override
	public void stronger(int value) {
		fighter.stronger(value);
	}

	
	@Override
	public String getMovementState() {
		return fighter.getMovementState();
	}

	@Override
	public void setMovementState(String movement) {
		fighter.setMovementState(movement);
	}
	
}