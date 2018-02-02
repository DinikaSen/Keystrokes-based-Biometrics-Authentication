
public class Character {
	
	private char character;
	private long pressTime;
	private long releaseTime;
	
	public Character(char character){
		this.character = character;
	}
	
	public char getChar() {
		return character;
	}
	public void setChar(char character) {
		this.character = character;
	}
	public long getPressTime() {
		return pressTime;
	}
	public void setPressTime(long pressTime) {
		this.pressTime = pressTime;
	}
	public long getReleaseTime() {
		return releaseTime;
	}
	public void setReleaseTime(long releaseTime) {
		this.releaseTime = releaseTime;
	}

}
