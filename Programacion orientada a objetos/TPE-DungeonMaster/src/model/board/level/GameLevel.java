package model.board.level;

import model.board.Board;
import model.element.AttackBonus;
import model.element.HealthBonus;
import model.element.HealthPotion;
import model.element.Shield;
import model.element.Sword;
import model.element.Wall;
import model.fighter.FighterHero;
import model.fighter.FighterHeroImpl;
import model.fighter.Goblin;
import model.fighter.Golem;
import model.fighter.Hero;
import model.fighter.Serpent;
import model.fighter.level.GoblinLevel;
import model.fighter.level.GolemLevel;
import model.fighter.level.HeroLevel;
import model.fighter.level.Level;
import model.fighter.level.SerpentLevel;

public abstract class GameLevel extends Board {
	
	/**
	 * Add hero of a given level to the board at position x, y
	 * It should be used once per level.
	 */
	protected void addHero(int level, int x, int y) {
		Level heroLevel = new HeroLevel(level, level);
		FighterHero fighter = new FighterHeroImpl(heroLevel);
		Hero hero = new Hero(fighter);
		get(x, y).setContent(hero);
	}
	
	/**
	 * Add a Goblin enemy of a given level at position x, y
	 * Adding two or more Goblins in the same position may produce an error
	 */
	protected void addGoblin(int level, int x, int y) {
		Level goblinLevel = new GoblinLevel(level);
		Goblin goblin = new Goblin(goblinLevel);
		getActionables().add(goblin);
		getEnemies().add(goblin);
		get(x, y).setContent(goblin);
	}
	
	/**
	 * Add a Golem enemy of a given level at position x, y
	 *  Adding two or more Golems in the same position may produce an error
	 */
	protected void addGolem(int level, int x, int y) {
		Level golemLevel = new GolemLevel(level);
		Golem golem = new Golem(golemLevel);
		getEnemies().add(golem);
		get(x, y).setContent(golem);
	}
	
	/**
	 * Add a Serpent enemy of a given level at position x, y
	 * Adding two or more Serpents in the same position may produce an error
	 */
	protected void addSerpent(int level, int x, int y) {
		Level serpentLevel = new SerpentLevel(level);
		Serpent serpent = new Serpent(serpentLevel);
		getEnemies().add(serpent);
		get(x, y).setContent(serpent);
	}
	
	/**
	 * Add a wall at position x, y
	 */
	protected void addWall(int x, int y) {
		get(x, y).setContent(new Wall());
	}
	
	/**
	 * Add an AttackBonus of a given value at position x, y
	 */
	protected void addAttackBonus(int value, int x, int y) {
		get(x, y).setContent(new AttackBonus(value));
	}
	
	/**
	 * Add a HealthBonus of a given vakue at position x, y
	 */
	protected void addHealthBonus(int value, int x, int y) {
		get(x, y).setContent(new HealthBonus(value));
	}
	
	/**
	 * Add a HealthPotion at position x, y
	 */
	protected void addHealthPotion(int x, int y) {
		get(x, y).setContent(new HealthPotion());
	}
	
	/**
	 * Add a Shield of a given value at position x, y
	 */
	protected void addShield(int value, int x, int y) {
		get(x, y).setContent(new Shield(value));
	}
	
	/**
	 * Add a Sword of a given value at position x, y
	 */
	protected void addSword(int value, int x, int y) {
		get(x, y).setContent(new Sword(value));
	}

	@Override
	public boolean gameOver() {
		return getEnemies().isEmpty() || !getHero().isAlive();
	}

	@Override
	public boolean playerWon() {
		return getHero().isAlive();
	}

}
