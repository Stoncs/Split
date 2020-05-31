import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;

public class Parser {
    private int numberOutputFile, prNumberOutputFile;     //текущий номер файла и предыдущий
    private int count;      //счётчик, сколько осталось напечатать строк/символов
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
    private String outputName = "x";

    @Argument(required = true, usage = "Input file name", metaVar = "InputName")
    String inputFileName;


    private String getOutputNameCharChar() {        //формирование названия файла, если флаг -d не стоит
        String name = outputName;
        if (numberOutputFile < 27) name += "a" + (char) ((int) 'a' + numberOutputFile - 1);
        else name += (char) ((int) 'a' + (numberOutputFile - 1) / 26) + "" +  (char) ((int) 'a' + (numberOutputFile - 1) % 26);
        return name;
    }

    private void writeToFile(String text, int size) throws IOException {
        if (numberOutputFile - prNumberOutputFile > 1 ) {       //если номер текущего файла изменился, создаём новый
            prNumberOutputFile++;
            writer = new BufferedWriter(new FileWriter("Files\\" + nameOfFile));
        }
        writer.write(text);
        if (!text.equals("\n")) count--;
        if (count == 0) {       //когда напечатали всё
            writer.close();
            numberOutputFile++;     //увеличиваем номер файла
            nameOfFile = flagD ? outputName + numberOutputFile : getOutputNameCharChar();       //имя следующего файла
            count = size;       //кол-во строк/символов, которое надо напечатать в следующем файле
        }
    }

    private void workOutputFile(boolean line, int size) throws IOException {
        int ch;       //код символа
        String initialName, str;    //начальное имя, строка
        prNumberOutputFile = 0;     //инициализация текущего
        numberOutputFile = 1;       //и предыдущего номера файла
        count = size;       //кол-во символо/строк, которые надо напечатать
        initialName = flagD ? outputName + "1" : outputName + "aa";     //название первого выходного файла
        writer = new BufferedWriter(new FileWriter("Files\\" + initialName));
        if (line) {
            while ((str = reader.readLine()) != null)       //записываем в файлы строки
                writeToFile(str + "\n", size);
        }
         else {
            while ((ch = reader.read()) != -1)      //записываем в файлы символы
                writeToFile("" + (char) ch, size);
        }
    }

    public void parseArgs(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        count = 0;
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("\nExample: split [-d] [-l num | -c num | -n num] [-o outputName] inputName");
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }
        reader = new BufferedReader(new FileReader("Files\\" + inputFileName));
        if (outputName.equals("-")) outputName = inputFileName;
        if (numberLines != -1) workOutputFile(true, numberLines);       //установлен флаг -l, работаем с кол-вом строк
        else if (numberCharacters != -1) workOutputFile(false, numberCharacters);       //установлен флаг -c, работаем с кол-вом символов
        else if (numberFile != -1) {        //установлен флаг -n, работаем с кол-вом файлов
            int numberCharsInFile = 0;
            while (reader.read() != -1) numberCharsInFile++;        //считаем кол-во символов в исходном файле
            reader.close();
            reader = new BufferedReader(new FileReader("Files\\" + inputFileName));
            numberCharacters = (int) Math.ceil((double) numberCharsInFile / numberFile);        //считаем кол-во символов в одном файле
            workOutputFile(false, numberCharacters);        //работаем с кол-вом символов
        }
        else workOutputFile(true, 100);     //если флаги не стоят, то в выходных файлах 100 строк
        reader.close();
        writer.close();
    }
}
