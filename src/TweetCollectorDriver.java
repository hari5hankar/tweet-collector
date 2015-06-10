
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import twitter4j.FilterQuery;
//import twitter4j.StallWarning;
//import twitter4j.Status;
//import twitter4j.StatusDeletionNotice;
//import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
//import twitter4j.conf.ConfigurationBuilder;
//import twitter4j.User ;

public class TweetCollectorDriver {
	
//	private final Logger LOG = LoggerFactory.getLogger(TweetCollectorDriver.class);
	
	public static void main(String[] args) {

		TwitterStream twitterStream = new TwitterStreamFactory(ConfigBuilder.getConfig()).getInstance();
		
		//2836421 msnbc //96951800 fcbarcelona
		long[] users = {
				/*msnbc*/
				2836421 , 
				/*msnbc 48 most active followers*/
				2487791838L, 1664059166, 199056422, 227259371, 2723705754L, 20618180, 29345753, 2480891059L, 316006002, 475614488, 
				16252960, 20476599, 874869986, 115744814, 27324239, 17082120, 37308433, 2828277842L, 47703013, 1884645062, 309190582,
				2986301995L, 99191859, 28475404, 101405399, 61015710, 2940960137L, 3025717611L, 36459641, 632690718, 570859259, 253258167, 
				3008459731L, 171745835, 998490372, 834672494, 207181401, 75052666, 450376679, 2939356781L, 29560634, 445406535, 765763200, 
				37365390, 43168615, 810053322, 274422511, 364423298} ;
		
		ArrayList<MyStatusListener> listeners = new ArrayList<MyStatusListener>();
		
		for (int i = 0; i < users.length; i++){
			listeners.add(new MyStatusListener(users[i]));
			MyStatusListener myStatusListener = listeners.get(i);
			twitterStream.addListener(myStatusListener);
			
		}
	
		FilterQuery filterQuery = new FilterQuery();
		
		/*
		 * TODO
		 * new thread for each user. OR use the long users[] and find a way to see OnStatus is being called for which user.
		 * follow 48 users of msnbc, save each output to <id>.txt
		 * follow @cnnbrk
		 * follow @barackobama
		 * follow @katyperry
		*/
		
		
		filterQuery.follow(users);
		//filterQuery.track();
		
		/* //for manual retweets
			String[] keywords = {"RT @FCBarcelona"}; 
			filterQuery.track(keywords);
		*/
		
		twitterStream.filter(filterQuery);
	
	}
}