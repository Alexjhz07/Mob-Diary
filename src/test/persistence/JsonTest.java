package persistence;

import model.Category;
import model.Mob;

import static org.junit.jupiter.api.Assertions.assertEquals;

// NOTE: THIS CLASS IS A MODIFIED VERSION FROM THE PROVIDED EXAMPLE FILE OF PROJECT PHASE 2
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Helper functions for testing JsonReader and JsonWriter
public class JsonTest {
    protected void checkCategory(String title, int size, Category category) {
        assertEquals(title, category.getTitle());
        assertEquals(size, category.getMobs().size());
    }

    protected void checkMob(String name, String stats, String description, int dropSize, Mob mob) {
        assertEquals(name, mob.getName());
        assertEquals(stats, mob.getStats());
        assertEquals(description, mob.getDescription());
        assertEquals(dropSize, mob.getDrops().size());
    }
}
