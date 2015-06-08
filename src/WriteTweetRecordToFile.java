import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import au.com.bytecode.opencsv.CSVWriter;
 
public class WriteTweetRecordToFile
{
/*	public static void writeRecordtoFile(TweetRecord tweetRecord){
		try{
			String csv = "data.csv";
			CSVWriter csvWriter = new CSVWriter(new FileWriter(csv, true));
			String record[] = {"statusDate", "statusId", "statusText",  "statusInReplyToStatusId",  "statusInReplyToUserId",  "statusIsRetweeted", 
					"statusIsRetweet", "statusRetweetCount", "statusIsEnglish", 
					"userID", "userScreenName", "userLocation", "userFollowersCount", "userFriendsCount", "userIsVerified"
					}  ;
					String[] record = tweetRecord.toStringArray();
			csvWriter.writeNext(record);
			csvWriter.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}*/

}