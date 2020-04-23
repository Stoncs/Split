import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    @Option(name = "-d", usage = "sequence name")
    private boolean outputNames = false;

    @Option(name = "-l", usage = "number of rows (default: 100)", forbids = {"-c", "-n"})
    private int rows = -1;

    @Option(name = "-c", usage = "the size of the output file in characters", forbids = {"-l", "-n"})
    private int size = -1;

    @Option(name = "-n", usage = "number of output files", forbids = {"-l", "-c"})
    private int number = -1;

    @Option(name = "-o", usage = "output name file", metaVar = "OUTPUT")
    private String out;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public static void main (String[] args){
        new Parser().parseArgs(args);
    }

    public void parseArgs(String [] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (arguments.isEmpty() || (!arguments.get(0).equals("split")) || (rows > 0 && size > 0) ||
                    (rows > 0 && number > 0) || (size > 0 && number > 0)) {
                System.err.println("Error entering arguments (for correct input, see the example)");
                System.err.println("split [options...] arguments...");
                parser.printUsage(System.err);
                System.err.println("\nExample: split [-d] [-l num | -c num | -n num] [-o outputname] inputname");
                throw new IllegalArgumentException("");
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("split [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println("\nExample: split [-d] [-l num | -c num | -n num] [-o outputname] inputname");
            throw new IllegalArgumentException("");
        }

    }
}
