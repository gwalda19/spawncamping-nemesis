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
			bw.write("[");

			
			for(int i=0; i<array.size(); i++) {
				BattlefieldAirmen ba = array.get(i);
				// append to JSON file
				bw.newLine();
	            bw.write(gson.toJson(ba));
	            if(i !=array.size()-1) {
	            	bw.write(",");
	            }

			}
            bw.newLine();
			bw.write("]");
			
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
    	

		
	}

}
