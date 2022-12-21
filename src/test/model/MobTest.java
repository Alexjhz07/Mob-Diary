package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MobTest {
    private Mob mob;

    @BeforeEach
    void runBefore() {
        mob = new Mob("Goblin", "1 armour", "A green warrior");
    }

    @Test
    void testConstructor() {
        assertEquals("Goblin", mob.getName());
        assertEquals("1 armour", mob.getStats());
        assertEquals(0, mob.getDrops().size());
        assertEquals("A green warrior", mob.getDescription());
    }

    @Test
    void testConstructorEmptyStats() {
        mob = new Mob("Goblin", "", "A green warrior");
        assertEquals("Goblin", mob.getName());
        assertEquals("No special stats", mob.getStats());
        assertEquals(0, mob.getDrops().size());
        assertEquals("A green warrior", mob.getDescription());
    }

    @Test
    void testConstructorEmptyDescription() {
        mob = new Mob("Goblin", "1 armour", "");
        assertEquals("Goblin", mob.getName());
        assertEquals("1 armour", mob.getStats());
        assertEquals(0, mob.getDrops().size());
        assertEquals("No description", mob.getDescription());
    }

    @Test
    void testAddDrop() {
        mob.addDrop("Iron nugget");
        assertEquals(1, mob.getDrops().size());
        assertEquals("Iron nugget", mob.getDrops().get(0));
    }

    @Test
    void testAddMultipleDrops() {
        mob.addDrop("Iron nugget");
        mob.addDrop("Sword");
        assertEquals(2, mob.getDrops().size());
        assertEquals("Iron nugget", mob.getDrops().get(0));
        assertEquals("Sword", mob.getDrops().get(1));
    }

    @Test
    void testSingleRemoveDropNoRemain() {
        mob.addDrop("Iron nugget");
        mob.removeDrop("Iron nugget");
        assertEquals(0, mob.getDrops().size());
    }

    @Test
    void testSingleRemoveDropSingleRemain() {
        mob.addDrop("Iron nugget");
        mob.addDrop("Sword");
        mob.removeDrop("Iron nugget");
        assertEquals(1, mob.getDrops().size());
        assertEquals("Sword", mob.getDrops().get(0));
    }

    @Test
    void testSingleRemoveDropMultipleRemain() {
        mob.addDrop("Iron nugget");
        mob.addDrop("Sword");
        mob.addDrop("Torch");
        mob.addDrop("Leather");
        mob.removeDrop("Sword");
        assertEquals(3, mob.getDrops().size());
        assertEquals("Iron nugget", mob.getDrops().get(0));
        assertEquals("Torch", mob.getDrops().get(1));
        assertEquals("Leather", mob.getDrops().get(2));
    }

    @Test
    void testMultipleRemoveDropMultipleRemain() {
        mob.addDrop("Iron nugget");
        mob.addDrop("Sword");
        mob.addDrop("Torch");
        mob.addDrop("Leather");
        mob.addDrop("Bacon");
        mob.removeDrop("Sword");
        mob.removeDrop("Bacon");
        assertEquals(3, mob.getDrops().size());
        assertEquals("Iron nugget", mob.getDrops().get(0));
        assertEquals("Torch", mob.getDrops().get(1));
        assertEquals("Leather", mob.getDrops().get(2));
    }

    @Test
    void testSetName() {
        mob.setName("Eula");
        assertEquals("Eula", mob.getName());
        mob.setName("Mister Li");
        assertEquals("Mister Li", mob.getName());
    }

    @Test
    void testSetStats() {
        mob.setStats("Plays music");
        assertEquals("Plays music", mob.getStats());
        mob.setStats("Dances");
        assertEquals("Dances", mob.getStats());
    }

    @Test
    void testSetDescription() {
        mob.setDescription("Prancing jester");
        assertEquals("Prancing jester", mob.getDescription());
        mob.setDescription("Thanks for reading :)");
        assertEquals("Thanks for reading :)", mob.getDescription());
    }
}
