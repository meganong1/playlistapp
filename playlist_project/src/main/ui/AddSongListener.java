package ui;

import model.Playlist;
import model.Song;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// represents the Action Listener for the add song button
// code influenced by ListDemo https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
public class AddSongListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled;
    private JButton button;
    private PlaylistAppFrame frame;
    private DefaultListModel listModel;
    private JList songList;
    private Playlist playlist;


    // EFFECTS: constructs the add song listener with its add song button, playlist app frame,
    //          list of songs, and list model with the button not already enabled
    public AddSongListener(JButton addSongButton, PlaylistAppFrame frame, DefaultListModel listModel,
                           JList songList, Playlist playlist) {
        this.button = addSongButton;
        this.frame = frame;
        this.listModel = listModel;
        this.songList = songList;
        this.alreadyEnabled = false;
        this.playlist = playlist;
    }

    // MODIFIES: listModel, frame.textField, songList
    // EFFECTS: if the text field is not empty and the song name is not already in the list,
    //          adds a new song to the playlist and resets the text field and updates the state of the buttons
    //          otherwise, request focus in the text field
    @Override
    public void actionPerformed(ActionEvent e) {

        String title = frame.getTextInput();
        if (title.equals("") || alreadyInList(title)) {
            Toolkit.getDefaultToolkit().beep();
            frame.requestFocusInWindowOnTextBox();
            frame.selectAllText();
            return;
        }

        Song newSong = new Song(title, "artist", 0);
        playlist.addSong(newSong);

        int index = songList.getSelectedIndex();
        if (index == -1) {
            index = 0;
        } else {
            index++;
        }

        listModel.insertElementAt(title, index);

        frame.resetText();
        frame.updateButtonStates();
        songList.setSelectedIndex(index);
        songList.ensureIndexIsVisible(index);
    }

    // EFFECTS: returns true if the listModel contains the given name
    protected boolean alreadyInList(String name) {
        return listModel.contains(name);
    }

    // MODIFIES: button
    // EFFECTS: enables the add song button if it is not already enabled when text is added into the text field
    @Override
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    // MODIFIES: button
    // EFFECTS: disables the add song button if the text field is empty
    @Override
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    // MODIFIES: button
    // EFFECTS: enables the add song button if the text field is not empty after a change occurs
    @Override
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    // MODIFIES: button
    // EFFECTS: Enables the add song button if it is not already enabled
    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    // MODIFIES: button, alreadyEnabled
    // EFFECTS: disables the add song button and sets alreadyEnabled to false if the text field is empty.
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



