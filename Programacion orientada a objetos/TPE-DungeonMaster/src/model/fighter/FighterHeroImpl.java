package model.fighter;

import model.board.Content;
import model.fighter.level.Level;

public class FighterHeroImpl implements FighterHero {

	private Level level;
	private int strengthBonus;
	private int health;
	private int experience;
	protected String movementState;
	
	public FighterHeroImpl(Level level){
		this.level = level;
		this.health = level.getMaxHealth();
		restartBonus();
		this.experience = 0;
		this.movementState = "down";
	}
	
	@Override
	public boolean canWalkOver() {
		return true;
	}

	@Override
	public Content interact(Fighter hero) {
		return this;
	}

	@Override
	public boolean isAlive() {
		return (health > 0);
	}

	@Override
	public void injured(int value) {
		if( (health - value) < 0)
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
		return level.getStrength()+strengthBonus;
	}

	@Override
	public void stronger(int value) {
		strengthBonus += value;
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

	@Override
	public void addExperience(int value) {
		if( (experience + value) >= getMaxExperience() ){
			int restExperience = (experience + value) - getMaxExperience();
			levelUp(restExperience);
			restartBonus();
		}
		else
			experience += value;
	}

	@Override
	public int getExperience() {
		return experience;
	}
	
	public int getStrenghtBonus(){
		return strengthBonus;
	}
	
	private void restartBonus(){
		this.strengthBonus = 0;
	}

	@Override
	public int getMaxExperience() {
		return level.getMaxExperience();
	}

	@Override
	public String getMovementState() {
		return movementState;
	}

	@Override
	public void setMovementState(String movement) {
		this.movementState = movement;
	}
	
	private void levelUp(int restExperience){
		experience = 0;
		level.levelUp();
		health = getMaxHealth();
		addExperience(restExperience);
	}
	
}
