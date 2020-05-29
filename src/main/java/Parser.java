import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;

public class Parser {
    private int numberOutputFile, prNumberOutputFile;
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
    private String outputName = "x";

    @Argument(required = true, usage = "Input file name", metaVar = "InputName")
    String inputFileName;


    private String getOutputNameCharChar() {
        String name = outputName;
        if (numberOutputFile < 27) name += "a" + (char) ((int) 'a' + numberOutputFile - 1);
        else name += (char) ((int) 'a' + (numberOutputFile - 1) / 26) + "" +  (char) ((int) 'a' + (numberOutputFile - 1) % 26);
        return name;
    }

    private void writeToFile(String text, int size) throws IOException {
        if (numberOutputFile - prNumberOutputFile > 1 ) {
            prNumberOutputFile++;
            writer = new BufferedWriter(new FileWriter("Files\\" + nameOfFile));
        }
        writer.write(text);
        count--;
        if (count == 0) {
            writer.close();
            numberOutputFile++;
            nameOfFile = flagD ? outputName + numberOutputFile : getOutputNameCharChar();
            count = size;
        }
    }

    private void workOutputFile(boolean line, int size) throws IOException {
        int ch;
        String initialName, str;
        prNumberOutputFile = 0;
        numberOutputFile = 1;
        count = size;
        initialName = flagD ? outputName + "1" : outputName + "aa";
        writer = new BufferedWriter(new FileWriter("Files\\" + initialName));
        if (line) {
            while ((str = reader.readLine()) != null)
                writeToFile(str + "\n", size);
        }
         else {
            while ((ch = reader.read()) != -1)
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
            System.err.println("\nExample: split [-d] [-l num | -c num | -n num] [-o outputname] inputname.txt");
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }
        reader = new BufferedReader(new FileReader("Files\\" + inputFileName));
        if (outputName.equals("-")) outputName = inputFileName;
        if (numberLines != -1) workOutputFile(true, numberLines);
        else if (numberCharacters != -1) workOutputFile(false, numberCharacters);
        else if (numberFile != -1) {
            int numberLinesInFile = 0;
            while (reader.readLine() != null) numberLinesInFile++;
            reader.close();
            reader = new BufferedReader(new FileReader("Files\\" + inputFileName));
            numberLines = (int) Math.ceil((double) numberLinesInFile / numberFile);
            workOutputFile(true, numberLines);
        }
        else workOutputFile(true, 100);
        reader.close();
        writer.close();
    }
}
