package persistence;

import model.Category;
import model.Diary;
import model.Mob;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// NOTE: THIS CLASS IS A MODIFIED VERSION FROM THE PROVIDED EXAMPLE FILE OF PROJECT PHASE 2
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Diary diary = new Diary();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDiary() {
        try {
            Diary diary = new Diary();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDiary.json");
            writer.open();
            writer.write(diary);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDiary.json");
            diary = reader.read();
            assertEquals(0, diary.getCategories().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterFilledDiary() {
        try {
            Diary diary = new Diary();

            Category woodlands = new Category("Woodlands");
            Category ocean = new Category("Ocean");
            Category mountain = new Category("Mountain");

            Mob tree = new Mob("Tree", "Woody", "Old");
            Mob goat = new Mob("Goat", "Tough", "Young");
            Mob dragon = new Mob("Dragon", "Large", "Scary");

            tree.addDrop("Stick");
            dragon.addDrop("Scales");
            dragon.addDrop("Dragon Meat");

            woodlands.addMob(tree);
            mountain.addMob(goat);
            mountain.addMob(dragon);

            diary.addCategory(woodlands);
            diary.addCategory(ocean);
            diary.addCategory(mountain);

            JsonWriter writer = new JsonWriter("./data/testWriterFilledDiary.json");
            writer.open();
            writer.write(diary);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFilledDiary.json");
            diary = reader.read();
            assertEquals(3, diary.getCategories().size());

            woodlands = diary.getCategories().get(0);
            ocean = diary.getCategories().get(1);
            mountain = diary.getCategories().get(2);

            checkCategory("Woodlands", 1, woodlands);
            checkCategory("Ocean", 0, ocean);
            checkCategory("Mountain", 2, mountain);

            tree = woodlands.getMobs().get(0);
            goat = mountain.getMobs().get(0);
            dragon = mountain.getMobs().get(1);

            checkMob("Tree", "Woody", "Old", 1, tree);
            checkMob("Goat", "Tough", "Young", 0, goat);
            checkMob("Dragon", "Large", "Scary",2, dragon);

            assertEquals("Stick", tree.getDrops().get(0));
            assertEquals("Scales", dragon.getDrops().get(0));
            assertEquals("Dragon Meat", dragon.getDrops().get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}