ackage com.company.logs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogsFiles {

    private List filelogs;
    private File file;

    public File getFile() {
        return file;
    }

    public List getFilelogs() {
        return filelogs;
    }

    public LogsFiles() {
    }

    public void setFile(String str, String name) {

        file = new File("History_" + name + "_logs.txt");

        try {
            FileOutputStream bos = new FileOutputStream(file, true);
            if (str.getBytes() != null) {
            bos.write(str.getBytes());
            bos.write("\n".getBytes());}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(File file) {
        filelogs = new ArrayList();
        try (BufferedReader fis = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fis.readLine()) != null) {
                filelogs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
