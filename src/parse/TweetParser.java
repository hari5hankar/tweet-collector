package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TweetParser {

	static File rootDirectory = new File("C:\\Users\\Security\\Workspace\\tweet-collector\\data\\");
	Map<String, Integer> map = new HashMap<String, Integer>();

	public TweetParser(int round) {

		goDeeper(rootDirectory, round);

	}

	public void goDeeper(File directory, int level) {

		if (level == 0) {

			File tweetsFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + ".raw");
			parse(tweetsFile);
			System.out.println(tweetsFile.getName() + " parsed");

			return;
		}

		for (File file : directory.listFiles()) {

			if (file.isDirectory()) {
				goDeeper(file, (level - 1));
			}

		}
	}

	/*
	 * parses file corresponding to id writes to a new file, with entry as
	 * <user>, <number of retweets>, in the same directory.
	 */
	public static void parse(File file) {

		String[] filename = file.getName().split("\\.");
		long id = Long.parseLong(filename[0]);

		Map<Long, Integer> map = new LinkedHashMap<Long, Integer>();
		String line;

		try {
			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			line = b.readLine();
			while (line != null) {
				if (line.contains("userID")) {
					String[] token = line.split(":");
					Long userID = Long.parseLong(token[1]);
					if (map.containsKey(userID)) {
						map.put(userID, map.get(userID) + 1);
					} else {
						map.put(userID, 1);
					}
				}
				line = b.readLine();
			}
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		map = sortByComparator(map);

		try {
			File allRetweetersFile = new File(
					file.getParentFile().getAbsolutePath() + "\\" + Long.toString(id) + "_NV.csv");
			File mostActiveRetweetersFile = new File(
					file.getParentFile().getAbsolutePath() + "\\" + Long.toString(id) + "_NV_MA5.csv");
			PrintWriter p1 = new PrintWriter(new FileWriter(allRetweetersFile));
			PrintWriter p2 = new PrintWriter(new FileWriter(mostActiveRetweetersFile));

			for (Map.Entry<Long, Integer> entry : map.entrySet()) {
				p1.println(entry.getKey() + "," + entry.getValue());
				p1.flush();
				if (entry.getValue() >= 5) {
					p2.println(entry.getKey() + "," + entry.getValue());
					p2.flush();
				}

			}
			p1.close();
			p2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * sorts a hashmap. from mykong.com
	 */
	private static LinkedHashMap<Long, Integer> sortByComparator(Map<Long, Integer> unsortedMap) {

		// Convert Map to List
		List<Map.Entry<Long, Integer>> list = new LinkedList<Map.Entry<Long, Integer>>(unsortedMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<Long, Integer>>() {
			public int compare(Map.Entry<Long, Integer> o1, Map.Entry<Long, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		LinkedHashMap<Long, Integer> sortedMap = new LinkedHashMap<Long, Integer>();
		for (Iterator<Map.Entry<Long, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<Long, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public static void main(String[] args) {

		new TweetParser(2);

	}
}
