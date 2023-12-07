package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Song;
import model.Playlist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            Playlist p = new Playlist();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }



    @Test
    void testWriterEmptyPlaylist() {
        try {
            Playlist p = new Playlist();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPlaylist.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPlaylist.json");
            p = reader.read();
            assertEquals(0, p.getTotalNumSongs());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testWriterGeneralPlaylist() {
        try {
            Playlist p = new Playlist();
            p.addSong(new Song("Dancing Queen", "ABBA", 230));
            p.addSong(new Song("Talking to the Moon", "Bruno Mars", 217));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlaylist.json");
            writer.open();
            writer.write(p);
            writer.close();

            List<Song> songs = p.getSongList();
            assertEquals(2, songs.size());
            checkSong("Dancing Queen", "ABBA", 230, songs.get(0));
            checkSong("Talking to the Moon", "Bruno Mars", 217, songs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }



}
