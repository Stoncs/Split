package split;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.function.BooleanSupplier;

import static org.apache.commons.io.FileUtils.contentEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LauncherTest {

    @Test
    void tests() throws IOException {
        String[] cmd = "-d -l 5 -o - First".split(" ");     //тест: по строкам, флаг -d, флаг -o "-"
        Launcher.main(cmd);
        assertTrue(contentEquals(new File("Files\\First1.txt"), new File("Files\\Expected\\eFirst1.txt")));
        assertTrue(contentEquals(new File("Files\\First2.txt"), new File("Files\\Expected\\eFirst2.txt")));
        assertTrue(contentEquals(new File("Files\\First3.txt"), new File("Files\\Expected\\eFirst3.txt")));
        assertTrue(contentEquals(new File("Files\\First4.txt"), new File("Files\\Expected\\eFirst4.txt")));

        cmd = "-c 30 -o xyz Second".split(" ");     //тест: по символам, флага -d нет, флаг -о "xyz"
        Launcher.main(cmd);
        assertTrue(contentEquals(new File("Files\\xyzaa.txt"), new File("Files\\Expected\\exyzaa.txt")));
        assertTrue(contentEquals(new File("Files\\xyzab.txt"), new File("Files\\Expected\\exyzab.txt")));
        assertTrue(contentEquals(new File("Files\\xyzac.txt"), new File("Files\\Expected\\exyzac.txt")));
        assertTrue(contentEquals(new File("Files\\xyzad.txt"), new File("Files\\Expected\\exyzad.txt")));
        assertTrue(contentEquals(new File("Files\\xyzae.txt"), new File("Files\\Expected\\exyzae.txt")));

        cmd = "-n 5 Third".split(" ");      //тест: по кол-ву файлов, флага -d нет, флага -о нет
        Launcher.main(cmd);
        assertTrue(new File("Files\\xaa.txt").exists());
        assertTrue(new File("Files\\xab.txt").exists());
        assertTrue(new File("Files\\xac.txt").exists());
        assertTrue(new File("Files\\xad.txt").exists());
        assertTrue(new File("Files\\xae.txt").exists());
    }
}