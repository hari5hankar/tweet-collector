package collect;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import twitter4j.Status;
import twitter4j.User;



public class TweetRecord {
	
	//Status related
	Date statusDate; 
	long statusId; 
	String statusText;
	//String statusSource; //android, iphone, browser?
	//long statusInReplyToStatusId; //which tweet was replied to?
	//long statusInReplyToUserId; //which user's tweet was replied tp check
	//String getInReplyToScreenName; 
	//GeoLocation statusGeoLocation; //check
	//Place statusPlace; //check
	//boolean statusIsRetweeted; //has this status been retweeted? useful for filtering										
	boolean statusIsRetweet; //is this status a retweet?
	//int statusRetweetCount; //number of times this status has been retweeted
	//String statusGetLang; 
	//boolean statusIsEnglish; //for filtering
	
	//User related
	long userID;  //userID of status
	//String userName;
	String userScreenName;
	//String userLocation; //check
	//String userDescription; 
	//boolean userIsContributorsEnabled;
	//String userURL;
	//boolean userIsProtected;
	//int userFollowersCount; //check
	//int userFriendsCount; //check
	//int userStatusesCount; //check
	//boolean userIsVerified; //check
	
	//original tweet related
	long originalTweetId;
	long originalUserId;
	String originalUserScreenName;
	
	
	
/*
String columns[] = {"statusDate", "statusId", "statusText",  "statusInReplyToStatusId",  "statusInReplyToUserId",  "statusIsRetweeted", 
		"statusIsRetweet", "statusRetweetCount", "statusIsEnglish", 
		"userID", "userScreenName", "userLocation", "userFollowersCount", "userFriendsCount", "userIsVerified"
	
 */
	
	public TweetRecord(Status retweetedStatus) {
		User user = retweetedStatus.getUser();
		
		this.statusDate = retweetedStatus.getCreatedAt();
		this.statusId = retweetedStatus.getId();
		this.statusText = retweetedStatus.getText(); //new
		this.statusIsRetweet = retweetedStatus.isRetweet();
		
		this.userID = user.getId();
		this.userScreenName = user.getScreenName();
		
		Status originalTweet = retweetedStatus.getRetweetedStatus();
		
		originalTweetId = originalTweet.getId();
		originalUserId = originalTweet.getUser().getId();
		originalUserScreenName = originalTweet.getUser().getScreenName();
	}

	@Override
	public String toString() {
		//this.statusText = status.getText();
		return  		statusDate 
				+ "," + statusId
				+ "," + statusText
				+ "," + statusIsRetweet
				
				+ "," + userID
				+ "," + userScreenName
				
				+ "," + originalTweetId
				+ "," + originalUserId
				+ "," + originalUserScreenName
				+ ";";
	}
		
	public void appendToFile(long originalUserID){
		 String filename = Long.toString(originalUserID);
		try{
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			printWriter.println(this.toString());
			printWriter.close();
		} catch (IOException e){
			e.printStackTrace();
		}

	}

}
