package app.statistics;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public final class ArtistStatistics implements Statistics {
    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topSongs;
    private Map<String, Integer> topFans;

    public static final int MAX_SIZE = 5;


    public ArtistStatistics() {
        topAlbums = new LinkedHashMap<>();
        topSongs = new LinkedHashMap<>();
        topFans = new LinkedHashMap<>();

    }

    /**
     * Metodele de tip setTopX adaugă în map-ul corespunzător numele albumului, cântecului sau
     * utilizatorului și numărul de ascultări.
     * Dacă albumul, cântecul sau utilizatorul există deja în map, se adaugă numărul de ascultări
     * la numărul de ascultări existent.
     */

    /**
     * adaugă în map-ul topAlbums numele albumului și numărul de ascultări
     * @param album
     */
    public void setTopAlbums(final String album) {
        if (topAlbums.containsKey(album)) {
            topAlbums.put(album, topAlbums.get(album) + 1);
        } else {
            topAlbums.put(album, 1);
        }
    }

    /**
     * adaugă în map-ul topSongs numele cântecului și numărul de ascultări
     * @param song
     */
    public void setTopSongs(final String song) {
        if (topSongs.containsKey(song)) {
            topSongs.put(song, topSongs.get(song) + 1);
        } else {
            topSongs.put(song, 1);
        }
    }

    /**
     * adaugă în map-ul topFans numele utilizatorului și numărul de ascultări
     * @param user
     */
    public void setTopFans(final String user) {
        if (topFans.containsKey(user)) {
            topFans.put(user, topFans.get(user) + 1);
        } else {
            topFans.put(user, 1);
        }
    }

    /**
     * Metoda updateStatistics actualizează statisticile.
     * Se sortează map-urile după numărul de ascultări și se limitează la primele 5 elemente.
     */
    @Override
    public void updateStatistics() {
        topAlbums = getTop5AlbumsByListens();
        topSongs = getTop5SongsByListens();
        topFans = getTop5FansByListens();
    }


    /**
     * getTop5FansByListens
     * @return un map cu primii 5 fani ai artistului curent, sortați după numărul de ascultări
     */
    public Map<String, Integer> getTop5FansByListens() {
        Map<String, Integer> top5Fans = new LinkedHashMap<>();

        topFans.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Fans.put(entry.getKey(), entry.getValue()));

        return top5Fans;
    }

    /**
     * getTop5SongsByListens
     * @return un map cu primele 5 cântece ale artistului curent, sortate după numărul de ascultări
     */
    public Map<String, Integer> getTop5SongsByListens() {
        Map<String, Integer> top5Songs = new LinkedHashMap<>();

        topSongs.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Songs.put(entry.getKey(), entry.getValue()));

        return top5Songs;
    }

    /**
     * getTop5AlbumsByListens
     * @return un map cu primele 5 albume ale artistului curent, sortate după numărul de ascultări
     */
    public Map<String, Integer> getTop5AlbumsByListens() {
        Map<String, Integer> top5Albums = new LinkedHashMap<>();

        topAlbums.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(MAX_SIZE)
                .forEachOrdered(entry -> top5Albums.put(entry.getKey(), entry.getValue()));

        return top5Albums;
    }

}
