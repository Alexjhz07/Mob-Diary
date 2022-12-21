package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private Category category;

    @BeforeEach
    void runBefore() {
        category = new Category("A");
    }

    @Test
    void testConstructor() {
        assertEquals("A", category.getTitle());
        assertEquals(0, category.getMobs().size());
    }

    @Test
    void testAddMob() {
        category.addMob("Goblin", "1 armour", "A green warrior");
        assertEquals(1, category.getMobs().size());
        assertEquals("Goblin", category.getMobs().get(0).getName());
    }

    @Test
    void testAddMultipleMobs() {
        category.addMob("Goblin", "1 armour", "A green warrior");
        category.addMob("Skeleton", "Fierce", "Shoots arrows");
        assertEquals(2, category.getMobs().size());
        assertEquals("Goblin", category.getMobs().get(0).getName());
        assertEquals("Skeleton", category.getMobs().get(1).getName());
    }

    @Test
    void testSingleRemoveMobNoRemain() {
        category.addMob("Goblin", "1 armour", "A green warrior");
        category.removeMob("Goblin");
        assertEquals(0, category.getMobs().size());
    }

    @Test
    void testSingleRemoveMobSingleRemain() {
        category.addMob("Goblin", "1 armour", "A green warrior");
        category.addMob("Skeleton", "Fierce", "Shoots arrows");
        category.removeMob("Goblin");
        assertEquals(1, category.getMobs().size());
        assertEquals("Skeleton", category.getMobs().get(0).getName());
    }

    @Test
    void testMultipleRemoveMobMultipleRemain() {
        category.addMob("Goblin", "1 armour", "A green warrior");
        category.addMob("Skeleton", "Fierce", "Shoots arrows");
        category.addMob("Orc", "Heavy", "Wields an axe");
        category.addMob("Ogre", "Very heavy", "Swings a tree");
        category.addMob("Fish", "Bubbles", "Can swim");
        category.removeMob("Skeleton");
        category.removeMob("Fish");
        assertEquals(3, category.getMobs().size());
        assertEquals("Goblin", category.getMobs().get(0).getName());
        assertEquals("Orc", category.getMobs().get(1).getName());
        assertEquals("Ogre", category.getMobs().get(2).getName());
    }

    @Test
    void testSetTitle() {
        category.setTitle("B");
        assertEquals("B", category.getTitle());
        category.setTitle("C");
        assertEquals("C", category.getTitle());
    }
}