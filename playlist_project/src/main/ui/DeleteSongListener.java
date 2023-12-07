package ui;

import model.Playlist;
import model.Song;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents the Action Listener for the delete song button
// code influenced by ListDemo https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
public class DeleteSongListener implements ActionListener {
    private JButton deleteSongButton;
    private JList songList;
    private DefaultListModel listModel;
    private Playlist playlist;

    // EFFECTS: constructs delete song listener with its delete song button, list of songs, and list model
    public DeleteSongListener(JButton deleteSongButton, JList songList, DefaultListModel listModel, Playlist playlist) {
        this.deleteSongButton = deleteSongButton;
        this.songList = songList;
        this.listModel = listModel;
        this.playlist = playlist;
    }

    // MODIFIES: listModel, deleteSongButton, songList
    // EFFECTS: removes the selected song from the playlist then disables
    //          the delete song button if songList is empty
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = songList.getSelectedIndex();
        String title = (String) listModel.get(index);
        Song s = playlist.getSongByTitle(title);
        if (s != null) {
            playlist.deleteSong(title);
            listModel.remove(index);
        }
        int size = listModel.getSize();
        if (size == 0) {
            deleteSongButton.setEnabled(false);
        } else {
            if (index == listModel.getSize()) {
                index--;
            }
            songList.setSelectedIndex(index);
            songList.ensureIndexIsVisible(index);
        }
    }
}