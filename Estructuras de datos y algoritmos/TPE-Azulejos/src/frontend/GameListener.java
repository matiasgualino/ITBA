package frontend;

import backend.Cell;

public interface GameListener {
	
	public void gridUpdated();
	
	public void cellExplosion(Cell e);
	
}