package model.fighter;


public interface FighterHero extends Fighter {

	public void addExperience(int value);
	
	public int getExperience();
	
	public int getMaxExperience();
	
	public String getMovementState();
	
	public void setMovementState(String movement);
	
}
