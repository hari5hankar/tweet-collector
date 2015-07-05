package collect;

import java.util.HashSet;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class MyStatusListener implements StatusListener{

	HashSet<Long> usersList = new HashSet<Long>();
	
	public MyStatusListener(HashSet<Long> usersList) {
		super();
		this.usersList = usersList;
	}

	public void onStatus(Status status) {

		TweetRecord tweetRecord = new TweetRecord(status);
		System.out.println(tweetRecord.statusIsRetweet + " " + 
				tweetRecord.originalUserId + " " + tweetRecord.userID + " " + tweetRecord.statusId);
		
		if ( !(tweetRecord.statusIsRetweet) || !(usersList.contains(tweetRecord.originalUserId)) ) {
			return;
		}		
		
		tweetRecord.appendToFile(); 
	}			 			
	

	public void onException(Exception ex) {
		// TODO Auto-generated method stub		
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
