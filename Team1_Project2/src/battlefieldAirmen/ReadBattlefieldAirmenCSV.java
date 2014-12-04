/*
 * File: ReadCSV.java
 * Purpose: to parse a given CSV file line by line into an array of strings which is then passed
 *          to the BattlefieldAirmen class to create unique BattlefieldAirmen objects for each line in the CSV file
 */

package battlefieldAirmen;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadBattlefieldAirmenCSV {

	/*
	 * Function: read()
	 * Inputs: none
	 * Outputs: ArrayList<BattlefieldAirmen> masterBattlefieldAirmenList
	 *          an ArrayList of BattlefieldAirmen objects for every home run in the CSV file
	 * Description: given the location of a CSV file, this function parses the file
	 *              line by line based on the "," delimiter and puts each value into
	 *              an array of strings which is then fed into the BattlefieldAirmen class
	 *              to create a BattlefieldAirmen object which is then added to the
	 *              ArrayList called masterBattlefieldAirmenList
	 */
	public static ArrayList<BattlefieldAirmen> read() {
		String csvFile = "./src/BattlefieldAirmenData.csv";
		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";
		ArrayList<BattlefieldAirmen> masterBattlefieldAirmenList = new ArrayList<BattlefieldAirmen>();
		
		//do the following and catch any exceptions
		try {
			//create a new buffered reader and open the csv file for reading
			br = new BufferedReader(new FileReader(csvFile));
			
			//read in the first line which will be thrown out because we don't want
			//to capture the column headings of the CSV file.
			line = br.readLine();
			
			//as long as there is another line in the file, we want to parse it.
			while ((line = br.readLine()) != null) {
				//splits the csv line text into an array of strings
				String[] baData = line.split(csvSplitBy);
		
				//create an object for this line of data and add it to the masterBattlefieldAirmenList
				masterBattlefieldAirmenList.add(new	BattlefieldAirmen(baData));

			}
		} catch (FileNotFoundException e) {
			//file not found so print the stack trace for debugging
			e.printStackTrace();
		} catch (IOException e) {
			//some form of io exception so print the stack trace for debugging
			e.printStackTrace();
		} finally {
			//close io connections
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return masterBattlefieldAirmenList;
	}
}
