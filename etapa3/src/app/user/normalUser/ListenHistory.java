package app.user.normalUser;

import app.audio.Collections.Album;
import app.audio.Collections.Podcast;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public final class ListenHistory {
    private Map<Album, Integer> listenAlbums = new HashMap<>();
    private Map<Podcast, Integer> listenPodcasts = new HashMap<>();

    /**
     * Add album to listen history.
     *
     * @param album    album
     * @param loadTime load time
     */
    public void addAlbumToListenHistory(final Album album, final Integer loadTime) {
        listenAlbums.put(album, loadTime);
    }

    /**
     * Add podcast to listen history.
     *
     * @param podcast  podcast
     * @param loadTime load time
     */
    public void addPodcastToListenHistory(final Podcast podcast, final Integer loadTime) {
        listenPodcasts.put(podcast, loadTime);
    }

}
