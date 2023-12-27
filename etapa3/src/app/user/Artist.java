package app.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.CommandRunner;
import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Files.Song;
import app.pages.ArtistPage;
import fileio.input.CommandInput;
import lombok.Getter;
import main.ArtistStatistics;
import main.ListenHistory;
import main.Statistics;
import main.UserStatistics;

/**
 * The type Artist.
 */
public final class Artist extends ContentCreator {
    private ArrayList<Album> albums;
    private ArrayList<Merchandise> merch;
    private ArrayList<Event> events;
    @Getter
    private Statistics statistics;

    /**
     * Instantiates a new Artist.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        albums = new ArrayList<>();
        merch = new ArrayList<>();
        events = new ArrayList<>();

        super.setPage(new ArtistPage(this));
        statistics = new ArtistStatistics();

    }

    /**
     * Gets albums.
     *
     * @return the albums
     */
    public ArrayList<Album> getAlbums() {
        return albums;
    }

    /**
     * Gets merch.
     *
     * @return the merch
     */
    public ArrayList<Merchandise> getMerch() {
        return merch;
    }

    /**
     * Gets events.
     *
     * @return the events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Gets event.
     *
     * @param eventName the event name
     * @return the event
     */
    public Event getEvent(final String eventName) {
        for (Event event : events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }

        return null;
    }

    /**
     * Gets album.
     *
     * @param albumName the album name
     * @return the album
     */
    public Album getAlbum(final String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }

        return null;
    }

    /**
     * Gets all songs.
     *
     * @return the all songs
     */
    public List<Song> getAllSongs() {
        List<Song> songs = new ArrayList<>();
        albums.forEach(album -> songs.addAll(album.getSongs()));

        return songs;
    }

    /**
     * Show albums array list.
     *
     * @return the array list
     */
    public ArrayList<AlbumOutput> showAlbums() {
        ArrayList<AlbumOutput> albumOutput = new ArrayList<>();
        for (Album album : albums) {
            albumOutput.add(new AlbumOutput(album));
        }

        return albumOutput;
    }

    /**
     * Get user type
     *
     * @return user type string
     */
    public String userType() {
        return "artist";
    }


    public void wrapStatistics(CommandInput command, List<User> users) {
        ArtistStatistics artistStatistics = (ArtistStatistics) statistics;
        for (User user : users) {
            ListenHistory listenHistory = user.getListenHistory();
            Map<Album, Integer> lastWrapped = user.getLastWrappedAlbum();

            for (Album album : listenHistory.getListenAlbums().keySet()) {
                Integer loadTime = listenHistory.getListenAlbums().get(album);

                if (lastWrapped.containsKey(album)) {
                    for (Song song : album.getSongs()) {
                        if (loadTime >= lastWrapped.get(album) &&
                                loadTime + song.getDuration() <= command.getTimestamp()) {
                            artistStatistics.setTopAlbums(album.getName());
                            artistStatistics.setTopSongs(song.getName());
                            artistStatistics.setTopFans(user.getUsername());
                        }
                        loadTime += song.getDuration();
                    }
                }
            }
        }
    }

    @Override
    public String subscribeMessage(User user) {
        return "%s subscribed to %s successfully."
                .formatted(user.getUsername(), this.getUsername());
    }

    @Override
    public String unSubscribeMessage(User user) {
        return "%s unsubscribed from %s successfully."
                .formatted(user.getUsername(), this.getUsername());
    }

    public void sendNewAlbumNotification() {
        for (User user : getSubscribers()) {
            user.addNotification("New Album", "New Album from %s.".formatted(getUsername()));
        }
    }

    public void sendNewMerchandiseNotification() {
        for (User user : getSubscribers()) {
            user.addNotification("New Merchandise", "New Merchandise from %s.".formatted(getUsername()));
        }
    }

    public void sendNewEventNotification() {
        for (User user : getSubscribers()) {
            user.addNotification("New Event", "New Event from %s.".formatted(getUsername()));
        }
    }
}
