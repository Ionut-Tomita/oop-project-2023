package app.statistics;

import app.Admin;
import app.CommandRunner;
import app.audio.Collections.Album;
import app.audio.Collections.Podcast;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.user.artist.Artist;
import app.user.normalUser.User;
import app.user.UserAbstract;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class UserStatistics implements Statistics {
    private Map<String, Integer> topArtists;
    private Map<String, Integer> topGenres;
    private Map<String, Integer> topSongs;
    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topEpisodes;
    public static final int MAX_SIZE = 5;

    public UserStatistics() {
        topArtists = new LinkedHashMap<>();
        topGenres = new LinkedHashMap<>();
        topSongs = new LinkedHashMap<>();
        topAlbums = new LinkedHashMap<>();
        topEpisodes = new LinkedHashMap<>();
    }

    /**
     * Adaugă un artist la topArtists
     * @param artist Numele artistului
     */
    public void setTopArtists(final String artist) {
        if (topArtists.containsKey(artist)) {
            topArtists.put(artist, topArtists.get(artist) + 1);
        } else {
            topArtists.put(artist, 1);
        }
    }

    /**
     * Adaugă un gen la topGenres
     * @param genre Numele genului
     */
    public void setTopGenres(final String genre) {
        if (topGenres.containsKey(genre)) {
            topGenres.put(genre, topGenres.get(genre) + 1);
        } else {
            topGenres.put(genre, 1);
        }
    }

    /**
     * Adaugă o melodie la topSongs
     * @param song Numele melodiei
     */
    public void setTopSongs(final String song) {
        if (topSongs.containsKey(song)) {
            topSongs.put(song, topSongs.get(song) + 1);
        } else {
            topSongs.put(song, 1);
        }
    }

    /**
     * Adaugă un album la topAlbums
     * @param album Numele albumului
     */
    public void setTopAlbums(final String album) {
        if (topAlbums.containsKey(album)) {
            topAlbums.put(album, topAlbums.get(album) + 1);
        } else {
            topAlbums.put(album, 1);
        }
    }

    /**
     * Adaugă un episod la topEpisodes
     * @param episode Numele episodului
     */
    public void setTopEpisodes(final String episode) {
        if (topEpisodes.containsKey(episode)) {
            topEpisodes.put(episode, topEpisodes.get(episode) + 1);
        } else {
            topEpisodes.put(episode, 1);
        }
    }


    /**
     * Actualizează statisticile dupa loadTime
     * @param user Utilizatorul
     * @param entry Melodia, albumul sau podcastul ascultat
     * @param type Tipul melodie, album sau podcast
     * @param loadTime
     */
    public void updateLoadStatistics(final UserAbstract user, final LibraryEntry entry,
                                     final String type, final Integer loadTime) {
        User user1 = (User) user;
        UserStatistics statistics = (UserStatistics) user1.getStatistics();

        if (type.equals("song")) {
            Song song = (Song) entry;
            statistics.setTopArtists(song.getArtist());
            Admin.addToGeneralStatistics(song.getArtist());
            statistics.setTopGenres(song.getGenre());
            statistics.setTopSongs(song.getName());
            statistics.setTopAlbums(song.getAlbum());

            // add to artist statistics
            Artist artist = CommandRunner.getAdmin().getArtist(song.getArtist());

            if (artist != null) {
                ArtistStatistics artistStatistics = (ArtistStatistics) artist.getStatistics();
                artistStatistics.setTopSongs(song.getName());
                artistStatistics.setTopAlbums(song.getAlbum());
                artistStatistics.setTopFans(user1.getUsername());
            }

        } else if (type.equals("album")) {
            Album album = (Album) entry;
            user1.getListenHistory().addAlbumToListenHistory(album, loadTime);
        } else if (type.equals("podcast")) {
            Podcast podcast = (Podcast) entry;
            user1.getListenHistory().addPodcastToListenHistory(podcast, loadTime);
        }
    }

    @Override
    public void updateStatistics() {
        topArtists = getTop5ArtistsByListenCount();
        topGenres = getTop5GenresByListenCount();
        topSongs = getTop5SongsByListenCount();
        topAlbums = getTop5AlbumsByListenCount();
        topEpisodes = getTop5EpisodesByListenCount();
    }

    /**
     * Returnează top 5 episoade după numărul de ascultări
     * @return Map cu top 5 episoade
     */
    private Map<String, Integer> getTop5EpisodesByListenCount() {
        Map<String, Integer> top5Episodes = new LinkedHashMap<>();

        // Sortează episoadele după numărul de ascultări și le limitează la primele 5
        topEpisodes.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    // Sortează descrescător după numărul de ascultări
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    // În cazul în care numărul de ascultări este același, sortează alfabetic
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Episodes.put(entry.getKey(), entry.getValue()));

        return top5Episodes;
    }


    /**
     * Returnează top 5 genuri după numărul de ascultări
     * @return Map cu top 5 genuri
     */
    private Map<String, Integer> getTop5GenresByListenCount() {
        Map<String, Integer> top5Genres = new LinkedHashMap<>();

        // Sortează genurile după numărul de ascultări și le limitează la primele 5
        topGenres.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    // Sortează descrescător după numărul de ascultări
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    // În cazul în care numărul de ascultări este același, sortează alfabetic
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Genres.put(entry.getKey(), entry.getValue()));

        return top5Genres;
    }

    /**
     * Returnează top 5 albume după numărul de ascultări
     * @return Map cu top 5 albume
     */
    private Map<String, Integer> getTop5AlbumsByListenCount() {
        Map<String, Integer> top5Albums = new LinkedHashMap<>();

        // Sortează albumele după numărul de ascultări și le limitează la primele 5
        topAlbums.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    // Sortează descrescător după numărul de ascultări
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    // În cazul în care numărul de ascultări este același, sortează alfabetic
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Albums.put(entry.getKey(), entry.getValue()));

        return top5Albums;
    }

    /**
     * Returnează top 5 artiști după numărul de ascultări
     * @return Map cu top 5 artiști
     */
    private Map<String, Integer> getTop5ArtistsByListenCount() {
        Map<String, Integer> top5Artists = new LinkedHashMap<>();

        // Sortează artiștii după numărul de ascultări și îi limitează la primii 5
        topArtists.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    // Sortează descrescător după numărul de ascultări
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    // În cazul în care numărul de ascultări este același, sortează alfabetic
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Artists.put(entry.getKey(), entry.getValue()));

        return top5Artists;
    }

    /**
     * Returnează top 5 melodii după numărul de ascultări
     * @return Map cu top 5 melodii
     */
    private Map<String, Integer> getTop5SongsByListenCount() {
        Map<String, Integer> top5Songs = new LinkedHashMap<>();

        // Sortează melodiile după numărul de ascultări și le limitează la primele 5
        topSongs.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    // Sortează descrescător după numărul de ascultări
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    // În cazul în care numărul de ascultări este același, sortează alfabetic
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Songs.put(entry.getKey(), entry.getValue()));

        return top5Songs;
    }

    /**
     * Verifică dacă statisticile utilizatorului sunt goale
     * @param statistics Statisticile utilizatorului
     * @return True dacă statisticile sunt goale, false în caz contrar
     */
    public boolean isEmpty(final UserStatistics statistics) {
        return statistics.getTopArtists().isEmpty()
                && statistics.getTopGenres().isEmpty()
                && statistics.getTopSongs().isEmpty()
                && statistics.getTopAlbums().isEmpty()
                && statistics.getTopEpisodes().isEmpty();
    }

}
