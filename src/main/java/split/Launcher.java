package split;

import java.io.*;

class Launcher {
    public static void main(String[] args){
        Parser parser = new Parser();
        try {
            parser.parseArgs(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
