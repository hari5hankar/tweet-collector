package validate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import twitter4j.Twitter;

public class ValidateFollowers {
	HashMap<Long, Integer> userIDMap = new HashMap<Long, Integer>(); // Map <userId, number of retweets>
	HashMap<Long, Integer> validatedUserIDMap = new HashMap<Long, Integer>(); // Map <userId, number of
												// retweets>
	ArrayList<Long> followersList = new ArrayList<Long>();

	
	private void readFileIntoHashMap(Long userID){
		
		userIDMap.clear();
		
		String filename = Long.toString(userID) + "_NV";
		String line;
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));	
			
			while( (line = bufferedReader.readLine()) != null ){
				String[] tokens = line.split(",");
				userIDMap.put(Long.parseLong(tokens[0]), Integer.parseInt(tokens[1]));
			}
			bufferedReader.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	private void readFileIntoList(Long userID){
		
		followersList.clear();
		
		String filename = Long.toString(userID) + "_L";
		String line;
		
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			while((line = bufferedReader.readLine())!= null){
				followersList.add(Long.parseLong(line));
				
			}
			bufferedReader.close();
		}catch (IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public void validate(Long mainUserID) {
		
		userIDMap.clear(); 		followersList.clear();		validatedUserIDMap.clear();
		
		readFileIntoHashMap(mainUserID);
		readFileIntoList(mainUserID);
		
		for (Long userID : userIDMap.keySet()) {
			if ((followersList.contains(userID))) {
				validatedUserIDMap.put(userID, userIDMap.get(userID));
			}
		}
		
		writeHashMapToFile(mainUserID);
		userIDMap.clear(); 		followersList.clear();		validatedUserIDMap.clear();
	}

	private void writeHashMapToFile(long mainUserID) {
		
		String filename = Long.toString(mainUserID) + "_V";
		System.out.println("writeHashMapToFile()");
		try {
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(filename, true)));
			for (HashMap.Entry<Long, Integer> entry : validatedUserIDMap.entrySet()) {
				printWriter.println(entry.getKey() + "," + entry.getValue());
			}
			printWriter.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
