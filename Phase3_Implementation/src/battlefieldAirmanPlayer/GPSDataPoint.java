/*
 * File: HomeRun.java
 * Purpose: to create HomeRun objects for each home run line
 *          read in from the CSV file. Also provides getter
 *          functions for each attribute of the object.
 */

package battlefieldAirmanPlayer;

public class GPSDataPoint {
	
	//Variable declarations for all of the object attributes
	private float latitude;
	private float longitude;
	private float timeStamp;
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
	public GPSDataPoint(String[] dataPoint){
		this.latitude = Float.parseFloat(dataPoint[i]);
		i++;
		
		this.longitude = Float.parseFloat(dataPoint[i]);
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
	
	public float getLatitude(){
		return latitude;
	}
	
	public float getLongitude(){
		return longitude;
	}
	
	public float getTimeStamp(){
		return timeStamp;
	}

}