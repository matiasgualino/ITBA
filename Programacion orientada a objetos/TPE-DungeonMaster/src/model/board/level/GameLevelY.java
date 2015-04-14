package model.board.level;

import java.awt.Point;

public class GameLevelY extends GameLevel {

	@Override
	protected Point getHeroInitPosition() {
		return new Point(5, 6);
	}

	@Override
	protected void setContents() {
		addHero(1, 5, 6);

		// add walls
		for (int i = 1; i < 11; i++) {
			addWall(i, 1);
		}
		addWall(1, 2);
		for (int i = 3; i < 9 ; i++) {
			addWall(i, 3);
		}
		addWall(1, 3);
		addWall(1, 4);
		addWall(8, 4);
		for (int i = 1; i < 7 ; i++) {
			addWall(i, 5);
		}
		addWall(8, 5);
		for (int i = 6; i < 9 ; i++) {
			addWall(i, 6);
		}
		for (int i = 1; i < 7; i++) {
			addWall(i, 7);
		}
		for (int i = 3; i < 7 ; i++) {
			addWall(i, 8);
		}
		addWall(1, 8);
		addWall(8, 8);
		addWall(9, 8);
		addWall(1, 9);
		for (int i = 1; i < 12; i++) {
			addWall(i, 10);
		}
		for (int i = 2; i < 9; i++) {
			addWall(10, i);
		}

		// add enemies
		addGoblin(1,0,6);
		addGoblin(1,0,2);
		addGoblin(2,4,0);
		addSerpent(2,5,11);
		addGolem(3, 2, 8);
		addGolem(4, 7, 5);
		
		//add Bonuses
		addShield(5,11,11);
		addAttackBonus(3,7,11);
		addHealthPotion(7,4);
		addHealthBonus(10,2,9);
		addSword(10,2,4);
		addSword(5,11,0);
	}

}
