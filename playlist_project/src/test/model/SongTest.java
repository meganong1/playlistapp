package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongTest {
    Song s1;
    @BeforeEach
    public void runBefore() {
        s1 = new Song("Bohemian Rhapsody", "Queen", 333);
    }

    @Test
    void testConstructor() {
        assertEquals("Bohemian Rhapsody", s1.getTitle());
        assertEquals("Queen", s1.getArtist());
        assertEquals(333, s1.getSongLength());
    }


    @Test
    void testEditSongTitle() {
        s1.editSongTitle("Don't Stop Me Now");
        assertEquals("Don't Stop Me Now", s1.getTitle());
        s1.editSongTitle("Another One Bites The Dust");
        assertEquals("Another One Bites The Dust", s1.getTitle());

    }

    @Test
    void testViewSong() {
        assertEquals(("\r\nTitle: " + "Bohemian Rhapsody" + "\r\nArtist: " + "Queen" +
                        "\r\nLength (in seconds): " + 333),
                s1.viewSong());
    }
}
