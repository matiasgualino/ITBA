package model.board.level;

import java.awt.Point;

public class GameLevelX extends GameLevel {

	@Override
	protected Point getHeroInitPosition() {
		return new Point(0, 0);
	}

	@Override
	protected void setContents() {
		addHero(1, 0, 0);

		// add walls
		for (int i = 0; i < 11; i++) {
			addWall(i, 1);
		}
		for (int i = 2; i < 10; i++) {
			addWall(10, i);
		}
		for (int i = 1; i < 11; i++) {
			addWall(i, 10);
		}
		for (int i = 4; i < 10; i++) {
			addWall(1, i);
		}
		for (int i = 1; i < 9; i++) {
			addWall(i, 3);
		}
		for (int i = 4; i < 9; i++) {
			addWall(8, i);
		}
		for (int i = 3; i < 8; i++) {
			addWall(i, 8);
		}
		for (int i = 5; i < 8; i++) {
			addWall(3, i);
		}
		for (int i = 4; i < 7; i++) {
			addWall(i, 5);
		}
		addWall(6, 6);
		addWall(5, 6);

		// add enemies
		addSerpent(1, 4, 0);
		addSerpent(2, 8, 0);
		addGoblin(2, 11, 0);
		addGoblin(3, 11, 4);
		addGoblin(3, 11, 7);
		addGoblin(3, 11, 10);
		addGoblin(4, 6, 11);
		addGoblin(4, 0, 7);
		addGoblin(5, 3, 2);
		addGoblin(5, 8, 2);
		addGolem(7, 9, 6);
		addGolem(8, 5, 9);
		addGolem(9, 4, 4);
		addGolem(10, 4, 6);

		// add Bonuses
		addShield(2, 6, 0);
		addSword(5, 11, 11);
		addShield(3, 0, 11);
		addSword(5, 7, 7);

	}

}
