# Playlist Maker
## Proposal

### What does this application do?


This playlist maker will allow users to save their favourite songs, making it easier to find them later and share with others. I am interested in this project because I enjoy listening to music and find that I want to share my interest with others, find people who have similar music tastes, or convince people to listen to music more.


## User Stories
* As a user, I want to **add** a song to my playlist.
* As a user, I want to be able to **view the list of songs** on my playlist.
* As a user, I want to be able to **edit the title** of a song on my playlist.
* As a user, I want to be able to **see the total length** of all songs on my playlist.
* As a user, I want to be able to **delete** a song from my playlist.
* As a user, I want to have the option to save my playlist from file.
* As a user, I want to have the option to load my playlist from file. 

# Instructions for Grader
- You can generate the first required action related to the user story "adding multiple songs to my playlist" by typing in the title of the song on the text field then pressing the add song button
- You can generate the second required action related to the user story "deleting multiple songs from my playlist" by selecting a song on the list panel and pressing the delete song button
- You can generate the third action related to the user story "editing the title of a song on my playlist" by selecting a song on the list panel, typing in the new title in the text field, and pressing the edit song button
- You can locate my visual component by looking at the splash screen that appears in the first 5 seconds after starting the program
- You can save the state of my application by locating the file menu on the top left corner and pressing ‘save playlist’
- You can reload the state of my application by locating the file menu on the top left corner and pressing ‘load playlist’

## Phase 4: Task 2
Wed Nov 29 23:02:22 PST 2023
Added song to playlist: Redbone

Wed Nov 29 23:02:27 PST 2023
Added song to playlist: It Will Rain

Wed Nov 29 23:02:36 PST 2023
Added song to playlist: I Want You Back

Wed Nov 29 23:02:36 PST 2023
Removed song from playlist: I Want You Back

Wed Nov 29 23:02:46 PST 2023
Song on playlist is renamed to: 3005

## Phase 4: Task 3
Even though my classes seem to have a specific focus meaning my code has high cohesion, there is a significant amount coupling in my project. The PlaylistAppFrame has many direct associations with many other classes which can indicate semantic coupling. Semantic coupling can be problematic because making a change in PlaylistAppFrame or its classes with direct relationships can create many issues in other classes but this can be fixed by applying the Observer Pattern in my code. I also noticed that I implemented bi-directional relationships just to pass the Playlist data so I would implement the Singleton Pattern to simplify access to the Playlist data. There is also some common functionality across each listener class so I would create an AbstractListener class with common listener methods to reduce redundant code.




