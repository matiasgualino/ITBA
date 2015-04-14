package game.backend.cell;

import game.backend.Grid;
import game.backend.element.*;
import game.backend.move.Direction;

public class WeirdWall extends Cell{
	
	/**
	 * Instantiates a new weird wall.
	 */
	public WeirdWall(Grid grid){
		super(grid);
	}
	
	@Override
	public boolean isEmpty() {
		return getAround()[Direction.UP.ordinal()].isEmpty();
	}

	@Override
	public boolean isMovable() {
		return getAround()[Direction.UP.ordinal()].isMovable();
	}
	
	@Override
	public Element getAndClearContent() {
		Element elem = getAround()[Direction.UP.ordinal()].getAndClearContent();
		return elem;
	}

	@Override
	public String getKey(){
	 	return "WEIRDWALL";
	 }

}
