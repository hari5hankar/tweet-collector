package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TweetRecordsParser {

	HashMap<Long, Integer> userIDMap; // Map <userId, number of retweets>
	ArrayList<Long> followersList;
	Twitter twitter;

	public TweetRecordsParser() {
		System.out.println("TweetRecordsParser()");
		userIDMap = new HashMap<Long, Integer>();
		followersList = new ArrayList<Long>();
		twitter = new TwitterFactory(authorization.ConfigBuilder.getConfig())
				.getInstance();
	}

	// TODO new folder for each round

	/*
	 * will generate one file, for each user specified in UserIDsArray.
	 * each file will contain a list: <follower Id, number of retweets>,
	 */

	public void parse(Long... userIDsArray) {
		
		System.out.println("parse()");

		for (Long mainUserID : userIDsArray) {
			parseAndWriteToFile(mainUserID);
		}
	}

	public void parseAndWriteToFile(Long mainUserID) {
		
		userIDMap.clear();		followersList.clear();
		String filename = Long.toString(mainUserID);
		
		System.out.println("parseAndWriteToFile()");
		String line;
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(filename)));

			while ((line = bufferedReader.readLine()) != null) {
				if (!(line.contains(";"))) {
					continue;
				}
				System.out.println(line);
				String[] tokens = line.split(",");
				int length = tokens.length;
				// 5th from last element is the userID in our String
				long userID = Long.parseLong(tokens[length - 5]);

				if (userIDMap.get(userID) == null) {
					userIDMap.put(userID, 1);
				} else {
					userIDMap.put(userID, userIDMap.get(userID) + 1);
				}

			}
			bufferedReader.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		
		writeHashMapToFile(mainUserID);
		userIDMap.clear();		followersList.clear();
	}

	 
	private void writeHashMapToFile(long userID) {
		
		String filename = Long.toString(userID) + "_NV"; 
		System.out.println("writeHashMapToFile()");
		try {
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(filename, true)));
			for (HashMap.Entry<Long, Integer> entry : userIDMap.entrySet()) {
				printWriter.println(entry.getKey() + "," + entry.getValue());
			}
			printWriter.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		
	}
}