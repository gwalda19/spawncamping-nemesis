package battlefieldAirmen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonFileGenerator {
	
	public static void makeJsonFile() {
		
		final String fileName = "json.json";
		
    	GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        
		ArrayList<BattlefieldAirmen> array = ReadBattlefieldAirmenCSV.read();
    	
    	try {
 
			File file = new File(fileName);
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			for(BattlefieldAirmen ba: array) {
	    		// append to JSON file
	            bw.write(gson.toJson(ba));
	            bw.newLine();
	    	}
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
    	

		
	}

}
