package collect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import authorization.ConfigBuilder;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;



public class TweetCollectorDriver {

	File rootDirectory = new File("data");
	
	/*
	 * The constructor accepts a round number and
	 * finds out whom to follow in the next round and starts following. 
	 */
	public TweetCollectorDriver(int round) {

		TwitterStream twitterStream = new TwitterStreamFactory(ConfigBuilder.getConfig()).getInstance();

		HashSet<Long> usersToFollowSet = new HashSet<Long>();
		goDeeper(rootDirectory, round, usersToFollowSet);

		System.out.println(usersToFollowSet.toString());
		System.out.println(usersToFollowSet.size());

	MyStatusListener mSL = new MyStatusListener(usersToFollowSet);
		twitterStream.addListener(mSL);

		long[] usersArray = new long[usersToFollowSet.size()];
		int i = 0;
		for (long user : usersToFollowSet) {
			usersArray[i++] = user;
		}

		FilterQuery filterQuery = new FilterQuery();
		filterQuery.follow(usersArray);
		twitterStream.filter(filterQuery);
	}

	public void goDeeper(File directory, int level, HashSet<Long> set) {

		if (level == 0) {

			try {

				File validatedListFile = new File(
						directory.getAbsolutePath() + "\\" + directory.getName() + "_MA5_V.csv");
				BufferedReader bR = new BufferedReader(new InputStreamReader(new FileInputStream(validatedListFile)));
				String line = bR.readLine();

				while (line != null) {
					String[] tokens = line.split(",");
					set.add(Long.parseLong(tokens[0]));
					line = bR.readLine();
				}

				bR.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(-1);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.exit(-1);
			}

			return;
		}

		for (File file : directory.listFiles()) {

			if (file.isDirectory()) {
				goDeeper(file, (level - 1), set);
			}

		}
	}

	public static void main(String[] args) {
		
		new TweetCollectorDriver(3);

	}

}
