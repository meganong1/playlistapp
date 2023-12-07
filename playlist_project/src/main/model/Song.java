package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents a song having a title, artist, and length in seconds
public class Song implements Writable {
    private String title;       // title of song
    private String artist;      // artist name of song
    private int songLength;     // length of song in seconds

    // REQUIRES: title and artist has a string length > 0 and length is > 0
    // EFFECTS: Constructs a song with a title, artist, and length in seconds
    public Song(String title, String artist, int songLength) {
        this.title = title;
        this.artist = artist;
        this.songLength = songLength;
    }

    // MODIFIES: this
    // EFFECTS: changes a song's title
    public void editSongTitle(String title) {
        this.title = title;
        EventLog.getInstance().logEvent(new Event("Song on playlist is renamed to: " + this.getTitle()));
    }

    // EFFECTS: returns information of a song as a string
    public String viewSong() {
        return ("\r\nTitle: " + title + "\r\nArtist: " + artist
                + "\r\nLength (in seconds): " + songLength);
    }


    public String getTitle() {
        return this.title;
    }

    public String getArtist() {
        return this.artist;
    }

    public int getSongLength() {
        return this.songLength;
    }

    // EFFECTS: creates and returns a song as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("artist", artist);
        json.put("songLength", songLength);
        return json;
    }
}
