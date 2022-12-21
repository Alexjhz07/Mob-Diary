package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;

// Represents a Diary containing a list of mob categories
public class Diary implements Writable {
    private ArrayList<Category> categories; // Full list of categories in this list

    // EFFECTS: Creates an empty Diary
    public Diary() {
        categories = new ArrayList<>();
    }

    // REQUIRES: title is not the empty string,
    //           category with same title must not exist in this list
    // MODIFIES: this
    // EFFECTS: adds an empty category with the given title to the end of this list
    public void addCategory(String title) {
        EventLog.getInstance().logEvent(new Event("Added new category " + title));
        Category newCategory = new Category(title);
        categories.add(newCategory);
    }

    // REQUIRES: Category is valid
    // MODIFIES: this
    // EFFECTS: adds a category to the end of this list
    public void addCategory(Category category) {
        EventLog.getInstance().logEvent(new Event("Added new category " + category.getTitle()));
        categories.add(category);
    }

    // REQUIRES: title is not the empty string,
    //           category with title must exist in this list
    // MODIFIES: this
    // EFFECTS: removes a category with the given title from this list
    public void removeCategory(String title) {
        EventLog.getInstance().logEvent(new Event("Removed category " + title));
        categories.removeIf(category -> category.getTitle().equals(title));
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("categories", categoriesToJson());
        return json;
    }

    // EFFECTS: returns json array of categories
    private JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Category c : categories) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
