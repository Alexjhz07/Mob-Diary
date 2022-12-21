package persistence;

import org.json.JSONObject;

// NOTE: THIS CLASS IS FROM THE PROVIDED EXAMPLE FILE OF PROJECT PHASE 2
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}