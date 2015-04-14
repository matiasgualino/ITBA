package game.backend.cell;

import game.backend.Grid;


public class Jelly extends Cell {

	private boolean isJelly;

	/**
	 * Instantiates a new jelly.
	 */
	public Jelly(Grid grid) {
		super(grid);
		this.isJelly = true;
	}

	@Override
	public void clearContent() {
		if(isJelly){
			this.isJelly=false;
			getGrid().state().setJellies(getGrid().state().getJellies()-1);
		}
		super.clearContent();
	}

	@Override
	public String getKey() {
		if (isJelly)
			return "JELLY";
		else
			return super.getKey();
	}

}
