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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class TweetCollectorDriver {

	//private static transient final Logger LOG = LoggerFactory.getLogger(TweetCollectorDriver.class);

	static ArrayList<Long> usersList = new ArrayList<Long>();

	public static void main(String[] args) {
		String line;
		try{
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream("428333_813286_21447363_NV_MA_F.csv")));

			while ((line = bufferedReader.readLine()) != null) {
				usersList.add(Long.parseLong(line));
			}
			bufferedReader.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		usersList.add(19715694L); //UBcommunity
		usersList.add(2836421L); //msnbc

		TwitterStream twitterStream = new TwitterStreamFactory(authorization.ConfigBuilder.getConfig()).getInstance();
		MyStatusListener myStatusListener = new MyStatusListener();
		twitterStream.addListener(myStatusListener);


		long[] usersArray = new long[usersList.size()];
		for(int i = 0; i< usersList.size(); i++){
			usersArray[i] = usersList.get(i);

		}

		FilterQuery filterQuery = new FilterQuery();
		filterQuery.follow(usersArray);
		
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