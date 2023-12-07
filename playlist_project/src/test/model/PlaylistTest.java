package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class PlaylistTest {
    private Playlist p1;
    private List<Song> testSongList;
    private Song s1;
    private Song s2;
    private Song s3;

    @BeforeEach
    void runBefore() {
        p1 = new Playlist();
        testSongList = new ArrayList<>();
        s1 = new Song("Dancing Queen", "ABBA", 230);
        s2 = new Song("Talking to the Moon", "Bruno Mars", 217);
        s3 = new Song("Cruel Summer", "Taylor Swift", 178);
    }

    @Test
    void testConstructor() {
        assertEquals(0, p1.getPlaylistLength());
        assertEquals(0, p1.getTotalNumSongs());
        assertTrue(p1.getSongList().isEmpty());
        assertFalse(testSongList.contains(s1));
        assertFalse(testSongList.contains(s2));
        assertFalse(testSongList.contains(s3));

    }

    @Test
    void testAddSong() {
        p1.addSong(s1);
        assertEquals(230, p1.getPlaylistLength());
        assertEquals(1, p1.getTotalNumSongs());
        assertEquals(1, p1.getSongList().size());
        assertEquals(s1, p1.getSongList().get(0));



        p1.addSong(s2);
        assertEquals(230 + 217, p1.getPlaylistLength());
        assertEquals(2, p1.getTotalNumSongs());
        assertEquals(2, p1.getSongList().size());
        assertEquals(s2, p1.getSongList().get(1));


    }

    @Test
    void testDeleteSong() {
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);

        p1.deleteSong("Dancing Queen");
        assertEquals( + 217 + 178, p1.getPlaylistLength());
        assertEquals(s2, p1.getSongList().get(0));
        assertEquals(s3, p1.getSongList().get(1));

    }

    @Test
    void testGetSongById() {

    }

    @Test
    void testViewAllSongs() {
        p1.addSong(s1);
        String result = "\r\nTitle: " + "Dancing Queen" + "\r\nArtist: " + "ABBA"
                + "\r\nLength (in seconds): " + 230;
        assertEquals(result, p1.viewAllSongs());
        p1.addSong(s2);
        String result2 = "\r\nTitle: " + "Dancing Queen" + "\r\nArtist: " + "ABBA" +
                        "\r\nLength (in seconds): " + 230 + "\r\nTitle: " + "Talking to the Moon" +
                        "\r\nArtist: " + "Bruno Mars" + "\r\nLength (in seconds): " + 217;
        assertEquals(result2, p1.viewAllSongs());
    }

    @Test
    void testClear() {
        p1.addSong(s1);
        p1.addSong(s2);
        assertEquals(2, p1.getSongList().size());
        p1.clear();
        assertEquals(0, p1.getSongList().size());
    }

}
