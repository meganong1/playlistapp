package ui;

import model.Playlist;
import model.Song;

import java.io.IOException;
import java.util.Scanner;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;

// represents the playlist application
public class PlaylistApp {
    private Playlist playlist;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/playlist.json";

    // EFFECTS: runs the playlist application
    // code based on TellerApp from Teller Application
    public PlaylistApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        playlist = new Playlist();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPlaylist();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // code based on TellerApp from Teller Application
    private void runPlaylist() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    // code based on TellerApp from Teller Application
    private void processCommand(String command) {
        if (command.equals("1")) {
            doAddSong();
        } else if (command.equals("2")) {
            doEditSong();
        } else if (command.equals("3")) {
            doViewTotalLength();
        } else if (command.equals("4")) {
            doViewAllSongs();
        } else if (command.equals("5")) {
            savePlaylist();
        } else if (command.equals("6")) {
            loadPlaylist();
        } else if (command.equals("7")) {
            doDeleteSong();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes playlist
    private void init() {
        playlist = new Playlist();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays input options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add song");
        System.out.println("\t2 -> edit song title");
        System.out.println("\t3 -> view length of playlist");
        System.out.println("\t4 -> view all songs");
        System.out.println("\t5 -> save playlist to file");
        System.out.println("\t6 -> load playlist from file");
        System.out.println("\t7 -> remove song");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: adds a song to Playlist
    private void doAddSong() {
        System.out.print("Enter title of song: ");
        String title = input.next();
        System.out.print("Enter name of artist: ");
        String artist = input.next();
        int length = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Enter length of song (in seconds): ");
            try {
                length = Integer.parseInt(input.next());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a number.");
            }
        }

        int idNum = playlist.getTotalNumSongs();
        Song newSong = new Song(title, artist, length);
        playlist.addSong(newSong);
        System.out.println("Song added successfully!");
    }

    // MODIFIES: this
    // EFFECTS: deletes song from Playlist
    private void doDeleteSong() {
        System.out.print("Enter title of song to remove: ");
        String title = input.next();
        Song s = playlist.getSongByTitle(title);
        if (s != null) {
            playlist.deleteSong(title);
            System.out.println("Song deleted successfully!");
        } else {
            System.out.println("No song found with given title");
        }
    }


    // MODIFIES: this
    // EFFECTS: changes a song's title
    private void doEditSong() {
        System.out.print("Enter the current title of the song: ");
        String currentTitle = input.next();
        System.out.print("Enter the new title: ");
        String newTitle = input.next();
        Song song = playlist.getSongByTitle(currentTitle);
        if (song != null) {
            song.editSongTitle(newTitle);
            System.out.println("Song title changed to " + newTitle + "!");
        } else {
            System.out.println("Song with title " + currentTitle + " does not exist...");
        }
    }

    // EFFECTS: returns a string representation of songs in Playlist
    private void doViewAllSongs() {
        System.out.print(playlist.viewAllSongs());
    }

    // EFFECTS: returns total length of songs in Playlist in seconds
    private void doViewTotalLength() {
        System.out.print(playlist.getPlaylistLength());
    }

    // EFFECTS: saves the playlist to file
    private void savePlaylist() {
        try {
            jsonWriter.open();
            jsonWriter.write(playlist);
            jsonWriter.close();
            System.out.println("Saved playlist to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }

    // MODIFIES: this
    // EFFECTS: loads playlist from file
    private void loadPlaylist() {
        try {
            playlist = jsonReader.read();
            System.out.println("Loaded playlist from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
