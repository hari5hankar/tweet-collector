import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import au.com.bytecode.opencsv.CSVWriter;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;

public class EnglishStatusListener implements StatusListener{
	
	File file; 
	
	public EnglishStatusListener(){
		File file = new File("data.csv");

		try{
			CSVWriter csvWriter = new CSVWriter(new FileWriter(file, true));
			String columns[] = {"statusDate", "statusId", "statusText",  "statusInReplyToStatusId",  "statusInReplyToUserId",  "statusIsRetweeted", 
					"statusIsRetweet", "statusRetweetCount", "statusIsEnglish", 
					"userID", "userScreenName", "userLocation", "userFollowersCount", "userFriendsCount", "userIsVerified"
					}  ;
			csvWriter.writeNext(columns);
			csvWriter.close();
			System.out.println("New csv File created!");
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void onException(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	public void onStatus(Status status) {
		// TODO Auto-generated method stub
		TweetRecord tweetRecord = new TweetRecord(status);
		System.out.println(tweetRecord.toString());
		tweetRecord.appendToFile(file); 
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO Auto-generated method stub
		
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO Auto-generated method stub
		
	}

	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO Auto-generated method stub
		
	}

	public void onStallWarning(StallWarning warning) {
		// TODO Auto-generated method stub
		
	}
	
}
