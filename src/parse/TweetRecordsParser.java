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
	HashMap<Long, Integer> validatedUserIDMap; // Map <userId, number of
												// retweets>
	ArrayList<Long> followersList;
	Twitter twitter;

	public TweetRecordsParser() {
		System.out.println("TweetRecordsParser()");
		userIDMap = new HashMap<Long, Integer>();
		validatedUserIDMap = new HashMap<Long, Integer>();
		followersList = new ArrayList<Long>();
		twitter = new TwitterFactory(collect.ConfigBuilder.getConfig())
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
			String filename = Long.toString(mainUserID);
			parseFileIntoMap(filename);
			writeHashMapToFile(userIDMap, filename + "_NV"); // test, write non validated
			getFollowersIntoList(mainUserID);
			validate();
			writeHashMapToFile(validatedUserIDMap, filename + "_V");
		}
	}

	private void parseFileIntoMap(String filename) {
		System.out.println("parseFileIntoMap()");
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
	}

	private void getFollowersIntoList(long mainUserID) {
		System.out.println("getFollowersAndValidate()");

		String filename = Long.toString(mainUserID) + "_L";
		PrintWriter printWriter;

		try {

			printWriter = new PrintWriter(new BufferedWriter(new FileWriter(
					filename, true)));
			long cursor = -1L;
			int pageCount = 0;
			IDs ids;
			do {
				ids = twitter.getFollowersIDs(mainUserID, cursor);
				for (long userID : ids.getIDs()) {
					followersList.add(userID);
					printWriter.println(userID);
				}

				System.out.println("page:" + (++pageCount));
				if (pageCount == 14) {
					Thread.sleep(900000);
					pageCount = 0;
				}

			} while ((cursor = ids.getNextCursor()) != 0);

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validate() {
		for (Long userID : userIDMap.keySet()) {
			if ((followersList.contains(userID))) {
				validatedUserIDMap.put(userID, userIDMap.get(userID));
			}
		}
	}

	
	 
	private void writeHashMapToFile(HashMap<Long, Integer> map, String filename) {
		System.out.println("writeHashMapToFile()");
		try {
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(filename, true)));
			for (HashMap.Entry<Long, Integer> entry : map.entrySet()) {
				printWriter.println(entry.getKey() + "," + entry.getValue());
			}
			printWriter.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}