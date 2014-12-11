/*
 * File: CreateLists.java
 * Purpose: to create unique lists of certain home run attributes which will be used
 *          to fill the JComboBox drop down boxes with valid field values to filter
 *          with
 */

package homeRunStats;

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
	 */
	public static ArrayList<String> createDateList(ArrayList<HomeRun> masterList) {
		ArrayList<String> dateList = new ArrayList<String>();
		for (HomeRun hr: masterList){
			if (dateList.contains(hr.getDate())){
			} else {
				dateList.add(hr.getDate());
			}
		}
		return dateList;
	}
	
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
	 */
	public static ArrayList<Integer> createDistanceList(ArrayList<HomeRun> masterList) {
		ArrayList<Integer> distanceList = new ArrayList<Integer>();
		for (HomeRun hr: masterList){
			if (distanceList.contains(hr.getDistance())){
			} else {
				distanceList.add(hr.getDistance());
			}
		}
		return distanceList;
	}
	
	/*
	 * Function: createBatterFullNameList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<String> batterFullNameList
	 *          an ArrayList of String objects containing the batters full name that
	 *          hit the home run.
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the batterFullName attribute of the home run object. It
	 *              then compares that name, with any other names already in the
	 *              batterFullNameList array. If the name already exists, don't add it.
	 *              If the name does not exist in the array, then add it.
	 */
	public static ArrayList<String> createBatterFullNameList(ArrayList<HomeRun> masterList) {
		ArrayList<String> batterFullNameList = new ArrayList<String>();
		batterFullNameList.add("ALL");
		for (HomeRun hr: masterList){
			if (batterFullNameList.contains(hr.getBatterFullName())){
			} else {
				batterFullNameList.add(hr.getBatterFullName());
			}
		}
		return batterFullNameList;
	}
	
	/*
	 * Function: createBatterTeamList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<String> batterTeamList
	 *          an ArrayList of String objects containing the batters team name
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the batterTeam attribute of the home run object. It
	 *              then compares that team name, with any other names already in the
	 *              batterTeamList array. If the name already exists, don't add it.
	 *              If the name does not exist in the array, then add it.
	 */
	public static ArrayList<String> createBatterTeamList(ArrayList<HomeRun> masterList) {
		ArrayList<String> batterTeamList = new ArrayList<String>();
		batterTeamList.add("ALL");
		for (HomeRun hr: masterList){
			if (batterTeamList.contains(hr.getBatterTeam())){
			} else {
				batterTeamList.add(hr.getBatterTeam());
			}
		}
		return batterTeamList;
	}
	
	/*
	 * Function: createPitcherFullNameList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<String> pitcherFullNameList
	 *          an ArrayList of String objects containing the pitchers full name that
	 *          gave up the home run.
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the pitcherFullName attribute of the home run object. It
	 *              then compares that name, with any other names already in the
	 *              pitcherFullNameList array. If the name already exists, don't add it.
	 *              If the name does not exist in the array, then add it.
	 */
	public static ArrayList<String> createPitcherFullNameList(ArrayList<HomeRun> masterList) {
		ArrayList<String> pitcherFullNameList = new ArrayList<String>();
		pitcherFullNameList.add("ALL");
		for (HomeRun hr: masterList){
			if (pitcherFullNameList.contains(hr.getPitcherFullName())){
			} else {
				pitcherFullNameList.add(hr.getPitcherFullName());
			}
		}
		return pitcherFullNameList;
	}
	
	/*
	 * Function: createPitcherTeamList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<String> pitcherTeamList
	 *          an ArrayList of String objects containing the pitchers team name
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the pitcherTeam attribute of the home run object. It
	 *              then compares that name, with any other names already in the
	 *              pitcherTeamList array. If the name already exists, don't add it.
	 *              If the name does not exist in the array, then add it.
	 */
	public static ArrayList<String> createPitcherTeamList(ArrayList<HomeRun> masterList) {
		ArrayList<String> pitcherTeamList = new ArrayList<String>();
		pitcherTeamList.add("ALL");
		for (HomeRun hr: masterList){
			if (pitcherTeamList.contains(hr.getPitcherTeam())){
			} else {
				pitcherTeamList.add(hr.getPitcherTeam());
			}
		}
		return pitcherTeamList;
	}
	
	/*
	 * Function: createInningsList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<Integer> inningsList
	 *          an ArrayList of Integer objects containing the innings that the home
	 *          runs were hit
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the inning attribute of the home run object. It
	 *              then compares that inning, with any other innings already in the
	 *              inningsList array. If the inning already exists, don't add it.
	 *              If the inning does not exist in the array, then add it.
	 */
	public static ArrayList<Integer> createInningsList(ArrayList<HomeRun> masterList) {
		ArrayList<Integer> inningsList = new ArrayList<Integer>();
		for (HomeRun hr: masterList){
			if (inningsList.contains(hr.getInning())){
			} else {
				inningsList.add(hr.getInning());
			}
		}
		return inningsList;
	}
	
	/*
	 * Function: createPhilliesScoreList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<Integer> philliesScoreList
	 *          an ArrayList of Integer objects containing the Phillies score at the
	 *          time the home run was hit
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the philliesScore attribute of the home run object. It
	 *              then compares that value, with any other values already in the
	 *              philliesScoreList array. If the value already exists, don't add it.
	 *              If the value does not exist in the array, then add it.
	 */
	public static ArrayList<Integer> createPhilliesScoreList(ArrayList<HomeRun> masterList) {
		ArrayList<Integer> philliesScoreList = new ArrayList<Integer>();
		for (HomeRun hr: masterList){
			if (philliesScoreList.contains(hr.getPhilliesScore())){
			} else {
				philliesScoreList.add(hr.getPhilliesScore());
			}
		}
		return philliesScoreList;
	}
	
	/*
	 * Function: createVisitorScoreList(ArrayList<HomeRun> masterList)
	 * Inputs: ArrayList<HomeRun> masterList
	 *         the master list of all home run objects read in from the CSV file
	 * Outputs: ArrayList<Integer> visitorScoreList
	 *          an ArrayList of Integer objects containing the visiting teams score
	 *          at the time the home run was hit
	 * Description: goes through each home run in the master list of home run objects
	 *              and grabs the visitorsScore attribute of the home run object. It
	 *              then compares that value, with any other values already in the
	 *              visitorScoreList array. If the value already exists, don't add it.
	 *              If the value does not exist in the array, then add it.
	 */
	public static ArrayList<Integer> createVisitorScoreList(ArrayList<HomeRun> masterList) {
		ArrayList<Integer> visitorScoreList = new ArrayList<Integer>();
		for (HomeRun hr: masterList){
			if (visitorScoreList.contains(hr.getVisitorsScore())){
			} else {
				visitorScoreList.add(hr.getVisitorsScore());
			}
		}
		return visitorScoreList;
	}
}
