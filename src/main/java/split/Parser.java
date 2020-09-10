package split;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;

public class Parser {
    private int numberOutputFile;    //текущий номер файла
    BufferedReader reader;
    BufferedWriter writer;

    @Option(name = "-d", usage = "sequence name")
    private boolean flagD = false;

    @Option(name = "-l", usage = "number of rows (default: 100)", forbids = {"-c", "-n"})
    private int numberLines = -1;

    @Option(name = "-c", usage = "the size of the output file in characters", forbids = {"-l", "-n"})
    private int numberCharacters = -1;

    @Option(name = "-n", usage = "number of output files", forbids = {"-l", "-c"})
    private int numberFile = -1;

    @Option(name = "-o", usage = "output name file", metaVar = "OUTPUT")
    private String outputName = "x";

    @Argument(required = true, usage = "Input file name", metaVar = "InputName")
    String inputFileName;

    public void parseArgs(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("\nExample: split [-d] [-l num | -c num | -n num] [-o outputName] inputName");
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }

        if (outputName.equals("-")) outputName = inputFileName;
        if (numberLines != -1) workOutputFile(true, numberLines);       //установлен флаг -l, работаем с кол-вом строк
        else if (numberCharacters != -1) workOutputFile(false, numberCharacters);       //установлен флаг -c, работаем с кол-вом символов
        else if (numberFile != -1) {        //установлен флаг -n, работаем с кол-вом файлов
            try (BufferedReader reader = new BufferedReader(new FileReader("Files\\" + inputFileName))) {
                int countLine = 0;
                while (reader.readLine() != null) countLine++;      //считаем кол-во строк в исходном файле
                int countLineInFile = (int) Math.ceil((double) countLine / numberFile);     //считаем кол-во строк в одном файле
                workOutputFile(true, countLineInFile);      //работаем с кол-вом символов
            }
        }
        else workOutputFile(true, 100);     //если флаги не стоят, то в выходных файлах 100 строк
    }

    private String getOutputName() {        //формирование названия файла, если флаг -d не стоит
        String name = outputName;
        if (flagD) {
            name += numberOutputFile;
        } else {
            name += (char) ((int) 'a' + (numberOutputFile - 1) / 26) + "" +  (char) ((int) 'a' + (numberOutputFile - 1) % 26);
        }
        return name;
    }

    private void workOutputFile(boolean line, int size) throws IOException {
        boolean end = false;        //если true, значит достигли конца файла
        numberOutputFile = 0;
        reader = new BufferedReader(new FileReader("Files\\" + inputFileName));
            while (!end) {
                numberOutputFile++;     //увеличиваем счётчик файлов
                File file = new File("Files\\" + getOutputName());      //создаём файл
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    end = writeToFile(reader, writer, line, size);
                }
            }
        }

    private boolean writeToFile(BufferedReader reader, BufferedWriter writer, boolean line, int size) throws IOException {
        for (int i = 0; i < size; i++) {
            String str;     //строка
            int ch;     //код символа
            if (line) {
                str = reader.readLine();        //читаем строку из файла
                if (str != null) {
                    writer.write(str);      //если не конец исходного файла, записываем
                } else return true;     //дошли до конца исходного файла
            } else {
                ch = reader.read();     //читаем символ из файла
                if (ch != -1) {
                    writer.write(ch);       //если не конец исходного файла, записываем
                } else return true;     //дошли до конца исходного файла
            }
        }
        return false;       //исходный файл ещё не кончился
    }
}
