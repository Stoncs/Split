import java.io.*;


class Main {
    public static void main(String[] args){
        Parser parser = new Parser();
        try {
            parser.parseArgs(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
