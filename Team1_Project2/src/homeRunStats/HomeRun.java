/*
 * File: HomeRun.java
 * Purpose: to create HomeRun objects for each home run line
 *          read in from the CSV file. Also provides getter
 *          functions for each attribute of the object.
 */

package homeRunStats;

public class HomeRun {
	
	//Variable declarations for all of the object attributes 
	private int homeRunID;
	private String date;
	private String batterFullName = "";
	private String batterLastName = "";
	private String batterFirstName = "";
	private String batterTeam = "";
	private String pitcherFullName = "";
	private String pitcherLastName = "";
	private String pitcherFirstName = "";
	private String pitcherTeam = "";
	private int inning;
	private int distance;
	private float angle;
	private int outs;
	private int balls;
	private int strikes;
	private boolean manOnFirst = false;
	private boolean manOnSecond = false;
	private boolean manOnThird = false;
	private int gameTimeTemp = 0;
	private String time = "";
	private String philliesOutcome = "";
	private int philliesScore = 0;
	private int visitorsScore = 0;
	private int runsBattedIn = 0;
	private int pitchZone = 0;
	private float pitchXPos = 0.0f;
	private float pitchZPos = 0.0f;
	private String stance = "";
	private int xpos = 0;
	private int ypos = 0;
	private float PI = 3.14f;
	private int i = 0;

	/*
	 * Function: HomeRun(String[])
	 * Inputs: String[] homeRun
	 *         array of strings that holds all the values of the current home
	 *         run being read in from the CSV file
	 * Description: Constructor for the HomeRun class that creates a
	 *              HomeRun object based on the values stored in the homeRun
	 *              array.
	 */
	public HomeRun(String[] homeRun){
		this.homeRunID = Integer.parseInt(homeRun[i]);
		i++;
		
		this.date = homeRun[i];
		i++;
		
		this.batterFullName = homeRun[i];
		i++;
		
		this.batterLastName = homeRun[i];
		i++;
		
		this.batterFirstName = homeRun[i];
		i++;
		
		this.batterTeam = homeRun[i];
		i++;
		
		this.pitcherFullName = homeRun[i];
		i++;
		
		this.pitcherLastName = homeRun[i];
		i++;
		
		this.pitcherFirstName = homeRun[i];
		i++;
		
		this.pitcherTeam = homeRun[i];
		i++;
		
		this.inning = Integer.parseInt(homeRun[i]);
		i++;
		
		this.distance = Integer.parseInt(homeRun[i]);
		i++;
		
		this.angle = Float.parseFloat(homeRun[i]);
		i++;
		
		this.outs = Integer.parseInt(homeRun[i]);
		i++;
		
		this.balls = Integer.parseInt(homeRun[i]);
		i++;
		
		this.strikes = Integer.parseInt(homeRun[i]);
		i++;
		
		this.manOnFirst = Boolean.parseBoolean(homeRun[i]);
		i++;
		
		this.manOnSecond = Boolean.parseBoolean(homeRun[i]);
		i++;
		
		this.manOnThird = Boolean.parseBoolean(homeRun[i]);
		i++;
		
		this.gameTimeTemp = Integer.parseInt(homeRun[i]);
		i++;
		
		this.time = homeRun[i];
		i++;
		
		this.philliesOutcome = homeRun[i];
		i++;
		
		this.philliesScore = Integer.parseInt(homeRun[i]);
		i++;
		
		this.visitorsScore = Integer.parseInt(homeRun[i]);
		i++;
		
		this.runsBattedIn = Integer.parseInt(homeRun[i]);
		i++;
		
		this.pitchZone = Integer.parseInt(homeRun[i]);
		i++;
		
		this.pitchXPos = Float.parseFloat(homeRun[i]);
		i++;
		
		this.pitchZPos = Float.parseFloat(homeRun[i]);
		i++;
		
		this.stance = homeRun[i];
		i++;
		//this.xpos = calculateXpos();
		//this.ypos = calculateYpos();
		calculateXpos();
		calculateYpos();
	}
	
	//calculates the xposition of where the dot will be displayed on the image
	public void calculateXpos(){
	 	xpos = (570 * (int) (distance * Math.cos((PI * ((angle - 45.0)/180f)))))/329;
	}
	
	//calculates the y position of where the dot will be displayed on the image
	public void calculateYpos(){
		ypos =750 - (570 * (int) (distance * (Math.sin(PI * ((angle - 45.0)/180f)))))/329;
	}
	
	//below are all of the getter functions that return the specified object attribute value
	
	public int getXpos(){
		return xpos;
	}
	
	public int getYpos(){
		return ypos;
	}
	
	public int getHomeRunId(){
		return homeRunID;
	}
	 
	public String getDate(){
		return date;
	}
	
	public String getBatterFullName(){
		return batterFullName;
	}
	
	public String getBatterLastName(){
		return batterLastName;
	}
	
	public String getBatterFirstName(){
		return batterFirstName;
	}
	
	public String getBatterTeam(){
		return batterTeam;
	}
	
	public String getPitcherFullName(){
		return pitcherFullName;
	}
	
	public String getPitcherLastName(){
		return pitcherLastName;
	}
	
	public String getPitcherFirstName(){
		return pitcherFirstName;
	}
	public String getPitcherTeam(){
		return pitcherTeam;
	}
	
	public int getInning(){
		return inning;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public float getAngle(){
		return angle;
	}
	
	public int getOuts(){
		return outs;
	}
	
	public int getBalls(){
		return balls;
	}
	
	public int getStrikes(){
		return strikes;
	}
	
	public boolean getManOnFirst(){
		return manOnFirst;
	}
	
	public boolean getManOnSecond(){
		return manOnSecond;
	}
	
	public boolean getManOnThird(){
		return manOnThird;
	}
	
	public int getGameTimeTemp(){
		return gameTimeTemp;
	}
	
	public String getTime(){
		return time;
	}
	
	public String getPhilliesOutcome(){
		return philliesOutcome;
	}
	
	public int getPhilliesScore(){
		return philliesScore;
	}
	
	public int getVisitorsScore(){
		return visitorsScore;
	}
	
	public int getRunsBattedIn(){
		return runsBattedIn;
	}
	
	public int getPitchZone(){
		return pitchZone;
	}
	
	public float getPitchXPos(){
		return pitchXPos;
	}
	
	public float getPitchZPos(){
		return pitchZPos;
	}
	
	public String getStance() {
		return stance;
	}

}