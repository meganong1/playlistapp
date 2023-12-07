package ui;

import model.Playlist;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// represents the playlist application's main window frame.
public class PlaylistAppFrame extends JFrame implements ActionListener, ListSelectionListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private JPanel buttonPanel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem savePlaylist;
    private JMenuItem loadPlaylist;
    private JButton addSongButton;
    private JButton editSongButton;
    private JButton deleteSongButton;
    private JTextField textField;
    private JList songList;
    private DefaultListModel listModel;
    private ActionListener deleteSongListener;
    private ActionListener addSongListener;
    private ActionListener editSongListener;
    private TextFieldDocumentListener textBoxListener;
    private static final String JSON_FILE = "./data/playlist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Playlist playlist;

    // EFFECTS: Creates the main window frame
    // EFFECTS: creates up the main window frame of the playlist application,
    //          displays list of songs and the home page panel
    public PlaylistAppFrame() {
        super("Playlist Application");
        this.playlist = new Playlist();
        this.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.createMenu();
        JScrollPane listScrollPane = createListScrollPane();
        this.add(listScrollPane, BorderLayout.CENTER);
        this.makeFeatures();
        this.add(createButtonPanel(), BorderLayout.EAST);
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonReader = new JsonReader(JSON_FILE);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // EFFECTS: creates exit window listener for Playlist App Frame
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            // EFFECTS: creates exit window listener for Playlist App Frame
            // Code influenced by https://stackoverflow.com/questions/16372241/run-function-on-jframe-close
            @Override
            public void windowClosing(java.awt.event.WindowEvent we) {
                we.getWindow().dispose();
                playlist.printLog();
                System.exit(0);
            }
        });
        this.setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: adds the add song button, delete song button, edit song button, and text field to frame
    private void makeFeatures() {
        this.createAddSongButton();
        this.createEditSongButton();
        this.createDeleteSongButton();
        this.createTextField();
    }

    // MODIFIES: this
    // EFFECTS: adds menu bar to playlist app frame
    private void createMenu() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        savePlaylist = new JMenuItem("Save Playlist");
        loadPlaylist = new JMenuItem("Load Playlist");

        savePlaylist.addActionListener(this);
        loadPlaylist.addActionListener(this);

        fileMenu.setMnemonic(KeyEvent.VK_F);       // F for file menu
        savePlaylist.setMnemonic(KeyEvent.VK_S);   // S for save playlist
        loadPlaylist.setMnemonic(KeyEvent.VK_L);   // L for load playlist

        fileMenu.add(savePlaylist);
        fileMenu.add(loadPlaylist);

        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
        this.setVisible(true);
    }

    // EFFECTS: creates the add song button
    private void createAddSongButton() {
        this.addSongButton = new JButton("Add Song");
        addSongListener = new AddSongListener(addSongButton, this, listModel, songList, this.playlist);
        addSongButton.setBounds(0, 0, 100, 30);
        addSongButton.setActionCommand("Add song");
        addSongButton.addActionListener(addSongListener);
        addSongButton.setEnabled(false);
    }

    // EFFECTS: creates the delete song button
    private void createDeleteSongButton() {
        this.deleteSongButton = new JButton("Delete Song");
        deleteSongButton.setActionCommand("Delete song");
        deleteSongButton.setBounds(0, 0, 100, 30);
        deleteSongListener = new DeleteSongListener(deleteSongButton, songList, listModel, playlist);
        deleteSongButton.addActionListener(deleteSongListener);
        deleteSongButton.setEnabled(false);

    }

    // EFFECTS: creates the edit song button
    private void createEditSongButton() {
        this.editSongButton = new JButton("Edit Song");
        editSongButton.setActionCommand("Edit song");
        editSongButton.setBounds(0, 0, 100, 30);
        editSongListener = new EditSongListener(editSongButton, this, listModel, songList, playlist);
        editSongButton.addActionListener(editSongListener);
        editSongButton.setEnabled(false);

    }

    // EFFECTS: creates a text field
    private void createTextField() {
        textField = new JTextField();
        textField.setMaximumSize(new Dimension(100, 30));
        textBoxListener = new TextFieldDocumentListener(textField, addSongButton,
                editSongButton, songList, this);
        textField.getDocument().addDocumentListener(textBoxListener);
    }

    // EFFECTS: creates a panel with the add song button, edit song button, delete song button, and text field
    private JPanel createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        addSongButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editSongButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteSongButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(this.addSongButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(this.deleteSongButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(this.editSongButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(this.textField);
        buttonPanel.add(Box.createVerticalGlue());
        return buttonPanel;
    }


    // EFFECTS: creates the listScrollPane where all songs in the playlist are found
    private JScrollPane createListScrollPane() {
        listModel = new DefaultListModel();
        songList = new JList(listModel);
        songList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        songList.setSelectedIndex(0);
        songList.addListSelectionListener(this);
        songList.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(songList);
        return listScrollPane;
    }

    // EFFECTS: loads the list of songs from file. if file is not found, catches IOException
    public void loadPlaylist() {
        try {
            this.playlist = this.jsonReader.read();
            List<Song> songs = this.playlist.getSongList();

            listModel.clear();
            for (Song s : songs) {
                listModel.addElement(s.getTitle());
            }

            JOptionPane.showMessageDialog(this,
                    "Playlist successfully loaded!", "Status", -1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to load playlist...", "Status", -1);
        }

    }


    // EFFECTS: saves the list of songs as a playlist to file. if file is not found, catches FileNotFoundExecption
    public void savePlaylist() {
        playlist.clear();
        for (int i = 0; i < songList.getModel().getSize(); i++) {
            String songTitle = (String) listModel.getElementAt(i);
            playlist.addSong(new Song(songTitle, "Artist", 0));
        }
        try {
            jsonWriter.open();
            jsonWriter.write(playlist);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this,
                    "Playlist saved!", "Status", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to save playlist...", "Status", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // EFFECTS: starts the application with a splash screen
    // code influenced by StackOverflow for the splash screen
    // (https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui)
    public static void main(String[] args) {
        JWindow window = new JWindow();
        String imagePath = "src/resources/playlistsplashscreen.gif";
        ImageIcon splashImage = new ImageIcon(imagePath);
        window.getContentPane().add(
                new JLabel("", splashImage, SwingConstants.CENTER)
        );
        window.setBounds(0, 0, WIDTH, HEIGHT);
        window.setVisible(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
        window.dispose();
        PlaylistAppFrame appFrame = new PlaylistAppFrame();
        appFrame.setVisible(true);
    }


    // EFFECTS: saves and loads playlist based on action performed on menu and button panel
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadPlaylist) {
            this.loadPlaylist();
        } else if (e.getSource() == savePlaylist) {
            this.savePlaylist();
        } else if (e.getSource() == addSongButton) {
            addSongListener.actionPerformed(e);
        } else if (e.getSource() == editSongButton) {
            editSongListener.actionPerformed(e);
        } else if (e.getSource() == deleteSongButton) {
            deleteSongListener.actionPerformed(e);
        }
    }

    // MODIFIES: deleteSongButton, editSongButton
    // EFFECTS: Enables the delete song button if a song is selected and enables the edit song button
    //          if the text field has text and a song is selected
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            boolean isSelected = songList.getSelectedIndex() >= 0;
            deleteSongButton.setEnabled(isSelected);
            editSongButton.setEnabled(isSelected && !getTextInput().isEmpty());
        }
    }

    // EFFECTS: returns the text found in the text field.
    public String getTextInput() {
        return textField.getText();
    }

    // EFFECTS: selects all text found in text field
    public void selectAllText() {
        textField.selectAll();
    }

    // EFFECTS: sets the focus on the text field
    public void requestFocusInWindowOnTextBox() {
        textField.requestFocusInWindow();
    }

    // EFFECTS: changes the text in the textbox to ""
    public void resetText() {
        textField.setText("");
    }

    // MODIFIES: addSongButton, editSongButton
    // EFFECTS: enables the add song button if the text field is not empty and enables the edit
    //          song button if a song is selected and the text field has text
    public void updateButtonStates() {
        boolean textNotEmpty = !textField.getText().isEmpty();
        boolean isItemSelected = songList.getSelectedIndex() != -1;
        addSongButton.setEnabled(textNotEmpty);
        editSongButton.setEnabled(isItemSelected && textNotEmpty);
    }


}

