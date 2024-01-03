package app.user;

import app.Admin;
import app.CommandRunner;
import app.audio.Collections.*;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pages.ArtistPage;
import app.pages.HomePage;
import app.pages.LikedContentPage;
import app.pages.Page;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import fileio.input.CommandInput;
import lombok.Getter;
import lombok.Setter;
import main.*;

import java.util.*;

/**
 * The type User.
 */
public final class User extends UserAbstract {
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    @Getter
    private final Player player;
    @Getter
    private boolean status;
    private final SearchBar searchBar;
    private boolean lastSearched;
    @Getter
    @Setter
    private Page currentPage;
    @Getter
    @Setter
    private HomePage homePage;
    @Getter
    @Setter
    private LikedContentPage likedContentPage;
    @Getter @Setter
    private Statistics statistics;
    @Getter
    private ListenHistory listenHistory;
    @Getter @Setter
    private Map<Album, Integer> lastWrappedAlbum;
    @Getter @Setter
    private Map<Podcast, Integer> lastWrappedPodcast;
    @Getter
    private List<Notifications> notifications;
    @Getter
    private Map<String, Integer> merch;
    @Getter @Setter
    private boolean premium;
    @Getter
    private List<Song> recommendedSongs;
    @Getter
    private List<Playlist> recommendedPlaylists;
    @Getter @Setter
    private String recommedationPlaylistUpdated;
    @Getter
    private LinkedList<Page> previousPages;
    @Getter @Setter
    private LinkedList<Page> nextPages;

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public User(final String username, final int age, final String city) {
        super(username, age, city);
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        status = true;

        homePage = new HomePage(this);
        currentPage = homePage;
        likedContentPage = new LikedContentPage(this);


        listenHistory = new ListenHistory();
        statistics = new UserStatistics();
        lastWrappedAlbum = new HashMap<>();
        notifications = new ArrayList<>();
        merch = new HashMap<>();
        premium = false;
        recommendedSongs = new ArrayList<>();
        recommendedPlaylists = new ArrayList<>();
        recommedationPlaylistUpdated = "";

        previousPages = new LinkedList<>();
        nextPages = new LinkedList<>();
    }

    @Override
    public String userType() {
        return "user";
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type) {

        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();

        if (type.equals("artist") || type.equals("host")) {
            List<ContentCreator> contentCreatorsEntries =
                    searchBar.searchContentCreator(filters, type);

            for (ContentCreator contentCreator : contentCreatorsEntries) {
                results.add(contentCreator.getUsername());
            }
        } else {
            List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

            for (LibraryEntry libraryEntry : libraryEntries) {
                results.add(libraryEntry.getName());
            }
        }
        return results;
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        if (searchBar.getLastSearchType().equals("artist")
                || searchBar.getLastSearchType().equals("host")) {
            ContentCreator selected = searchBar.selectContentCreator(itemNumber);

            if (selected == null) {
                return "The selected ID is too high.";
            }

            currentPage = selected.getPage();
            return "Successfully selected %s's page.".formatted(selected.getUsername());
        } else {
            LibraryEntry selected = searchBar.select(itemNumber);

            if (selected == null) {
                return "The selected ID is too high.";
            }

            return "Successfully selected %s.".formatted(selected.getName());
        }
    }

    /**
     * Load string.
     *
     * @return the string
     */
    public String load(Integer timestamp) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("song")
                && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        UserStatistics userStatistics = (UserStatistics) statistics;
        userStatistics.updateLoadStatistics(this, searchBar.getLastSelected(),
                searchBar.getLastSearchType(), timestamp);

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> {
                repeatStatus = "no repeat";
            }
            case REPEAT_ONCE -> {
                repeatStatus = "repeat once";
            }
            case REPEAT_ALL -> {
                repeatStatus = "repeat all";
            }
            case REPEAT_INFINITE -> {
                repeatStatus = "repeat infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                repeatStatus = "repeat current song";
            }
            default -> {
                repeatStatus = "";
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist")
                && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")
                && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, getUsername(), timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        if (!status) {
            return "%s is offline.".formatted(getUsername());
        }

        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(getUsername())) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Gets player stats.
     *
     * @return the player stats
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Show preferred songs array list.
     *
     * @return the array list
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Gets preferred genre.
     *
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * Switch status.
     */
    public void switchStatus() {
        status = !status;
    }

    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(final int time) {
        if (!status) {
            return;
        }

        player.simulatePlayer(time);
    }

    public Statistics wrapStatistics(CommandInput command) {
        // pentru fiecare album ascutat, adaugam in statistici
        // doar melodiile care au fost deja ascutate

        UserStatistics statistics1 = (UserStatistics) statistics;

        for (Album album : listenHistory.getListenAlbums().keySet()) {
            Artist artist = CommandRunner.getAdmin().getArtist(album.getOwner());
            int loadTime = listenHistory.getListenAlbums().get(album);
            for (Song song : album.getSongs()) {
                if (loadTime <= command.getTimestamp()) {
                    statistics1.setTopArtists(song.getArtist());
                    Admin.addToGeneralStatistics(song.getArtist());
                    statistics1.setTopGenres(song.getGenre());
                    statistics1.setTopSongs(song.getName());
                    statistics1.setTopAlbums(song.getAlbum());
                    loadTime += song.getDuration();

                    // add to artist statistics
                    ArtistStatistics artistStatistics = (ArtistStatistics) artist.getStatistics();
                    artistStatistics.setTopSongs(song.getName());
                    artistStatistics.setTopAlbums(song.getAlbum());
                    artistStatistics.setTopFans(getUsername());
                } else {
                    if (command.getCommand().equals("wrapped")) {
                        lastWrappedAlbum.put(album, command.getTimestamp());
                    }
                    break;
                }
            }
        }
        for (Podcast podcast : listenHistory.getListenPodcasts().keySet()) {
            Host host = CommandRunner.getAdmin().getHost(podcast.getOwner());
            int loadTime = listenHistory.getListenPodcasts().get(podcast);
            for (Episode episode : podcast.getEpisodes()) {
                if (loadTime <= command.getTimestamp()) {
                    statistics1.setTopEpisodes(episode.getName());
                    loadTime += episode.getDuration();

                    if (host != null) {
                        HostStatistics hostStatistics = (HostStatistics) host.getStatistics();
                        hostStatistics.setTopEpisodes(episode.getName());
                        hostStatistics.setTopFans(getUsername());
                    }

                } else {
                    if (command.getCommand().equals("wrapped")) {
                        lastWrappedPodcast.put(podcast, command.getTimestamp());
                    }
                }
            }
        }
        return statistics1;
    }

    public void clearHistory() {
        listenHistory = new ListenHistory();
    }

    public void clearLastWrapped() {
        lastWrappedAlbum = new HashMap<>();
    }

    public String subscribe(CommandInput command) {
        if (currentPage.getOwner().userType().equals("artist") ||
                currentPage.getOwner().userType().equals("host")) {
            ContentCreator contentCreator = (ContentCreator) currentPage.getOwner();

            if (contentCreator.getSubscribers().contains(this)) {
                // se dezaboneaza
                contentCreator.getSubscribers().remove(this);
                return contentCreator.unSubscribeMessage(this);
            }

            contentCreator.getSubscribers().add(this);
            return contentCreator.subscribeMessage(this);
        }
        return "To subscribe you need to be on the page of an artist or host.";
    }

    public void addNotification(String name, String description) {
        notifications.add(new Notifications(name, description));
    }

    public void clearNotifications() {
        notifications = new ArrayList<>();
    }

    public String buyMerch(CommandInput command) {
        if (!currentPage.getOwner().userType().equals("artist")) {
            return "Cannot buy merch from this page.";
        }
        Artist artist = (Artist) currentPage.getOwner();
        if (!artist.containsMerch(command.getName())) {
            return "The merch %s doesn't exist.".formatted(command.getName());
        }

        // adauga pentru artist merchRevenue
        Admin.addToGeneralStatistics(artist.getUsername());
        artist.addMerchRevenue(artist.getMerchPrice(command.getName()));
        artist.addTotalRevenue(artist.getMerchPrice(command.getName()));

        merch.put(command.getName(), command.getTimestamp());
        return "%s has added new merch successfully.".formatted(getUsername());

    }

    public List<String> seeMerch(CommandInput command) {
        List<String> results = new ArrayList<>();
        // adauga toate merchurile in ordine crescatoare dupa timestamp
        merch.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> results.add(x.getKey()));

        return results;
    }

    public String buyPremium(CommandInput command) {
        if (premium) {
            return "%s is already a premium user.".formatted(getUsername());
        }

        setPremium(true);
        return "%s bought the subscription successfully.".formatted(getUsername());
    }

    public String cancelPremium(CommandInput command) {
        if (!premium) {
            return "%s is not a premium user.".formatted(getUsername());
        }

        setPremium(false);
        return "%s cancelled the subscription successfully.".formatted(getUsername());
    }

    public String adBreak(CommandInput command) {

        if (player.getCurrentAudioFile() == null) {
            return "%s is not playing any music.".formatted(getUsername());
        }

        return "Ad inserted successfully.";
    }

    public String updateRecommendation(List<Song> songs, CommandInput command) {

        if (command.getRecommendationType().equals("random_playlist")) {
            recommedationPlaylistUpdated = "%s's recommendations".formatted(getUsername());
            //return "No new recommendations were found";
        }

        if (player.getSource() != null && player.getType().equals("song") ) {
            Song song = (Song) player.getCurrentAudioFile();

            if (command.getRecommendationType().equals("fans_playlist")) {
                recommedationPlaylistUpdated = "%s Fan Club recommendations".formatted(song.getArtist());
                //return "No new recommendations were found";
            }

            if (command.getRecommendationType().equals("random_song")) {
                Integer passedTime = song.getDuration() - player.getRemainedDuration();
                if (passedTime >= 30) {
                    //Se cauta melodiile din genre-ul melodiei care canta acum.
                    List<Song> songsByGenre = new ArrayList<>();
                    for (Song song1 : songs) {
                        if (song1.getGenre().equals(song.getGenre())) {
                            songsByGenre.add(song1);
                        }
                    }

                    Random random = new Random(passedTime);
                    int index = random.nextInt(songsByGenre.size());
                    Song generatedSong = songsByGenre.get(index);
                    recommendedSongs.add(generatedSong);
                    getHomePage().getRecommendedSongs().add(generatedSong);

                } else {
                    return "No new recommendations were found";
                }
            }

        }


        return "The recommendations for user %s have been updated successfully."
                .formatted(getUsername());
    }

    public Page getArtistPage() {
        Song song = (Song) player.getSource().getAudioFile();
        Artist artist = CommandRunner.getAdmin().getArtist(song.getArtist());
        return artist.getPage();
    }

    public Page getHostPage() {
        Episode episode = (Episode) player.getSource().getAudioFile();
        Host host = CommandRunner.getAdmin().getHost(episode.getHost());
        return host.getPage();
    }

    public String previousPage(CommandInput command) {
        if (previousPages.isEmpty()) {
            return "There are no pages left to go back.";
        }

        // update la pagina curenta si la forward pages and previous pages
        Page currentPage = previousPages.removeFirst();
        nextPages.addFirst(this.currentPage);
        this.currentPage = currentPage;

        return "The user %s has navigated successfully to the previous page.".formatted(getUsername());
    }

    public String nextPage(CommandInput command) {
if (nextPages.isEmpty()) {
            return "There are no pages left to go forward.";
        }

        // update la pagina curenta si la forward pages and previous pages
        Page currentPage = nextPages.removeFirst();
        previousPages.addFirst(this.currentPage);
        this.currentPage = currentPage;

        return "The user %s has navigated successfully to the next page.".formatted(getUsername());
    }

    public String loadRecommendations(CommandInput command) {
    if (recommendedSongs.isEmpty()) {
            return "No recommendations available.";
        }

        player.setSource(recommendedSongs.get(0), "song");
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }
}
