package persistence;

import model.Playlist;
import model.Song;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads a playlist from JSON data stored in file
// Code based on JsonReader from JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader that will read given source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads playlist from file and returns it;
    //          throws IOException if it fails to read data stored in file
    public Playlist read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlaylist(jsonObject);
    }

    // EFFECTS: reads given source file and returns it as a string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses playlist from JSON object and returns it
    private Playlist parsePlaylist(JSONObject jsonObject) {
        Playlist p = new Playlist();
        addSongs(p, jsonObject);
        return p;
    }


    // MODIFIES: p
    // EFFECTS: parses songs from JSON object and adds them to playlist
    private void addSongs(Playlist p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("playlist");
        for (Object json : jsonArray) {
            JSONObject next = (JSONObject) json;
            addSong(p, next);
        }
    }


    // MODIFIES: p
    // EFFECTS: parses song from JSON object and adds it to playlist
    private void addSong(Playlist p, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String artist = jsonObject.getString("artist");
        int songLength = jsonObject.getInt("songLength");
        Song song = new Song(title, artist, songLength);
        p.addSong(song);

    }



}



