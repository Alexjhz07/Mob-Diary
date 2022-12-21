package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import java.util.ArrayList;

// Represents a category with a title that can contain mobs
public class Category implements Writable {
    private String title;        // Title of this category
    private ArrayList<Mob> mobs; // Full list of mobs in this list

    // REQUIRES: title is not the empty string
    // EFFECTS: Constructs an empty category with the specified title
    public Category(String title) {
        this.title = title;
        mobs = new ArrayList<>();
    }

    // REQUIRES: name is not the empty string
    //           mob with same name must not exist in this category
    // MODIFIES: this
    // EFFECTS: Constructs a mob with the given name,
    //          empty drops,
    //          given stats,
    //          and given description
    //          and adds it to the end of this category
    public void addMob(String name, String stats, String description) {
        EventLog.getInstance().logEvent(new Event("Added mob " + name + " to category " + title));
        Mob newMob = new Mob(name, stats, description);
        mobs.add(newMob);
    }

    // REQUIRES: Mob is valid
    // MODIFIES: this
    // EFFECTS: Adds a given mob to the end of this category
    public void addMob(Mob mob) {
        EventLog.getInstance().logEvent(new Event("Added mob " + mob.getName() + " to category " + title));
        mobs.add(mob);
    }

    // REQUIRES: name is not the empty string,
    //           mob with name must exist in this list
    // MODIFIES: this
    // EFFECTS: removes a mob with the given name from this list
    public void removeMob(String name) {
        EventLog.getInstance().logEvent(new Event("Removed mob " + name + " from category " + title));
        mobs.removeIf(mob -> mob.getName().equals(name));
    }

    // REQUIRES: title is not the empty string,
    //           Diary containing this category does not have category with the same newTitle
    public void setTitle(String newTitle) {
        EventLog.getInstance().logEvent(new Event("Changed category name from " + title + " to " + newTitle));
        title = newTitle;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("mobs", mobsToJson());
        return json;
    }

    // EFFECT: Convert mobs in category to json array
    private JSONArray mobsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Mob m : mobs) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }
}
