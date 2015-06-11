package collect;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
//import twitter4j.User;

public class MyStatusListener implements StatusListener{
	
	public void onException(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	public void onStatus(Status status) {
		// TODO Auto-generated method stub
		if (status.isRetweet()){
			TweetRecord tweetRecord = new TweetRecord(status);
			tweetRecord.appendToFile(Long.toString(tweetRecord.originalUserId)); 
		}			 			
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
