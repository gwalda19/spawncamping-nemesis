/*
 * File: HomeRun.java
 * Purpose: to create HomeRun objects for each home run line
 *          read in from the CSV file. Also provides getter
 *          functions for each attribute of the object.
 */

package battlefieldAirmen;

import java.util.Calendar;

public class BattlefieldAirmen {
	
	public enum RecordingState {
		MISSION_STARTED,
		GPS,
		INCOMING,
		OUTGOING,
		MISSION_ENDED
	}
	
	//Variable declarations for all of the object attributes 
	private int baDataID;
	private int sourceID;
	private Calendar timestamp;
	private double lat;
	private double lon;
	private RecordingState recordingReason;
	private int destinationID;
	private String audioPath;
	private String videoPath;
	private int i = 0;

	/*
	 * Function: BattlefieldAirmen(String[])
	 * Inputs: String[] baData
	 *         array of strings that holds all the values of the current home
	 *         run being read in from the CSV file
	 * Description: Constructor for the HomeRun class that creates a
	 *              HomeRun object based on the values stored in the homeRun
	 *              array.
	 */
	public BattlefieldAirmen(String[] baData){		
		this.baDataID = Integer.parseInt(baData[i]);
		i++;
		
		//Date
		String date = baData[i];
		String[] dateArray = date.split("/");
		int mon = Integer.parseInt(dateArray[0]) - 1;
		int day = Integer.parseInt(dateArray[1]);
		int year = Integer.parseInt(dateArray[2]);
		i++;
		
		//Time
		String time = baData[i];
		String[] timeArray = time.split(":");
		int hour = Integer.parseInt(timeArray[0]);
		int minute = Integer.parseInt(timeArray[1]);
		int second = Integer.parseInt(timeArray[2]);
		i++;
		
		this.timestamp = Calendar.getInstance();
		timestamp.set(year, mon, day, hour, minute, second);

		// Get Lat Long
		this.lat = Double.parseDouble(baData[i]);
		i++;
		
		this.lon = Double.parseDouble(baData[i]);
		i++;
		
		// Set Recording Reason
		this.recordingReason = RecordingState.valueOf(baData[i]);
		i++;
		
		this.sourceID = Integer.parseInt(baData[i]);
		i++;
		
		this.destinationID = Integer.parseInt(baData[i]);
		i++;
		
		this.audioPath = baData[i];
		i++;
		
		this.videoPath = baData[i];
		i++;

	}

	public int getBaDataID() {
		return baDataID;
	}

	public int getSourceID() {
		return sourceID;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public RecordingState getRecordingReason() {
		return recordingReason;
	}

	public int getDestinationID() {
		return destinationID;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BattlefieldAirmen [baDataID=");
		builder.append(baDataID);
		builder.append(", sourceID=");
		builder.append(sourceID);
		builder.append(", timestamp=");
		builder.append(timestamp.getTime().toString());
		builder.append(", lat=");
		builder.append(lat);
		builder.append(", lon=");
		builder.append(lon);
		builder.append(", recordingReason=");
		builder.append(recordingReason);
		builder.append(", destinationID=");
		builder.append(destinationID);
		builder.append(", audioPath=");
		builder.append(audioPath);
		builder.append(", videoPath=");
		builder.append(videoPath);
		builder.append("]");
		return builder.toString();
	}

	
	

}