package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// NOTE: THIS CLASS IS A MODIFIED VERSION FROM THE PROVIDED EXAMPLE FILE OF PROJECT PHASE 2
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads Diary from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Diary from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Diary read() throws IOException {
        EventLog.getInstance().logEvent(new Event("Diary Loaded"));
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfCategory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Diary from JSON object and returns it
    private Diary parseListOfCategory(JSONObject jsonObject) {
        Diary loc = new Diary();
        addCategories(loc, jsonObject);
        return loc;
    }

    // MODIFIES: diary
    // EFFECTS: parses Category from JSON object and adds them to Diary
    private void addCategories(Diary diary, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("categories");
        for (Object json : jsonArray) {
            JSONObject nextCategory = (JSONObject) json;
            Category category = new Category(nextCategory.getString("title"));
            addMobs(category, nextCategory);
            diary.addCategory(category);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses Mob from JSON object and adds it to Category
    private void addMobs(Category c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("mobs");
        for (Object json : jsonArray) {
            JSONObject nextMob = (JSONObject) json;
            Mob mob = new Mob(nextMob.getString("name"),
                    nextMob.getString("stats"), nextMob.getString("description"));
            addDrops(mob, nextMob.getJSONArray("drops"));
            c.addMob(mob);
        }
    }

    // MODIFIES: m
    // EFFECTS: parses List of String for drops in Mob and adds it to the mob
    private void addDrops(Mob m, JSONArray jsonArray) {
        for (Object drop : jsonArray) {
            m.addDrop((String) drop);
        }
    }
}
