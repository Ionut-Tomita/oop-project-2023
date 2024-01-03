package main;

import app.audio.Collections.Album;
import app.audio.Collections.Podcast;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ListenHistory {
    private Map<Album, Integer> listenAlbums = new HashMap<>();
    private Map<Podcast, Integer> listenPodcasts = new HashMap<>();

    public void addAlbumToListenHistory(Album album, Integer loadTime) {
        listenAlbums.put(album, loadTime);
    }

    public void addPodcastToListenHistory(Podcast podcast, Integer loadTime) {
        listenPodcasts.put(podcast, loadTime);
    }

}
