package battlefieldAirmanPlayer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FilterGPSDataPointList {
	private static ArrayList<GPSDataPoint> filteredGPSDataPointList = new ArrayList<GPSDataPoint>(); 
	
	public FilterGPSDataPointList () {
		
	}
	
	public static ArrayList<GPSDataPoint> filter(ArrayList<GPSDataPoint> GPSDataPointList, String[] filterString) {
		//try{
		//DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		//Date startDate = df.parse(filterString[0]);
		//Date endDate = df.parse(filterString[1]);
		//Date homeRunDate;
		
		
		//traverse through GPSDataPointList
		for (GPSDataPoint dataPoint: GPSDataPointList){
			//check if filter applies, break out of iteration if necessary
			//make sure index aligns with way you store them in other file


			//homeRunDate = df.parse(dataPoint.getDate());

			//if (startDate.compareTo(homeRunDate)>0) continue;//startDate is after homeRunDate
			//if (endDate.compareTo(homeRunDate)<0) continue;//endDate is before homeRunDate
					    
	
				
				/*
				//if (filterString[0] > hr.getDate() && filterString[0] != "ALL") continue; //TODO:fix comparison
				//if (filterString[1] < hr.getDate() && filterString[1] != "ALL") continue; //TODO:fix comparison
				if (!(filterString[2].equals(dataPoint.getBatterTeam())) && !(filterString[2].equals("ALL"))) continue; 
				if (!(filterString[3].equals(dataPoint.getPitcherTeam())) && !(filterString[3].equals("ALL"))) continue;
				if (!(filterString[4].equals(dataPoint.getBatterFullName())) && !(filterString[4].equals("ALL"))) continue;
				if (!(filterString[5].equals(dataPoint.getPitcherFullName())) && !(filterString[5].equals("ALL"))) continue;
				if (Integer.parseInt(filterString[6]) != dataPoint.getInning() && Integer.parseInt(filterString[6]) != 999) continue;
				if (!(filterString[7].equals(dataPoint.getPhilliesOutcome())) && !(filterString[7].equals("ALL"))) continue;
				if (Integer.parseInt(filterString[8]) != dataPoint.getOuts() && Integer.parseInt(filterString[8]) != 999) continue;
				if (Integer.parseInt(filterString[9]) != dataPoint.getBalls() && Integer.parseInt(filterString[9]) != 999) continue;
				if (Integer.parseInt(filterString[10]) != dataPoint.getStrikes() && Integer.parseInt(filterString[10]) != 999) continue;
				if (!(filterString[11].equals(dataPoint.getTime())) && !(filterString[11].equals("ALL"))) continue; 
				if (Integer.parseInt(filterString[12]) != dataPoint.getPhilliesScore() && Integer.parseInt(filterString[12]) != 999) continue;
				if (Integer.parseInt(filterString[13]) != dataPoint.getVisitorsScore() && Integer.parseInt(filterString[13]) != 999) continue;
				if (Integer.parseInt(filterString[14]) != dataPoint.getRunsBattedIn() && Integer.parseInt(filterString[14]) != 999) continue;
				if (!(filterString[15].equals(dataPoint.getStance())) && !(filterString[15].equals("ALL"))) continue; 
				if (Integer.parseInt(filterString[16]) > dataPoint.getDistance()) continue;
				if (Integer.parseInt(filterString[17]) < dataPoint.getDistance()) continue;
				*/
				//all criteria match so add homerun to subset arraylist
				filteredGPSDataPointList.add(dataPoint);
			}//end for
		//}//end try 
		//catch (ParseException e) {
			  //e.printStackTrace();
		//}		
		return filteredGPSDataPointList;
	}
}
