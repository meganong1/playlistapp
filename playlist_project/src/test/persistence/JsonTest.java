package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Song;

public class JsonTest {
    protected void checkSong(String title, String artist, int songLength, Song song) {
        assertEquals(title, song.getTitle());
        assertEquals(artist, song.getArtist());
        assertEquals(songLength, song.getSongLength());
    }
}
