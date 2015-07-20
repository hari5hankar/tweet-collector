package analyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class Analyze {

	File rootDirectory;
	long root;
	int round;

	static int apiCallCount = 0;
	static int totalCallCount = 0;

	int totalRetweeters = 0;
	int totalTweets = 0;
	int totalMostActiveRetweeters = 0;
	int totalMostActiveFollowers = 0;
	int totalRetweetedInNextRound = 0;
	File retweetsDoneFollowersFollowingFile;
	File retweetsHadFollowersFollowingFile;
	
	public Analyze(long root) {
		this.root = root;
		this.rootDirectory = new File("data\\" + Long.toString(this.root) + "\\");
	}

	public void analyze(int round) {
		this.round = round;
		this.retweetsDoneFollowersFollowingFile = new File(
				"analysis\\" + Integer.toString(round) + "\\" + Long.toString(root) + "_RFF_DONE.csv");
		this.retweetsHadFollowersFollowingFile = new File(
				"analysis\\" + Integer.toString(round) + "\\" + Long.toString(root) + "_RFF_HAD.csv");
		
		goDeeper(rootDirectory, round);
		System.out.println();
		System.out.println(totalMostActiveRetweeters);
	}


	public void goDeeper(File directory, int level) {

		if (level == 1) {

			  totalMostActiveRetweeters(directory);
/*			  retweetsFollowersFollowingHad(directory);
			  retweetsFollowersFollowingDone(directory);
			  totalTweets(directory); totalRetweeters(directory);
			  totalMostActiveRetweeters(directory);
			  totalMostActiveFollowerRetweeters(directory);
			  totalRetweetedInNextRound(directory);
*/			 
			return;
		}

		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				goDeeper(file, (level - 1));
			}

		}
	}

	private int countLines(File file) {
		int total = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			line = br.readLine();
			while (line != null) {
				total++;
				line = br.readLine();
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return total;
	}

	public int totalTweets(File directory) {
		File allRetweetersFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + "_NV.csv");
		int total = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(allRetweetersFile)));
			String line;
			line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				total += Integer.parseInt(tokens[1]);
				line = br.readLine();
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		this.totalTweets += total;
		return total;
	}

	public int totalRetweeters(File directory) {
		File allRetweetersFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + "_NV.csv");
		int total = countLines(allRetweetersFile);
		this.totalRetweeters += total;
		return total;
	}

	public int totalMostActiveRetweeters(File directory) {
		File mostActiveRetweetersFile = new File(
				directory.getAbsolutePath() + "\\" + directory.getName() + "_NV_MA5.csv");
		int total = countLines(mostActiveRetweetersFile);
		System.out.println(mostActiveRetweetersFile.getName() + "," + total);
		this.totalMostActiveRetweeters += total;
		return total;

	}

	public int totalMostActiveFollowers(File directory) {
		File mostActiveFollowersFile = new File(
				directory.getAbsolutePath() + "\\" + directory.getName() + "_MA5_V.csv");
		int total = countLines(mostActiveFollowersFile);
		this.totalMostActiveFollowers += total;
		return total;
	}

	public int totalRetweetedInNextRound(File directory) {
		int total = 0;
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				total++;
			}
		}
		this.totalRetweetedInNextRound += total;
		return total;
	}
	
	public void retweetsFollowersFollowingHad(File directory) {
		int retweetsHad = 0;
		int followers = 0;
		int following = 0;
		try {
			File mostActiveFollowersFile = new File(
					directory.getAbsolutePath() + "\\" + directory.getName() + "_NV.csv");
			System.out.println(mostActiveFollowersFile.getAbsolutePath());
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mostActiveFollowersFile)));
			
			String line;
			line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				line = br.readLine();
				retweetsHad += Integer.parseInt(tokens[1]);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		apiCallCount++;
		totalCallCount++;
		System.out.println("    count: api:  " + apiCallCount + " total:  " + totalCallCount);

		if (apiCallCount == 179) {
			try {
				System.out.println("    sleeping...");
				Thread.sleep(900000);
				apiCallCount = 0;
			} catch (InterruptedException inte) {
				inte.printStackTrace();
			}
		}

		try {
			Twitter twitter = new TwitterFactory(authorization.ConfigBuilder.getConfig()).getInstance();
			
			User user = twitter.showUser(Long.parseLong(directory.getName()));
			followers = user.getFollowersCount();
			following = user.getFriendsCount();
		} catch (TwitterException te) {
			te.printStackTrace();
		}
		
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(this.retweetsHadFollowersFollowingFile, true));
			pw.println(directory.getName() + "," + retweetsHad + "," + followers + "," + following);
			pw.flush();
			pw.close();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
			
	}

	public void retweetsFollowersFollowingDone(File directory) {
		Twitter twitter = new TwitterFactory(authorization.ConfigBuilder.getConfig()).getInstance();

		try {
			File mostActiveFollowersFile = new File(
					directory.getAbsolutePath() + "\\" + directory.getName() + "_MA5_V.csv");
			System.out.println(mostActiveFollowersFile.getAbsolutePath());
			
			PrintWriter pw = new PrintWriter(new FileWriter(this.retweetsDoneFollowersFollowingFile, true));

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mostActiveFollowersFile)));
			String line;
			int followers;
			int following;
			int retweets;
			line = br.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				line = br.readLine();

				followers = 0;
				following = 0;
				retweets = Integer.parseInt(tokens[1]);
				long id = Long.parseLong(tokens[0]);

				apiCallCount++;
				totalCallCount++;
				System.out.println("    count: api:  " + apiCallCount + " total:  " + totalCallCount);

				if (apiCallCount == 179) {
					try {
						System.out.println("    sleeping...");
						Thread.sleep(900000);
						apiCallCount = 0;
					} catch (InterruptedException inte) {
						inte.printStackTrace();
					}
				}

				try {
					User user = twitter.showUser(id);
					followers = user.getFollowersCount();
					following = user.getFriendsCount();
				} catch (TwitterException te) {
					te.printStackTrace();
				}

				pw.println(id + "," + retweets + "," + followers + "," + following);
				System.out.println(id + "," + retweets + "," + followers + "," + following);
				pw.flush();
			}
			br.close();
			pw.close();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		new Analyze(428333).analyze(4);
		
	}
}
