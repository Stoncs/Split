package split;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import java.io.*;

public class Parser {
    //текущий номер файла и предыдущий
    private int numberOutputFile, prNumberOutputFile;
    //счётчик, сколько осталось напечатать строк/символов
    private int count;
    private BufferedReader reader;
    private  BufferedWriter writer;
    String nameOfFile;

    @Option(name = "-d", usage = "sequence name")
    private boolean flagD = false;
    @Option(name = "-l", usage = "number of rows (default: 100)", forbids = {"-c", "-n"})
    private int numberLines = -1;
    @Option(name = "-c", usage = "the size of the output file in characters", forbids = {"-l", "-n"})
    private int numberCharacters = -1;
    @Option(name = "-n", usage = "number of output files", forbids = {"-l", "-c"})
    private int numberFile = -1;
    @Option(name = "-o", usage = "output name file", metaVar = "OUTPUT")
    private String outputName = "files\\x";
    @Argument(required = true, usage = "Input file name", metaVar = "InputName")
    String inputFileName;

    public void parseArgs(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        count = 0;
        int ch;

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("\nExample: split [-d] [-l num | -c num | -n num] [-o outputName] inputName");
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }

        reader = new BufferedReader(new FileReader(new File (inputFileName + ".txt")));
        if (outputName.equals("-")) outputName = inputFileName;
        //установлен флаг -l, работаем с кол-вом строк
        if (numberLines != -1) workWithFiles(true, numberLines);

        //установлен флаг -c, работаем с кол-вом символов
        else if (numberCharacters != -1) workWithFiles(false, numberCharacters);

        //установлен флаг -n, работаем с кол-вом файлов
        else if (numberFile != -1) {
            int numberCharsInFile = 0;
            while ((ch = reader.read()) != -1)
                //считаем кол-во символов в исходном файле
                if (ch != '\n') numberCharsInFile++;
            reader.close();
            reader = new BufferedReader(new FileReader(new File (inputFileName + ".txt")));
            //считаем кол-во символов в одном файле
            numberCharacters = (int) Math.ceil((double) numberCharsInFile / numberFile);
            //работаем с кол-вом символов
            workWithFiles(false, numberCharacters);
        }

        //если флаги не стоят, то в выходных файлах 100 строк
        else workWithFiles(true, 100);

        reader.close();
        writer.close();
    }

    //формирование названия файла, если флаг -d не стоит
    private String getOutputName() {
        String name = outputName;
        if (numberOutputFile < 27) name += "a" + (char) ((int) 'a' + numberOutputFile - 1);
        else {
            name += (char) ((int) 'a' + (numberOutputFile - 1) / 26);
            name += (char) ((int) 'a' + (numberOutputFile - 1) % 26);
        }
        return name;
    }

    private void writeToFile(String text, int size) throws IOException {
        //если номер текущего файла изменился, создаём новый
        if (numberOutputFile - prNumberOutputFile > 1 ) {
            prNumberOutputFile++;
            writer = new BufferedWriter(new FileWriter(new File(nameOfFile + ".txt")));
        }

        writer.write(text);
        if (!text.equals("\n")) count--;

        //когда напечатали всё
        if (count == 0) {
            writer.close();
            //увеличиваем номер файла
            numberOutputFile++;
            //имя следующего файла
            nameOfFile = flagD ? outputName + numberOutputFile : getOutputName();
            //кол-во строк/символов, которое надо напечатать в следующем файле
            count = size;
        }
    }

    private void workWithFiles(boolean line, int size) throws IOException {
        int ch;
        String initialName, str;
        prNumberOutputFile = 0;
        numberOutputFile = 1;
        count = size;
        //название первого выходного файла
        initialName = flagD ? outputName + "1" : outputName + "aa";

        writer = new BufferedWriter(new FileWriter(new File(initialName + ".txt")));
        if (line) {
            //записываем в файлы строки
            while ((str = reader.readLine()) != null) {
                if (size == count) writeToFile(str, size);
                else writeToFile("\n" + str, size);
            }
        }
        else {
            //записываем в файлы символы
            while ((ch = reader.read()) != -1)
                writeToFile("" + (char) ch, size);
        }
    }
}