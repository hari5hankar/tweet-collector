
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
//import twitter4j.User ;

public class TweetCollectorDriver {
	
	private final Logger LOG = LoggerFactory.getLogger(TweetCollectorDriver.class);
	
	public static void main(String[] args) {

		TwitterStream twitterStream = new TwitterStreamFactory(ConfigBuilder.getConfig()).getInstance();
		EnglishStatusListener englishStatusListener = new EnglishStatusListener();
		twitterStream.addListener(englishStatusListener);
		
		FilterQuery filterQuery = new FilterQuery();
		
		long[] users = {96951800}; //msnbc
		filterQuery.follow(users);
		//String[] keywords = {"RT @FCBarcelona"}; //for manual retweets
		//filterQuery.track(keywords);
		twitterStream.filter(filterQuery);
	}
}

/*
 * REST API related stuff
 */

/*TwitterFactory twitterFactory = new TwitterFactory(configurationBuilder.build());
Twitter twitter = twitterFactory.getInstance();

try {
	Query query = new Query("india");
	QueryResult queryResult;
	int i = 1;
	do{
		queryResult = twitter.search(query);
		List<Status> tweets = queryResult.getTweets();
		for(Status tweet: tweets){
			System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
		}
		i++;
	}while(i == 1(query = queryResult.nextQuery()) != null);

	System.out.println("Successfully updated the status in Twitter.");
} catch (TwitterException te) {
	te.printStackTrace();
}*/