
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
//import twitter4j.User;

public class EnglishStatusListener implements StatusListener{
	
	public void onException(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	public void onStatus(Status status) {
		// TODO Auto-generated method stub
		TweetRecord tweetRecord = new TweetRecord(status);
		System.out.println(tweetRecord.toString());
		tweetRecord.appendToFile(); 
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
