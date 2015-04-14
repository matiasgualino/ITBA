package model.fighter.level;

public class HeroLevel extends Level {

	public HeroLevel(int value, int maxLevel) {
		super(value, maxLevel);
	}

	@Override
	public int getMaxHealth() {
		return getValue() * 10;
	}

	@Override
	public int getStrength() {
		return getValue() * 5;
	}

	@Override
	public int getMaxExperience() {
		return getValue() * 5;
	}

}
