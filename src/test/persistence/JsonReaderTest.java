package persistence;

import model.Category;
import model.Diary;
import model.Mob;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// NOTE: THIS CLASS IS A MODIFIED VERSION FROM THE PROVIDED EXAMPLE FILE OF PROJECT PHASE 2
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Diary diary = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDiary() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDiary.json");
        try {
            Diary diary = reader.read();
            assertEquals(0, diary.getCategories().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFilledDiary() {
        JsonReader reader = new JsonReader("./data/testReaderFilledDiary.json");
        try {
            Diary diary = reader.read();
            assertEquals(3, diary.getCategories().size());

            Category woodlands = diary.getCategories().get(0);
            Category ocean = diary.getCategories().get(1);
            Category mountain = diary.getCategories().get(2);

            checkCategory("Woodlands", 1, woodlands);
            checkCategory("Ocean", 0, ocean);
            checkCategory("Mountain", 2, mountain);

            Mob tree = woodlands.getMobs().get(0);
            Mob goat = mountain.getMobs().get(0);
            Mob dragon = mountain.getMobs().get(1);

            checkMob("Tree", "Woody", "Old", 1, tree);
            checkMob("Goat", "Tough", "Young", 0, goat);
            checkMob("Dragon", "Large", "Scary",2, dragon);

            assertEquals("Stick", tree.getDrops().get(0));
            assertEquals("Scales", dragon.getDrops().get(0));
            assertEquals("Dragon Meat", dragon.getDrops().get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}