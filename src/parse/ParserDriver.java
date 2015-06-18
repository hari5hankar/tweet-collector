package parse;

public class ParserDriver {
	
	public static void main(String[] args){
		
		TweetRecordsParser tweetRecordsParser = new TweetRecordsParser();
		long[] userIDsArray = {96951800L};
		for (Long mainUserID : userIDsArray) {
			tweetRecordsParser.parseAndWriteToFile(mainUserID);
		}
	}
}