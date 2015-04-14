package game.backend.element;

public class Wall extends Element {
	
	@Override
	public boolean isMovable() {
		return false;
	}
	
	public boolean isWall(){
		return true;
	}
	
	@Override
	public String getKey() {
		return "WALL";
	}
}
