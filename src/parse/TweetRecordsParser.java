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

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;


public class TweetRecordsParser {
	
	static HashMap<Long, Integer> hashMap = new HashMap<Long, Integer>();
	static ArrayList<Long> followers = new ArrayList<Long>();
	
	public static void main(String[] args){
		
		parseFile();
		getFollowers(1234); 
		validate();
		writeToFile();
		
	}
	
	public static void parseFile(){
		String line;
		try{
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("cnnbrk_round1.txt")));
			
			while ((line = bufferedReader.readLine()) !=null) {
				System.out.println(line);
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
		
		Twitter twitter = new TwitterFactory(collect.ConfigBuilder.getConfig()).getInstance();
		
		try{
			PagableResponseList<User> pagableFollowers;
	        pagableFollowers = twitter.getFollowersList(userId, -1);   	
			while(pagableFollowers.hasNext()){
			   for (User user : pagableFollowers) {
                    followers.add(user.getId());
                }
		   }
	    }catch(TwitterException twitterException){
		    	twitterException.printStackTrace();
		    }
	}
	
	public static void validate(){
		
		
		
	}
	
	public static void writeToFile(){
		
		try{
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("hashMap.txt", true)));
			for(HashMap.Entry<Long, Integer> entry : hashMap.entrySet()){
			    printWriter.println(entry.getKey() + "," + entry.getValue());
			}
			printWriter.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}	
	}
	
}