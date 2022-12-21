package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a mob having a name, stats, drops, and description
public class Mob implements Writable {
    private String name;             // Name of mob
    private String stats;            // Additional stats for this mob
    private ArrayList<String> drops; // ArrayList of item drop names for this mob
    private String description;      // Full description for this mob

    // REQUIRES: name is not the empty string
    // EFFECTS: Constructs a mob with the given name,
    //          given stats if provided or "No special stats" if not provided,
    //          empty drops,
    //          and given description if provided or "No description" if not provided
    public Mob(String name, String stats, String description) {
        this.name = name;
        if (stats.isEmpty()) {
            this.stats = "No special stats";
        } else {
            this.stats = stats;
        }
        drops = new ArrayList<>();
        if (description.isEmpty()) {
            this.description = "No description";
        } else {
            this.description = description;
        }
    }

    // REQUIRES: dropName is not the empty string
    //           dropName does not already exist in the drop list
    // MODIFIES: this
    // EFFECTS: adds the dropName to the end of the drops list
    public void addDrop(String dropName) {
        EventLog.getInstance().logEvent(new Event("Added drop " + dropName + " to mob " + name));
        drops.add(dropName);
    }

    // REQUIRES: dropName is not the empty string,
    //           drops contains a drop named dropName
    // MODIFIES: this
    // EFFECTS: removes the drop with dropName
    public void removeDrop(String dropName) {
        EventLog.getInstance().logEvent(new Event("Removed drop " + dropName + " from mob " + name));
        drops.removeIf(drop -> drop.equals(dropName));
    }

    // REQUIRES: name is not the empty string
    //           Category containing this mob does not have another mob with the same newName
    public void setName(String newName) {
        EventLog.getInstance().logEvent(new Event("Changed mob name " + name + " to " + newName));
        name = newName;
    }

    // REQUIRES: stats is not the empty string
    public void setStats(String newStats) {
        EventLog.getInstance().logEvent(new Event("Changed " + name + " stats to " + newStats));
        stats = newStats;
    }

    // REQUIRES: description is not the empty string
    public void setDescription(String newDescription) {
        EventLog.getInstance().logEvent(new Event("Changed " + name + " description to " + newDescription));
        description = newDescription;
    }

    public String getName() {
        return name;
    }

    public String getStats() {
        return stats;
    }

    public ArrayList<String> getDrops() {
        return drops;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("stats", stats);
        json.put("drops", dropsToJson());
        json.put("description", description);
        return json;
    }

    // EFFECTS: Convert all drops in mob to json array
    private JSONArray dropsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String d : drops) {
            jsonArray.put(d);
        }

        return jsonArray;
    }
}
