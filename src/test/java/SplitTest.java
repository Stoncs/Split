import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.apache.commons.io.FileUtils.contentEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SplitTest {

    @Test
    void tests() throws IOException {
        String[] cmd = new String[] {"-d -l 5 -o - First"};     //тест: по строкам, флаг -d, флаг -o "-"
        Split.main(cmd);
        assertTrue(contentEquals(new File("Files/First1"), new File("Files/Expected/First1")));
        assertTrue(contentEquals(new File("Files/First2"), new File("Files/Expected/First2")));
        assertTrue(contentEquals(new File("Files/First3"), new File("Files/Expected/First3")));
        assertTrue(contentEquals(new File("Files/First4"), new File("Files/Expected/First4")));
        assertTrue(contentEquals(new File("Files/First5"), new File("Files/Expected/First5")));
    }
}