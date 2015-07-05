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

	static File rootDirectory = new File("C:\\Users\\Security\\Workspace\\tweet-collector\\");

	public CopyTweetsByID(int round) {

		goDeeper(rootDirectory, round);

	}

	public void readIntoList(File file, List<Long> list) {

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

	public void goDeeper(File directory, int level) {

		if (level == 0) {

			List<Long> validatedList = new ArrayList<Long>();
			File validatedListFile = new File(directory.getAbsolutePath() + "\\" + directory.getName() + "_MA5_V.csv");

			readIntoList(validatedListFile, validatedList);

			for (long id : validatedList) {

				File directoryForThisUser = new File(directory.getAbsolutePath() + "\\" + id + "\\");
				directoryForThisUser.mkdirs();

				final File source = new File(Long.toString(id) + ".raw");
				final File destination = new File(
						directory.getAbsolutePath() + "\\" + id + "\\" + Long.toString(id) + ".raw");
				try {
					Files.copy(source.toPath(), destination.toPath());
				} catch (IOException io) {
					io.printStackTrace();
				}
			}

		}

		for (File file : directory.listFiles()) {

			if (file.isDirectory()) {
				goDeeper(file, (level - 1));
			}

		}
	}

	public static void main(String[] args) {

		new CopyTweetsByID(3);

	}
}
