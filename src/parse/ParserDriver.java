package parse;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ParserDriver {
	
	public static void main(String[] args) throws InterruptedException {

		ArrayList<Long> usersList = new ArrayList<Long>();
		String currentFolder = "C:\\Users\\Security\\workspace\\tweet-collector-3\\data\\813286\\round2";
		final File folder = new File(currentFolder);
		for(final File sourceFile : folder.listFiles()){
			usersList.add(Long.parseLong(sourceFile.getName()));
		}
		System.out.println(usersList.toString());
		System.out.println(usersList.size());

		TweetRecordsParser tweetRecordsParser = new TweetRecordsParser();
		for(long mainUserID : usersList){
			tweetRecordsParser.parseAndWriteToFile(mainUserID, currentFolder);
		}
	}
}