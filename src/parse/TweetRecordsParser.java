package parse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class TweetRecordsParser {

	HashMap<Long, Integer> userIDMap; // Map <userId, number of retweets>
	Map<Long, Integer> sortedUserIDMap; // Map <userId, number of retweets>

	ArrayList<Long> followersList;
	Twitter twitter;

	public TweetRecordsParser() {
		System.out.println("TweetRecordsParser()");
		userIDMap = new HashMap<Long, Integer>();
		sortedUserIDMap = new LinkedHashMap<Long, Integer>();
		followersList = new ArrayList<Long>();
		twitter = new TwitterFactory(authorization.ConfigBuilder.getConfig())
				.getInstance();
	}

	// TODO new folder for each round

	/*
	 * will generate one file, for each user specified in UserIDsArray.
	 * each file will contain a list: <follower Id, number of retweets>,
	 */

	public void parseAndWriteToFile(Long mainUserID, String filepath) {
		
		userIDMap.clear();		followersList.clear();		sortedUserIDMap.clear();
		System.out.println(filepath);
		System.out.println("parseAndWriteToFile()");
		String line;

		HashSet<Long> set = new HashSet<>();
		String filename = filepath + "\\" + Long.toString(mainUserID);
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(filename)));

			boolean isTwoLines = false;
			long previousLineTweetID = 0L;

			while ((line = bufferedReader.readLine()) != null) {

				String[] tokens = line.split(",");
				int length = tokens.length;
				if (!(line.contains(";"))) {
					if(isTwoLines){
						continue;
					}
					long tweetID = Long.parseLong(tokens[1]);
					isTwoLines = true;
					previousLineTweetID = tweetID;
				}else{
					//System.out.println(line);
					if(isTwoLines){
						long tempPreviousLineTweetID = previousLineTweetID;
						isTwoLines = false;
						previousLineTweetID = 0L;
						if(!set.contains(tempPreviousLineTweetID)){
							set.add(tempPreviousLineTweetID);
						}else{
							continue;
						}

						long userID = Long.parseLong(tokens[length - 5]);

						if (userIDMap.get(userID) == null) {
							userIDMap.put(userID, 1);
						} else {
							userIDMap.put(userID, userIDMap.get(userID) + 1);
						}

					}else{
						Long tweetID = Long.parseLong(tokens[1]);
						if(!set.contains(tweetID)){
							set.add(tweetID);
						}else{
							continue;
						}

						// 5th from last element is the userID in our String
						long userID = Long.parseLong(tokens[length - 5]);

						if (userIDMap.get(userID) == null) {
							userIDMap.put(userID, 1);
						} else {
							userIDMap.put(userID, userIDMap.get(userID) + 1);
						}

					}
				}

			}
			bufferedReader.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		System.out.println(userIDMap.toString());
		sortedUserIDMap = sortByComparator(userIDMap);
		System.out.println(sortedUserIDMap.toString());
		writeHashMapToFile(mainUserID, filepath);
		userIDMap.clear();		followersList.clear(); 		sortedUserIDMap.clear();
	}

	private void writeHashMapToFile(long userID, String filepath) {

		System.out.println(filepath);
		String filename = filepath + "\\" + Long.toString(userID) + "_NV.csv" ;

		System.out.println("writeHashMapToFile()");
		try {
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(filename, true)));
			for (Map.Entry<Long, Integer> entry : sortedUserIDMap.entrySet()) {
				printWriter.println(entry.getKey() + "," + entry.getValue());
			}
			printWriter.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		System.out.println("writeHashMapToFile() done");
	}


	//mykong.com
	private static LinkedHashMap<Long, Integer> sortByComparator(Map<Long, Integer> unsortedMap) {

		// Convert Map to List
		List<Map.Entry<Long, Integer>> list =
				new LinkedList<Map.Entry<Long, Integer>>(unsortedMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
			public int compare(Map.Entry<Long, Integer> o1,
							   Map.Entry<Long, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		LinkedHashMap<Long, Integer> sortedMap = new LinkedHashMap<Long, Integer>();
		for (Iterator<Map.Entry<Long, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Long, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}