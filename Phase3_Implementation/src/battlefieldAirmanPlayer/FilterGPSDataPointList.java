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
	
	public static ArrayList<GPSDataPoint> filter(ArrayList<GPSDataPoint> GPSDataPointList, int endPoint) {
		//try{
		//traverse through GPSDataPointList
			for (GPSDataPoint dataPoint: GPSDataPointList){
			//create a temporary list including only the data points in the range selected by slider
			//make sure index aligns with way you store them in other file
				if (dataPoint.getPointId() <= endPoint){
					//all criteria match so add homerun to subset arraylist
					filteredGPSDataPointList.add(dataPoint);
				}
			}//end for
		//}//end try 
		//catch (ParseException e) {
			  //e.printStackTrace();
		//}		
		return filteredGPSDataPointList;
	}
}
