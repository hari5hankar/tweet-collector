package copy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CopyTweetsByID {

	static File rootDirectory = new File("data\\");

	public CopyTweetsByID(int round) {

		goDeeper(rootDirectory, round);

	}


	public void goDeeper(File directory, int level) {

		if (level == 0) {

			List<Long> validatedList = new ArrayList<Long>();
			File validatedListFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + "_MA5_V.csv");
			readFileIntoList(validatedListFile, validatedList);
			copy(validatedList, directory);
			System.out.println("followers of " + validatedListFile.getName() + " copied");
		}

		for (File file : directory.listFiles()) {

			if (file.isDirectory()) {
				goDeeper(file, (level - 1));
			}

		}
	}
	
	public void readFileIntoList(File file, List<Long> list) {

		try {
			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = b.readLine();
			while (line != null) {
				String[] tokens = line.split(",");
				list.add(Long.parseLong(tokens[0]));
				line = b.readLine();
			}
			b.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public void copy(List<Long> list, File directory){
		
		for (long id : list) {

			File directoryForThisUser = new File(directory.getAbsolutePath() + "\\" + id + "\\");
			directoryForThisUser.mkdirs();

			final File source = new File(rootDirectory + "\\" + Long.toString(id) + ".raw");

			if (!source.exists()) {
				directoryForThisUser.delete();
				continue;
			}

			final File destination = new File(
					directory.getAbsolutePath() + "\\" + id + "\\" + Long.toString(id) + ".raw");
			try {
				Files.copy(source.toPath(), destination.toPath());
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		new CopyTweetsByID(3);

	}
}
