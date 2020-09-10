package split;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.contentEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LauncherTest {

    @Test
    void tests() throws IOException {
        String[] cmd = "-d -l 5 -o - First".split(" ");     //тест: по строкам, флаг -d, флаг -o "-"
        Launcher.main(cmd);
        assertTrue(contentEquals(new File("Files/First1"), new File("Files/Expected/eFirst1")));
        assertTrue(contentEquals(new File("Files/First2"), new File("Files/Expected/eFirst2")));
        assertTrue(contentEquals(new File("Files/First3"), new File("Files/Expected/eFirst3")));
        assertTrue(contentEquals(new File("Files/First4"), new File("Files/Expected/eFirst4")));

        cmd = "-c 30 -o xyz Second".split(" ");     //тест: по символам, флага -d нет, флаг -о "xyz"
        Launcher.main(cmd);
        assertTrue(contentEquals(new File("Files/xyzaa"), new File("Files/Expected/exyzaa")));
        assertTrue(contentEquals(new File("Files/xyzab"), new File("Files/Expected/exyzab")));
        assertTrue(contentEquals(new File("Files/xyzac"), new File("Files/Expected/exyzac")));
        assertTrue(contentEquals(new File("Files/xyzad"), new File("Files/Expected/exyzad")));
        assertTrue(contentEquals(new File("Files/xyzae"), new File("Files/Expected/exyzae")));

        cmd = "-n 5 Third".split(" ");      //тест: по кол-ву файлов, флага -d нет, флага -о нет
        Launcher.main(cmd);
        assertTrue(new File("Files/xaa").exists());
        assertTrue(new File("Files/xab").exists());
        assertTrue(new File("Files/xac").exists());
        assertTrue(new File("Files/xad").exists());
        assertTrue(new File("Files/xae").exists());

    }
}