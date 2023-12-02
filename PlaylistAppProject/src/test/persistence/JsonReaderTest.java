package persistence;

import model.Song;
import model.Playlist;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderEmptyPlaylist() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPlaylist.json");
        try {
            Playlist p = reader.read();
            assertEquals(0, p.getTotalNumSongs());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }



    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Playlist p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }


    @Test
    void testReaderGeneralPlaylist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPlaylist.json");
        try {
            Playlist p = reader.read();
            List<Song> songs = p.getSongList();
            assertEquals(2, songs.size());
            checkSong("Dancing Queen", "ABBA", 230, songs.get(0));
            checkSong( "Talking to the Moon", "Bruno Mars", 217, songs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
