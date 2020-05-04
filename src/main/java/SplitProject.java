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
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        if (outputName.equals("")) outputName = "X";
        if (outputName.equals("-")) {
            int n = inputName.length();
            outputName = inputName.substring(6, n - 4);
        }
        try {
            reader = new BufferedReader(new FileReader(input));
            if (name) {
                while ((line = reader.readLine()) != null) {
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
                while ((line = reader.readLine()) != null) {
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
    public static void numberOfCharacters(boolean name, int numberOfChars, String outputName, String inputName) throws IOException {
        File input = new File(inputName);
        File output;
        BufferedReader reader = null;
        int number = 1;     //количество файлов выходных файлов
        int chars = 0;      //количество символов в текущем файле
        int symbol;
        String line;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        if (outputName.equals("")) outputName = "X";
        if (outputName.equals("-")) {
            int n = inputName.length();
            outputName = inputName.substring(6, n - 4);
        }
        try {
            reader = new BufferedReader(new FileReader(input));
            if (name) {
                output = new File("Output\\" + outputName + number + ".txt");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"));
                while ((symbol = reader.read()) != -1) {        //когда дойдём до конца файла, получим -1
                    if (chars == numberOfChars) {
                        number++;
                        output = new File("Output\\" + outputName + number + ".txt");
                        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"));
                        chars = 0;
                    }
                    writer.write(symbol);
                    writer.flush();
                    chars++;
                }
                writer.close();
            } else {
                output = new File("Output\\" + outputName + "a" +
                        "a" + ".txt");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"));
                while ((symbol = reader.read()) != -1) {
                    if (chars == numberOfChars) {
                        number++;
                        int first = (int) (double) number / 26;
                        int second;
                        if (first != 0) {
                            second = number - first * 26;
                        } else {
                            second = number - 1;
                        }
                        output = new File("Output\\" + outputName + alphabet.charAt(first) +
                                alphabet.charAt(second) + ".txt");
                        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF8"));
                        chars = 0;
                    }
                    writer.write(symbol);
                    writer.flush();
                    chars++;
                }
                writer.close();
            }
        } catch (IOException e) {
            System.out.print("Error:" + e);
        } finally {
            assert reader != null;
            reader.close();
        }
    }

    public static void numberOfFiles(boolean name, int number, String outputName, String inputName) {

    }
}
