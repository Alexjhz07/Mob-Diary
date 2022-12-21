package persistence;

import model.Diary;
import model.Event;
import model.EventLog;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// NOTE: THIS CLASS IS A MODIFIED VERSION FROM THE PROVIDED EXAMPLE FILE OF PROJECT PHASE 2
// Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that writes JSON representation of Diary to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Diary to file
    public void write(Diary diary) {
        EventLog.getInstance().logEvent(new Event("Diary Saved"));
        JSONObject json = diary.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}