package validate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import twitter4j.Relationship;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class ValidateFollowers {

	static File rootDirectory = new File("data\\");

	public ValidateFollowers(int round) {

		goDeeper(rootDirectory, round);

	}

	public void goDeeper(File directory, int level) {

		if (level == 0) {

			File parsedTweetsFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + "_NV_MA5.csv");
			System.out.println(parsedTweetsFile.getAbsolutePath());
			validate(parsedTweetsFile);
			System.out.println(parsedTweetsFile.getName() + " validated");
			return;
		}

		for (File file : directory.listFiles()) {

			if (file.isDirectory()) {
				goDeeper(file, (level - 1));
			}

		}
	}

	/*
	 * validates a file against the user it belongs to. and writes new file
	 * i.e removes all the tweets of users in the file who are not followers of the given user.
	 */
	static int apiCallCount = 0;
	static int totalCallCount = 0;

	public static void validate(File file) {

		String[] fileName = file.getName().split("_");
		long id = Long.parseLong(fileName[0]);

		Map<Long, Integer> nonValidatedMap = new LinkedHashMap<Long, Integer>();

		try {
			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = b.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				nonValidatedMap.put(Long.parseLong(tokens[0]), Integer.parseInt(tokens[1]));
				line = b.readLine();
			}
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Twitter twitter = new TwitterFactory(authorization.ConfigBuilder.getConfig()).getInstance();

		try {
			File f = new File(file.getParentFile().getAbsolutePath() + "\\" + Long.toString(id) + "_MA5_V.csv");
			
			PrintWriter p = new PrintWriter(new FileWriter(f));
			for (Map.Entry<Long, Integer> entry : nonValidatedMap.entrySet()) {

				apiCallCount++;
				totalCallCount++;
				System.out.println("    count: " + apiCallCount + " " + totalCallCount);
				if (apiCallCount == 180) {
					try {
						System.out.println("    sleeping...");
						Thread.sleep(900000);
						apiCallCount = 0;
					} catch (InterruptedException inte) {
						inte.printStackTrace();
					}
				}

				Relationship r = twitter.showFriendship(entry.getKey(), id);
				if (r.isSourceFollowingTarget()) {
					p.println(entry.getKey() + "," + entry.getValue());
					p.flush();
				}
			}

			p.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TwitterException te) {
			te.printStackTrace();
		}

	}

	public static void main(String[] args) {

		new ValidateFollowers(4);

	}
}
