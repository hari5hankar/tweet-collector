
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
		//2836421 msnbc //96951800 fcbarcelona
		
		/*
		 * TODO
		 * follow @cnnbrk 428333
		 * follow @barackobama 813286
		 * follow @katyperry 21447363
		*/
		long[] users = {2836421};
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

/*	
	//create separate listener for each handle
	ArrayList<MyStatusListener> listeners = new ArrayList<MyStatusListener>();
for (int i = 0; i < users.length; i++){
	listeners.add(new MyStatusListener(users[i]));
	MyStatusListener myStatusListener = listeners.get(i);
	twitterStream.addListener(myStatusListener);

}*/