package src;

public class Player {
	String name;
	int xpos;
	int ypos;
	int xpre;
	int ypre;
	int point;
	String direction;

	public Player(String name, int xpos, int ypos, String direction) {
		this.name = name;
		this.xpos = xpos;
		this.ypos = ypos;
		this.direction = direction;
		this.point = 0;
	}
	//tilføjet
	public String getName() {
		return name;
	}

	public int getXpos() {
		return xpos;
	}
	public void setXpos(int xpos) {
		this.xpre = this.xpos;
		this.xpos = xpos;
	}
	public int getYpos() {
		return ypos;
	}
	public void setYpos(int ypos) {
		this.ypre = this.ypos;
		this.ypos = ypos;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public void addPoints(int p) {
		point+=p;
	}
	public String toString() {
		return name+":   "+point;
	}
	
	public String getPosition() {
		return "" + name + " " + xpos + " " + ypos + " " + direction;
	}

	public String getAllInfo() {
		return "" + name + " " + xpos + " " + ypos + " " + direction + " " + point;
	}

	public void updatePlayer(String s) {

	}

	public int getPreviousX() {
		return this.xpre;
	}

	public int getPreviousY() {
		return this.ypre;
	}

	public void setPoint(int points) {
		this.point = points;
	}
}
