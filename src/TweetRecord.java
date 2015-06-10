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
	long statusInReplyToStatusId; //which tweet was retweeted?
	long statusInReplyToUserId; //check
	//String getInReplyToScreenName; 
	//GeoLocation statusGeoLocation; //check
	//Place statusPlace; //check
	boolean statusIsRetweeted; //has this status been retweeted? useful for filtering										
	boolean statusIsRetweet; //is this status a retweet?
	int statusRetweetCount; 
	//String statusGetLang; 
	boolean statusIsEnglish; //for filtering
	
	//User related
	long userID;  //userID of status
	//String userName;
	String userScreenName;
	String userLocation; //check
	//String userDescription; 
	//boolean userIsContributorsEnabled;
	//String userURL;
	//boolean userIsProtected;
	int userFollowersCount; //check
	int userFriendsCount; //check
	//int userStatusesCount; //check
	boolean userIsVerified; //check
	
/*
String columns[] = {"statusDate", "statusId", "statusText",  "statusInReplyToStatusId",  "statusInReplyToUserId",  "statusIsRetweeted", 
		"statusIsRetweet", "statusRetweetCount", "statusIsEnglish", 
		"userID", "userScreenName", "userLocation", "userFollowersCount", "userFriendsCount", "userIsVerified"
	
 */
	
	public TweetRecord(Status status) {
		User user = status.getUser();
		
		this.statusDate = status.getCreatedAt();
		this.statusId = status.getId();
		this.statusText = status.getText(); //new
		this.statusInReplyToStatusId = status.getInReplyToStatusId();
		this.statusInReplyToUserId = status.getInReplyToUserId();
		this.statusIsRetweeted = status.isRetweeted();
		this.statusIsRetweet = status.isRetweet();
		this.statusRetweetCount = status.getRetweetCount();
		this.statusIsEnglish = false;
		
		this.userID = user.getId();
		this.userScreenName = user.getScreenName();
		this.userLocation = user.getLocation();
		this.userFollowersCount = user.getFollowersCount();
		this.userFriendsCount = user.getFriendsCount();
		this.userIsVerified = user.isVerified();
	}

	@Override
	public String toString() {
		//this.statusText = status.getText();
		return   statusDate 
				+ "," + statusId
				+ "," + statusText
				+ "," + statusInReplyToStatusId
				+ "," + statusInReplyToUserId
				+ "," + statusIsRetweeted
				+ "," + statusIsRetweet
				+ "," + statusRetweetCount
				+ "," + statusIsEnglish
				
				+ "," + userID
				+ "," + userScreenName
				+ "," + userLocation
				+ "," + userFollowersCount
				+ "," + userFriendsCount
				+ "," + userIsVerified;
	}
	
	public String[] toStringArray(){
		return this.toString().split(",");
		
	}
	
	public void appendToFile(String filename){
		
		try{
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(filename, true)));
			System.out.println("Writing record: \n" + this.toString());
			printWriter.println(this.toString());
			printWriter.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		
	}

}
