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
        String[] cmd = "-d -l 5 -o - files\\First".split(" ");     //тест: по строкам, флаг -d, флаг -o "-"
        Launcher.main(cmd);
        assertTrue(contentEquals(new File("files\\First1.txt"), new File("files\\expected\\eFirst1.txt")));
        assertTrue(contentEquals(new File("files\\First2.txt"), new File("files\\expected\\eFirst2.txt")));
        assertTrue(contentEquals(new File("files\\First3.txt"), new File("files\\expected\\eFirst3.txt")));
        assertTrue(contentEquals(new File("files\\First4.txt"), new File("files\\expected\\eFirst4.txt")));

        cmd = "-c 30 -o files\\xyz files\\Second".split(" ");     //тест: по символам, флага -d нет, флаг -о "xyz"
        Launcher.main(cmd);
        assertTrue(contentEquals(new File("files\\xyzaa.txt"), new File("files\\expected\\exyzaa.txt")));
        assertTrue(contentEquals(new File("files\\xyzab.txt"), new File("files\\expected\\exyzab.txt")));
        assertTrue(contentEquals(new File("files\\xyzac.txt"), new File("files\\expected\\exyzac.txt")));
        assertTrue(contentEquals(new File("files\\xyzad.txt"), new File("files\\expected\\exyzad.txt")));
        assertTrue(contentEquals(new File("files\\xyzae.txt"), new File("files\\expected\\exyzae.txt")));

        cmd = "-n 5 files\\Third".split(" ");      //тест: по кол-ву файлов, флага -d нет, флага -о нет
        Launcher.main(cmd);
        assertTrue(new File("files\\xaa.txt").exists());
        assertTrue(new File("files\\xab.txt").exists());
        assertTrue(new File("files\\xac.txt").exists());
        assertTrue(new File("files\\xad.txt").exists());
        assertTrue(new File("files\\xae.txt").exists());
    }
}