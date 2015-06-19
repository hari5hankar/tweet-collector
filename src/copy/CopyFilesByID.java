package copy;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by Security on 6/18/2015.
 */
public class CopyFilesByID {
    public static void main(String[] args) {
        ArrayList<Long> list = new ArrayList<>();
        String line;

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                    (new FileInputStream("C:\\Users\\Security\\workspace\\tweet-collector-3\\data\\21447363\\round1\\21447363_NV_MA.csv")));
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(",");
                list.add(Long.parseLong(tokens[0]));
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println(list.toString());
        System.out.println(list.size());

        final File folder = new File("C:\\Users\\Security\\workspace\\tweet-collector-3\\data\\round2\\raw");
        for (final File file : folder.listFiles()) {
            if (file.getName().contains(".")) {
                continue;
            }
            if (!file.isDirectory()) {
                String destinaionFileName = "C:\\Users\\Security\\workspace\\tweet-collector-3\\data\\21447363\\round2\\" + file.getName();
                final File source = file;
                final File destination = new File(destinaionFileName);
                if (list.contains(Long.parseLong(file.getName()))) {
                    try {
                        Files.copy(source.toPath(), destination.toPath());
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }
            }
        }
    }
}
