package analyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Analyze {

	static File rootDirectory = new File("C:\\Users\\Security\\Workspace2\\tweet-collector\\data\\");
	int totalRetweeters = 0;
	int totalTweets = 0;
	int totalMostActiveRetweeters = 0;
	int totalMostActiveFollowerRetweeters = 0;
	int totalRetweetedInNextRound = 0;

	public Analyze(int round) {

		goDeeper(rootDirectory, round);
		System.out.println(this.totalRetweeters);
		System.out.println(this.totalTweets);
		System.out.println(this.totalMostActiveRetweeters);
		System.out.println(this.totalMostActiveFollowerRetweeters);
		System.out.println(this.totalRetweetedInNextRound);
	}

	public void goDeeper(File directory, int level) {

		if (level == 0) {

			File allRetweetersFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + "_NV.csv");
			totalTweets(allRetweetersFile);
			totalRetweeters(allRetweetersFile);
			File mostActiveRetweetersFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + "_NV_MA5.csv");
			totalMostActiveRetweeters(mostActiveRetweetersFile);
			File mostActiveFollowerRetweetersFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + "_MA5_V.csv");
			totalMostActiveFollowerRetweeters(mostActiveFollowerRetweetersFile);
			totalRetweetedInNextRound(directory);
			
			return;
		}

		for (File file : directory.listFiles()) {

			if (file.isDirectory()) {
				goDeeper(file, (level - 1));
			}

		}
	}

	public void totalTweets (File file) {
		int total = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
	}
	
	public void totalRetweeters(File file) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			line = br.readLine();
			while (line != null) {
				this.totalRetweeters++;
				line = br.readLine();
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void totalMostActiveRetweeters(File file) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			line = br.readLine();
			while (line != null) {
				this.totalMostActiveRetweeters++;
				line = br.readLine();
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public void totalMostActiveFollowerRetweeters(File file) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
			line = br.readLine();
			while (line != null) {
				this.totalMostActiveFollowerRetweeters++;
				line = br.readLine();
			}
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}

	public void totalRetweetedInNextRound(File directory) {
		for(File file : directory.listFiles()) {
			if (file.isDirectory()) {
				this.totalRetweetedInNextRound++;
			}
		}
	}
	public static void main(String[] args) {

		new Analyze(3);

	}
}
