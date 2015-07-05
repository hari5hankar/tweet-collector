package getfollowers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import twitter4j.IDs;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class GetFollowersOfUser {

	static int pageCount = 0;

	public void getFollowersIntoFile(long mainUserID, File file) {

		try {
			System.out.println("mainUserID:" + mainUserID);
			System.out.println("pageCount:" + pageCount);

			Twitter twitter = new TwitterFactory(authorization.ConfigBuilder.getConfig()).getInstance();

			User user = twitter.showUser(mainUserID);
			int numberOfFollowers = user.getFollowersCount();

			System.out.println("numberOfFollowers:" + numberOfFollowers);

			PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));

			if (numberOfFollowers > 300000) {
				System.out.println("too many followers. skipping ... " + mainUserID);
				printWriter.println("SKIPPED");
				printWriter.close();
				return;
			}

			long cursor = -1L;
			int pageCount = 0;
			int followersCount = 0;
			IDs ids;
			do {

				if (pageCount == 15) {
					System.out.println("sleeping...");
					Thread.sleep(960000);
					pageCount = 0;
				}

				ids = twitter.getFollowersIDs(mainUserID, cursor);
				pageCount++;

				for (long userID : ids.getIDs()) {
					printWriter.println(userID);
					System.out.println(++followersCount);
				}

			} while ((cursor = ids.getNextCursor()) != 0);

			printWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		GetFollowersOfUser gFU = new GetFollowersOfUser();
		File file = new File("");
		gFU.getFollowersIntoFile(96951800L, file);

	}
}