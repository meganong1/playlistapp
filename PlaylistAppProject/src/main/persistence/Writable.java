package persistence;

import org.json.JSONObject;

// Represents the current state of the object in JSON format
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
