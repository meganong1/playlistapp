package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// represents the document listener for the text field
public class TextFieldDocumentListener implements DocumentListener {
    JTextField textField;
    JButton addSongButton;
    JButton editSongButton;
    JList songList;
    PlaylistAppFrame frame;

    // EFFECTS: constructs a TextFieldDocumentListener with its text field,
    //          add song button, edit song button, playlist, and playlist app frame
    public TextFieldDocumentListener(JTextField textField, JButton addSongButton, JButton editSongButton,
                                     JList songList, PlaylistAppFrame frame) {
        this.textField = textField;
        this.addSongButton = addSongButton;
        this.editSongButton = editSongButton;
        this.songList = songList;
        this.frame = frame;
    }

    // MODIFIES: frame
    // EFFECTS: updates all buttons on frame when text is added into the text field
    @Override
    public void insertUpdate(DocumentEvent e) {
        frame.updateButtonStates();
    }

    // MODIFIES: frame
    // EFFECTS: updates all buttons on frame when text is deleted from the text field
    @Override
    public void removeUpdate(DocumentEvent e) {
        frame.updateButtonStates();
    }

    // MODIFIES: frame
    // EFFECTS: updates all buttons on frame when text in the text field has changed
    @Override
    public void changedUpdate(DocumentEvent e) {
        frame.updateButtonStates();
    }
}
