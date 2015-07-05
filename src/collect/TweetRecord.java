package collect;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
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
										
	boolean statusIsRetweet; //is this status a retweet?
	
	//User related
	long userID;  //userID of status
	String userScreenName;
	
	//original tweet related
	long originalStatusId;
	long originalUserId;
	String originalUserScreenName;
	

	public TweetRecord(Status status) {
		User user = status.getUser();
		
		this.statusDate = status.getCreatedAt();
		this.statusId = status.getId();
		this.statusText = status.getText();
		this.statusIsRetweet = status.isRetweet();
		
		this.userID = user.getId();
		this.userScreenName = user.getScreenName();
		
		Status originalStatus = status.getRetweetedStatus();
		
		originalStatusId = originalStatus.getId();
		originalUserId = originalStatus.getUser().getId();
		originalUserScreenName = originalStatus.getUser().getScreenName();
	}

	@Override
	public String toString() {
		
		return 
		 		"statusDate:" + statusDate + "\r\n"
				+ "statusId:" + statusId + "\r\n"
				+ "statusText:" + statusText + "\r\n"
				
				+ "userID:" + userID + "\r\n"
				+ "userScreenName:" + userScreenName + "\r\n"
				
				+ "originalStatusId:" + originalStatusId + "\r\n"
				+ "originalUserId:" + originalUserId + "\r\n"
				+ "originalUserScreenName:" + originalUserScreenName + "\r\n"
				+ "---------------------------------------------------------";				
	}
	
	public void appendToFile(){
		
		File file = new File(Long.toString(this.originalUserId) + ".raw");
		System.out.println("appending to :" + file.getName());
		
		try{
			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
			printWriter.println(this.toString());
			printWriter.close();
		} catch (IOException e){
			e.printStackTrace();
		}

	}

}
