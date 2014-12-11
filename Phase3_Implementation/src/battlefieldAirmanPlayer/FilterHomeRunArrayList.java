package battlefieldAirmanPlayer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class FilterHomeRunArrayList {
	private static ArrayList<HomeRun> filteredHomeRunArrayList = new ArrayList<HomeRun>(); 
	
	public FilterHomeRunArrayList () {
		
	}
	
	public static ArrayList<HomeRun> filter(ArrayList<HomeRun> HomeRunArrayList, String[] filterString) {
		try{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date startDate = df.parse(filterString[0]);
		Date endDate = df.parse(filterString[1]);
		Date homeRunDate;
		
		
		//traverse through homerunarraylist
		for (HomeRun hr: HomeRunArrayList){
			//check if filter applies, break out of iteration if necessary
			//make sure index aligns with way you store them in other file


			homeRunDate = df.parse(hr.getDate());

			if (startDate.compareTo(homeRunDate)>0) continue;//startDate is after homeRunDate
			if (endDate.compareTo(homeRunDate)<0) continue;//endDate is before homeRunDate
					    
	
				
				
				//if (filterString[0] > hr.getDate() && filterString[0] != "ALL") continue; //TODO:fix comparison
				//if (filterString[1] < hr.getDate() && filterString[1] != "ALL") continue; //TODO:fix comparison
				if (!(filterString[2].equals(hr.getBatterTeam())) && !(filterString[2].equals("ALL"))) continue; 
				if (!(filterString[3].equals(hr.getPitcherTeam())) && !(filterString[3].equals("ALL"))) continue;
				if (!(filterString[4].equals(hr.getBatterFullName())) && !(filterString[4].equals("ALL"))) continue;
				if (!(filterString[5].equals(hr.getPitcherFullName())) && !(filterString[5].equals("ALL"))) continue;
				if (Integer.parseInt(filterString[6]) != hr.getInning() && Integer.parseInt(filterString[6]) != 999) continue;
				if (!(filterString[7].equals(hr.getPhilliesOutcome())) && !(filterString[7].equals("ALL"))) continue;
				if (Integer.parseInt(filterString[8]) != hr.getOuts() && Integer.parseInt(filterString[8]) != 999) continue;
				if (Integer.parseInt(filterString[9]) != hr.getBalls() && Integer.parseInt(filterString[9]) != 999) continue;
				if (Integer.parseInt(filterString[10]) != hr.getStrikes() && Integer.parseInt(filterString[10]) != 999) continue;
				if (!(filterString[11].equals(hr.getTime())) && !(filterString[11].equals("ALL"))) continue; 
				if (Integer.parseInt(filterString[12]) != hr.getPhilliesScore() && Integer.parseInt(filterString[12]) != 999) continue;
				if (Integer.parseInt(filterString[13]) != hr.getVisitorsScore() && Integer.parseInt(filterString[13]) != 999) continue;
				if (Integer.parseInt(filterString[14]) != hr.getRunsBattedIn() && Integer.parseInt(filterString[14]) != 999) continue;
				if (!(filterString[15].equals(hr.getStance())) && !(filterString[15].equals("ALL"))) continue; 
				if (Integer.parseInt(filterString[16]) > hr.getDistance()) continue;
				if (Integer.parseInt(filterString[17]) < hr.getDistance()) continue;
				
				//all criteria match so add homerun to subset arraylist
				filteredHomeRunArrayList.add(hr);
			}//end for
		}//end try 
		catch (ParseException e) {
			  e.printStackTrace();
		}


		
		return filteredHomeRunArrayList;
	}
}
