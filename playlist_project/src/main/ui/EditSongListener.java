package ui;


import model.Playlist;
import model.Song;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents the Action Listener for the edit song button
public class EditSongListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private JButton button;
    private PlaylistAppFrame frame;
    private DefaultListModel listModel;
    private JList list;
    private Playlist playlist;

    // EFFECTS: constructs edit song listener with its edit song button, playlist app frame, list model
    //          and list of songs
    public EditSongListener(JButton editSongButton, PlaylistAppFrame frame, DefaultListModel listModel,
                            JList list, Playlist playlist) {
        this.button = editSongButton;
        this.frame = frame;
        this.listModel = listModel;
        this.list = list;
        this.playlist = playlist;
    }

    // MODIFIES: listModel, frame.textField
    // EFFECTS: If a song is selected and the text field is not empty,
    //          updates the song title with the text from the text field then clears the text field.
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = list.getSelectedIndex();
        String name = frame.getTextInput();

        String title = (String) listModel.get(index);
        Song s = playlist.getSongByTitle(title);
        if (s != null) { // Check if the song exists in the playlist
            s.editSongTitle(name);
        }
        if (index >= 0 && !name.isEmpty()) {
            listModel.setElementAt(name, index);
            frame.resetText();
        }
    }

    // MODIFIES: button
    // EFFECTS: enables the edit button if it was disabled and a song is selected on songList
    @Override
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }


    // MODIFIES: button
    // EFFECTS: disables the edit button if the text field is empty
    @Override
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }


    // MODIFIES: button
    // EFFECTS: enables the edit song button if the text field is not empty after a change occurs
    @Override
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    // MODIFIES: button
    // EFFECTS: enables the edit button if it was disabled and a song is selected in the songList
    private void enableButton() {
        if (!alreadyEnabled && list.getSelectedIndex() >= 0) {
            button.setEnabled(true);
        }
    }

    // MODIFIES: button, alreadyEnabled
    // EFFECTS: disables the edit button and sets alreadyEnabled to false if the text field is empty.
    //          returns true if the text field is empty, false otherwise.
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}
