import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SplitProject {
    public static void numberOfLine(boolean name, int numberOfLines, String outputName, String inputName) throws IOException {
        File input = new File(inputName);
        File output;
        BufferedReader reader = null;
        int count;      //количество строк в текущем файле
        int number = 1;     //количество файлов выходных файлов
        String line;
        String alphabet = "abcdefghijkmnopqrstuvwxyz";
        if (outputName.equals("")) outputName = "X";
        if (outputName.equals("-")) {
            int n = inputName.length();
            outputName = inputName.substring(6, n - 4);
        }
        try {
            reader = new BufferedReader(new FileReader(input));
            if (name) {
                while ((line = reader.readLine()) != null && number != numberOfLines) {
                    output = new File("Output\\" + outputName + number + ".txt");
                    PrintWriter pw = new PrintWriter(output);
                    pw.println(line);
                    count = 1;
                    while (count != numberOfLines && (line = reader.readLine()) != null) {
                        pw.println(line);
                        count++;
                    }
                    number++;
                    pw.close();
                }
            } else {
                while ((line = reader.readLine()) != null && number != numberOfLines) {
                    int first = (int) (double) number / 26;
                    int second;
                    if (first != 0) {
                        second = number - first - first * 26;
                    }
                    else {
                        second = number - 1;
                    }
                    output = new File("Output\\" + outputName + alphabet.charAt(first) +
                            alphabet.charAt(second) + ".txt");
                    PrintWriter pw = new PrintWriter(output);
                    pw.println(line);
                    count = 1;
                    while (count != numberOfLines && (line = reader.readLine()) != null) {
                        pw.println(line);
                        count++;
                    }
                    number++;
                    pw.close();
                }
            }
        } catch (IOException e) {
            System.out.print("Error:" + e);
        } finally {
            assert reader != null;
            reader.close();
        }
    }
    public static void numberOfCharacters(boolean name, int number, String outputName, String inputName) {
        File input = new File(inputName);
        File output = new File(outputName);

    }

    public static void numberOfFiles(boolean name, int number, String outputName, String inputName) {

    }
}
