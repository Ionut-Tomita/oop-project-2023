package app.user.artist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Files.Song;
import app.pages.ArtistPage;
import app.user.ContentCreator;
import app.user.normalUser.User;
import fileio.input.CommandInput;
import lombok.Getter;
import app.statistics.ArtistStatistics;
import app.user.normalUser.ListenHistory;
import app.statistics.Statistics;

/**
 * The type Artist.
 */
public final class Artist extends ContentCreator {
    private ArrayList<Album> albums;
    private ArrayList<Merchandise> merch;
    private ArrayList<Event> events;
    @Getter
    private Statistics statistics;
    @Getter
    private double totalRevenue;
    @Getter
    private double merchRevenue;
    @Getter
    private double songRevenue;
    @Getter
    private String mostProfitableSong = "N/A";

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
        totalRevenue = 0;
        merchRevenue = 0;
        songRevenue = 0;

    }

    public Artist(final String artistName) {
        super(artistName, 0, "");
        merchRevenue = 0;
        totalRevenue = 0;
        songRevenue = 0;
    }


    /**
     * add to total revenue
     * @param revenue
     */
    public void addTotalRevenue(final double revenue) {
        totalRevenue += revenue;
    }

    /**
     * add merch revenue
     * @param revenue
     */
    public void addMerchRevenue(final double revenue) {
        merchRevenue += revenue;
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

    @Override
    public Statistics wrap(final CommandInput commandInput, final List<User> users) {
        wrapStatistics(commandInput, users);
        ArtistOutput artistOutput = new ArtistOutput((ArtistStatistics) statistics);
        if (artistOutput.isEmpty(artistOutput)) {
            return null;
        }
        return artistOutput;
    }

    @Override
    public String noDataMessage() {
        return "No data to show for artist %s.".formatted(this.getUsername());
    }


    /**
     * verify if contains merch
     * @param merchName
     * @return boolean
     */
    public boolean containsMerch(final String merchName) {
        return merch.stream().anyMatch(merchandise -> merchandise.getName().equals(merchName));
    }

    /**
     * wrap statistics
     * @param command
     * @param users
     */
    public void wrapStatistics(final CommandInput command, final List<User> users) {
        ArtistStatistics artistStatistics = (ArtistStatistics) statistics;

        users.forEach(user -> {
            ListenHistory listenHistory = user.getListenHistory();
            Map<Album, Integer> lastWrapped = user.getLastWrappedAlbum();

            listenHistory.getListenAlbums().forEach((album, loadTime) -> {
                if (lastWrapped.containsKey(album)) {
                    int passedTime = loadTime;
                    for (Song song : album.getSongs()) {
                        if (passedTime >= lastWrapped.get(album) && passedTime + song.getDuration()
                                <= command.getTimestamp()) {
                            artistStatistics.setTopAlbums(album.getName());
                            artistStatistics.setTopSongs(song.getName());
                            artistStatistics.setTopFans(user.getUsername());
                        }
                        passedTime += song.getDuration();
                    }
                }
            });
        });
    }


    /**
     * subscribe message
     * @param user
     * @return message
     */
    @Override
    public String subscribeMessage(final User user) {
        return "%s subscribed to %s successfully."
                .formatted(user.getUsername(), this.getUsername());
    }

    /**
     * Unsubscribe message string.
     *
     * @param user the user
     * @return the string
     */
    @Override
    public String unSubscribeMessage(final User user) {
        return "%s unsubscribed from %s successfully."
                .formatted(user.getUsername(), this.getUsername());
    }

    /**
     * Send new album notification.
     */
    public void sendNewAlbumNotification() {
        getSubscribers().forEach(user ->
                user.addNotification("New Album", "New Album from %s."
                        .formatted(getUsername()))
        );
    }

    /**
     * Send new merchandise notification.
     */
    public void sendNewMerchandiseNotification() {
        getSubscribers().forEach(user ->
                user.addNotification("New Merchandise", "New Merchandise from %s."
                        .formatted(getUsername()))
        );
    }

    /**
     * Send new event notification.
     */
    public void sendNewEventNotification() {
        getSubscribers().forEach(user ->
                user.addNotification("New Event", "New Event from %s.".formatted(getUsername()))
        );
    }

    /**
     * Gets merch price.
     * @param name
     * @return price
     */
    public Integer getMerchPrice(final String name) {
        return merch.stream()
                .filter(merchandise -> merchandise.getName().equals(name))
                .map(Merchandise::getPrice)
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets total revenue.
     * @return the total revenue
     */
    public double getTotalRevenue() {
        return totalRevenue;
    }
}
