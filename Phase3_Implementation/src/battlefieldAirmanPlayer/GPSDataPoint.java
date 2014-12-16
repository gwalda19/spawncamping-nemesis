/*
 * File: GPSDataPoint.java
 * Purpose: to create HomeRun objects for each home run line
 *          read in from the CSV file. Also provides getter
 *          functions for each attribute of the object.
 */

package battlefieldAirmanPlayer;

public class GPSDataPoint {
	
	//Variable declarations for all of the object attributes
	private int boxId;
	private int pointId;
	private float latitude;
	private float longitude;
	private int xPos;
	private int yPos;
	private float timeStamp;
	private int i = 0;

	/*
	 * Function: GPSDataPoint(String[])
	 * Inputs: String[] dataPoint
	 *         array of strings that holds all the values of the current data
	 *         point being read in from the CSV file
	 * Description: Constructor for the GPSDataPoint class that creates a
	 *              GPSDataPoint object based on the values stored in the dataPoint
	 *              array.
	 */
	public GPSDataPoint(String[] dataPoint){
		this.boxId = Integer.parseInt(dataPoint[i]);
		i++;
		
		this.pointId = Integer.parseInt(dataPoint[i]);
		i++;
		
		this.latitude = Float.parseFloat(dataPoint[i]);
		i++;
		
		this.longitude = Float.parseFloat(dataPoint[i]);
		i++;
		
		this.xPos = Integer.parseInt(dataPoint[i]);
		i++;
		
		this.yPos = Integer.parseInt(dataPoint[i]);
		i++;
		
		this.timeStamp = Float.parseFloat(dataPoint[i]);
		i++;
		
		//this.xpos = calculateXpos();
		//this.ypos = calculateYpos();
		//calculateXpos();
		//calculateYpos();
	}
	
	//calculates the xposition of where the dot will be displayed on the image
	//public void calculateXpos(){
	 	//xpos = (570 * (int) (distance * Math.cos((PI * ((angle - 45.0)/180f)))))/329;
	//}
	
	//calculates the y position of where the dot will be displayed on the image
	//public void calculateYpos(){
		//ypos =750 - (570 * (int) (distance * (Math.sin(PI * ((angle - 45.0)/180f)))))/329;
	//}
	
	//below are all of the getter functions that return the specified object attribute value
	public int getBoxId(){
		return boxId;
	}
	
	public int getPointId(){
		return pointId;
	}
	
	public float getLatitude(){
		return latitude;
	}
	
	public float getLongitude(){
		return longitude;
	}
	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public float getTimeStamp(){
		return timeStamp;
	}

}