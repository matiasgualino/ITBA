package model.fighter.level;

public class GolemLevel extends Level {

	private double s = 1.35;
	private double f = 1;

	public GolemLevel(int value) {
		super(value, value);
	}

	@Override
	public int getMaxHealth() {
		// Piso { [ (N + 3)^2 ñ 10 ] * S }
		return (int) Math.floor((Math.pow(getValue() + 3, 2) - 10) * s);
	}

	@Override
	public int getStrength() {
		// Piso [ ( N^2 + 5 * N ) * 0.5 * F ]
		return (int) Math.floor(((getValue() * getValue()) + (5 * getValue()))
				* 0.5 * f);
	}

	@Override
	public int getMaxExperience() {
		return 0;
	}

}
