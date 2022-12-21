package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiaryTest {
    private Diary list;

    @BeforeEach
    void runBefore() {
        list = new Diary();
    }

    @Test
    void testConstructor() {
        assertEquals(0, list.getCategories().size());
    }

    @Test
    void testAddCategory() {
        list.addCategory("A");
        assertEquals(1, list.getCategories().size());
        assertEquals("A", list.getCategories().get(0).getTitle());
    }

    @Test
    void testAddMultipleCategories() {
        list.addCategory("A");
        list.addCategory("B");
        assertEquals(2, list.getCategories().size());
        assertEquals("A", list.getCategories().get(0).getTitle());
        assertEquals("B", list.getCategories().get(1).getTitle());
    }

    @Test
    void testSingleRemoveCategoryNoRemain() {
        list.addCategory("A");
        list.removeCategory("A");
        assertEquals(0, list.getCategories().size());
    }

    @Test
    void testSingleRemoveCategorySingleRemain() {
        list.addCategory("A");
        list.addCategory("B");
        list.removeCategory("A");
        assertEquals(1, list.getCategories().size());
        assertEquals("B", list.getCategories().get(0).getTitle());
    }

    @Test
    void testSingleRemoveCategoryMultipleRemain() {
        list.addCategory("A");
        list.addCategory("B");
        list.addCategory("C");
        list.removeCategory("B");
        assertEquals(2, list.getCategories().size());
        assertEquals("A", list.getCategories().get(0).getTitle());
        assertEquals("C", list.getCategories().get(1).getTitle());
    }

    @Test
    void testMultipleRemoveCategoryMultipleRemain() {
        list.addCategory("A");
        list.addCategory("B");
        list.addCategory("C");
        list.addCategory("D");
        list.addCategory("E");
        list.removeCategory("B");
        list.removeCategory("E");
        assertEquals(3, list.getCategories().size());
        assertEquals("A", list.getCategories().get(0).getTitle());
        assertEquals("C", list.getCategories().get(1).getTitle());
        assertEquals("D", list.getCategories().get(2).getTitle());
    }
}
