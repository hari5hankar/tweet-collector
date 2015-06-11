package collect;

//import org.slf4j.Logger;

//import org.slf4j.LoggerFactory;

//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import twitter4j.StallWarning;
//import twitter4j.Status;
//import twitter4j.StatusDeletionNotice;
//import twitter4j.StatusListener;

//import twitter4j.conf.ConfigurationBuilder;
//import twitter4j.User ;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TweetCollectorDriver {
	
	//private static transient final Logger LOG = LoggerFactory.getLogger(TweetCollectorDriver.class);
	
	public static void main(String[] args) {


		TwitterStream twitterStream = new TwitterStreamFactory(ConfigBuilder.getConfig()).getInstance();
		MyStatusListener myStatusListener = new MyStatusListener();
		twitterStream.addListener(myStatusListener);

		long[] users = {428333, 813286, 21447363};
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.follow(users);
		
		/* //TODO ? check for manual retweets
			String[] keywords = {"RT @FCBarcelona"}; 
			filterQuery.track(keywords);
		*/
		twitterStream.filter(filterQuery);
	}
}

/*
@cnnbrk 428333
@barackobama 813286
@katyperry 21447363
@msnbc 2836421 
@fcbarcelona 96951800 
 */