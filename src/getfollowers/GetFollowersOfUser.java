package getfollowers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class GetFollowersOfUser {
	
	Twitter twitter;

	public GetFollowersOfUser() {
		twitter = new TwitterFactory(authorization.ConfigBuilder.getConfig())
				.getInstance();	
	}
	
	public void getFollowersIntoFile(long mainUserID) {
		System.out.println("getFollowersIntoFile()");

		String filename = Long.toString(mainUserID) + "_L";
		try {

			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(
					filename, true)));
			long cursor = -1L;
			int pageCount = 0;
			int followersCount = 0;
			IDs ids;
			do {
				ids = twitter.getFollowersIDs(mainUserID, cursor);
				for (long userID : ids.getIDs()) {
					printWriter.println(userID);
					System.out.println(++followersCount);
				}

				++pageCount;
				if (pageCount == 15) {
					Thread.sleep(960000);
					pageCount = 0;
				}

			} while ((cursor = ids.getNextCursor()) != 0);

			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
