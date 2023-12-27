package main;

import app.CommandRunner;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.user.Artist;
import app.user.User;
import app.user.UserAbstract;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ArtistStatistics implements Statistics{
    private Map<String, Integer> topAlbums;
    private Map<String, Integer> topSongs;
    private Map <String, Integer> topFans;


    public ArtistStatistics() {
        topAlbums = new LinkedHashMap<>();
        topSongs = new LinkedHashMap<>();
        topFans = new LinkedHashMap<>();

    }

    public void setTopAlbums(String album) {
        if (topAlbums.containsKey(album)) {
            topAlbums.put(album, topAlbums.get(album) + 1);
        } else {
            topAlbums.put(album, 1);
        }
    }

    public void setTopSongs(String song) {
        if (topSongs.containsKey(song)) {
            topSongs.put(song, topSongs.get(song) + 1);
        } else {
            topSongs.put(song, 1);
        }
    }

    public void setTopFans(String user) {
        if (topFans.containsKey(user)) {
            topFans.put(user, topFans.get(user) + 1);
        } else {
            topFans.put(user, 1);
        }
    }

    @Override
    public void updateStatistics() {
        topAlbums = getTop5AlbumsByListens();
        topSongs = getTop5SongsByListens();
        topFans = getTop5FansByListens();
    }


    public Map<String, Integer> getTop5FansByListens() {
        Map<String, Integer> top5Fans = new LinkedHashMap<>();

        // Sortează albumele după numărul de ascultări și le limitează la primele 5
        topFans.entrySet().stream()
                .sorted((entry1, entry2) -> {
                    // Sortează descrescător după numărul de ascultări
                    int compare = entry2.getValue().compareTo(entry1.getValue());
                    if (compare != 0) {
                        return compare;
                    }
                    // În cazul în care numărul de ascultări este același, sortează alfabetic
                    return entry1.getKey().compareTo(entry2.getKey());
                })
                .limit(5)
                .forEachOrdered(entry -> top5Fans.put(entry.getKey(), entry.getValue()));

        return top5Fans;
    }

    public Map<String, Integer> getTop5SongsByListens() {
        Map<String, Integer> top5Songs = new LinkedHashMap<>();

        // Sortează cântecele după numărul de ascultări și le limitează la primele 5
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
                .limit(5)
                .forEachOrdered(entry -> top5Songs.put(entry.getKey(), entry.getValue()));

        return top5Songs;
    }

    public Map<String, Integer> getTop5AlbumsByListens() {
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
                .limit(5)
                .forEachOrdered(entry -> top5Albums.put(entry.getKey(), entry.getValue()));

        return top5Albums;
    }

    public List<String> extractTopFans() {
        List<String> fansList = new ArrayList<>(topFans.keySet());
        return fansList;
    }

}
