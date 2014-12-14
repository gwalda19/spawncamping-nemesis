/*
 * File: CreateLists.java
 * Purpose: to create unique lists of certain home run attributes which will be used
 *          to fill the JComboBox drop down boxes with valid field values to filter
 *          with
 */

package battlefieldAirmanPlayer;

import java.util.ArrayList;

public class CreateLists {

	/*
	 * Function: createDataList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<String> dateList
	 *          an ArrayList of String objects containing the date the home run was
	 *          hit
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the date attribute of the home run object. It then
	 *              compares that date, with any other dates already in the dateList
	 *              array. If the date already exists, don't add it. If the date does
	 *              not exist in the dateList array, then add it.
	 *
	public static ArrayList<String> createDateList(ArrayList<GPSDataPoint> masterList) {
		ArrayList<String> dateList = new ArrayList<String>();
		for (GPSDataPoint dataPoint: masterList){
			if (dateList.contains(dataPoint.getDate())){
			} else {
				dateList.add(dataPoint.getDate());
			}
		}
		return dateList;
	}
	*/
	
	/*
	 * Function: createDistanceList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<Integer> distanceList
	 *          an ArrayList of Integer objects containing the distance the home run was
	 *          hit
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the distance attribute of the home run object. It then
	 *              compares that distance, with any other values already in the
	 *              distanceList array. If the distance already exists, don't add it.
	 *              If the distance does not exist in the dateList array, then add it.
	 *
	public static ArrayList<Integer> createDistanceList(ArrayList<GPSDataPoint> masterList) {
		ArrayList<Integer> distanceList = new ArrayList<Integer>();
		for (GPSDataPoint dataPoint: masterList){
			if (distanceList.contains(dataPoint.getDistance())){
			} else {
				distanceList.add(dataPoint.getDistance());
			}
		}
		return distanceList;
	}
	*/
}
