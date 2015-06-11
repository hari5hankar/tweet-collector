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

import collect.ConfigBuilder;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;


public class TweetRecordsParser {
	
	static HashMap<Long, Integer> hashMap = new HashMap<Long, Integer>();
	static ArrayList<Long> followers = new ArrayList<Long>();
	static Twitter twitter;
	
	public static void main(String[] args){
		
		twitter = new TwitterFactory(collect.ConfigBuilder.getConfig()).getInstance();
		
		parseFileIntoHashMap("428333");
		writeHashMapToFile("428333_map_novalidation");
		getFollowers(428333L); 
		validate();
		writeHashMapToFile("428333_map_aftervalidation");			
		
	}
	
	public static void parseFileIntoHashMap(String filename){
		String line;
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			
			while ((line = bufferedReader.readLine()) !=null) {
				String[] tokens = line.split(",");
				int length = tokens.length;
				long userId = Long.parseLong(tokens[length - 5]);
				if (hashMap.get(userId) == null){
					hashMap.put(userId, 1);	
				} else{
					hashMap.put(userId, hashMap.get(userId) +1 );
				}
			}
			
			bufferedReader.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	public static void getFollowers(long userId){
		
	/*	 try {
	            // get followers
	            long cursor = -1;
	            PagableResponseList<User> pagableFollowers;
	            do {
	                pagableFollowers = twitter.getFollowersList("cnnbrk", cursor);
	                for (User user : pagableFollowers) {
	                	followers.add(user.getId()); // ArrayList<User>
	                	System.out.println(user.getId());
	                }
	            } while ((cursor = pagableFollowers.getNextCursor()) != 0);

	        } catch (TwitterException twitterException) {
	            twitterException.printStackTrace();
	        }
		 
*/		 
		 try {
		        IDs ids = twitter.getFollowersIDs(userId, -1);
		        do {
		            for (long id : ids.getIDs()) {               
		                System.out.println(id);
		            }
		        } while (ids.hasNext());

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
	}
	
	public static void validate(){
		
	}
	
	public static void writeHashMapToFile(String filename){
		
		try{
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			for(HashMap.Entry<Long, Integer> entry : hashMap.entrySet()){
			    printWriter.println(entry.getKey() + "," + entry.getValue());
			}
			printWriter.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}	
	}
	
}