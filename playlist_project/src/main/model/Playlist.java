package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;


// Represents a playlist with total length in seconds and an empty list of songs
public class Playlist implements Writable {
    private int playlistLength;        // total length of all songs in seconds
    private int totalNumSongs;         // total number of songs playlist
    private List<Song> songList;       // list of songs in playlist



    // EFFECTS: Constructs a Playlist with initial length (in seconds) of 0, total number of songs of 0,
    //          and an empty list of songs.
    public Playlist() {
        playlistLength = 0;
        totalNumSongs = 0;
        songList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a song to the Playlist's songList
    public void addSong(Song s) {
        if (getSongByTitle(s.getTitle()) != null) {
            try {
                throw new DuplicateSongException("Song with title '" + s.getTitle() + "' already exists.");
            } catch (DuplicateSongException e) {
                throw new RuntimeException(e);
            }
        }
        EventLog.getInstance().logEvent(new Event("Added song to playlist: " + s.getTitle()));
        songList.add(s);
        playlistLength += s.getSongLength();
        totalNumSongs += 1;
    }

    // Represents exception class for duplicate song titles
    class DuplicateSongException extends Exception {
        public DuplicateSongException(String message) {
            super(message);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a song from the Playlist's songList
    public void deleteSong(String title) {
        Song s = getSongByTitle(title);
        EventLog.getInstance().logEvent(new Event("Removed song from playlist: " + s.getTitle()));
        songList.remove(s);
        playlistLength -= s.getSongLength();
        totalNumSongs -= 1;
    }

    // EFFECTS: returns information of all songs
    public String viewAllSongs() {
        StringBuilder str = new StringBuilder();
        for (Song song : songList) {
            str.append(song.viewSong());
        }
        return str.toString();
    }

    // EFFECTS: returns the song with the given title.
    //          if song is not found return null
    public Song getSongByTitle(String title) {
        for (Song song : songList) {
            if (song.getTitle().equalsIgnoreCase(title)) {
                return song;
            }
        }
        return null;
    }


    // MODIFIES: this
    // EFFECTS: clears the songList so there are no songs
    public void clear() {
        songList.clear();
    }

    public List<Song> getSongList() {
        return songList;
    }

    public int getTotalNumSongs() {
        return totalNumSongs;
    }

    public int getPlaylistLength() {
        return playlistLength;
    }

    @Override
    // EFFECTS: returns playlist as a JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playlist", songsToJson());
        return json;
    }

    // EFFECTS: returns songs in playlist as a JSON Array
    public JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Song s : songList) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: prints event log to the console
    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println("\r\n" + next.toString());
        }
    }






}


